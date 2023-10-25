package com.okay.testcenter.controller.tool;

import com.alibaba.fastjson.JSONObject;
import com.okay.testcenter.domain.RetResponse;
import com.okay.testcenter.domain.RetResult;
import com.okay.testcenter.service.tool.SendEmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(description = "发送邮件接口")
@Controller
public class SendEmailController {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SendEmailService sendEmailService;


    @ApiOperation(value = "发送邮件", notes = "发送邮件")
    @PostMapping(value = "/sendEmail")
    @ResponseBody
    public RetResult<Object> sendEmail(@Validated @RequestBody JSONObject request) {


        Boolean result = sendEmailService.sendEmail(request);
        if (result == true) {
            return RetResponse.makeOKRsp();
        } else {
            return RetResponse.makeErrRsp("发送邮件失败");
        }

    }


}
