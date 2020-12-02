package com.example.search.controller;

import com.example.basic.dto.SimpleDto;
import com.example.search.service.interfaces.InformationService;
import com.example.search.service.interfaces.KeywordService;
import com.example.search.service.interfaces.UserService;
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
    private final InformationService informationService;
    private final UserService userService;

    public SearchController(KeywordService keywordService, InformationService informationService, UserService userService) {
        this.keywordService = keywordService;
        this.informationService = informationService;
        this.userService = userService;
    }

    @GetMapping("/Keyword/{keyword}/{size}")
    public SimpleDto searchKeyword(@PathVariable String keyword,@PathVariable int size){
        return new SimpleDto(true,null,keywordService.matchKeyword(keyword,size));
    }

    @GetMapping("/Comprehensive/{keyword}/{from}/{size}")
    public SimpleDto comprehensive(@PathVariable String keyword,@PathVariable int from,@PathVariable int size){
        return new SimpleDto(true,null,informationService.comprehensive(keyword,from,size));
    }

    @GetMapping("/Question/{keyword}/{from}/{size}")
    public SimpleDto question(@PathVariable String keyword,@PathVariable int from,@PathVariable int size){
        return new SimpleDto(true,null,informationService.question(keyword,from,size));
    }

    @GetMapping("/Article/{keyword}/{from}/{size}")
    public SimpleDto article(@PathVariable String keyword,@PathVariable int from,@PathVariable int size){
        return new SimpleDto(true,null,informationService.article(keyword,from,size));
    }

    @GetMapping("/User/{keyword}/{from}/{size}")
    public SimpleDto user(@PathVariable String keyword,@PathVariable int from,@PathVariable int size){
        return new SimpleDto(true,null,userService.user(keyword,from,size));
    }

}
