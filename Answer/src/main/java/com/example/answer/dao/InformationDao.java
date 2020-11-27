package com.example.answer.dao;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InformationDao {
    void insertInformation(@Param("contentId") Long contentId,@Param("authorId") Long authorId);
}
