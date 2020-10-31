package com.example.answer.service.rpc;

import com.example.basic.po.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/10/26 8:46 下午
 */


//注意！！！这里的服务名不需要加 "/" 如 "/User"
@FeignClient("User")
@RequestMapping("/API")
public interface UserService {

    @GetMapping("/{id}")
    User getUserById(@PathVariable("id") Long id);


}