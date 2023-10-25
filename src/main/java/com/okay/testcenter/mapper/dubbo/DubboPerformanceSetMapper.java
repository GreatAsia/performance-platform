package com.okay.testcenter.mapper.dubbo;


import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.dubbo.DubboPerformanceSet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhou
 * @date 2019/12/13
 */
@Mapper
public interface DubboPerformanceSetMapper {


    void insert(DubboPerformanceSet dubboPerformanceSet);

    void update(DubboPerformanceSet dubboPerformanceSet);

    void delete(int id);

    DubboPerformanceSet findByName(String name);

    DubboPerformanceSet findById(int id);

    List<DubboPerformanceSet> findList();

    PageInfo findList(int currentPage, int pageSize);
}
