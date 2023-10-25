package com.okay.testcenter.controller.dubbo;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.Page;
import com.okay.testcenter.domain.RetResponse;
import com.okay.testcenter.domain.RetResult;
import com.okay.testcenter.domain.dubbo.DubboCase;
import com.okay.testcenter.domain.dubbo.DubboPerformance;
import com.okay.testcenter.domain.dubbo.DubboPerformanceSet;
import com.okay.testcenter.service.dubbo.DubboCaseService;
import com.okay.testcenter.service.dubbo.DubboPerformanceResultService;
import com.okay.testcenter.service.dubbo.DubboPerformanceService;
import com.okay.testcenter.service.dubbo.DubboPerformanceSetService;
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

@Api(description = "Dubbo压测管理接口")
@Controller
@RequestMapping(value = "/perf/dubbo/per")
@Validated
public class DubboPerformanceController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    DubboPerformanceService dubboPerformanceService;
    @Resource
    DubboCaseService dubboCaseService;
    @Resource
    DubboPerformanceSetService dubboPerformanceSetService;
    @Resource
    DubboPerformanceResultService dubboPerformanceResultService;


    @ApiOperation(value = "运行Dubbo压测计划", notes = "运行Dubbo压测计划")
    @GetMapping(value = "/run/{id}/{threads}/{time}")
    @ResponseBody
    public RetResult runPerformance(@PathVariable(value = "id", required = true) int id, @PathVariable(value = "threads", required = true) int threads, @PathVariable(value = "time", required = true) int time) {
        dubboPerformanceService.runSingle(id, threads, time);
        return RetResponse.makeOKRsp();
    }

    @ApiOperation(value = "Jmeter运行Dubbo压测计划", notes = "运行Dubbo压测计划")
    @GetMapping(value = "/run/jmeter")
    @ResponseBody
    public RetResult runJmeter(@NotNull(message = "caseId不能为空") @Min(value = 1, message = "caseId不能小于1") @RequestParam("caseId") Integer caseId,
                               @NotNull(message = "threadNum不能为空") @Min(value = 1, message = "threadNum不能小于1")
                               @RequestParam("threadNum") Integer threadNum,
                               @NotNull(message = "runTime") @Min(value = 1, message = "runTime不能小于1") @RequestParam("runTime") Integer runTime) {
        int runId = dubboPerformanceResultService.getLastRunId();
        dubboPerformanceService.runJmeter(caseId, threadNum, runTime, runId);
        return RetResponse.makeOKRsp("压测中，请压测完成查看结果");


    }


    @ApiOperation(value = "添加Dubbo压测计划", notes = "添加Dubbo压测计划")
    @PostMapping(value = "/insertPlan")
    @ResponseBody
    public RetResult<Object> insertPlan(@Validated @RequestBody DubboPerformance dubboPerformance) {

        List<DubboPerformance> performance = dubboPerformanceService.findByCaseId(dubboPerformance.getCase_id());
        if (performance.size() > 0) {
            return RetResponse.makeErrRsp("用例Id已添加");
        }

        DubboCase dubboCase = dubboCaseService.findDubboInterfaceById(dubboPerformance.getCase_id());
        if (dubboCase == null) {
            return RetResponse.makeErrRsp("用例Id不存在");
        }

        dubboPerformanceService.insert(dubboPerformance);
        return RetResponse.makeOKRsp();
    }


    @ApiOperation(value = "更新Dubbo压测计划", notes = "更新Dubbo压测计划")
    @PostMapping(value = "/updatePlan")
    @ResponseBody
    public RetResult<Object> updatePlan(@Validated @RequestBody DubboPerformance dubboPerformance) {
        DubboCase dubboCase = dubboCaseService.findDubboInterfaceById(dubboPerformance.getCase_id());
        if (dubboCase == null) {
            return RetResponse.makeErrRsp("用例Id不存在");
        }
        dubboPerformanceService.update(dubboPerformance);
        return RetResponse.makeOKRsp();
    }


    @ApiOperation(value = "删除Dubbo压测计划", notes = "删除Dubbo压测计划")
    @GetMapping(value = "/deletePlan")
    @ResponseBody
    public RetResult<Object> deletePlan(@NotNull(message = "id不能为空") @Min(value = 1, message = "id不能小于1") @RequestParam("id") Integer id) {

        dubboPerformanceService.delete(id);
        return RetResponse.makeOKRsp();
    }


    @GetMapping(value = "/list")
    public String list(Model model) {

        PageInfo pageInfo = dubboPerformanceService.findList(1, 10);
        model.addAttribute("pageInfo", pageInfo);
        return "dubbo/DubboPerformance";
    }

    @ApiOperation(value = "Dubbo压测计划列表", notes = "Dubbo压测计划列表")
    @GetMapping(value = "/getlist")
    @ResponseBody
    public String getlist(@Validated Page page) {

        PageInfo pageInfo = dubboPerformanceService.findList(page.getCurrentPage(), page.getPageSize());
        return JSONObject.toJSONString(pageInfo);
    }


    @ApiOperation(value = "通过caseId查找Dubbo压测计划", notes = "通过caseId查找Dubbo压测计划")
    @GetMapping(value = "/findByCaseId")
    @ResponseBody
    public String findByCaseId(@NotNull(message = "caseId不能为空") @Min(value = 1, message = "caseId不能小于1") @RequestParam("caseId") Integer caseId) {

        List<DubboPerformance> list = dubboPerformanceService.findByCaseId(caseId);
        return JSONObject.toJSONString(list);
    }


    @ApiOperation(value = "添加Dubbo压测计划集合", notes = "添加Dubbo压测计划集合")
    @PostMapping(value = "/insertPlanSet")
    @ResponseBody
    public RetResult<Object> insertPlanSet(@Validated @RequestBody DubboPerformanceSet dubboPerformanceSet) {
        dubboPerformanceSetService.insert(dubboPerformanceSet);

        return RetResponse.makeOKRsp();
    }

    @ApiOperation(value = "更新Dubbo压测计划集合", notes = "更新Dubbo压测计划集合")
    @PostMapping(value = "/updatePlanSet")
    @ResponseBody
    public RetResult<Object> updatePlanSet(@Validated @RequestBody DubboPerformanceSet dubboPerformanceSet) {
        dubboPerformanceSetService.update(dubboPerformanceSet);
        return RetResponse.makeOKRsp();
    }

    @ApiOperation(value = "删除Dubbo压测计划集合", notes = "删除Dubbo压测计划集合")
    @GetMapping(value = "/deletePlanSet")
    @ResponseBody
    public RetResult<Object> deletePlanSet(@NotNull(message = "id不能为空") @Min(value = 1, message = "id不能小于1") @RequestParam("id") Integer id) {

        dubboPerformanceSetService.delete(id);
        return RetResponse.makeOKRsp();
    }


    @GetMapping(value = "/set/list")
    public String setList(Model model) {

        PageInfo pageInfo = dubboPerformanceSetService.findList(1, 10);
        model.addAttribute("pageInfo", pageInfo);
        return "dubbo/DubboPerformanceSet";
    }

    @ApiOperation(value = "通过名称查找Dubbo压测计划集合", notes = "通过名称查找Dubbo压测计划集合")
    @GetMapping(value = "/set/findBySetName")
    @ResponseBody
    public String findBySetName(@NotNull(message = "计划集合名称不能为空") @RequestParam("setName") String setName) {

        DubboPerformanceSet dubboPerformanceSet = dubboPerformanceSetService.findByName(setName);

        return JSONObject.toJSONString(dubboPerformanceSet);
    }


    @ApiOperation(value = "Dubbo压测计划集合列表", notes = "Dubbo压测计划集合列表")
    @GetMapping(value = "/set/getlist")
    @ResponseBody
    public String setGetlist(@Validated Page page) {

        PageInfo pageInfo = dubboPerformanceSetService.findList(page.getCurrentPage(), page.getPageSize());
        return JSONObject.toJSONString(pageInfo);
    }


    @ApiOperation(value = "运行Dubbo压测计划集合", notes = "运行Dubbo压测计划集合")
    @GetMapping(value = "/run/set")
    @ResponseBody
    public RetResult runSet(@NotNull(message = "集合ID不能为空") @RequestParam("setId") int setId) {

        dubboPerformanceService.runJmeterSet(setId);
        return RetResponse.makeOKRsp("压测中,请稍后查看结果");
    }

    @ApiOperation(value = "停止Jmeter运行", notes = "停止Jmeter运行")
    @GetMapping(value = "/stop")
    @ResponseBody
    public RetResult stop() {

        dubboPerformanceService.stopJmeter();
        return RetResponse.makeOKRsp("Jmeter已停止");
    }


}
