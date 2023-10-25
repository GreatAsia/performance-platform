package com.okay.testcenter.runner;

import com.okay.testcenter.domain.middle.RequestSampler;
import com.okay.testcenter.domain.middle.ResponseSampler;
import com.okay.testcenter.request.RequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * @author zhou
 * @date 2020/7/15
 */
public class WebRunner extends RunnerBase {

    private static final String CODE = "\"code\":0";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String mall = "smart-mall-server";

    @Override
    public ResponseSampler webLogin(RequestSampler requestInfo) {

        ResponseSampler responseSampler = new ResponseSampler();
        try {
            String uname = requestInfo.getUname();
            String pwd = requestInfo.getPwd();
            String urlHeader = requestInfo.getUrl_header();
            //登录
            RequestSampler requestSampler = new RequestSampler();
            requestSampler.setUrl(urlHeader + requestInfo.getLoginUrl());
            requestSampler.setRequestId(getRequestId(requestInfo.getRequestPre()));
            String loginInfo = requestInfo.getLoginParam().replace("${mname}", uname).replace("${mpwd}", pwd);
            requestSampler.setParams(loginInfo);
            requestSampler.setMethod("Post-Form");
            responseSampler = RequestFactory.build(requestSampler);

            if (responseSampler.getResponse().contains(CODE)) {
                logger.info(requestInfo.getProjectName() + "登录完成");
                responseSampler.setLoginResult(true);
            } else {
                logger.error(requestInfo.getProjectName() + "登录失败");
                responseSampler.setLoginResult(false);
            }
        } catch (Exception e) {
            logger.error("登录异常==" + e.getMessage());
            e.printStackTrace();
        }
        return responseSampler;


    }


    @Override
    public ResponseSampler runWebCase(RequestSampler requestInfo) {
        try {
            String requestId = getRequestId(requestInfo.getRequestPre());
            requestInfo.setRequestId(requestId);
            Map<String, String> headers = new HashMap<>();
            headers.put("requestid", requestId);
            headers.put("X-Requested-With", "XMLHttpRequest");
            if (mall.equals(requestInfo.getProjectName())) {
                headers.put("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 12_1_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/16C50 ;IOS/com.okay.education ;okay_version/(null)");
            } else {
                headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36");
            }
            requestInfo.setHeaders(headers);

            if (mall.equals(requestInfo.getProjectName())) {
                Map map = requestInfo.getBody();
                map.putAll(mallParam(requestInfo));
                requestInfo.setBody(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return run(requestInfo);
    }

}
