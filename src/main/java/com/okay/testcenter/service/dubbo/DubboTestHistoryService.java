package com.okay.testcenter.service.dubbo;

import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.dubbo.DubboTestHistory;

import java.util.List;


public interface DubboTestHistoryService {

    public void insertTestHistory(DubboTestHistory dubboTestHistory);

    public void deleteTestHistory(int id);

    public void updateTestHistory(DubboTestHistory dubboTestHistory);

    public DubboTestHistory findTestHistoryById(int id);

    public PageInfo findTestHistoryList(int currentPage, int pageSize);

    public PageInfo findTestHistoryListByModelIdAndEnv(int modelId, String env, int currentPage, int pageSize);

    public int getLastTestHistoryId();

    public List<DubboTestHistory> findTestHistoryList();


}
