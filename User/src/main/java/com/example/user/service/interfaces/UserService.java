package com.example.user.service.interfaces;

import com.example.user.po.User;

public interface UserService {
    public boolean mailHasRegistered(String mail);
    public User insertUser(User user);
    public boolean nameHasRegistered(String userName);
    public User queryUserByMail(String mail);
    public User queryUserByMailPassword(String mail,String password);
    public boolean phoneHasRegistered(String phone);
    public void setPortraitFileNameById(Long id,String fileName);
    public User getUserById(Long id);
}
