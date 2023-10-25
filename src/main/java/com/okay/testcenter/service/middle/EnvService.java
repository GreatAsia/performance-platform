package com.okay.testcenter.service.middle;

import com.okay.testcenter.domain.Env;

import java.util.List;

public interface EnvService {

    List<Env> findEnvList();

    Env findEnvByName(String name);

    Env findEnvById(int id);
}
