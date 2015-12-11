package com.bamboo.service.impl;

import com.bamboo.dao.UserDAO;
import com.bamboo.model.User;
import com.bamboo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Bob Guan on 2015/12/11.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDAO userDAO;

    @Override
    public int insertUser(User user) {
        // TODO Auto-generated method stub
        return userDAO.insertUser(user);
    }

    @Override
    public User getUserInfoByName(String username) {
        // TODO Auto-generated method stub
        return userDAO.getUserInfoByName(username);
    }
}
