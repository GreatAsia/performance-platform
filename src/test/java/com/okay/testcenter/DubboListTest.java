package com.okay.testcenter;



import com.alibaba.fastjson.JSONObject;
import com.okay.testcenter.tools.dubbo.ProviderService;
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
public class DubboListTest {


    private static final Logger logger = LoggerFactory.getLogger(DubboListTest.class);
   @Resource
    ProviderService providerService;

    @Test
    public void testInsert() {
        String protocol = "zookeeper";
        String address = "zk-stress.wf:2182";
        String group = "";
        List list = providerService.getProviders(protocol, address, group);
        logger.info("list==" + JSONObject.toJSONString(list));
    }

}
