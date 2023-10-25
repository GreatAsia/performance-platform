package com.okay.testcenter.tools.singlelogin;

import com.okay.testcenter.tools.RequestId;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static io.restassured.config.RestAssuredConfig.config;


//单点登录
public class SingleLogin {


    private String url;
    private String userName;
    private String passWord;
    private String ssoUrl;
    private Map<String, String> cookies = new HashMap<>();
    private final static Logger logger = LoggerFactory.getLogger(SingleLogin.class);
    private boolean loginResult = false;
    private String requestId;

    public SingleLogin() {

    }

    /**
     * @param url      域名
     * @param userName 用户名
     * @param passWord 密码
     */
    public SingleLogin(String url, String userName, String passWord) {
        this.url = url;
        this.userName = userName;
        this.passWord = passWord;
        this.requestId = new RequestId().generateRequestId("01");
        logger.info("url==" + url);
        logger.info("userName==" + userName);
        logger.info("passWord==" + passWord);
        RestAssured.config = config().redirect(redirectConfig().followRedirects(false));


    }


    public static void main(String[] args) {

        new SingleLogin("https://jiaoshi-hotfix.xk12.cn", "61951058256", "Okay@123").login();

//        CookieResponse cookieResponse = new SingleLogin("https://111jigou-dev.xk12.cn", "62951278505", "Okay@123").login();
//        logger.info("requestId==" + cookieResponse.getRequestId());
//        new SingleLogin("https://jiaoshi-hotfix.xk12.cn", "61951058256", "Okay@123").login();
//
//        new SingleLogin("https://edu-hotfix.xk12.cn","61951280017","Okay@123").login();
    }



    /**
     * 登录成功返回cookies
     */
    public CookieResponse login() {

        CookieResponse cookieResponse = new CookieResponse();
        ssoUrl = judgeEnv(url);

        Map<String, String> loginCookies = getCookies();
        //判断是否登录成功，不成功则返回空
        if (url.contains("jiaoshi")) {
            loginResult = loginCookies.containsKey("teacher_id");

        } else if (url.contains("edu") || url.contains("public")) {
            loginResult = loginCookies.containsKey("public_id");

        } else if (url.contains("jigou") || url.contains("private")) {
            loginResult = loginCookies.containsKey("private_id");

        } else {
            logger.error("url is error");
        }

        if (loginResult == false) {
            logger.error(url + "  单点登录失败");
            cookieResponse.setResult("fail");

        } else {
            logger.info(url + "  单点登录成功");
            cookieResponse.setResult("pass");
            cookieResponse.setCookies(loginCookies);
            String host = url.split("//")[1].trim();
            CookieStore cookieStore = new BasicCookieStore();
            for (Object str : cookies.keySet()) {

                //给cookie设置超时时间 +1小时
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(Calendar.HOUR, 1);

                BasicClientCookie cookie = new BasicClientCookie(str.toString(), cookies.get(str));
                cookie.setDomain(host);
                cookie.setPath("/");
                cookie.setSecure(true);
                cookie.setVersion(0);
                cookie.setExpiryDate(cal.getTime());

                cookieStore.addCookie(cookie);
            }

            cookieResponse.setCookieStore(cookieStore);

        }
        cookieResponse.setRequestId(requestId);
        return cookieResponse;
    }


    /**
     * 判断环境
     *
     * @param url  域名
     * @return ssoUrl
     */
    private static String judgeEnv(String url) {
        logger.info("获取 ssoUrl");
        String ssoUrl = "";
        if (url.contains("hotfix")) {

            ssoUrl = "https://sso-hotfix.xk12.cn/login?service=" + url + "/";

        } else if (url.contains("dev")) {
            ssoUrl = "https://sso-dev.xk12.cn/login?service=" + url + "/";

        } else {

            ssoUrl = "https://sso.okjiaoyu.cn/login?service=" + url + "/";
        }
        logger.info("[ssoUrl]==" + ssoUrl);

        return ssoUrl;
    }


    //获取 lt/execution/_eventId
    public Map<String,String> getPageParams(){
        //获取  lt/execution/_eventId
        logger.info("获取 lt/execution/_eventId");
        Map<String, String> params = new HashMap<>();
        try {
            params = new Weather(ssoUrl).run();
            loginResult = true;
        } catch (Exception e) {
            logger.error("获取lt/execution/_eventId数据出错:" + e.getLocalizedMessage());
            e.printStackTrace();
            loginResult = false;
        }

        return params;
    }


    //获取cookies
    public Map<String, String> getCookies() {
        //第一次请求获取参数
        Map<String, String> params = getPageParams();
        //如果获取lt/execution/_eventId失败，直接返回空
        if(loginResult == false){
            return new HashMap<>();
        }
        //获取 TGC 数据
        String platformType = "";
        if (url.contains("jiaoshi")) {
            platformType = "teacher";

        } else if (url.contains("edu") || url.contains("public")) {
            platformType = "org";

        } else if (url.contains("jigou") || url.contains("private")) {

            platformType = "org";
        } else {
            logger.error("platformType is error");
        }
        logger.info("获取 locationURL");
        Map<String, String> requestParam = new HashMap<>();
        requestParam.put("loginType", "1");
        //teacher/org
        requestParam.put("platformType", platformType);
        requestParam.put("username", userName);
        requestParam.put("password", passWord);
        //performance
        requestParam.put("pictureVerifyCode", "performance");
        requestParam.put("phone", "");
        requestParam.put("traceno", "");
        requestParam.put("phoneVerifyCode", "");
        requestParam.put("lt", params.get("lt"));
        requestParam.put("execution", params.get("execution"));
        requestParam.put("_eventId", params.get("_eventId"));
        Response response;
        String location = "";
        //第二次请求获取Location
        try{
             response = post(ssoUrl, cookies, requestParam);
             if(302 != response.getStatusCode()){
                 loginResult = false;
                 logger.error("获取location失败");
                 logger.error("requestId:" + requestId);
                 logger.error("响应内容:" + response.asString());
                 return new HashMap<>();
             }
            location = response.getHeaders().get("Location").getValue();
            logger.info("[locationURL]==" + location);
            logger.info("requestId:" + requestId);
            cookies.putAll(response.getCookies());
            loginResult = true;

        }catch (Exception e){
            logger.error("获取locationURL异常:" + e.getLocalizedMessage());
            logger.error("requestId:" + requestId);
            logger.error(ExceptionUtils.getStackTrace(e));
            e.printStackTrace();
            return new HashMap<>();
        }
        //第三次请求通过Location获取cookie
        Response ticketResponse = null;
        try{
            ticketResponse = get(new HashMap<String, String>(), location);
            if(302 != ticketResponse.getStatusCode()){
                loginResult = false;
                logger.error("获取cookies失败");
                logger.error("requestId:" + requestId);
                logger.error("响应内容:" + response.asString());
                return new HashMap<>();
            }
            logger.info("[cookies]==" + ticketResponse.getCookies());
            logger.info("requestId:" + requestId);
            cookies.putAll(ticketResponse.getCookies());
            loginResult = true;
        }catch (Exception e){
            logger.error("获取cookies异常:" + e.getLocalizedMessage());
            logger.error("requestId:" + requestId);
            logger.error(ExceptionUtils.getStackTrace(e));
            e.printStackTrace();
            return new HashMap<>();
        }

        return ticketResponse.getCookies();
    }


    public Response post(String host, Map<String, String> cookies, Map<String, String> params) {
        RestAssured.useRelaxedHTTPSValidation();
        Response response = null;
        try {
            response = given()
                    .cookies(cookies)
                    .header("requestId", requestId)
                    .contentType(ContentType.URLENC)
                    .params(params)
                    .when()
                    .post(host);

        } catch (Exception e) {
            logger.error("[requestFail]==" + e.getLocalizedMessage());
            logger.error("requestId:" + requestId);
            logger.error(ExceptionUtils.getStackTrace(e));
            e.printStackTrace();
        }
        logger.info("[响应时间]==" + response.getTime());

        return response;
    }


    /**
     * GET请求方法
     *
     * @param getPath
     * @return
     */
    public Response get(Map<String, String> cookies, String getPath) {

        RestAssured.useRelaxedHTTPSValidation();
        Response response = null;
        try {
            response = given()
                    .cookies(cookies)
                    .header("requestId",requestId)
                    .when()
                    .get(getPath);

        } catch (Exception e) {
            logger.error("[requestFail]==" + e.getLocalizedMessage());
            logger.error("requestId:" + requestId);
            logger.error(ExceptionUtils.getStackTrace(e));
            e.printStackTrace();
        }
        logger.info("[响应时间]==" + response.getTime());


        return response;
    }
}
