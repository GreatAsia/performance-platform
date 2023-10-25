package com.okay.testcenter.mapper.middle;


import com.okay.testcenter.domain.middle.MiddleLoginType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MiddleLoginTypeMapper {


    List<MiddleLoginType> findMiddleLoginTypeList();
}
