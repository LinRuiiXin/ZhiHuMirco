package com.example.user.service.interfaces;

import com.example.basic.po.Information;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/24 7:48 下午
 */
public interface NewPushInformationService {

    /*
    * 获取集合中每个用户指定数量的Information(即更新内容的id)
    * */
    List<Information> getNewInformation(Map<Long, Integer> paramForUpdate);

    List<Information> getUserInformation(Long userId,int from,int size);
}
