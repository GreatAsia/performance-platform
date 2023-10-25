package com.okay.testcenter.controller.middle;


import com.alibaba.fastjson.JSONObject;
import com.okay.testcenter.domain.RetResponse;
import com.okay.testcenter.domain.middle.*;
import com.okay.testcenter.domain.report.MiddleRequestHistory;
import com.okay.testcenter.domain.report.MiddleTestHistory;
import com.okay.testcenter.domain.report.MiddleTestHistoryTotal;
import com.okay.testcenter.service.middle.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.okay.testcenter.runner.RunnerFactory.build;
import static com.okay.testcenter.runner.RunnerFactory.prepare;
import static com.okay.testcenter.tools.GetTime.getTime;

/**
 * @author asia
 * @date 2019-10-22
 */
@Api(description = "中间层运行接口")
@Controller
@RequestMapping(value = "/perf/middle")
public class MiddleInterfaceRunController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MiddleProjectService middleProjectService;
    @Autowired
    MiddleModuleService middleModuleService;
    @Autowired
    MiddleInterfaceService middleInterfaceService;
    @Autowired
    MiddleCaseService middleCaseService;
    @Autowired
    MiddleTestHistoryService middleTestHistoryService;
    @Autowired
    MiddleRequestHisoryService middleRequestHisoryService;

    @Resource
    MiddleRunService middleRunService;


    @GetMapping(value = "/run")
    public String run(Model model) {

        List<MiddleProject> middleProjectList = middleProjectService.findMiddleProjectList();
        model.addAttribute("middleProjectList", middleProjectList);
        return "middle/MiddleInterfaceTest";
    }

    /**
     * 调试中间层接口的方法
     * @param request
     * @return
     */
    @ApiOperation(value = "调试中间层接口", notes = "调试中间层接口")
    @PostMapping(value = "runMiddleInterface")
    @ResponseBody
    public Object runMiddleInterface(@Validated @RequestBody JSONObject request) {
        return middleRunService.debugInterface(request);
    }

    /**
     * 运行单条用例的方法
     * @param id
     * @return
     */
    @ApiOperation(value = "通过ID运行中间层用例", notes = "通过ID运行中间层用例")
    @GetMapping(value = "/runMiddleSingle")
    @ResponseBody
    public Object runMiddleSingle(@Validated int id) {

        return middleRunService.runSingle(id);
    }


    @ApiOperation(value = "中间层接口列表", notes = "中间层接口列表")
    @GetMapping(value = "/interfacelist")
    @ResponseBody
    public Object interfacelist(@Validated String project_name) {

       List<MiddleInterface> middleInterfaces = new ArrayList<>();

      MiddleProject middleProject = middleProjectService.findMiddleProjectByName(project_name);
      if(middleProject == null){
          return RetResponse.makeErrRsp("project_name is error");
      }
      int project_id = middleProject.getId();
      List<MiddleModule> middleModuleList = middleModuleService.findMiddleModuleByProjectId(project_id);
      for(MiddleModule middleModule : middleModuleList){

          List<MiddleInterface> middleInterfaceList = middleInterfaceService.findMiddleInterfaceByModuleId(middleModule.getId());
          middleInterfaces.addAll(middleInterfaceList);

      }


     return RetResponse.makeOKRsp(middleInterfaces);

    }


    /**
     * 给OP提供的运行项目的接口
     * @param request
     * @return
     */
    @ApiOperation(value = "给OP提供的通过项目名称和环境ID运行中间层用例", notes = "给OP提供的通过项目名称和环境ID运行中间层用例")
    @PostMapping(value = "/runMiddleCase")
    @ResponseBody
    public Object runMiddleCase(@Validated @RequestBody JSONObject request) {
        String projectNames = request.getString("project_names");
        int env_id = request.getInteger("env_id");
        List<MiddleRequestHistory> middleRequestHistoryList = new ArrayList<>();
        List<MiddleTestHistory> middleTestHistoryList = new ArrayList<>();
        MiddleTestHistoryTotal middleTestHistoryTotal = new MiddleTestHistoryTotal();
        List<MiddleRequestHistory> errorHistory = new ArrayList<>();
        int total = 0;
        int fail = 0;
        int pass = 0;
        String[] projectNameList = projectNames.split(",");
        for(int i=0;i<projectNameList.length;i++){
            int totalCase = 0;
            int passCase = 0;
            int failCase = 0;
            String totalResult = "PASS";
            String startTime = "";
            startTime = getTime();
            MiddleProject middleProject = middleProjectService.findMiddleProjectByName(projectNameList[i]);
            if (middleProject == null){
                return RetResponse.makeErrRsp("project name is error");
            }
            int project_id = middleProjectService.findMiddleProjectByName(projectNameList[i]).getId();
            //获取登录成功后的cookies
            RequestSampler requestSampler = middleTestHistoryService.findLoginInfoByProjectAndEnv(project_id, env_id);
            requestSampler.setProject_id(project_id);
            ResponseSampler loginInfo = prepare(requestSampler);
            //获取模块列表
            List<MiddleModule> middleModuleList = middleModuleService.findMiddleModuleByProjectId(project_id);
            for (MiddleModule module : middleModuleList) {
                //获取接口列表
                List<MiddleInterface> middleInterfaceList = middleInterfaceService.findMiddleInterfaceByModuleId(module.getId());
                for (MiddleInterface middleInterface : middleInterfaceList) {
                    //获取用例列表
                    List<MiddleCase> middleCaseList = middleCaseService.findMiddleCaseByEnvAndInterface(env_id, middleInterface.getId());
                    for (MiddleCase middleCase : middleCaseList) {

                        totalCase++;
                        requestSampler.setCookies(loginInfo.getCookies());
                        requestSampler.setUrl(middleInterface.getUrl());
                        requestSampler.setMethod(middleInterface.getRequest_method());
                        requestSampler.setEnv_id(middleCase.getEnv_id());
                        requestSampler.setProject_id(module.getProject_id());
                        if ("Post-Json".equals(middleInterface.getRequest_method())) {
                            JSONObject jsonObject = JSONObject.parseObject(middleCase.getRequest_data());
                            Map map = (Map) jsonObject;
                            requestSampler.setBody(map);
                        } else {
                            requestSampler.setParams(middleCase.getRequest_data());
                        }
                        //获取响应结果
                        ResponseSampler responseSampler = build(requestSampler);
                        boolean result = responseSampler.getResponse().contains(middleCase.getCheck_data());
                        logger.info("[result]==" + result);
                        if (result == true) {
                            passCase++;
                        } else {
                            failCase++;
                            totalResult = "FAIL";
                        }
                        MiddleRequestHistory middleRequestHistory = new MiddleRequestHistory();
                        middleRequestHistory.setInterfaceId(middleCase.getInterface_id());
                        middleRequestHistory.setRequestId(responseSampler.getRequestId());
                        middleRequestHistory.setUrl(middleInterface.getUrl());
                        middleRequestHistory.setCaseName(middleCase.getName());
                        middleRequestHistory.setRequestData(middleCase.getRequest_data());
                        middleRequestHistory.setEnv(middleCase.getEnv_id() + "");
                        middleRequestHistory.setStartTime(responseSampler.getStartTime());
                        middleRequestHistory.setEndTime(responseSampler.getEndTime());
                        middleRequestHistory.setResponseContent(responseSampler.getResponse());
                        middleRequestHistory.setResult(result == true ? "PASS" : "FAIL");
                        middleRequestHistory.setResponseCode(responseSampler.getResponseCode());
                        middleRequestHistoryList.add(middleRequestHistory);
                    }
                }
            }
            //统计总的运行数
            total += totalCase;
            pass += passCase;
            fail += failCase;

            //添加测试历史记录
            MiddleTestHistory middleTestHistory = new MiddleTestHistory();
            middleTestHistory.setProjectId(project_id);
            middleTestHistory.setProjectName(projectNameList[i]);
            middleTestHistory.setEnv(env_id);
            middleTestHistory.setResult(totalResult);
            middleTestHistory.setStartTime(startTime);
            middleTestHistory.setEndTime(getTime());
            middleTestHistory.setTotalCase(totalCase);
            middleTestHistory.setPassCase(passCase);
            middleTestHistory.setFailCase(failCase);
            middleTestHistoryService.insertMiddleTestHistory(middleTestHistory);
            int testHistoryId = middleTestHistoryService.getLastMiddleTestHistoryId();


            for (MiddleRequestHistory middleRequestHistory : middleRequestHistoryList) {
                middleRequestHistory.setHistoryId(testHistoryId);
                middleRequestHisoryService.insertMiddleRequestHistory(middleRequestHistory);
                if ("FAIL".equals(middleRequestHistory.getResult())) {
                    errorHistory.add(middleRequestHistory);
                }
            }
            middleTestHistory.setData(errorHistory);
            middleTestHistoryList.add(middleTestHistory);
        }
        middleTestHistoryTotal.setTotalCase(total);
        middleTestHistoryTotal.setPassCase(pass);
        middleTestHistoryTotal.setFailCase(fail);
        middleTestHistoryTotal.setData(middleTestHistoryList);
        return middleTestHistoryTotal;


    }




}
