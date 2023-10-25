package com.okay.testcenter.impl.middle;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.RequestSampler;
import com.okay.testcenter.domain.report.MiddleTestHistory;
import com.okay.testcenter.mapper.middle.MiddleTestHistoryMapper;
import com.okay.testcenter.service.middle.MiddleTestHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;



@Service("MiddleTestHistoryService")
public class MiddleTestHistoryServiceImpl implements MiddleTestHistoryService {

    @Resource
    MiddleTestHistoryMapper middleTestHistoryMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertMiddleTestHistory(MiddleTestHistory middleTestHistory) {

        return middleTestHistoryMapper.insertMiddleTestHistory(middleTestHistory);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMiddleTestHistory(MiddleTestHistory middleTestHistory) {

        middleTestHistoryMapper.updateMiddleTestHistory(middleTestHistory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMiddleTestHistory(int id) {
        middleTestHistoryMapper.deleteMiddleTestHistory(id);
    }

    @Override
    public MiddleTestHistory findMiddleTestHistoryById(int id) {
        return middleTestHistoryMapper.findMiddleTestHistoryById(id);
    }

    @Override
    public PageInfo findMiddleTestHistoryList(int currentPage, int pageSize) {

        PageHelper.startPage(currentPage, pageSize);
        List<MiddleTestHistory> middleTestHistories = middleTestHistoryMapper.findMiddleTestHistoryList();
        PageInfo pageInfo = new PageInfo(middleTestHistories);

        return pageInfo;
    }

    @Override
    public PageInfo findMiddleTestHistoryByEnvAndProjectId(int env, int interfaceId, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<MiddleTestHistory> middleTestHistories = middleTestHistoryMapper.findMiddleTestHistoryByEnvAndProjectId(env, interfaceId);
        PageInfo pageInfo = new PageInfo(middleTestHistories);

        return pageInfo;
    }

    @Override
    public RequestSampler findLoginInfoByProjectAndEnv(int project_id, int env_id) {

        return middleTestHistoryMapper.findLoginInfoByProjectAndEnv(project_id, env_id);
    }

    @Override
    public int getLastMiddleTestHistoryId() {
        return middleTestHistoryMapper.getLastMiddleTestHistoryId();
    }
}
