package com.okay.testcenter.impl.middle;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.okay.testcenter.domain.RetResponse;
import com.okay.testcenter.domain.middle.*;
import com.okay.testcenter.request.RequestFactory;
import com.okay.testcenter.service.middle.*;
import com.okay.testcenter.service.tool.AddUserDetailService;
import com.okay.testcenter.tools.GetTime;
import com.okay.testcenter.tools.MailInfo;
import com.okay.testcenter.tools.MailUtil;
import org.apache.commons.mail.EmailAttachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

import static com.okay.testcenter.runner.RunnerFactory.build;
import static com.okay.testcenter.runner.RunnerFactory.prepare;
import static com.okay.testcenter.tools.GetTime.getTime;


@Service("MiddleRunService")
public class MiddleRunServiceImpl implements MiddleRunService {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${logging.path}")
    private String logPath;

    @Resource
    MiddleModuleService middleModuleService;
    @Resource
    MiddleInterfaceService middleInterfaceService;
    @Resource
    MiddleCaseService middleCaseService;
    @Resource
    MiddleTestHistoryService middleTestHistoryService;
    @Resource
    MiddleRequestHisoryService middleRequestHisoryService;
    @Resource
    AddUserDetailService addUserDetailService;
    @Resource
    MiddleProjectService middleProjectService;

    @Override
    public Object debugInterface(JSONObject request) {

        RequestSampler requestSampler = new RequestSampler();
        ResponseSampler response;
        Map bodyContent = new HashMap();
        String mehtod = request.getString("method");
        String url = request.getString("url");
        String header = request.getString("header");
        String params = request.getString("params");
        String body = request.getString("body");
        if (!body.isEmpty()) {
            bodyContent = JSON.parseObject(body, Map.class);
        }
        String cookie = request.getString("cookie");

        requestSampler.setMethod(mehtod);
        requestSampler.setUrl(url);
        requestSampler.setParams(params);
        requestSampler.setBody(bodyContent);
        requestSampler.setHeader(header);

        //处理headers
        Map<String, String> headerMap = new HashMap<>();
        if (!header.isEmpty()) {
            String[] headers = header.split(";");
            for (int j = 0; j < headers.length; j++) {
                String[] mapHeader = headers[j].split(":");
                headerMap.put(mapHeader[0], mapHeader[1]);
            }
            requestSampler.setHeaders(headerMap);
        }
        //处理cookies
        Map<String, String> cookieMap = new HashMap<>();
        if (!cookie.isEmpty()) {
            String[] cookies = cookie.split(";");
            for (int i = 0; i < cookies.length; i++) {
                String[] mapCookie = cookies[i].split("=");
                cookieMap.put(mapCookie[0], mapCookie[1]);
            }
            requestSampler.setCookies(cookieMap);
        }
        //发送请求
        response = RequestFactory.build(requestSampler);
        return JSONObject.toJSONString(response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object runSingle(int id) {

        MiddleCase middleCase = middleCaseService.findMiddleCaseById(id);
        MiddleInterface middleInterface = middleInterfaceService.findMiddleInterfaceById(middleCase.getInterface_id());
        MiddleModule middleModule = middleModuleService.findMiddleModuleById(middleInterface.getModule_id());
        MiddleProject middleProject = middleProjectService.findMiddleProjectById(middleModule.getProject_id());
        RequestSampler requestSampler = middleTestHistoryService.findLoginInfoByProjectAndEnv(middleModule.getProject_id(), middleCase.getEnv_id());
        if (requestSampler == null) {
            return RetResponse.makeErrRsp("请添加账号信息!!!");
        }
        requestSampler.setProject_id(middleModule.getProject_id());
        requestSampler.setLoginParam(middleProject.getLoginParam());
        requestSampler.setSecretUrl(middleProject.getSecretUrl());
        requestSampler.setLoginType(middleProject.getLoginType());
        requestSampler.setRunType(middleProject.getRunType());
        requestSampler.setLoginUrl(middleProject.getLoginUrl());
        requestSampler.setProjectName(middleProject.getName());
        requestSampler.setRequestPre(middleProject.getRequestPre());
        //登录
        ResponseSampler loginInfo = prepare(requestSampler);

        if (false == loginInfo.getLoginResult()) {
            loginFailSendEmail(requestSampler, loginInfo);
            return RetResponse.makeErrRsp("登录失败，请查看邮件");
        }
        if ("Post-Json".equals(middleInterface.getRequest_method())) {
            Map jsonObject = JSONObject.parseObject(middleCase.getRequest_data());
            requestSampler.setBody(jsonObject);
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
        requestSampler.setMiddleCase(middleCase);
        //获取响应结果
        ResponseSampler responseSampler = build(requestSampler);
        return responseSampler;
    }


    /**
     * 添加账号的方法
     *
     * @param id    用例Id
     * @param count 添加的个数
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object addAccunt(int id, int count, int envId, String type, int historyId) {
        MiddleCase middleCase = new MiddleCase();
        MiddleInterface middleInterface = new MiddleInterface();
        MiddleModule middleModule = new MiddleModule();
        MiddleProject middleProject = new MiddleProject();
        middleCase = middleCaseService.findMiddleCaseById(id);
        middleInterface = middleInterfaceService.findMiddleInterfaceById(middleCase.getInterface_id());
        middleModule = middleModuleService.findMiddleModuleById(middleInterface.getModule_id());
        middleProject = middleProjectService.findMiddleProjectById(middleModule.getProject_id());

        ResponseSampler responseSampler = new ResponseSampler();
        RequestSampler requestSampler = new RequestSampler();
        requestSampler = getLoginReq(middleModule.getProject_id(), middleCase.getEnv_id(), middleProject);
        if (requestSampler == null) {
            responseSampler.setResponse("请添加账号信息!!!");
            logger.error("请添加账号信息!!!");
            return new ArrayList<>();
        }
        //登录
        ResponseSampler loginInfo = prepare(requestSampler);
        if (false == loginInfo.getLoginResult()) {
            responseSampler.setResponse("登录失败，请查看邮件!!!");
            logger.error("请添加账号信息!!!");
            return new ArrayList<>();
        }
        setRequestInfo(requestSampler, middleCase, middleInterface, loginInfo, middleModule.getProject_id());

        //获取响应结果
        List<AddUserDetail> addUserDetailList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            if (i == 0) {
                responseSampler = build(requestSampler);
            } else {
                responseSampler = RequestFactory.build(requestSampler);
            }

            String response = responseSampler.getResponse();
            if (!response.contains("\"code\":0")) {
                logger.error("创建账号失败==" + response);
                break;
            }
            JSONObject accountInfo = JSON.parseObject(response);
            JSONObject data = JSON.parseObject(accountInfo.getString("data"));
            long account;
            if ("学生".equals(type)) {
                account = (long) data.get("system_id");
            } else {
                account = (long) data.get("systemId");
            }

            String pwd = data.getString("password");

            AddUserDetail addUserDetail = userLogin(envId, type, account, pwd);
            if (addUserDetail.getPwd() != null) {
                addUserDetail.setHistoryId(historyId);
                addUserDetail.setUpdateTime(getTime());
                addUserDetailService.insertDetail(addUserDetail);
                addUserDetailList.add(addUserDetail);
            }

        }

        return addUserDetailList;
    }


    /**
     * 登录账号
     *
     * @param type
     * @param account
     * @param pwd
     * @return
     */
    public AddUserDetail userLogin(int envId, String type, long account, String pwd) {

        logger.info("登录账号===envId=" + envId + "  type=" + type + " account=" + account + " pwd=" + pwd);
        int projectId = 0;
        if ("学生".equals(type)) {
            projectId = 2;
        } else if ("老师".equals(type)) {
            projectId = 1;
        } else {
            logger.error("type 错误=" + type);
        }
        MiddleProject middleProject = middleProjectService.findMiddleProjectById(projectId);
        RequestSampler reqLogin = getLoginReq(projectId, envId, middleProject);
        if (reqLogin == null) {
            logger.error("请在信息管理模块添加项目对应环境的账号信息!!!");
            return new AddUserDetail();
        }
        reqLogin.setUname(String.valueOf(account));
        reqLogin.setPwd(pwd);
        reqLogin.setProject_id(projectId);
        ResponseSampler loginInfo = prepare(reqLogin);
        if (false == loginInfo.getLoginResult()) {
            logger.error("登录失败=={}", account);
            return new AddUserDetail();
        }

        AddUserDetail addUserDetail = new AddUserDetail();

        if ("学生".equals(type)) {
            String token = loginInfo.getToken();
            addUserDetail.setToken(token);
        } else {
            Map cookies = loginInfo.getCookies();
            Set<String> set = cookies.keySet();
            StringBuilder stringBuilder = new StringBuilder();
            for (String key : set) {
                stringBuilder.append(key).append("=")
                        .append(cookies.get(key).toString()).append(";");
            }
            String cookiesString = stringBuilder.toString().substring(0, stringBuilder.lastIndexOf(";"));
            addUserDetail.setCookies(cookiesString);
        }
        addUserDetail.setAccount(account);
        addUserDetail.setPwd(pwd);
        return addUserDetail;
    }




    public void loginFailSendEmail(RequestSampler requestSampler, ResponseSampler responseSampler) {
        //判断登录失败不进行运行用例，直接发送邮件
        String errorLogPath = logPath + "/server-error." + GetTime.getYmd() + ".log";
        MailInfo mailInfo = new MailInfo();
        List<String> toList = new ArrayList<>();
        toList.add("zhangyazhou@okay.cn");
        toList.add("huzhiqiang@okay.cn");
        toList.add("zhangchenglin@okay.cn");
        toList.add("zhudong@okay.cn");
        toList.add("shenbingbing@okay.cn");
        toList.add("fankaiqiang@okay.cn");
        toList.add("xieyangyang@okay.cn");
        //添加附件
        EmailAttachment att = new EmailAttachment();
        att.setPath(errorLogPath);
        att.setName("errorlog.txt");
        List<EmailAttachment> atts = new ArrayList<EmailAttachment>();
        atts.add(att);
        mailInfo.setAttachments(atts);
        //收件人
        mailInfo.setToAddress(toList);
        mailInfo.setSubject(requestSampler.getUrl_header() + "_登录失败");
        mailInfo.setContent("登录失败，请检查登录功能是否正常 <br>" +
                            "域名:" + requestSampler.getUrl_header() + "<br>"  +
                            "用户名:" + requestSampler.getUname() +  "<br>"  +
                            "requestId:" + responseSampler.getRequestId() + "<br>"  +
                            "响应内容:" + responseSampler.getResponse());

        MailUtil.sendEmail(mailInfo);


    }


    /**
     * 获取登录的信息
     *
     * @param projectId
     * @param envId
     * @param middleProject
     * @return
     */
    public RequestSampler getLoginReq(int projectId, int envId, MiddleProject middleProject) {
        RequestSampler loginReq = new RequestSampler();
        loginReq = middleTestHistoryService.findLoginInfoByProjectAndEnv(projectId, envId);

        if (loginReq.getPwd() != null) {
            loginReq.setProject_id(projectId);
            loginReq.setLoginParam(middleProject.getLoginParam());
            loginReq.setSecretUrl(middleProject.getSecretUrl());
            loginReq.setLoginType(middleProject.getLoginType());
            loginReq.setRunType(middleProject.getRunType());
            loginReq.setLoginUrl(middleProject.getLoginUrl());
            loginReq.setProjectName(middleProject.getName());
            loginReq.setRequestPre(middleProject.getRequestPre());
        }
        return loginReq;

    }

    /**
     * 设置请求参数
     *
     * @param requestSampler
     * @param middleCase
     * @param middleInterface
     * @param loginInfo
     * @param projectId
     */
    public void setRequestInfo(RequestSampler requestSampler, MiddleCase middleCase, MiddleInterface middleInterface, ResponseSampler loginInfo, int projectId) {

        if ("Post-Json".equals(middleInterface.getRequest_method())) {
            Map jsonObject = JSONObject.parseObject(middleCase.getRequest_data());
            requestSampler.setBody(jsonObject);
        } else {
            requestSampler.setParams(middleCase.getRequest_data());
        }
        requestSampler.setUname(loginInfo.getuName());
        requestSampler.setToken(loginInfo.getToken());
        requestSampler.setCookies(loginInfo.getCookies());
        requestSampler.setUrl(middleInterface.getUrl());
        requestSampler.setMethod(middleInterface.getRequest_method());
        requestSampler.setEnv_id(middleCase.getEnv_id());
        requestSampler.setProject_id(projectId);
        requestSampler.setCaseName(middleCase.getName());
    }


}
