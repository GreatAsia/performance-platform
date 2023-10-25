package com.okay.testcenter.controller.slave;


import com.alibaba.fastjson.JSONObject;
import com.okay.testcenter.domain.RetResponse;
import com.okay.testcenter.domain.RetResult;
import com.okay.testcenter.domain.slave.StressTestSlaveEntity;
import com.okay.testcenter.service.slave.StressTestSlaveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Api(description = "分布式节点接口")
@Controller
@RequestMapping(value = "/perf/slave")
public class SlaveController {


    @Resource
    StressTestSlaveService slaveService;


    @GetMapping(value = "/list")
    public String listSlave(@Validated Model model) {

        List<StressTestSlaveEntity> slaveEntityList = slaveService.queryList();

        model.addAttribute("slaveEntityList", slaveEntityList);
        return "slave/SlaveList";

    }


    @ApiOperation(value = "删除节点", notes = "删除节点")
    @PostMapping(value = "/delete")
    @ResponseBody
    public RetResult<Object> deleteSlave(@Validated @RequestBody JSONObject request) {
        Long id = Long.parseLong(request.get("id").toString());
        Long[] ids = {id};
        slaveService.deleteBatch(ids);
        return RetResponse.makeOKRsp();
    }

    @ApiOperation(value = "添加节点", notes = "添加节点")
    @PostMapping(value = "/insert")
    @ResponseBody
    public RetResult<Object> insertSlave(@Validated @RequestBody StressTestSlaveEntity slaveEntity) {

        slaveService.save(slaveEntity);
        return RetResponse.makeOKRsp();

    }

    @ApiOperation(value = "更新节点", notes = "更新节点")
    @PostMapping(value = "/update")
    @ResponseBody
    public RetResult<Object> updateSlave(@Validated @RequestBody StressTestSlaveEntity slaveEntity) {

        slaveService.update(slaveEntity);
        return RetResponse.makeOKRsp();
    }


}
