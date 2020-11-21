package com.example.user.service;

import com.example.basic.po.User;
import com.example.basic.status.ChangePassword;
import com.example.user.dao.UserDao;
import com.example.user.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.basic.status.ChangePassword.SUCCESS;
import static com.example.basic.status.ChangePassword.WRONG_PASSWORD;

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
        return userDao.getUserIdByPhone(phone) != null;
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

    /*
    * 通过Id查找相应的账号，并校验密码正确性，如果正确，则修改用户密码
    * @userId 用户Id
    * @password 密码
    * @newPassword 新密码
    * @return 修改状态码
    * */
    @Transactional
    @Override
    public ChangePassword changePassword(Long userId, String password, String newPassword) {
        String passwordById = userDao.queryPasswordById(userId);
        if(passwordById.equals(password)){
            userDao.updateUserPassword(userId,newPassword);
            return SUCCESS;
        }else{
            return WRONG_PASSWORD;
        }
    }

    /*
    * 判断用户是否被关注
    * @beAttentionUserId 被关注用户
    * @userId 用户Id
    * @return true--是 false--否
    * */
    @Override
    public boolean whetherTheUserIsFollowed(Long beAttentionUserId, Long userId) {
        return userDao.whetherTheUserIsFollowed(beAttentionUserId,userId) != null;
    }

}
