package com.example.search.controller;

import com.example.search.document.Information;
import com.example.search.service.interfaces.InformationService;
import com.example.search.service.interfaces.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/28 3:24 下午
 */
@RestController
@RequestMapping("/API")
public class SearchRpcController {

    private final KeywordService keywordService;
    private final InformationService informationService;

    @Autowired
    public SearchRpcController(KeywordService keywordService, InformationService informationService) {
        this.keywordService = keywordService;
        this.informationService = informationService;
    }

    @PostMapping("/Keyword/{title}")
    public void insertKeyword(@PathVariable String title){
        keywordService.addKeyword(title);
    }

    @PostMapping("/Information")
    public void insertInformation(@RequestBody Information information){
        informationService.insertInformation(information);
    }
}
