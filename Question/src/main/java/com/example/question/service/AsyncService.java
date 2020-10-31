package com.example.question.service;

import com.example.question.service.interfaces.ClassifyQuestionService;
import com.example.question.service.interfaces.TypeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/10/27 8:48 下午
 */

@Service
public class AsyncService {

    private final ClassifyQuestionService classifyQuestionService;
    private final TypeRecordService typeRecordService;

    @Autowired
    public AsyncService(ClassifyQuestionService classifyQuestionService, TypeRecordService typeRecordService) {
        this.classifyQuestionService = classifyQuestionService;
        this.typeRecordService = typeRecordService;
    }

    public void setQuestionType(Long questionId, String typeStr){
        String[] split = typeStr.split("-");
        for(String typeId : split){
            classifyQuestionService.setQuestionType(questionId,Long.valueOf(typeId));
        }
    }
    public void recordUserBrowse(List<Long> ids, Long userId){
        ids.forEach(id->{
            typeRecordService.addUserTypeRecord(id,userId);
        });
    }

}
