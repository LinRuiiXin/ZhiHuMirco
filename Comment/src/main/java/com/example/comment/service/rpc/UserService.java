package com.example.comment.service.rpc;

import com.example.basic.po.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("User")
@RequestMapping("/API")
public interface UserService {

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id")Long id);

    @GetMapping("/Batch/{ids}")
    public List<User> getUserBatch(@PathVariable("ids") String ids);

}
