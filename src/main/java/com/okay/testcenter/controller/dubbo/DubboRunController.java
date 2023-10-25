package com.okay.testcenter.controller.dubbo;

import com.alibaba.fastjson.JSONObject;
import com.okay.testcenter.domain.Env;
import com.okay.testcenter.domain.RetResponse;
import com.okay.testcenter.domain.RetResult;
import com.okay.testcenter.domain.dubbo.DubboCase;
import com.okay.testcenter.domain.dubbo.DubboModule;
import com.okay.testcenter.domain.dubbo.DubboServerInfo;
import com.okay.testcenter.service.dubbo.DubboCaseService;
import com.okay.testcenter.service.middle.EnvService;
import com.okay.testcenter.tools.ZookeeperConnect;
import com.okay.testcenter.tools.dubbo.ProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;



@Api(description = "Dubbo运行接口")
@RequestMapping(value = "/perf/dubbo")
@Controller
public class DubboRunController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    DubboCaseService dubboCaseService;


    @GetMapping(value = "/inter/list")
    public RetResult<Object> dubboInterfaceList() {

        String address = "zookeeper://zk-dev.wf:2181";
        String protocol = "zookeeper";
        String group = "";
        List list = ProviderService.get(address).getProviders(protocol, address, group);
        logger.info("list==" + list.toString());
        return  RetResponse.makeOKRsp(JSONObject.toJSONString(list));
    }



    @ApiOperation(value = "运行Dubbo接口", notes = "通过ID运行Dubbo接口")
    @PostMapping(value = "/runById")
    @ResponseBody
    @Transactional()
    public Object runById(@Validated @RequestBody JSONObject request) {

        return dubboCaseService.runDubboById(request);

    }

    @ApiOperation(value = "通过模块运行Dubbo接口", notes = "通过模块运行Dubbo接口")
    @PostMapping(value = "/runByModel")
    @ResponseBody
    public RetResult<Object> dubboRunWithModel(@Validated @RequestBody JSONObject request) {

        Object result = dubboCaseService.runDubboByModule(request);
        if ("fail".equals(result.toString())) {

            return RetResponse.makeErrRsp(result.toString());
        } else {

            return RetResponse.makeOKRsp(result);
        }

    }






}
