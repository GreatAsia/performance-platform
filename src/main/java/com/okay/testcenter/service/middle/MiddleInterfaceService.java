package com.okay.testcenter.service.middle;

import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.MiddleInterface;

import java.util.List;


public interface MiddleInterfaceService {

    public void insertMiddleInterface(MiddleInterface middleInterface);

    public void deleteMiddleInterface(int id);

    public void updateMiddleInterface(MiddleInterface middleInterface);

    public MiddleInterface findMiddleInterfaceById(int id);

    public List<MiddleInterface> findMiddleInterfaceByName(String name);

    public List<MiddleInterface> findMiddleInterfaceByPath(String path);

    public PageInfo findMiddleInterfaceList(int currentPage,int pageSize);

    public PageInfo findMiddleInterfaceByModuleId(int moduleId, int currentPage, int pageSize);

    public List<MiddleInterface> findMiddleInterfaceByModuleId(int module_Id);
}
