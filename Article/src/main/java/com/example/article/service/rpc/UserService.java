package com.example.article.service.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
