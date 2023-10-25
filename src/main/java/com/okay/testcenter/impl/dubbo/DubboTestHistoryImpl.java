package com.okay.testcenter.impl.dubbo;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.dubbo.DubboTestHistory;
import com.okay.testcenter.mapper.dubbo.DubboTestHistoryMapper;
import com.okay.testcenter.service.dubbo.DubboTestHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;



@Service("DubboTestHistoryService")
public class DubboTestHistoryImpl implements DubboTestHistoryService {

    @Resource
    DubboTestHistoryMapper dubboTestHistoryMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertTestHistory(DubboTestHistory dubboTestHistory) {
        dubboTestHistoryMapper.insertTestHistory(dubboTestHistory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTestHistory(int id) {
        dubboTestHistoryMapper.deleteTestHistory(id);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTestHistory(DubboTestHistory dubboTestHistory) {
        dubboTestHistoryMapper.updateTestHistory(dubboTestHistory);
    }

    @Override
    public DubboTestHistory findTestHistoryById(int id) {
        return dubboTestHistoryMapper.findTestHistoryById(id);
    }

    @Override
    public PageInfo findTestHistoryList(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<DubboTestHistory> dubboTestHistoryList = dubboTestHistoryMapper.findTestHistoryList(currentPage, pageSize);
        PageInfo pageInfo = new PageInfo(dubboTestHistoryList);
        return pageInfo;

    }

    @Override
    public PageInfo findTestHistoryListByModelIdAndEnv(int modelId, String env, int currentPage, int pageSize) {

        PageHelper.startPage(currentPage, pageSize);
        List<DubboTestHistory> dubboTestHistoryList = dubboTestHistoryMapper.findTestHistoryListByModelIdAndEnv(modelId, env, currentPage, pageSize);
        PageInfo pageInfo = new PageInfo(dubboTestHistoryList);
        return pageInfo;
    }

    @Override
    public int getLastTestHistoryId() {

        int id = dubboTestHistoryMapper.getLastTestHistoryId();
        return id;
    }

    @Override
    public List<DubboTestHistory> findTestHistoryList() {
        return dubboTestHistoryMapper.findTestHistoryList();
    }
}
