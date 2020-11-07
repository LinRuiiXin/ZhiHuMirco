package com.example.recommend.service.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("Question")
@RequestMapping("/API")
public interface QuestionService {

    @GetMapping("/Question/QuestionTitle/{questionId}")
    String getQuestionTitle(@PathVariable("questionId") Long questionId);

    @GetMapping("/TypeRecord/UserRecordType/{userId}")
    List<Long> getUserRecordType(@PathVariable("userId") Long userId);

    @GetMapping("/ClassifyQuestion/RandomQuestion/{typeId}/{sum}")
    List<Long> getRandomQuestion(@PathVariable("typeId") Long typeId, @PathVariable("sum") int sum);
}
