package com.okay.testcenter.mapper.middle;

import com.okay.testcenter.domain.middle.MiddleInterface;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MiddleInterfaceMapper {

    public void insertMiddleInterface(MiddleInterface MiddleInterface);

    public void deleteMiddleInterface(int id);

    public void updateMiddleInterface(MiddleInterface MiddleInterface);

    public MiddleInterface findMiddleInterfaceById(int id);

    public List<MiddleInterface> findMiddleInterfaceByName(String name);

    public List<MiddleInterface> findMiddleInterfaceByPath(String path);

    public List<MiddleInterface> findMiddleInterfaceList();

    public List<MiddleInterface> findMiddleInterfaceByModuleId(int moduleId);

}
