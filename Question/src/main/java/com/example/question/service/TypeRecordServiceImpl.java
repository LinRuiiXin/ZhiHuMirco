package com.example.question.service;

import com.example.question.dao.TypeRecordDao;
import com.example.question.service.interfaces.TypeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/10/28 2:47 下午
 */
@Service
public class TypeRecordServiceImpl implements TypeRecordService {

    private final TypeRecordDao typeRecordDao;

    @Autowired
    public TypeRecordServiceImpl(TypeRecordDao typeRecordDao) {
        this.typeRecordDao = typeRecordDao;
    }

    /*
    * 垃圾代码，需优化
    * */
    @Override
    public List<Long> getUserRecordType(Long userId) {
        List<Long> typeRecord = typeRecordDao.queryUserTypeRecord(userId);
        List<Long> doTypeIds = new ArrayList<>();
        typeRecord.stream().distinct().forEach(doTypeIds::add);
        return doTypeIds;
    }

    @Override
    public void addUserTypeRecord(Long id, Long userId) {
        typeRecordDao.addUserTypeRecord(id,userId);
    }
}
