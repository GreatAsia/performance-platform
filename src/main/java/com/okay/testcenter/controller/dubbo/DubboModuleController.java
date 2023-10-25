package com.okay.testcenter.controller.dubbo;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.Env;
import com.okay.testcenter.domain.RetResponse;
import com.okay.testcenter.domain.RetResult;
import com.okay.testcenter.domain.dubbo.DubboCase;
import com.okay.testcenter.domain.dubbo.DubboModule;
import com.okay.testcenter.domain.dubbo.DubboServerInfo;
import com.okay.testcenter.service.dubbo.DubboCaseService;
import com.okay.testcenter.service.dubbo.DubboModelService;
import com.okay.testcenter.service.middle.EnvService;
import com.okay.testcenter.tools.CreateDubboCase;
import com.okay.testcenter.tools.DubboFactory;
import com.okay.testcenter.tools.ZookeeperConnect;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.curator.utils.ZookeeperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.okay.testcenter.tools.GetTime.getTime;


@Api(description = "Dubbo模块接口")
@Controller
@RequestMapping(value = "/perf/dubbo")
public class DubboModuleController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    DubboCaseService dubboCaseService;
    @Resource
    DubboModelService dubboModelService;
    @Resource
    EnvService envService;


    @GetMapping(value = "/test")
    public String dubboList(Model model) {

        Env env = envService.findEnvById(1);
        String zkAddress =  env.getAddress() + ":" + env.getPort();
        ZookeeperConnect zkClient = ZookeeperConnect.getInstance(zkAddress);
        List<String> serverList =  zkClient.getServiceList();

        DubboCase dubboCase = zkClient.getServiceInfo(serverList.get(0));
        List<String> listSvr = zkClient.getSvrList(serverList);
        List<String> getServer = zkClient.getRelateServiceList(listSvr.get(0),serverList);
        zkClient.closed();
        List<Env> envList = envService.findEnvList();
        model.addAttribute("dubboCase", dubboCase);
        model.addAttribute("serverList", getServer);
        model.addAttribute("listSvr", listSvr);
        model.addAttribute("envList", envList);

        return "dubbo/DubboTest";
    }

    @ApiOperation(value = "根据环境获取Dubbo服务列表", notes = "根据环境获取Dubbo服务列表")
    @GetMapping(value = "/svr/list")
    @ResponseBody
    public String svrList(int envId) {

        Env env = envService.findEnvById(envId);
        String zkAddress =  env.getAddress() + ":" + env.getPort();
        ZookeeperConnect zkClient = ZookeeperConnect.getInstance(zkAddress);
        List<String> serverList =  zkClient.getServiceList();
        List<String> listSvr = zkClient.getSvrList(serverList);
        zkClient.closed();
        return JSONObject.toJSONString(listSvr);

    }


    @ApiOperation(value = "根据svr获取服务列表", notes = "根据svr获取服务列表")
    @GetMapping(value = "/server/list")
    @ResponseBody
    public String serverList(int envId,String svr) {

        Env env = envService.findEnvById(envId);
        String zkAddress =  env.getAddress() + ":" + env.getPort();
        ZookeeperConnect zkClient = ZookeeperConnect.getInstance(zkAddress);
        List<String> serverList =  zkClient.getServiceList();
        List<String> currentServer = zkClient.getRelateServiceList(svr,serverList);
        zkClient.closed();
        return JSONObject.toJSONString(currentServer);
    }


    @ApiOperation(value = "根据server获取服务信息", notes = "根据server获取服务信息")
    @GetMapping(value = "/server/info")
    @ResponseBody
    public  String serviceInfo(String serverName,int envId) {

        Env env = envService.findEnvById(envId);
        String zkAddress =  env.getAddress() + ":" + env.getPort();
        ZookeeperConnect zkClient = ZookeeperConnect.getInstance(zkAddress);
        DubboCase dubboCase = zkClient.getServiceInfo(serverName);
        zkClient.closed();
        return  JSONObject.toJSONString(dubboCase);
    }









    @GetMapping(value = "/list")
    public String dubboInterfaceList(@Validated Model model) {

        PageInfo pageInfo = dubboCaseService.findDubboInterfaceListByModelAndEnv(1, 1, 1, 10);

        List<DubboModule> dubboModuleList = dubboModelService.findModelList();
        model.addAttribute("moduleId", 1);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("dubboModelList", dubboModuleList);
        List<Env> envList = envService.findEnvList();
        model.addAttribute("envList", envList);
        return "dubbo/DubboCase";

    }

    @ApiOperation(value = "获取Dubbo用例列表", notes = "通过分页获取Dubbo用例列表")
    @ApiImplicitParam(name = "moduleId", value = "模块ID")
    @GetMapping(value = "/getList")
    @ResponseBody
    public String dubboInterfaceList(int moduleId, int envId, int currentPage, int pageSize) {

        PageInfo pageInfo = dubboCaseService.findDubboInterfaceListByModelAndEnv(moduleId, envId, currentPage, pageSize);
        logger.info("pageInfo==" + JSONObject.toJSONString(pageInfo));
        return JSONObject.toJSONString(pageInfo);

    }

    @ApiOperation(value = "运行Dubbo", notes = "运行调试Dubbo接口")
    @PostMapping(value = "/run")
    @ResponseBody
    public Object dubboRun(@Validated @RequestBody JSONObject request) {


        DubboCase dubboCase = JSONObject.parseObject(request.toJSONString(), DubboCase.class);
        dubboCase = new CreateDubboCase().create(dubboCase);
        //docker环境去调用docker环境的中转服务
        DubboFactory dubbo = DubboFactory.getInstance();
        Object result = dubbo.genericInvoke(dubboCase);
        dubbo.destory();
        logger.info("responseResult===" + result);
        return JSONObject.toJSONString(result);
    }


    @GetMapping(value = "/module")
    public String dubboModel(Model model) {

        List<DubboModule> dubboModuleList = dubboModelService.findModelList();

        model.addAttribute("dubboModelList", dubboModuleList);

        return "dubbo/DubboModel";
    }

    @ApiOperation(value = "删除Dubbo模块", notes = "通过Id删除Dubbo模块")
    @PostMapping(value = "/module/delete")
    @ResponseBody
    public RetResult<Object> deleteDubboModel(@Validated @RequestBody JSONObject request) {
        int id = Integer.parseInt(request.get("id").toString());
        dubboModelService.deleteModel(id);
        return RetResponse.makeOKRsp();
    }

    @ApiOperation(value = "添加Dubbo模块", notes = "添加Dubbo模块")
    @PostMapping(value = "/module/insert")
    @ResponseBody
    public RetResult<Object> insertDubboModel(@Validated @RequestBody DubboModule dubboModule) {

        dubboModelService.insertModel(dubboModule);
        return RetResponse.makeOKRsp();

    }

    @ApiOperation(value = "更新Dubbo模块", notes = "更新Dubbo模块")
    @PostMapping(value = "/module/update")
    @ResponseBody
    public RetResult<Object> updateDubboModel(@Validated @RequestBody DubboModule dubboModule) {

        dubboModelService.updateModel(dubboModule);
        return RetResponse.makeOKRsp();
    }

    @ApiOperation(value = "删除Dubbo接口", notes = "删除Dubbo接口")
    @PostMapping(value = "/interface/delete")
    @ResponseBody
    public RetResult<Object> deleteDubboInterface(@Validated @RequestBody JSONObject request) {
        int id = Integer.parseInt(request.get("id").toString());
        dubboCaseService.deleteDubboInterfaceById(id);
        return RetResponse.makeOKRsp();
    }

    @ApiOperation(value = "添加Dubbo接口", notes = "添加Dubbo接口")
    @PostMapping(value = "/interface/insert")
    @ResponseBody
    public RetResult<Object> insertDubboInterface(@Validated @RequestBody DubboCase dubboCase) {


        dubboCase.setCreateTime(getTime());
        dubboCaseService.insertDubboInterface(dubboCase);
        return RetResponse.makeOKRsp();

    }

    @ApiOperation(value = "更新Dubbo接口", notes = "更新Dubbo接口")
    @PostMapping(value = "/interface/update")
    @ResponseBody
    public RetResult<Object> updateDubboInterface(@Validated @RequestBody DubboCase dubboCase) {

        dubboCase.setEditTime(getTime());
        dubboCaseService.updateDubboInterface(dubboCase);
        return RetResponse.makeOKRsp();

    }

    @ApiOperation(value = "通过名称查找Dubbo用例", notes = "通过名称查找Dubbo用例")
    @GetMapping(value = "/findCaseByName")
    @ResponseBody
    public String findCaseByName(@Validated String caseName) {

        List<DubboCase> dubboCaseList = dubboCaseService.findDubbocaseByName(caseName);
        return JSONObject.toJSONString(dubboCaseList);

    }

}
