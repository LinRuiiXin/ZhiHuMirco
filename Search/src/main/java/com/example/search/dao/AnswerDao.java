package com.example.search.dao;

import com.example.basic.po.Answer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerDao {
    List<Answer> getAllAnswer();
}
