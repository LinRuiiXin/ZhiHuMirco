package com.example.user.dao;

import com.example.basic.po.Information;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InformationDao {
    List<Information> getAuthorNewInformation(@Param("authorId") Long authorId,@Param("start") int start,@Param("size") int size);
}
