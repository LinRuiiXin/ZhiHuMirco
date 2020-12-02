package com.example.search.service.interfaces;

import com.example.search.document.Information;

import java.util.List;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/28 3:53 下午
 */
public interface InformationService {
    void insertInformation(Information information);
    void insertInformationBatch(List<Information> information);
    List<Information> getInformation(int limit,int size);
    List<Information> comprehensive(String key,int from,int size);
    List<Information> question(String key,int from,int size);
    List<Information> article(String key,int from,int size);
}
