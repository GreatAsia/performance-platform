package com.okay.testcenter.mapper.middle;


import com.okay.testcenter.domain.middle.MiddleModule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MiddleModuleMapper {

    public void insertMiddleModule(MiddleModule MiddleModule);

    public void deleteMiddleModule(int id);

    public void updateMiddleModule(MiddleModule MiddleModule);

    public MiddleModule findMiddleModuleById(int id);

    public MiddleModule findMiddleModuleByName(String name);

    public List<MiddleModule> findMiddleModuleList();

    public List<MiddleModule> findMiddleModuleByProjectId(int project_id);


}
