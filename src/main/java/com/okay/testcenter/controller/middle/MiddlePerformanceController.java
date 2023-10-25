package com.okay.testcenter.controller.middle;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.Page;
import com.okay.testcenter.domain.RetResponse;
import com.okay.testcenter.domain.RetResult;
import com.okay.testcenter.domain.middle.MiddleCase;
import com.okay.testcenter.domain.middle.MiddlePerformance;
import com.okay.testcenter.domain.middle.MiddlePerformanceSet;
import com.okay.testcenter.service.middle.MiddleCaseService;
import com.okay.testcenter.service.middle.MiddlePerformanceResultService;
import com.okay.testcenter.service.middle.MiddlePerformanceService;
import com.okay.testcenter.service.middle.MiddlePerformanceSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Api(description = "中间层压测管理接口")
@Controller
@RequestMapping(value = "/perf/middle/per")
@Validated
public class MiddlePerformanceController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    MiddlePerformanceService middlePerformanceService;
    @Resource
    MiddleCaseService middleCaseService;
    @Resource
    MiddlePerformanceSetService middlePerformanceSetService;
    @Resource
    MiddlePerformanceResultService middlePerformanceResultService;


    @ApiOperation(value = "运行中间层压测计划", notes = "运行中间层压测计划")
    @GetMapping(value = "/run/{id}/{threads}/{time}")
    @ResponseBody
    public synchronized RetResult runPerformance(@PathVariable(value = "id", required = true) int id, @PathVariable(value = "threads", required = true) int threads, @PathVariable(value = "time", required = true) int time) {
        middlePerformanceService.runSingle(id, threads, time);
        return RetResponse.makeOKRsp();
    }

    @ApiOperation(value = "Jmeter运行中间层压测计划", notes = "运行中间层压测计划")
    @GetMapping(value = "/run/jmeter")
    @ResponseBody
    public RetResult runJmeter(@NotNull(message = "caseId不能为空") @Min(value = 1, message = "caseId不能小于1") @RequestParam("caseId") Integer caseId,
                               @NotNull(message = "threadNum不能为空") @Min(value = 1, message = "threadNum不能小于1") @RequestParam("threadNum") Integer threadNum,
                               @NotNull(message = "runTime") @Min(value = 1, message = "runTime不能小于1") @RequestParam("runTime") Integer runTime,
                               @NotNull(message = "moreUser") @NotNull(message = "moreUser不能为空") @RequestParam("moreUser") String moreUser,
                               @RequestParam("slaveStr") String slaveStr) {
        int runId = 0;
        try {
             runId = middlePerformanceResultService.getLastRunId();

        }catch (Exception e){
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            RetResponse.makeErrRsp("压测发生异常==" + e.getLocalizedMessage());
        } finally {
            if(runId == 0){
                runId = 1 ;
            }else {
                runId = runId + 1;
            }
            middlePerformanceService.runJmeter(caseId, threadNum, runTime, runId, moreUser, slaveStr);
            return RetResponse.makeOKRsp("压测中，请压测完成查看结果");
        }


    }


    @ApiOperation(value = "添加中间层压测计划", notes = "添加中间层压测计划")
    @PostMapping(value = "/insertPlan")
    @ResponseBody
    public RetResult<Object> insertPlan(@Validated @RequestBody MiddlePerformance middlePerformance) {

        List<MiddlePerformance> performance = middlePerformanceService.findByCaseId(middlePerformance.getCase_id());
        if (performance.size() > 0) {
            return RetResponse.makeErrRsp("用例Id已存在");
        }
        MiddleCase middleCase = middleCaseService.findMiddleCaseById(middlePerformance.getCase_id());
        if (middleCase == null) {
            return RetResponse.makeErrRsp("用例Id不存在");
        }
        //不允许添加线上环境case进行压测
        if(middleCase.getEnv_id() == 3){
            return RetResponse.makeErrRsp("不允许添加线上环境的用例");
        }
        middlePerformanceService.insert(middlePerformance);
        return RetResponse.makeOKRsp();
    }


    @ApiOperation(value = "更新中间层压测计划", notes = "更新中间层压测计划")
    @PostMapping(value = "/updatePlan")
    @ResponseBody
    public RetResult<Object> updatePlan(@Validated @RequestBody MiddlePerformance middlePerformance) {
        MiddleCase middleCase = middleCaseService.findMiddleCaseById(middlePerformance.getCase_id());
        if (middleCase == null) {
            return RetResponse.makeErrRsp("用例Id不存在");
        }
        //不允许添加线上环境case进行压测
        if(middleCase.getEnv_id() == 3){
            return RetResponse.makeErrRsp("不允许添加线上环境的用例");
        }
        middlePerformanceService.update(middlePerformance);
        return RetResponse.makeOKRsp();
    }


    @ApiOperation(value = "删除中间层压测计划", notes = "删除中间层压测计划")
    @GetMapping(value = "/deletePlan")
    @ResponseBody
    public RetResult<Object> deletePlan(@NotNull(message = "id不能为空") @Min(value = 1, message = "id不能小于1") @RequestParam("id") Integer id) {

        middlePerformanceService.delete(id);
        return RetResponse.makeOKRsp();
    }


    @GetMapping(value = "/list")
    public String list(Model model) {

        PageInfo pageInfo = middlePerformanceService.findList(1, 10);
        model.addAttribute("pageInfo", pageInfo);
        return "middle/MiddlePerformance";
    }

    @ApiOperation(value = "中间层压测计划列表", notes = "中间层压测计划列表")
    @GetMapping(value = "/getlist")
    @ResponseBody
    public String getlist(@Validated Page page) {

        PageInfo pageInfo = middlePerformanceService.findList(page.getCurrentPage(), page.getPageSize());
        return JSONObject.toJSONString(pageInfo);
    }


    @ApiOperation(value = "通过caseId查找中间层压测计划", notes = "通过caseId查找中间层压测计划")
    @GetMapping(value = "/findByCaseId")
    @ResponseBody
    public String findByCaseId(@NotNull(message = "caseId不能为空") @Min(value = 1, message = "caseId不能小于1") @RequestParam("caseId") Integer caseId) {

        List<MiddlePerformance> middlePerformance = middlePerformanceService.findByCaseId(caseId);
        return JSONObject.toJSONString(middlePerformance);
    }




    @ApiOperation(value = "添加中间层压测计划集合", notes = "添加中间层压测计划集合")
    @PostMapping(value = "/insertPlanSet")
    @ResponseBody
    public RetResult<Object> insertPlanSet(@Validated @RequestBody MiddlePerformanceSet middlePerformanceSet) {
        middlePerformanceSetService.insert(middlePerformanceSet);

        return RetResponse.makeOKRsp();
    }

    @ApiOperation(value = "更新中间层压测计划集合", notes = "更新中间层压测计划集合")
    @PostMapping(value = "/updatePlanSet")
    @ResponseBody
    public RetResult<Object> updatePlanSet(@Validated @RequestBody MiddlePerformanceSet middlePerformanceSet) {
        middlePerformanceSetService.update(middlePerformanceSet);
        return RetResponse.makeOKRsp();
    }

    @ApiOperation(value = "删除中间层压测计划集合", notes = "删除中间层压测计划集合")
    @GetMapping(value = "/deletePlanSet")
    @ResponseBody
    public RetResult<Object> deletePlanSet(@NotNull(message = "id不能为空") @Min(value = 1, message = "id不能小于1") @RequestParam("id") Integer id) {

        middlePerformanceSetService.delete(id);
        return RetResponse.makeOKRsp();
    }



    @GetMapping(value = "/set/list")
    public String setList(Model model) {

        PageInfo pageInfo = middlePerformanceSetService.findList(1, 10);
        model.addAttribute("pageInfo", pageInfo);
        return "middle/MiddlePerformanceSet";
    }

    @ApiOperation(value = "通过名称查找中间层压测计划集合", notes = "通过名称查找中间层压测计划集合")
    @GetMapping(value = "/set/findBySetName")
    @ResponseBody
    public String findBySetName(@NotNull(message = "计划集合名称不能为空")  @RequestParam("setName") String setName) {

        MiddlePerformanceSet middlePerformanceSet = middlePerformanceSetService.findByName(setName);

        return JSONObject.toJSONString(middlePerformanceSet);
    }


    @ApiOperation(value = "中间层压测计划集合列表", notes = "中间层压测计划集合列表")
    @GetMapping(value = "/set/getlist")
    @ResponseBody
    public String setGetlist(@Validated Page page) {

        PageInfo pageInfo = middlePerformanceSetService.findList(page.getCurrentPage(), page.getPageSize());
        return JSONObject.toJSONString(pageInfo);
    }


    @ApiOperation(value = "运行中间层压测计划集合", notes = "运行中间层压测计划集合")
    @GetMapping(value = "/run/set")
    @ResponseBody
    public synchronized RetResult runSet(@NotNull(message = "集合ID不能为空") @RequestParam("setId") int setId) {

        middlePerformanceService.runJmeterSet(setId);
        return RetResponse.makeOKRsp("压测中,请稍后查看结果");
    }

    @ApiOperation(value = "停止Jmeter运行", notes = "停止Jmeter运行")
    @GetMapping(value = "/stop")
    @ResponseBody
    public RetResult stop() {

        middlePerformanceService.stopJmeter();
        return RetResponse.makeOKRsp("Jmeter已停止");
    }



}
