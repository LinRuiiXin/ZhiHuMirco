package com.example.question.controller;

import com.example.question.service.interfaces.TypeRecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/6 9:32 下午
 */
@RestController
@RequestMapping("/API/TypeRecord")
public class TypeRecordRpcController {
    private final TypeRecordService typeRecordService;

    public TypeRecordRpcController(TypeRecordService typeRecordService) {
        this.typeRecordService = typeRecordService;
    }

    @GetMapping("/UserRecordType/{userId}")
    public List<Long> getUserRecordType(@PathVariable Long userId){
        return typeRecordService.getUserRecordType(userId);
    }
}
