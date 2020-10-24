package com.example.user.controller;

import com.example.user.dto.SimpleDto;
import com.example.user.po.User;
import com.example.user.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/User")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }


    @GetMapping("/Login/{mail}/{password}")
    public SimpleDto loginWithPassword(@PathVariable String mail,@PathVariable String password){
        User user = userService.queryUserByMailPassword(mail, password);
        if(user == null){
            return new SimpleDto(false,"账号或密码错误",null);
        }else{
            return new SimpleDto(true,null,user);
        }
    }
}
