package com.example.question.service.interfaces;

import java.util.List;

public interface TypeRecordService {
    List<Long> getUserRecordType(Long userId);
    void addUserTypeRecord(Long id,Long userId);
}
