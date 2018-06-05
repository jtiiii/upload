package com.funeral.upload.util;

import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 读取Yaml文件的工具类
 *
 * @author FuneralObjects 张峰
 * CreateTime 2018/5/23 3:39 PM
 */
public class YamlUtils {
    /**
     * yaml文件的缓存,减少I/O访问
     */
    private Map<String,Map<String,Object>> yamlPool = new ConcurrentHashMap<>();
    /**
     * 有效时间Map缓存
     */
    private Map<String,Long> expireTime = new ConcurrentHashMap<>();
    /**
     * 有效时时间（1小时），单位为ms
     */
    private Long EXPIRE_PERIOD = 3600000L;

    /**
     * 自身创建一个私有对象单例化
     */
    private static final YamlUtils yamlUtils = new YamlUtils();

    /**
     * 获取一个yaml文件对象
     * @param classpath yaml文件路径
     * @return yaml文件的Map对象集合
     */
    public static Map<String,Object> getYamlMap(String classpath){
        Map<String,Object> result = yamlUtils.getYamlObjInPool( classpath );
        return result == null ? getNewYamlMap(classpath): result;
    }

    /**
     * 获取一个新的yaml文件对象
     * @param classpath yaml文件路径
     * @return yaml文件的Map对象集合
     */
    public static Map<String,Object> getNewYamlMap( String classpath ){
        //使用spring的YamlMapFactoryBean读取yaml文件
        YamlMapFactoryBean factoryBean = new YamlMapFactoryBean();
        factoryBean.setResources( new ClassPathResource( classpath ) );
        Map<String,Object> result = factoryBean.getObject();
        //将该对象
        yamlUtils.addOrRefreshYaml( classpath, result );
        return result;
    }

    /**
     * 将yaml文件对象进行反序列化为指定对象
     * @param clzz 指定类
     * @param yamlObj yaml文件对象
     * @param <T> 指定类
     * @return 指定类对象
     */
    public static <T> T deserializationObjectWithYamlObject(Class<T> clzz,Map<String,Object> yamlObj){
        try {
            T result = clzz.newInstance();
            //若返回的是Map类型集合直接返回当前对象，不进行序列化操作
            if(Map.class.isAssignableFrom(clzz)){
                return (T) yamlObj;
            }
            //遍历所有属性值进行反序列化操作
            for(String key:yamlObj.keySet()){
                //获得属性值
                Object value = yamlObj.get(key);
                //获得需要反序列化的属性对象
                Field field = clzz.getDeclaredField(key);
                //获得属性类型
                Class fieldType =  field.getType();
                //获得setter方法用于填充数据
                Method method = clzz.getMethod(getSetterWithFieldName(key),clzz.getDeclaredField(key).getType());
                if(value instanceof LinkedHashMap){
                    //当是还需要反序列化下一层属性时
                    value = deserializationObjectWithYamlObject(fieldType, (Map<String, Object>) value);
                }else if(value instanceof ArrayList){
                    //当反序列化属性是List集合时候
                    value = deserializationList(field, (List) value);
                }
                //使用setter方法填充数据
                method.invoke(result,value);
            }
            return result;
        } catch (NoSuchFieldException | InvocationTargetException | InstantiationException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得setter方法名
     * @param fieldName 属性名
     * @return setter方法名
     */
    private static String getSetterWithFieldName(String fieldName){
        String first = fieldName.substring(0,1);
        StringBuilder sb = new StringBuilder(fieldName);
        sb.replace(0,1,first.toUpperCase());
        sb.insert(0,"set");
        return sb.toString();
    }

    /**
     * 反序列化属性为集合类的对象
     * @param field 属性对象
     * @param list 将要反序列化的对象
     * @return 反序列化成功的对象
     */
    private static List<?> deserializationList(Field field,List list){
        List<Object> result = new ArrayList<>();
        for(Object value:list){
            if(value instanceof ArrayList){
                value = deserializationList(field, (List) value);
            }else if(value instanceof LinkedHashMap){
                Class<?> tempClass = Map.class;
                //获取泛型类型
                if(field.getGenericType() != null){
                    ParameterizedType type = (ParameterizedType) field.getGenericType();
                    tempClass = (Class<?>) type.getActualTypeArguments()[0];
                }
                //若泛型获取失败，则直接以Map.class，不进行反序列化操作了
                value = deserializationObjectWithYamlObject(tempClass, (Map<String, Object>) value);
            }
            result.add(value);
        }
        return result;
    }

    /**
     * 私有化构造方法
     * 将执行额外一个线程用于执行清理缓存
     */
    private YamlUtils(){
        //启动一个额外线程用于定时清理yaml文件的缓存
        Thread thread = new Thread( () -> scheduledTask());
        thread.start();
    }

    /**
     * 定时任务
     * 用于清理yaml文件缓存
     */
    private void scheduledTask(){
        while (true){
            scanAndClear();
            //暂停半小时再扫描一次
            try {
                Thread.sleep(1800000L);
            } catch (InterruptedException e) {
//                LogManager.getLogger("YamlUtils").error("YamlClearFresh is destroyed!",e);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 清理yaml文件缓存
     */
    private void scanAndClear(){
        Set<String> clears = new HashSet<>();
        for( String path: this.expireTime.keySet() ){
            //判断是否超时
            if( EXPIRE_PERIOD.compareTo( TimeUtils.getNow() - this.expireTime.get( path ) ) > 0){
                continue;
            }
            clears.add(path);
        }
        //清理掉超时未访问yaml文件缓存
        for(String clear:clears){
            this.expireTime.remove(clear);
            this.yamlPool.remove(clear);
        }
    }

    /**
     * 添加并刷新yaml对象文件
     * @param path yaml文件路径
     * @param yamlObj yaml对象
     */
    private void addOrRefreshYaml(String path,Map<String,Object> yamlObj){
        this.yamlPool.put(path,yamlObj);
        this.refreshExpire(path);
    }

    /**
     * 从缓存中获取Yaml文件对象
     * @param path 文件路径
     * @return yaml文件对象
     */
    private Map<String,Object> getYamlObjInPool(String path){
        this.refreshExpire(path);
        return this.yamlPool.get(path);
    }

    /**
     * 刷新yaml文件对象的缓存有效时间
     * @param path yaml文件路径
     */
    private void refreshExpire(String path){
        if(this.expireTime.containsKey(path)){
            this.expireTime.put(path,TimeUtils.getNow());
        }
    }


}
