package com.example.answer.service.rpc;

import com.example.search.document.Information;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/28 5:07 下午
 */
@FeignClient("Search")
@RequestMapping("/API")
public interface SearchService {

    @PostMapping("/Keyword/{title}")
    void insertKeyword(@PathVariable("title") String title);

    @PostMapping("/Information")
    void insertInformation(@RequestBody Information information);
}
