package com.example.user.service.interfaces;


import com.example.basic.po.User;
import com.example.basic.status.ChangePassword;

public interface UserService {
    boolean mailHasRegistered(String mail);

    User insertUser(User user);

    boolean nameHasRegistered(String userName);

    User queryUserByMail(String mail);

    User queryUserByMailPassword(String mail, String password);

    boolean phoneHasRegistered(String phone);

    void setPortraitFileNameById(Long id, String fileName);

    User getUserById(Long id);

    ChangePassword changePassword(Long userId, String password, String newPassword);

    boolean whetherTheUserIsFollowed(Long beAttentionUserId,Long userId);

}
