package com.okay.testcenter.service.middle;

import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.RequestSampler;
import com.okay.testcenter.domain.report.MiddleTestHistory;


public interface MiddleTestHistoryService {

    public int insertMiddleTestHistory(MiddleTestHistory middleTestHistory);

    public void updateMiddleTestHistory(MiddleTestHistory middleTestHistory);

    public void deleteMiddleTestHistory(int id);

    public MiddleTestHistory findMiddleTestHistoryById(int id);

    public PageInfo findMiddleTestHistoryList(int currentPage, int pageSize);

    public PageInfo findMiddleTestHistoryByEnvAndProjectId(int env, int projectId, int currentPage, int pageSize);

    public RequestSampler findLoginInfoByProjectAndEnv(int project_id, int env_id);

    public int getLastMiddleTestHistoryId();

}
