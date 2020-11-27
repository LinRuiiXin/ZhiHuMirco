package com.example.search.service.interfaces;

import com.example.search.document.Keyword;

import java.util.List;

public interface KeywordService{

    /*
    * 添加搜索关键词
    * @keyword 关键词
    * */
    void addKeyword(String keyword);

    /*
    * 批量添加搜索关键词
    * @keywords 关键词(集合)
    * */
    void addKeywordBatch(List<String> keywords);

    /*
    * 获取所有关键词
    * @size 数量
    * */
    List<Keyword> getAllKeyword(int size);

    /*
    * 模糊匹配关键词
    * @keyword 关键词
    * */
    List<Keyword> matchKeyword(String keyword,int size);

}
