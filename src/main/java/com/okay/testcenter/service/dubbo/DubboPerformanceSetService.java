package com.okay.testcenter.service.dubbo;


import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.dubbo.DubboPerformanceSet;

import java.util.List;

public interface DubboPerformanceSetService {


    void insert(DubboPerformanceSet dubboPerformanceSet);

    void update(DubboPerformanceSet dubboPerformanceSet);

    void delete(int id);

    DubboPerformanceSet findByName(String name);

    DubboPerformanceSet findById(int id);

    List<DubboPerformanceSet> findList();

    PageInfo findList(int currentPage, int pageSize);


}
