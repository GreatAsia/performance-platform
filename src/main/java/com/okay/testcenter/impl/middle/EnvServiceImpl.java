package com.okay.testcenter.impl.middle;


import com.okay.testcenter.domain.Env;
import com.okay.testcenter.mapper.middle.EnvMapper;
import com.okay.testcenter.service.middle.EnvService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("EnvService")
public class EnvServiceImpl implements EnvService {

    @Resource
    EnvMapper envMapper;

    @Override
    public List<Env> findEnvList() {
        return envMapper.findEnvList();
    }

    @Override
    public Env findEnvByName(String name) {
        return envMapper.findEnvByName(name);
    }

    @Override
    public Env findEnvById(int id) {
        return envMapper.findEnvById(id);
    }
}
