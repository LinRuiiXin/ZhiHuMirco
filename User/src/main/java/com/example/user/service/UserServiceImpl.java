package com.example.user.service;

import com.example.user.dao.UserDao;
import com.example.user.po.User;
import com.example.user.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public boolean mailHasRegistered(String mail) {
        Long userIdByEmail = userDao.getUserIdByMail(mail);
        if(userIdByEmail == null){
            return false;
        }else{
            return true;
        }
    }

    @Transactional
    @Override
    public User insertUser(User user) {
        userDao.insertUser(user);
        return user;
    }

    @Override
    public boolean nameHasRegistered(String userName) {
        return userDao.getUserIdByUserName(userName) == null ? false:true;
    }

    @Transactional
    @Override
    public User queryUserByMail(String mail) {
        return userDao.queryUserByMail(mail);
    }

    @Transactional
    @Override
    public User queryUserByMailPassword(String mail, String password) {
        return userDao.queryUserByMailPassword(mail,password);
    }

    @Override
    public boolean phoneHasRegistered(String phone) {
        return userDao.getUserIdByPhone(phone) == null?false:true;
    }

    @Override
    public void setPortraitFileNameById(Long id, String fileName) {
        userDao.setPortraitFileName(id,fileName);
    }

    @Cacheable(cacheNames = "User",key = "#id")
    @Override
    public User getUserById(Long id) {
        User user = userDao.queryUserById(id);
        return user;
    }
}
