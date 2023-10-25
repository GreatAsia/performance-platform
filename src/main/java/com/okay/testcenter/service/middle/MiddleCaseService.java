package com.okay.testcenter.service.middle;


import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.MiddleCase;

import java.util.List;

public interface MiddleCaseService {

    public void insertMiddleCase(MiddleCase middleCase);

    public void deleteMiddleCase(int id);

    public void updateMiddleCase(MiddleCase middleCase);

    public MiddleCase findMiddleCaseById(int id);

    public List<MiddleCase> findMiddleCaseByName(String name);

    public List<MiddleCase> findMiddleCaseByPath(String path);

    public PageInfo findMiddleCaseList(int currentPage, int pageSize);

    public List<MiddleCase> findMiddleCaseList();

    public PageInfo findMiddleCaseByEnvAndInterface(int env_id,int interface_id, int currentPage, int pageSize);

    public List<MiddleCase> findMiddleCaseByEnvAndInterface(int env_id, int interface_id);
}
