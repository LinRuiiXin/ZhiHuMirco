package com.example.search.dao;

import com.example.basic.po.User;
import com.example.search.document.UserDoc;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    User getUserById(@Param("id") Long id);
    List<UserDoc> getAllUser();
}
