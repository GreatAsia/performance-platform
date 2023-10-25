package com.okay.testcenter.impl.middle;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.MiddlePerformanceSet;
import com.okay.testcenter.mapper.middle.MiddlePerformanceSetMapper;
import com.okay.testcenter.service.middle.MiddlePerformanceSetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhou
 * @date 2019/12/23
 */
@Service("middlePerformanceSetService")
public class MiddlePerformanceSetImpl implements MiddlePerformanceSetService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    MiddlePerformanceSetMapper middlePerformanceSetMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(MiddlePerformanceSet middlePerformanceSet) {
        middlePerformanceSetMapper.insert(middlePerformanceSet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MiddlePerformanceSet middlePerformanceSet) {
       middlePerformanceSetMapper.update(middlePerformanceSet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(int id) {
        middlePerformanceSetMapper.delete(id);
    }

    @Override
    public MiddlePerformanceSet findByName(String name) {
        return middlePerformanceSetMapper.findByName(name);
    }

    @Override
    public MiddlePerformanceSet findById(int id) {
        return middlePerformanceSetMapper.findById(id);
    }

    @Override
    public List<MiddlePerformanceSet> findList() {
        return middlePerformanceSetMapper.findList();
    }

    @Override
    public PageInfo findList(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<MiddlePerformanceSet> setList = middlePerformanceSetMapper.findList();
        PageInfo pageInfo = new PageInfo(setList);
        return pageInfo;
    }
}
