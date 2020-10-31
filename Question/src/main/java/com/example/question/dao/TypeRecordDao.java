package com.example.question.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRecordDao {
    List<Long> queryUserTypeRecord(Long userId);
    void addUserTypeRecord(Long typeId,Long userId);
}
