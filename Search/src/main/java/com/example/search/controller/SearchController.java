package com.example.search.controller;

import com.example.basic.dto.SimpleDto;
import com.example.search.service.interfaces.KeywordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/27 9:40 下午
 */
@RestController
@RequestMapping("/Search")
public class SearchController {

    private final KeywordService keywordService;

    public SearchController(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @GetMapping("/Keyword/{keyword}/{size}")
    public SimpleDto searchKeyword(@PathVariable String keyword,@PathVariable int size){
        return new SimpleDto(true,null,keywordService.matchKeyword(keyword,size));
    }
}
