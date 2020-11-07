package com.example.recommend.service.rpc;

import com.example.basic.po.Answer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("Answer")
@RequestMapping("/API")
public interface AnswerService {

    @GetMapping("/RandomAnswerByQuestionId/{questionId}")
    Answer queryRandomAnswerByQuestionId(@PathVariable("questionId") Long questionId);

    @GetMapping("/RandomAnswer/{sum}")
    List<Answer> getRandomAnswer(@PathVariable("sum") int sum);
}
