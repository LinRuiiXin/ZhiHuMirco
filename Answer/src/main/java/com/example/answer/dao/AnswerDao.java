package com.example.answer.dao;

import com.example.basic.po.Answer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/10/26 7:45 下午
 */

@Repository
public interface AnswerDao {
    void insertAnswer(Answer answer);
    Answer queryRandomAnswerByQuestionId(Long questionId);
    List<Answer> getRandomAnswer(Integer sum);
    Answer queryAnswerById(Long id);
    List<Long> getAnswerOrder(Long questionId);
    Integer userHasAttention(@Param("userId") Long userId, @Param("answererId") Long answererId);
    Integer userHasSupport(@Param("userId") Long userId,@Param("answerId") Long answerId);
    void updateAnswerCommentSum(Long answerId);
    void incrementSupportSum(Long answerId);
    void decrementSupportSum(Long answerId);
}
