package com.okay.testcenter.controller.tool;


import com.alibaba.fastjson.JSONObject;
import com.okay.testcenter.domain.Env;
import com.okay.testcenter.domain.RetResponse;
import com.okay.testcenter.domain.RetResult;
import com.okay.testcenter.domain.middle.AddUserDetail;
import com.okay.testcenter.domain.middle.AddUserHistory;
import com.okay.testcenter.impl.middle.MiddleRunServiceImpl;
import com.okay.testcenter.service.middle.EnvService;
import com.okay.testcenter.service.tool.AddUserDetailService;
import com.okay.testcenter.service.tool.AddUserHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import static com.okay.testcenter.tools.GetTime.getTime;


/**
 * @author asia
 * @date 2019-10-22
 */
@Api(description = "添加账号接口")
@Controller
@Validated
@RequestMapping(value = "/perf/middle/account")
public class AddAccountController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Resource
    AddUserHistoryService addUserHistoryService;
    @Resource
    AddUserDetailService addUserDetailService;
    @Resource
    MiddleRunServiceImpl middleRunService;
    @Resource
    EnvService envService;


    @GetMapping(value = "/history/{id}")
    public String historyDetail(Model model, @PathVariable(value = "id", required = true) int id) {

        List<AddUserDetail> userDetails = addUserDetailService.findDetailByHistoryId(id);
        model.addAttribute("userDetails", userDetails);
        return "data/dataList";
    }


    @GetMapping(value = "/history/list")
    public String list(Model model) {

        List<AddUserHistory> addUserHistoryList = addUserHistoryService.findHistoryList();
        model.addAttribute("addUserHistoryList", addUserHistoryList);

        List<Env> envList = envService.findEnvList();
        model.addAttribute("envList", envList);


        return "data/create";
    }

    @GetMapping(value = "/history/download/{id}")
    public void download(@PathVariable(value = "id", required = true) int id, HttpServletResponse response) throws IOException {

        List<AddUserDetail> addUserDetailList = addUserDetailService.findDetailByHistoryId(id);

        String fileName = "data.txt";
        StringBuilder stringBuilder = new StringBuilder();
        //在表中存放查询到的数据放入对应的列
        for (AddUserDetail detail : addUserDetailList) {
            if (detail.getToken() != null) {
                stringBuilder.append(detail.getAccount() + "," + detail.getToken() + "\n");

            } else {

                stringBuilder.append(detail.getAccount() + "," + detail.getCookies() + "\n");
            }

        }

        response.setContentType("text/plain");
        try {
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ServletOutputStream outputStream = null;
        BufferedOutputStream buffer = null;

        try {
            outputStream = response.getOutputStream();
            buffer = new BufferedOutputStream(outputStream);
            buffer.write(stringBuilder.toString().getBytes("UTF-8"));
            buffer.flush();
            buffer.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 批量添加账号和生成token/cookies
     *
     * @param
     * @return
     */
    @ApiOperation(value = "批量添加账号和生成token/cookies", notes = "批量添加账号和生成token/cookies")
    @GetMapping(value = "/generate")
    @ResponseBody
    public Object generate(@NotNull(message = "Id不能为空") @Min(value = 1, message = "ID不能小于1 ") @RequestParam("id") int id,
                           @NotNull(message = "账号类型不能为空") @NotBlank(message = "账号类型不能为空") @RequestParam("type") String type,
                           @NotNull(message = "数量不能为空") @Min(value = 1, message = "数量不能小于1") @RequestParam("count") Integer count,
                           @NotNull(message = "环境ID不能为空") @Min(value = 1, message = "环境ID不能小于1") @RequestParam("envId") Integer envId) {
        //TODO:用例ID需要根据不同的账号修改
        int caseId = 0;
        if ("学生".equals(type)) {

            if (1 == envId) {
                caseId = 3175;
            }
            if (2 == envId) {
                caseId = 1477;
            }
            if (4 == envId) {
                caseId = 7744;
            }


        } else {

            if (1 == envId) {
                caseId = 7743;
            }
            if (2 == envId) {
                caseId = 3176;
            }
            if (4 == envId) {
                caseId = 7742;
            }


        }
        Object addUserDetailList = new AddUserDetail();
        try {
            AddUserHistory history = addUserHistoryService.findHistoryByUserTypeAndEnvId(type, envId);
            List<AddUserDetail> addUserDetail = addUserDetailService.findDetailByHistoryId(history.getId());
            if (addUserDetail.size() != 0) {
                return RetResponse.makeErrRsp("数据已存在，请更新数据使用");
            }

            addUserDetailList = middleRunService.addAccunt(caseId, count, envId, type, id);

        } catch (Exception e) {
            logger.error("批量添加账号异常==" + e.getMessage());
            e.printStackTrace();
        }
        return JSONObject.toJSONString(addUserDetailList);
    }

    /**
     * 创建账号类型/数量/环境数据
     *
     * @param
     * @return
     */
    @ApiOperation(value = "创建账号数据", notes = "创建账号数据")
    @GetMapping(value = "/createData")
    @ResponseBody
    public Object createData(@NotNull(message = "账号类型不能为空") @NotBlank(message = "账号类型不能为空") @RequestParam("type") String type,
                             @NotNull(message = "数量不能为空") @Min(value = 1, message = "数量不能小于1") @RequestParam("count") Integer count,
                             @NotNull(message = "环境ID不能为空") @Min(value = 1, message = "环境ID不能小于1") @RequestParam("envId") Integer envId) {

        AddUserHistory history = addUserHistoryService.findHistoryByUserTypeAndEnvId(type, envId);
        if (history != null) {
            return RetResponse.makeErrRsp("数据已存在，请更新数据使用");
        }
        AddUserHistory addUserHistory = new AddUserHistory();
        addUserHistory.setTotalCount(count);
        addUserHistory.setUserType(type);
        addUserHistory.setCreateTime(getTime());
        addUserHistory.setUpdateTime(getTime());
        addUserHistory.setEnvId(envId);
        addUserHistoryService.insertHistory(addUserHistory);

        return RetResponse.makeOKRsp();
    }


    /**
     * 更新token/cookies
     *
     * @param
     * @return
     */
    @ApiOperation(value = "更新token/cookies", notes = "更新token/cookies")
    @GetMapping(value = "/updateAccountData")
    @ResponseBody
    public RetResult createData(@NotNull(message = "历史ID不能为空") @Min(value = 1, message = "历史ID不能小于1") @RequestParam("historyId") Integer historyId) {

        addUserHistoryService.updateHistoryData(historyId);
        return RetResponse.makeOKRsp("更新完成,请查看数据");
    }


}
