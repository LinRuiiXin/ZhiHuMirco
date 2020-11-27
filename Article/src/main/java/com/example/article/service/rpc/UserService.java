package com.example.article.service.rpc;

import com.example.basic.po.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/12 10:02 下午
 */
@FeignClient("User")
@RequestMapping("/API")
public interface UserService {
    @GetMapping("/IsAttention/{beAttentionUserId}/{userId}")
    boolean whetherTheUserIsFollowed(@PathVariable("beAttentionUserId") Long beAttentionUserId,@PathVariable("userId") Long userId);


    @GetMapping("/{id}")
    User getUserById(@PathVariable("id") Long id);

    @GetMapping("/Batch/{ids}")
    List<User> getUserBatch(@PathVariable("ids") String ids);

    @PutMapping("/Version/{id}")
    void incrementVersion(@PathVariable Long id);
}
