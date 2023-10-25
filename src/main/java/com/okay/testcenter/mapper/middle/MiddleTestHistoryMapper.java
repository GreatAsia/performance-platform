package com.okay.testcenter.mapper.middle;

import com.okay.testcenter.domain.middle.RequestSampler;
import com.okay.testcenter.domain.report.MiddleTestHistory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MiddleTestHistoryMapper {

    public int insertMiddleTestHistory(MiddleTestHistory middleTestHistory);

    public void updateMiddleTestHistory(MiddleTestHistory middleTestHistory);

    public void deleteMiddleTestHistory(int id);

    public MiddleTestHistory findMiddleTestHistoryById(int id);

    public List<MiddleTestHistory> findMiddleTestHistoryList();

    public List<MiddleTestHistory> findMiddleTestHistoryByEnvAndProjectId(int env, int projectId);

    public RequestSampler findLoginInfoByProjectAndEnv(int project_id, int env_id);

    public int getLastMiddleTestHistoryId();

}
