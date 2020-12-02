package com.example.user.service.rpc;

import com.example.search.document.UserDoc;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("Search")
@RequestMapping("/API")
public interface SearchService {
    @PostMapping("/User")
    void insertUserDoc(@RequestBody UserDoc userDoc);
}
