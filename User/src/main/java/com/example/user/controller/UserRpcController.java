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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


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

    @GetMapping("/Batch/{ids}")
    public List<User> getUserBatch(@PathVariable String ids){
        String[] split = ids.split("-");
        List<User> users = new ArrayList<>();
        for(String str : split){
            users.add(userService.getUserById(Long.valueOf(str)));
        }
        return users;
    }

    @ApiOperation("查询某个用户是否被另一个用户关注")
    @GetMapping("/IsAttention/{beAttentionUserId}/{userId}")
    public boolean whetherTheUserIsFollowed(@ApiParam("被关注用户Id")@PathVariable Long beAttentionUserId,@ApiParam("用户Id")@PathVariable Long userId){
        return userService.whetherTheUserIsFollowed(beAttentionUserId,userId);
    }
}

