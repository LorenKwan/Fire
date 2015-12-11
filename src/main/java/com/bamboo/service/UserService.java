package com.bamboo.service;

import com.bamboo.model.User;

/**
 * Created by Bob Guan on 2015/12/11.
 */
public interface UserService {
    public int insertUser(User user);

    public User getUserInfoByName(String username);
}
