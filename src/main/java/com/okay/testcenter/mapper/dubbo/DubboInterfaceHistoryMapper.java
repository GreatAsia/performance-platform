package com.okay.testcenter.mapper.dubbo;

import com.okay.testcenter.domain.dubbo.DubboCaseHistory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DubboInterfaceHistoryMapper {

    public void insertHistory(DubboCaseHistory dubboCaseHistory);

    public void updateHistory(DubboCaseHistory dubboCaseHistory);

    public void deleteHistory(int id);

    public DubboCaseHistory findHistoryById(int id);

    public List<DubboCaseHistory> findHistoryList(int currentPage, int pageSize);

    public List<DubboCaseHistory> findHistoryByHistoryId(int id);

    public List<DubboCaseHistory> findHistoryByModel(int modelId, int currentPage, int pageSize);


}
