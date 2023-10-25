package com.okay.testcenter.service.dubbo;

import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.dubbo.DubboCaseHistory;

import java.util.List;

public interface DubboInterfaceHistoryService {

    public void insertHistory(DubboCaseHistory dubboCaseHistory);

    public void updateHistory(DubboCaseHistory dubboCaseHistory);

    public void deleteHistory(int id);

    public DubboCaseHistory findHistoryById(int id);

    public PageInfo findHistoryList(int currentPage, int pageSize);

    public List<DubboCaseHistory> findHistoryByHistoryId(int id);

    public PageInfo findHistoryByModel(int modelId, int currentPage, int pageSize);


}
