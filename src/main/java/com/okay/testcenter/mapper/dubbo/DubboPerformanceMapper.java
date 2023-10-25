package com.okay.testcenter.mapper.dubbo;

import com.okay.testcenter.domain.dubbo.DubboPerformance;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhou
 * @date 2019/12/13
 */
@Mapper
public interface DubboPerformanceMapper {


    void insert(DubboPerformance dubboPerformance);

    void update(DubboPerformance dubboPerformance);

    void delete(int id);

    List<DubboPerformance> findByCaseId(int caseId);

    DubboPerformance findById(int id);

    List<DubboPerformance> findList();


}
