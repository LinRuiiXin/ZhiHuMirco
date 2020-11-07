package com.example.question.controller;

import com.example.question.service.interfaces.ClassifyQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/6 9:36 下午
 */
@RestController
@RequestMapping("/API/ClassifyQuestion")
public class ClassifyQuestionController {

    private final ClassifyQuestionService classifyQuestionService;

    @Autowired
    public ClassifyQuestionController(ClassifyQuestionService classifyQuestionService) {
        this.classifyQuestionService = classifyQuestionService;
    }

    @GetMapping("/RandomQuestion/{typeId}/{sum}")
    public List<Long> getRandomQuestion(@PathVariable Long typeId,@PathVariable int sum){
        return classifyQuestionService.getRandomQuestion(typeId,sum);
    }

}
