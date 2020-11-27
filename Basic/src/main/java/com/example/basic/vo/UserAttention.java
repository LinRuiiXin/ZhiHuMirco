package com.example.basic.vo;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/23 3:33 下午
 */
public class UserAttention {
    private Long userId;
    private Long beAttentionUserId;
    private int version;

    public UserAttention() {
    }

    public UserAttention(Long userId, Long beAttentionUserId, int version) {
        this.userId = userId;
        this.beAttentionUserId = beAttentionUserId;
        this.version = version;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBeAttentionUserId() {
        return beAttentionUserId;
    }

    public void setBeAttentionUserId(Long beAttentionUserId) {
        this.beAttentionUserId = beAttentionUserId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
