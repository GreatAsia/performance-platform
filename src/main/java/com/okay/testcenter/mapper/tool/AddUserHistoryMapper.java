package com.okay.testcenter.mapper.tool;

import com.okay.testcenter.domain.middle.AddUserHistory;

import java.util.List;


public interface AddUserHistoryMapper {


    int insertHistory(AddUserHistory addUserHistory);

    void deleteHistory(int id);

    void updateHistory(AddUserHistory addUserHistory);

    AddUserHistory findHistoryById(int id);

    AddUserHistory findHistoryByUserTypeAndEnvId(String userType, int envId);

    List<AddUserHistory> findHistoryList();
}
