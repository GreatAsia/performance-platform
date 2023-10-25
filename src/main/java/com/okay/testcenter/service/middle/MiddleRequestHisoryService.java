package com.okay.testcenter.service.middle;

import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.report.MiddleRequestHistory;

import java.util.List;

public interface MiddleRequestHisoryService {


    public void insertMiddleRequestHistory(MiddleRequestHistory middleRequestHistory);

    public void updateMiddleRequestHistory(MiddleRequestHistory middleRequestHistory);

    public void deleteMiddleRequestHistory(int id);

    public MiddleRequestHistory findMiddleRequestHistoryById(int id);

    public PageInfo findMiddleRequestHistoryList(int currentPage, int pageSize);

    public List<MiddleRequestHistory> findHistoryByHistoryId(int id);

    public PageInfo findMiddleRequestHistoryByInterfaceId(int interfaceId, int currentPage, int pageSize);


}
