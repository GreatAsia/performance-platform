package com.okay.testcenter.impl.middle;


import com.okay.testcenter.domain.middle.MiddleLoginType;
import com.okay.testcenter.mapper.middle.MiddleLoginTypeMapper;
import com.okay.testcenter.service.middle.MiddleLoginTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("MiddleLoginTypeService")
public class MiddleLoginTypeServiceImpl implements MiddleLoginTypeService {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    MiddleLoginTypeMapper middleLoginTypeMapper;


    @Override
    public List<MiddleLoginType> findMiddleLoginTypeList() {
        return middleLoginTypeMapper.findMiddleLoginTypeList();
    }
}
