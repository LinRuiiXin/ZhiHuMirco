package com.example.user.service.interfaces;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/12/1 8:52 下午
 */
public interface AttentionService {
    boolean userHasAttention(Long targetUserId,Long userId);

    void follow(Long targetUserId,Long userId);

    void unFollow(Long targetUserId,Long userId);
}
