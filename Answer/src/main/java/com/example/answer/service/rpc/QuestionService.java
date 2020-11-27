package com.example.answer.service.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/25 11:02 上午
 */
@FeignClient("Question")
@RequestMapping("/API")
public interface QuestionService {
    @GetMapping("/Title/{id}")
    String getQuestionTitle(@PathVariable("id") Long id);
}
