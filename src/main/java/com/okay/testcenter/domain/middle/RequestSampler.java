package com.okay.testcenter.domain.middle;


import com.okay.testcenter.domain.dubbo.DubboCase;

import java.util.HashMap;
import java.util.Map;

public class RequestSampler {


    private String requestId;
    private Map<String, String> cookies = new HashMap<>();
    private Map<String, Object> paramsList = new HashMap<>();
    private String url;
    private String params;
    private String contentType;
    private String token;
    private String productId;
    private String type;
    private String method;
    private String cookie;
    private String header;
    private int project_id;


    private String moreUser;

    private String projectName;
    private int env_id;
    private String uname;
    private String pwd;
    private int id;
    private String url_header;


    private int caseId;

    private String jmeterPath;


    private String jmeterDataPath;

    private String caseName;

    private int threads;
    private int runTime;

    private int run_id;

    private int set_id;
    private MiddleCase middleCase;

    private DubboCase dubboCase;

    private String loginParam;
    private String secretUrl;
    private int loginType;
    private int runType;
    private String loginUrl;
    private String requestPre;

    private String slaveStr;

    public String getSlaveStr() {
        return slaveStr;
    }

    public void setSlaveStr(String slaveStr) {
        this.slaveStr = slaveStr;
    }

    public DubboCase getDubboCase() {
        return dubboCase;
    }

    public void setDubboCase(DubboCase dubboCase) {
        this.dubboCase = dubboCase;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }
    public String getJmeterDataPath() {
        return jmeterDataPath;
    }

    public void setJmeterDataPath(String jmeterDataPath) {
        this.jmeterDataPath = jmeterDataPath;
    }

    public String getMoreUser() {
        return moreUser;
    }

    public void setMoreUser(String moreUser) {
        this.moreUser = moreUser;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getLoginParam() {
        return loginParam;
    }

    public void setLoginParam(String loginParam) {
        this.loginParam = loginParam;
    }

    public String getSecretUrl() {
        return secretUrl;
    }

    public void setSecretUrl(String secretUrl) {
        this.secretUrl = secretUrl;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public int getRunType() {
        return runType;
    }

    public void setRunType(int runType) {
        this.runType = runType;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getRequestPre() {
        return requestPre;
    }

    public void setRequestPre(String requestPre) {
        this.requestPre = requestPre;
    }

    public int getSet_id() {
        return set_id;
    }

    public void setSet_id(int set_id) {
        this.set_id = set_id;
    }

    public int getRun_id() {
        return run_id;
    }

    public void setRun_id(int run_id) {
        this.run_id = run_id;
    }

    public String getJmeterPath() {
        return jmeterPath;
    }

    public void setJmeterPath(String jmeterPath) {
        this.jmeterPath = jmeterPath;
    }
    public MiddleCase getMiddleCase() {
        return middleCase;
    }

    public void setMiddleCase(MiddleCase middleCase) {
        this.middleCase = middleCase;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    private Map<String, String> headers = new HashMap<>();
    private Map<Object, Object> body = new HashMap<>();


    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getEnv_id() {
        return env_id;
    }

    public void setEnv_id(int env_id) {
        this.env_id = env_id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<Object, Object> getBody() {
        return body;
    }

    public void setBody(Map<Object, Object> body) {
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }


    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }


    public String getUrl_header() {
        return url_header;
    }

    public void setUrl_header(String url_header) {
        this.url_header = url_header;
    }

    public Map<String, Object> getParamsList() {
        return paramsList;
    }

    public void setParamsList(Map<String, Object> paramsList) {
        this.paramsList = paramsList;
    }
}
