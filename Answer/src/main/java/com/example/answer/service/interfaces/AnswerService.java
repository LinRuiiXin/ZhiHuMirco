package com.example.answer.service.interfaces;

import com.example.basic.dto.SimpleDto;
import com.example.basic.po.Answer;
import com.example.basic.vo.AnswerVo;
import com.example.basic.vo.RecommendViewBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface AnswerService {
    SimpleDto insertAnswer(MultipartFile file, Answer answer);
    Answer queryRandomAnswerByQuestionId(Long questionId);
    List<Answer> getRandomAnswer(Integer sum);
    AnswerVo getAnswerById(Long userId, Long id) throws ExecutionException, InterruptedException;
    AnswerVo getNextAnswer(Long userId,Long questionId, Long supportSum) throws ExecutionException, InterruptedException;
    AnswerVo getPreviousAnswer(Long userId,Long questionId,Long id) throws ExecutionException, InterruptedException;
    List<AnswerVo> getNextTreeAnswer(Long userId, Long questionId, Long id) throws ExecutionException, InterruptedException;
    boolean userHasAttention(Long userId,Long answererId);
    boolean userHasSupport(Long userId,Long answerId);
    void updateAnswerCommentSum(Long answerId);
    List<Long> getAnswerOrder(Long questionId);
    void updateAnswerOrder(Long questionId);
    void supportAnswer(Long answerId,Long userId);
    void unSupportAnswer(Long answerId,Long userId);
    List<RecommendViewBean> getOrderAnswerViewBean(Long questionId,int limit) throws NoSuchFieldException, IllegalAccessException;
}
