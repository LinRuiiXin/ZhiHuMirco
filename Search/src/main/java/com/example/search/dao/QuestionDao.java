package com.example.search.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/27 7:45 下午
 */
@Repository
public interface QuestionDao {
    List<String> getAllQuestion();
}
