package com.example.user.controller;

import com.example.basic.po.User;
import com.example.user.service.interfaces.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("为其他微服务提供用户服务的接口")
@RestController
@RequestMapping("/API")
public class UserRpcController {

    private final UserService userService;

    @Autowired
    public UserRpcController(UserService userService){
        this.userService = userService;
    }

    @ApiOperation("根据用户Id获取User信息")
    @GetMapping("/{id}")
    public User getUserById(@ApiParam("用户Id")@PathVariable Long id){
        return userService.getUserById(id);
    }

}

