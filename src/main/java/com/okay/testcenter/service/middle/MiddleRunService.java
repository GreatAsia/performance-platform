package com.okay.testcenter.service.middle;

import com.alibaba.fastjson.JSONObject;


public interface MiddleRunService {

    Object debugInterface(JSONObject requestInfo);

    Object runSingle(int id);

//    @Async
//    Object runProject(int env_id, int project_id, int caseType);
//
//    @Async
//    CompletableFuture<MiddleTestHistory> runMonitorProject(int env_id, int project_id, int caseType);

    public Object addAccunt(int id, int count, int envId, String type, int historyId);

}
