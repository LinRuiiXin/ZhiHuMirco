package com.example.search.service.interfaces;

import com.example.search.document.UserDoc;

import java.util.List;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/30 7:50 下午
 */
public interface UserService {
    void addUser(UserDoc userDoc);
    void addUserBatch(List<UserDoc> userDocs);
    List<UserDoc> getUser(int from,int size);
    List<UserDoc> user(String keyword,int from,int size);
}
