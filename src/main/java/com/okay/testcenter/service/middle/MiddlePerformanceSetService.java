package com.okay.testcenter.service.middle;


import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.MiddlePerformanceSet;

import java.util.List;

public interface MiddlePerformanceSetService {


    void insert(MiddlePerformanceSet middlePerformanceSet);

    void update(MiddlePerformanceSet middlePerformanceSet);

    void delete(int id);

    MiddlePerformanceSet findByName(String name);

    MiddlePerformanceSet findById(int id);

    List<MiddlePerformanceSet> findList();

    PageInfo findList(int currentPage, int pageSize);


}
