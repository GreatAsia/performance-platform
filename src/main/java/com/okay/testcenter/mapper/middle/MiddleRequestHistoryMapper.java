package com.okay.testcenter.mapper.middle;


import com.okay.testcenter.domain.report.MiddleRequestHistory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MiddleRequestHistoryMapper {

    public void insertMiddleRequestHistory(MiddleRequestHistory middleRequestHistory);

    public void updateMiddleRequestHistory(MiddleRequestHistory middleRequestHistory);

    public void deleteMiddleRequestHistory(int id);

    public MiddleRequestHistory findMiddleRequestHistoryById(int id);

    public List<MiddleRequestHistory> findMiddleRequestHistoryList();

    public List<MiddleRequestHistory> findHistoryByHistoryId(int id);

    public List<MiddleRequestHistory> findMiddleRequestHistoryByInterfaceId(int interfaceId);


}
