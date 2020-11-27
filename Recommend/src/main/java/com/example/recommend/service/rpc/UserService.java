package com.example.recommend.service.rpc;

import com.example.basic.po.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("User")
@RequestMapping("/API")
public interface UserService {

    @GetMapping("/{id}")
    User getUserById(@PathVariable("id") Long id);

    @GetMapping("/Batch/{ids}")
    List<User> getUserBatch(@PathVariable("ids") String ids);

}
