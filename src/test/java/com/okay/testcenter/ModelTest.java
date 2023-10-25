package com.okay.testcenter;


import com.alibaba.fastjson.JSONObject;
import com.okay.testcenter.domain.dubbo.DubboModule;
import com.okay.testcenter.service.dubbo.DubboModelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModelTest {

    private static final Logger logger = LoggerFactory.getLogger(ModelTest.class);

    @Resource
    DubboModelService modelService;

    @Test
    public void insertModel() {
        DubboModule model = new DubboModule();
        model.setName("admin");
        model.setComments("管理");
        modelService.insertModel(model);
    }

    @Test
    public void deleteModel() {
        modelService.deleteModel(52);
    }

    @Test
    public void updateModel() {
        DubboModule model = new DubboModule();
        model.setId(2);
        model.setName("user");
        model.setComments("修改用户");
        modelService.updateModel(model);
    }


    @Test
    public void findModelById() {
        DubboModule model = modelService.findModelById(2);
        logger.info("【model】:" + JSONObject.toJSONString(model));

    }

    @Test
    public void findModelByName() {
        DubboModule model = modelService.findModelByName("user");
        logger.info("[model]:" + JSONObject.toJSONString(model));
    }

    @Test
    public void findModelList() {
        List<DubboModule> modelsList = modelService.findModelList();
        logger.info("[modelList]:" + JSONObject.toJSONString(modelsList));
    }


}
