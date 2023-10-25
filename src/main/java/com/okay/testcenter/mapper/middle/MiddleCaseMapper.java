package com.okay.testcenter.mapper.middle;

import com.okay.testcenter.domain.middle.MiddleCase;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MiddleCaseMapper {

    public void insertMiddleCase(MiddleCase middleCase);

    public void deleteMiddleCase(int id);

    public void updateMiddleCase(MiddleCase middleCase);

    public MiddleCase findMiddleCaseById(int id);

    public List<MiddleCase> findMiddleCaseByName(String name);

    public List<MiddleCase> findMiddleCaseByPath(String path);

    public List<MiddleCase> findMiddleCaseList();

    public List<MiddleCase> findMiddleCaseByEnvAndInterface(int env_id,int interface_id);


}
