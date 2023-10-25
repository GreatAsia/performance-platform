package com.okay.testcenter.mapper.middle;

import com.okay.testcenter.domain.middle.MiddlePerformance;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhou
 * @date 2019/12/13
 */
@Mapper
public interface MiddlePerformanceMapper {


    void insert(MiddlePerformance middlePerformance);

    void update(MiddlePerformance middlePerformance);

    void delete(int id);

    List<MiddlePerformance> findByCaseId(int caseId);

    MiddlePerformance findById(int id);

    List<MiddlePerformance> findList();


}
