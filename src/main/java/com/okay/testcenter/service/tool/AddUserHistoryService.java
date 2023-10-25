package com.okay.testcenter.service.tool;

import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.AddUserHistory;

import java.util.List;


public interface AddUserHistoryService {


    int insertHistory(AddUserHistory addUserHistory);

    void deleteHistory(int id);

    void updateHistory(AddUserHistory addUserHistory);


    void updateHistoryData(int historyId);

    AddUserHistory findHistoryById(int id);

    AddUserHistory findHistoryByUserTypeAndEnvId(String userType, int envId);

    PageInfo findHistoryList(int currentPage, int pageSize);

    List<AddUserHistory> findHistoryList();
}
