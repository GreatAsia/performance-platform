package com.okay.testcenter.performance.base;

import com.alibaba.fastjson.JSONObject;
import com.okay.testcenter.domain.RetResponse;
import com.okay.testcenter.domain.middle.*;
import com.okay.testcenter.domain.report.MiddleRequestHistory;
import com.okay.testcenter.domain.report.MiddleTestHistory;
import com.okay.testcenter.request.PostBinaryRequest;
import com.okay.testcenter.request.PostHtmlRequest;
import com.okay.testcenter.request.PostTextRequest;
import com.okay.testcenter.request.PostXmlRequest;
import com.okay.testcenter.runner.AppRunner;
import com.okay.testcenter.runner.RunnerBase;
import com.okay.testcenter.runner.WebRunner;
import com.okay.testcenter.service.middle.*;
import com.okay.testcenter.tools.RequestId;
import com.okay.testcenter.tools.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static com.okay.testcenter.runner.RunnerFactory.prepare;


public class CaseInit implements Serializable,Cloneable {

    private static final Logger logger = LoggerFactory.getLogger(CaseInit.class);

    private static final long serialVersionUID = -9023144886230583498L;

    //获取ApplicationContext的实例
    private ApplicationContext applicationContext = SpringUtils.getApplicationContext();

    MiddleInterfaceService middleInterfaceService = applicationContext.getBean(MiddleInterfaceService.class);

    MiddleTestHistoryService middleTestHistoryService = applicationContext.getBean(MiddleTestHistoryService.class);

    MiddleCaseService middleCaseService = applicationContext.getBean(MiddleCaseService.class);

    private MiddleModuleService middleModuleService = applicationContext.getBean(MiddleModuleService.class);

    private MiddleRunService service = applicationContext.getBean(MiddleRunService.class);

    public RequestSampler requestSampler;

    private CaseInit() {

    }

    public CaseInit(int id) {
        runSingle(id);
    }

    public Object runSingle(int id) {
        MiddleCase middleCase = middleCaseService.findMiddleCaseById(id);
        MiddleInterface middleInterface = middleInterfaceService.findMiddleInterfaceById(middleCase.getInterface_id());
        MiddleModule middleModule = middleModuleService.findMiddleModuleById(middleInterface.getModule_id());

        requestSampler = middleTestHistoryService.findLoginInfoByProjectAndEnv(middleModule.getProject_id(), middleCase.getEnv_id());
        if (requestSampler == null) {
            return RetResponse.makeErrRsp("请在信息管理模块添加项目对应环境的账号信息!!!");
        }
        requestSampler.setProject_id(middleModule.getProject_id());
        ResponseSampler loginInfo = prepare(requestSampler);
        //判断登录失败不进行运行用例，直接发送邮件
        if (false == loginInfo.getLoginResult()) {
            return RetResponse.makeErrRsp("登录失败，请查看邮件");
        }
        if ("Post-Json".equals(middleInterface.getRequest_method())) {
            JSONObject jsonObject = JSONObject.parseObject(middleCase.getRequest_data());
            Map map = jsonObject;
            requestSampler.setBody(map);
        } else {
            requestSampler.setParams(middleCase.getRequest_data());
        }
        requestSampler.setUname(loginInfo.getuName());
        requestSampler.setToken(loginInfo.getToken());
        requestSampler.setCookies(loginInfo.getCookies());
        requestSampler.setUrl(middleInterface.getUrl());
        requestSampler.setMethod(middleInterface.getRequest_method());
        requestSampler.setEnv_id(middleCase.getEnv_id());
        requestSampler.setProject_id(middleModule.getProject_id());
        requestSampler.setCaseName(middleCase.getName());
        //获取响应结果
        ResponseSampler responseSampler = build(requestSampler);
        logger.info("运行完成");
        MiddleTestHistory middleTestHistory = new MiddleTestHistory();
        MiddleRequestHistory middleRequestHistory = new MiddleRequestHistory();
        //添加测试历史记录
//        middleTestHistory.setProjectId(middleModule.getProject_id());
//        middleTestHistory.setEnv(middleCase.getEnv_id());
//        middleTestHistory.setStartTime(responseSampler.getStartTime());
//        middleTestHistory.setEndTime(responseSampler.getEndTime());
//        middleTestHistory.setTotalCase(1);
//        boolean result = responseSampler.getResponse().contains(middleCase.getCheck_data());
//        if (result == true) {
//            middleTestHistory.setResult("PASS");
//            middleTestHistory.setPassCase(1);
//            middleTestHistory.setFailCase(0);
//        } else {
//            middleTestHistory.setResult("FAIL");
//            middleTestHistory.setPassCase(0);
//            middleTestHistory.setFailCase(1);
//        }
//        logger.info("用例总数==" + middleTestHistory.getTotalCase());
//        logger.info("用例PASS数==" + middleTestHistory.getPassCase());
//        logger.info("用例FAIL数==" + middleTestHistory.getFailCase());
//        middleTestHistoryService.insertMiddleTestHistory(middleTestHistory);

        logger.info("数据库添加数据完成");

        return responseSampler.getResponse();
    }

    public static ResponseSampler runCase(RequestSampler requestSampler) {

        requestSampler.setRequestId(new RequestId().generateRequestId("02"));
        Map<String, String> headers = new HashMap<>();
        headers.put("requestid", requestSampler.getRequestId());
        requestSampler.setHeaders(headers);
        return run(requestSampler);


    }

    private Map<Object, Object> getReplaceParam(RequestSampler requestSampler) {
        Map<Object, Object> params = new HashMap<>();
        params.put("uid", requestSampler.getUname());
        params.put("token", requestSampler.getToken());
        params.putAll(getCommonParam());

        return params;
    }


    private Map<Object, Object> getCommonParam() {
        Map<Object, Object> params = new HashMap<>(20);

        params.put("vs", "1.2.0");
        params.put("vc", "35");
        params.put("ua", "aliyunos");
        params.put("os", "android");
        params.put("sw", "480");
        params.put("sh", "800");
        params.put("contype", 1);
        params.put("imei", "qwiuenqweqadas");
        params.put("mac", "qwe123123ada23111");
        params.put("channel", "168");
        params.put("udid", "uqwbejqweqwoeqnwekjasdadd");


        return params;
    }

    public static ResponseSampler run(RequestSampler requestSampler) {

        String urlHeader = requestSampler.getUrl_header();
        ResponseSampler responseSampler;
        requestSampler.setUrl(urlHeader + requestSampler.getUrl());
        //设置开始时间
        responseSampler = build2(requestSampler);
//        responseSampler.setStartTime(startTime);
//        responseSampler.setEndTime(getTime());
//        Long secondTime = System.currentTimeMillis();
//        Long elapsedTime = secondTime - firstTime;
//        responseSampler.setElapsedTime(String.valueOf(elapsedTime));
//        //把替换的token,uid的数据返回
//        if ("Post-Json".equals(requestSampler.getMethod())) {
//            responseSampler.setRequestData(JSONObject.toJSONString(requestSampler.getBody()));
//        } else {
//            responseSampler.setRequestData(requestSampler.getParams());
//        }
        return responseSampler;
    }

    public static ResponseSampler build(RequestSampler requestSampler) {
        ResponseSampler responseSampler;
        int runType = requestSampler.getRunType();
        switch (runType) {
            case 1:
                responseSampler = new WebRunner().runWebCase(requestSampler);
                break;
            case 2:
                responseSampler = new AppRunner().runAppCase(requestSampler);
                break;
            case 3:
                responseSampler = new RunnerBase().runDirectly(requestSampler);
                break;
            default:
                responseSampler = new RunnerBase().runDirectly(requestSampler);
                logger.info("default runner ");
                break;

        }
        return responseSampler;


    }

    public static ResponseSampler build2(RequestSampler requestSampler) {
        ResponseSampler responseSampler = new ResponseSampler();
        switch (requestSampler.getMethod()) {
            case "Post-Form":
                requestSampler.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
                responseSampler = Tool.postForm(requestSampler);
                break;
            case "Post-Json":
                requestSampler.setContentType("application/json");
                responseSampler = Tool.postJson(requestSampler);
                break;
            case "Post-Text":
                requestSampler.setContentType("text/plain");
                responseSampler = new PostTextRequest().post(requestSampler);
                break;
            case "Post-Html":
                requestSampler.setContentType("text/html");
                responseSampler = new PostHtmlRequest().post(requestSampler);
                break;
            case "Post-Xml":
                requestSampler.setContentType("application/xml");
                responseSampler = new PostXmlRequest().post(requestSampler);
                break;
            case "Post-Binary":
                requestSampler.setContentType("application/octet-stream");
                responseSampler = new PostBinaryRequest().post(requestSampler);
                break;
            case "Get":
                responseSampler = Tool.get(requestSampler);
                break;
            default:
                logger.error("request method error");
        }
        return responseSampler;


    }

    @Override
    public CaseInit clone() {
        try {
            return (CaseInit) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
