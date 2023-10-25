package com.okay.testcenter.mapper.middle;


import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.MiddlePerformanceSet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhou
 * @date 2019/12/13
 */
@Mapper
public interface MiddlePerformanceSetMapper {


    void insert(MiddlePerformanceSet middlePerformanceSet);

    void update(MiddlePerformanceSet middlePerformanceSet);

    void delete(int id);

    MiddlePerformanceSet findByName(String name);

    MiddlePerformanceSet findById(int id);

    List<MiddlePerformanceSet> findList();

    PageInfo findList(int currentPage, int pageSize);
}
