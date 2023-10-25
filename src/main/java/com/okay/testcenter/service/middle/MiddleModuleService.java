package com.okay.testcenter.service.middle;

import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.MiddleModule;

import java.util.List;


public interface MiddleModuleService {

    public void insertMiddleModule(MiddleModule middleModule);

    public void deleteMiddleModule(int id);

    public void updateMiddleModule(MiddleModule middleModule);

    public MiddleModule findMiddleModuleById(int id);

    public MiddleModule findMiddleModuleByName(String name);

    public PageInfo findMiddleModuleList(int currentPage,int pageSize);

    public PageInfo findMiddleModuleByProjectId(int project_id, int currentPage, int pageSize);

    public List<MiddleModule> findMiddleModuleByProjectId(int project_id);

}
