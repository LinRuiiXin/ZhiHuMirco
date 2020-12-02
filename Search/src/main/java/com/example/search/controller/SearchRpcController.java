package com.example.search.controller;

import com.example.search.document.Information;
import com.example.search.document.UserDoc;
import com.example.search.service.interfaces.InformationService;
import com.example.search.service.interfaces.KeywordService;
import com.example.search.service.interfaces.UserService;
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
    private final UserService userService;

    @Autowired
    public SearchRpcController(KeywordService keywordService, InformationService informationService, UserService userService) {
        this.keywordService = keywordService;
        this.informationService = informationService;
        this.userService = userService;
    }

    @PostMapping("/Keyword/{title}")
    public void insertKeyword(@PathVariable String title){
        keywordService.addKeyword(title);
    }

    @PostMapping("/Information")
    public void insertInformation(@RequestBody Information information){
        informationService.insertInformation(information);
    }

    @PostMapping("/User")
    public void insertUserDoc(@RequestBody UserDoc userDoc){
        userService.addUser(userDoc);
    }
}
