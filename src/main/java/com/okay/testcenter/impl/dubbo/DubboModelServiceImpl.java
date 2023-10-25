package com.okay.testcenter.impl.dubbo;


import com.okay.testcenter.domain.dubbo.DubboModule;
import com.okay.testcenter.mapper.dubbo.DubboModelMapper;
import com.okay.testcenter.service.dubbo.DubboModelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service("ModelService")
public class DubboModelServiceImpl implements DubboModelService {

    @Resource
    DubboModelMapper dubboModelMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertModel(DubboModule model) {
        dubboModelMapper.insertModel(model);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteModel(int id) {
        dubboModelMapper.deleteModel(id);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateModel(DubboModule model) {

        dubboModelMapper.updateModel(model);
    }

    @Override
    public DubboModule findModelById(int id) {
        return dubboModelMapper.findModelById(id);
    }

    @Override
    public DubboModule findModelByName(String name) {
        return dubboModelMapper.findModelByName(name);
    }

    @Override
    public List<DubboModule> findModelList() {
        return dubboModelMapper.findModelList();
    }
}
