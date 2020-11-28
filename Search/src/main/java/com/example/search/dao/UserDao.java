package com.example.search.dao;

import com.example.basic.po.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    User getUserById(@Param("id") Long id);
}
