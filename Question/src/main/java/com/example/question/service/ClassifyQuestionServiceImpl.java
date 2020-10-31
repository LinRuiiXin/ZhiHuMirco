package com.example.question.service;

import com.example.question.dao.ClassifyQuestionDao;
import com.example.question.service.interfaces.ClassifyQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/10/28 2:40 下午
 */
@Service
public class ClassifyQuestionServiceImpl implements ClassifyQuestionService {

    private final ClassifyQuestionDao classifyQuestionDao;

    @Autowired
    public ClassifyQuestionServiceImpl(ClassifyQuestionDao classifyQuestionDao) {
        this.classifyQuestionDao = classifyQuestionDao;
    }

    @Override
    public void setQuestionType(Long questionId, Long classifyId) {
        classifyQuestionDao.insertClassifyQuestion(questionId,classifyId);
    }

    @Override
    public List<Long> getQuestionType(Long questionId) {
        return classifyQuestionDao.getQuestionType(questionId);
    }

    @Override
    public List<Long> getRandomQuestion(Long typeId, Integer sum) {
        return classifyQuestionDao.getRandomQuestionId(typeId,sum);
    }
}
