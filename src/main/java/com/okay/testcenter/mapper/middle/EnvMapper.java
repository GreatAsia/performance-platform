package com.okay.testcenter.mapper.middle;

import com.okay.testcenter.domain.Env;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EnvMapper {


    public List<Env> findEnvList();

    public Env findEnvByName(String name);

    Env findEnvById(int id);
}
