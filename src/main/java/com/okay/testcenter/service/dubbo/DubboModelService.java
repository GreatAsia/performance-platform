package com.okay.testcenter.service.dubbo;

import com.okay.testcenter.domain.dubbo.DubboModule;

import java.util.List;

public interface DubboModelService {

    public void insertModel(DubboModule model);

    public void deleteModel(int id);

    public void updateModel(DubboModule model);

    public DubboModule findModelById(int id);

    public DubboModule findModelByName(String name);

    public List<DubboModule> findModelList();
}
