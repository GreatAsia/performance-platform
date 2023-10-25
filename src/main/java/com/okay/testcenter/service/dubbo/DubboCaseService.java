package com.okay.testcenter.service.dubbo;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.dubbo.DubboCase;
import com.okay.testcenter.domain.dubbo.DubboCaseHistory;

import java.util.List;

public interface DubboCaseService {

    public void insertDubboInterface(DubboCase dubboCase);

    public void updateDubboInterface(DubboCase dubboCase);

    public DubboCase findDubboInterfaceById(int id);

    public void deleteDubboInterfaceById(int id);

    public PageInfo findDubboInterfaceList(int currentPage, int pageSize);

    public List<DubboCase> findDubboInterfaceList();

    public PageInfo findDubboInterfaceListByModel(int model, int currentPage, int pageSize);

    public List<DubboCase> findDubboInterfaceListByModel(int model);

    public List<DubboCase> findDubboInterfaceListByModelName(String modelName);

    public PageInfo findDubboInterfaceListByModelName(String modelName, int currentPage, int pageSize);


    DubboCaseHistory runDubboById(JSONObject request);

    Object runDubboByModule(JSONObject request);


    PageInfo findDubboInterfaceListByModelAndEnv(int model, int envId, int currentPage, int pageSize);



     List<DubboCase> findDubbocaseByName(String name);






}