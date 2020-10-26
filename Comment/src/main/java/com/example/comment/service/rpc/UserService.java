package com.example.comment.service.rpc;

import com.example.basic.po.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("User")
public interface UserService {

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id")Long id);
}
