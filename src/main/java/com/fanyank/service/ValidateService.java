package com.fanyank.service;

import com.fanyank.entity.User;

/**
 * Created by yanfeng-mac on 2017/3/29.
 */
public class ValidateService {
    public String emailNotExist(String email) {
        UserService userService = new UserService();
        User user = userService.findByEmail(email);

        if(user == null) {
            return "true";
        } else {
            return "false";
        }
    }

    public String usernameNotExist(String username) {
        UserService userService = new UserService();
        User user = userService.findByUsername(username);

        if(user == null) {
            return "true";
        } else {
            return "false";
        }
    }
}
