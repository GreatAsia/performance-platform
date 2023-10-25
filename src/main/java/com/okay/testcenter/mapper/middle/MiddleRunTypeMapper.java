package com.okay.testcenter.mapper.middle;


import com.okay.testcenter.domain.middle.MiddleRunType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MiddleRunTypeMapper {


    List<MiddleRunType> findMiddleRunTypeList();
}
