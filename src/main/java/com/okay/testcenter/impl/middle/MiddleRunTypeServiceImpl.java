package com.okay.testcenter.impl.middle;


import com.okay.testcenter.domain.middle.MiddleRunType;
import com.okay.testcenter.mapper.middle.MiddleRunTypeMapper;
import com.okay.testcenter.service.middle.MiddleRunTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("MiddleRunTypeService")
public class MiddleRunTypeServiceImpl implements MiddleRunTypeService {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    MiddleRunTypeMapper middleRunTypeMapper;

    @Override
    public List<MiddleRunType> findMiddleRunTypeList() {
        return middleRunTypeMapper.findMiddleRunTypeList();
    }
}
