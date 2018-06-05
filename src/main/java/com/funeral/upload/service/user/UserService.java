package com.funeral.upload.service.user;

import com.funeral.upload.entity.LoginUser;
import com.funeral.upload.entity.LoginUserList;
import com.funeral.upload.util.YamlUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户业务
 *
 * @author FuneralObjects 张峰
 * CreateTime 2018/6/5 2:50 PM
 */
public class UserService {
    /**
     * 所有用户
     */
    private static final Map<String,LoginUser> USERS = new ConcurrentHashMap<>();

    static{
        Map<String,Object> yamlObj = YamlUtils.getYamlMap("users.yaml");
        LoginUserList users = YamlUtils.deserializationObjectWithYamlObject(LoginUserList.class, yamlObj);

        for(LoginUser user: users.getUsers()){
            USERS.put(user.getUsername(),user);
        }
    }

    public static LoginUser findUser(String username){
        return USERS.get(username);
    }

}
