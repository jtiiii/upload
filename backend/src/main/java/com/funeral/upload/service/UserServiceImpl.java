package com.funeral.upload.service;

import com.funeral.upload.entity.LoginUser;
import com.funeral.upload.entity.LoginUserList;
import com.funeral.upload.security.User;
import com.funeral.upload.util.YamlUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户业务
 *
 * @author FuneralObjects
 * CreateTime 2018/6/5 2:50 PM
 */
@Service
public class UserServiceImpl implements UserDetailsService {
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser user = findUser(username);
        if(user == null){
            throw new UsernameNotFoundException("Unable to find eligible user.");
        }
        return new User(user.getUsername(),user.getPassword(),true,true);
    }
}
