package com.example.user.service;

import com.example.user.dao.AttentionDao;
import com.example.user.service.interfaces.AttentionService;
import com.example.user.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/12/1 8:56 下午
 */
@Service
public class AttentionServiceImpl implements AttentionService {

    @Autowired
    private UserService userService;

    private final AttentionDao attentionDao;

    @Autowired
    public AttentionServiceImpl(AttentionDao attentionDao) {
        this.attentionDao = attentionDao;
    }

    @Override
    public boolean userHasAttention(Long targetUserId, Long userId) {
        return attentionDao.userHasAttention(targetUserId,userId) != null;
    }


    @Override
    public void follow(Long targetUserId, Long userId) {
        if(!userHasAttention(targetUserId,userId)){
            attentionDao.follow(targetUserId,userId);
            userService.followUser(targetUserId,userId);
        }
    }

    @Override
    public void unFollow(Long targetUserId, Long userId) {
        if(userHasAttention(targetUserId,userId)){
            attentionDao.unFollow(targetUserId,userId);
            userService.unFollowUser(targetUserId,userId);
        }
    }
}
