package com.okay.testcenter.mapper.dubbo;

import com.okay.testcenter.domain.dubbo.DubboModule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DubboModelMapper {

    public int insertModel(DubboModule model);

    public void deleteModel(int id);

    public void updateModel(DubboModule model);

    public DubboModule findModelById(int id);

    public DubboModule findModelByName(String name);

    public List<DubboModule> findModelList();
}
