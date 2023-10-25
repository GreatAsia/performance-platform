package com.okay.testcenter.mapper.dubbo;

import com.okay.testcenter.domain.dubbo.DubboCase;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface DubboCaseMapper {

    public void insertDubboInterface(DubboCase dubboCase);

    public void updateDubboInterface(DubboCase dubboCase);

    public DubboCase findDubboInterfaceById(int id);

    public List<DubboCase> findDubbocaseByName(String name);

    public void deleteDubboInterfaceById(int id);

    public List<DubboCase> findDubboInterfaceList();

    public List<DubboCase> findDubboInterfaceListByModel(int model);

    public List<DubboCase> findDubboInterfaceListByModelName(String modelName);

    List<DubboCase> findDubboInterfaceListByModelAndEnv(int model, int envId, int currentPage, int pageSize);

}
