package com.example.user.service;

import com.example.basic.po.Information;
import com.example.basic.po.User;
import com.example.basic.status.ChangePassword;
import com.example.basic.vo.NewInformationVo;
import com.example.basic.vo.UserAttention;
import com.example.user.dao.UserDao;
import com.example.user.service.interfaces.NewPushInformationService;
import com.example.user.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.basic.status.ChangePassword.SUCCESS;
import static com.example.basic.status.ChangePassword.WRONG_PASSWORD;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final NewPushInformationService newPushInformationService;

    @Autowired
    public UserServiceImpl(UserDao userDao, NewPushInformationService newPushInformationService){
        this.userDao = userDao;
        this.newPushInformationService = newPushInformationService;
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

    /*
    * 获取用户的关注列表以及对应用户的版本号
    * */
    @Override
    public List<UserAttention> getUserAttentions(Long userId) {
        return userDao.getUserAttentions(userId);
    }

    /*
    * 根据用户传来的关注列表与被关注用户的版本号，与最新的版本号比对，如果用户的版本号低于被订阅用户的最新版本号，那么将获取新的数据(文章，回答等)
    *
    * */
    @Override
    public NewInformationVo getAttentionUserNewInformation(List<UserAttention> userAttentions) {
        if(userAttentions.size() > 0){
            List<Integer> newVersionBatch = userDao.getUserVersionBatch(getAttentionUserIds(userAttentions));
            Map<Long,Integer> paramForUpdate = compareVersion(userAttentions,newVersionBatch);
            if(paramForUpdate.size() > 0){
                List<Information> newInformation = newPushInformationService.getNewInformation(paramForUpdate);
                return new NewInformationVo(newVersionBatch,newInformation);
            }
        }
        return null;
    }

    @Override
    public void incrementVersion(Long id) {
        userDao.incrementVersion(id);
    }

    /*
    * 比较版本信息，将有跟新内容的用户Id与更新数统计出来。
    * */
    private Map<Long, Integer> compareVersion(List<UserAttention> userAttentions, List<Integer> newVersionBatch) {
        Map<Long,Integer> res = new HashMap<>();
        for (int i = 0; i < userAttentions.size(); i++) {
            UserAttention userAttention = userAttentions.get(i);
            int oldVersion = userAttention.getVersion();
            int newVersion = newVersionBatch.get(i);
            if(newVersion > oldVersion){
                res.put(userAttention.getBeAttentionUserId(),newVersion - oldVersion);
            }
        }
        return res;
    }

    private List<Long> getAttentionUserIds(List<UserAttention> userAttentions) {
        List<Long> res = new ArrayList<>(userAttentions.size());
        userAttentions.forEach(userAttention -> res.add(userAttention.getBeAttentionUserId()));
        return res;
    }

}
