package com.okay.testcenter.impl.dubbo;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.dubbo.DubboCaseHistory;
import com.okay.testcenter.mapper.dubbo.DubboInterfaceHistoryMapper;
import com.okay.testcenter.service.dubbo.DubboInterfaceHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service("DubboInterfaceHistoryService")
public class DubboInterfaceHistoryServiceImpl implements DubboInterfaceHistoryService {

    @Resource
    DubboInterfaceHistoryMapper dubboInterfaceHistoryMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertHistory(DubboCaseHistory dubboCaseHistory) {

        dubboInterfaceHistoryMapper.insertHistory(dubboCaseHistory);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateHistory(DubboCaseHistory dubboCaseHistory) {

        dubboInterfaceHistoryMapper.updateHistory(dubboCaseHistory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteHistory(int id) {

        dubboInterfaceHistoryMapper.deleteHistory(id);

    }

    @Override
    public DubboCaseHistory findHistoryById(int id) {
        return dubboInterfaceHistoryMapper.findHistoryById(id);
    }

    @Override
    public PageInfo findHistoryList(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<DubboCaseHistory> dubboCaseHistoryList = dubboInterfaceHistoryMapper.findHistoryList(currentPage, pageSize);
        PageInfo pageInfo = new PageInfo(dubboCaseHistoryList);
        return pageInfo;
    }

    @Override
    public List<DubboCaseHistory> findHistoryByHistoryId(int id) {

        List<DubboCaseHistory> dubboCaseHistoryList = dubboInterfaceHistoryMapper.findHistoryByHistoryId(id);
        return dubboCaseHistoryList;
    }


    @Override
    public PageInfo findHistoryByModel(int modelId, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<DubboCaseHistory> dubboCaseHistoryList = dubboInterfaceHistoryMapper.findHistoryByModel(modelId, currentPage, pageSize);
        PageInfo pageInfo = new PageInfo(dubboCaseHistoryList);
        return pageInfo;
    }
}
