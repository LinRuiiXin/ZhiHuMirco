package com.example.user.service;

import com.example.basic.po.Information;
import com.example.user.dao.InformationDao;
import com.example.user.service.interfaces.NewPushInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/24 7:48 下午
 */

@Service
public class NewPushInformationServiceImpl implements NewPushInformationService {

    private final InformationDao informationDao;

    @Autowired
    public NewPushInformationServiceImpl(InformationDao informationDao) {
        this.informationDao = informationDao;
    }

    @Override
    public List<Information> getNewInformation(Map<Long, Integer> paramForUpdate) {
        List<Information> res = new ArrayList<>();
        paramForUpdate.keySet().forEach(authorId -> {
            res.addAll(informationDao.getAuthorNewInformation(authorId,0,paramForUpdate.get(authorId)));
        });
        return res;
    }
}
