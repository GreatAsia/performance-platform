package com.okay.testcenter.mapper.dubbo;

import com.okay.testcenter.domain.dubbo.DubboTestHistory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DubboTestHistoryMapper {

    public void insertTestHistory(DubboTestHistory dubboTestHistory);

    public void deleteTestHistory(int id);

    public void updateTestHistory(DubboTestHistory dubboTestHistory);

    public DubboTestHistory findTestHistoryById(int id);

    public List<DubboTestHistory> findTestHistoryList(int currentPage, int pageSize);

    public List<DubboTestHistory> findTestHistoryList();

    public List<DubboTestHistory> findTestHistoryListByModelIdAndEnv(int modelId, String env, int currentPage, int pageSize);
    public int getLastTestHistoryId();

}
