package com.okay.testcenter.service.tool;

import com.alibaba.fastjson.JSONObject;

public interface SendEmailService {

    /**
     * 发送邮件
     * @param request 请求参数
     */
    Boolean sendEmail(JSONObject request);




}