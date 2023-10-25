package com.okay.testcenter.impl.middle;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.report.MiddleRequestHistory;
import com.okay.testcenter.mapper.middle.MiddleRequestHistoryMapper;
import com.okay.testcenter.service.middle.MiddleRequestHisoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;



@Service("MiddleRequestHisoryService")
public class MiddleRequestHistoryServiceImpl implements MiddleRequestHisoryService {


    @Resource
    MiddleRequestHistoryMapper middleRequestHistoryMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertMiddleRequestHistory(MiddleRequestHistory middleRequestHistory) {
        middleRequestHistoryMapper.insertMiddleRequestHistory(middleRequestHistory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMiddleRequestHistory(MiddleRequestHistory middleRequestHistory) {
        middleRequestHistoryMapper.updateMiddleRequestHistory(middleRequestHistory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMiddleRequestHistory(int id) {
        middleRequestHistoryMapper.deleteMiddleRequestHistory(id);
    }

    @Override
    public MiddleRequestHistory findMiddleRequestHistoryById(int id) {
        return middleRequestHistoryMapper.findMiddleRequestHistoryById(id);
    }

    @Override
    public PageInfo findMiddleRequestHistoryList(int currentPage, int pageSize) {

        PageHelper.startPage(currentPage, pageSize);
        List<MiddleRequestHistory> middleRequestHistoryList = middleRequestHistoryMapper.findMiddleRequestHistoryList();
        PageInfo pageInfo = new PageInfo(middleRequestHistoryList);

        return pageInfo;
    }

    @Override
    public List<MiddleRequestHistory> findHistoryByHistoryId(int id) {
        List<MiddleRequestHistory> middleRequestHistoryList = middleRequestHistoryMapper.findHistoryByHistoryId(id);
        return middleRequestHistoryList;
    }

    @Override
    public PageInfo findMiddleRequestHistoryByInterfaceId(int interfaceId, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<MiddleRequestHistory> middleRequestHistoryList = middleRequestHistoryMapper.findMiddleRequestHistoryByInterfaceId(interfaceId);
        PageInfo pageInfo = new PageInfo(middleRequestHistoryList);
        return pageInfo;
    }
}
