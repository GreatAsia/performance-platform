package com.okay.testcenter.runner;


import com.alibaba.fastjson.JSONObject;
import com.okay.testcenter.domain.middle.RequestSampler;
import com.okay.testcenter.domain.middle.ResponseSampler;
import com.okay.testcenter.request.RequestFactory;
import com.okay.testcenter.tools.RSAEncodeUtils;
import com.okay.testcenter.tools.RequestId;
import com.okay.testcenter.tools.singlelogin.CookieResponse;
import com.okay.testcenter.tools.singlelogin.SingleLogin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.okay.testcenter.tools.GetTime.getTime;
import static com.okay.testcenter.tools.RSAUtils.getPassWordKey;

/**
 * Created by zhou on 2019/10/18
 */
public class RunnerBase implements Runner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());



    @Override
    public String getRequestId(String productId) {
        return new RequestId().generateRequestId(productId);
    }

    @Override
    public ResponseSampler run(RequestSampler requestSampler) {
        ResponseSampler responseSampler = new ResponseSampler();
        try {
            String urlHeader = requestSampler.getUrl_header();
            requestSampler.setUrl(urlHeader + requestSampler.getUrl());
            //设置开始时间
            String startTime = getTime();
            Long firstTime = System.currentTimeMillis();
            responseSampler = RequestFactory.build(requestSampler);
            responseSampler.setStartTime(startTime);
            responseSampler.setEndTime(getTime());
            Long secondTime = System.currentTimeMillis();
            Long elapsedTime = secondTime - firstTime;
            responseSampler.setElapsedTime(String.valueOf(elapsedTime));
            //把替换的token,uid的数据返回
            if ("Post-Json".equals(requestSampler.getMethod())) {
                responseSampler.setRequestData(JSONObject.toJSONString(requestSampler.getBody()));
            } else {
                responseSampler.setRequestData(requestSampler.getParams());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseSampler;
    }


    public ResponseSampler runDirectly(RequestSampler req) {

        try {
            req.setRequestId(getRequestId(req.getRequestPre()));
            Map<String, String> headers = new HashMap<>();
            String requestId = req.getRequestId();
            headers.put("requestid", requestId);
            req.setHeaders(headers);
            req = replaceRequestId(req);
            if (req.getProjectName().equals("ticket")) {
                String token = RSAEncodeUtils.getEncryptToken(String.valueOf(req.getUname()));
                logger.info("token==" + token);
                //替换用例里面的请求参数
                String params = "callback=cb&type=js&token=" + token;
                req.setParams(params);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return run(req);
    }


    public RequestSampler replaceRequestId(RequestSampler req) {

        try {
            if (!req.getMethod().equals("Post-Json")) {
                String[] params = req.getParams().split("&");
                StringBuilder stringBuilder = new StringBuilder();
                for (String str : params) {
                    if (str.equals("requestId") || str.equals("requestid")) {
                        str = "requestId=" + req.getRequestId();
                    }
                    stringBuilder.append(str + "&");
                }
                if (stringBuilder.toString().endsWith("&")) {

                    req.setParams(stringBuilder.substring(0, stringBuilder.length() - 1));
                } else {
                    req.setParams(stringBuilder.toString());
                }

            } else {

                Map map = req.getBody();
                map.get("requestId");
                map.put("requestId", req.getRequestId());
                req.setBody(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return req;
    }


    /**
     * 运行单点登录项目登录(教师空间、公私立后台)
     * @param requestInfo
     * @return
     */
    @Override
    public ResponseSampler runSingleLogin(RequestSampler requestInfo) {
        ResponseSampler responseSampler = new ResponseSampler();
        try {
            String userName = requestInfo.getUname();
            String passWord = requestInfo.getPwd();
            String urlHeader = requestInfo.getUrl_header();
            CookieResponse cookieResponse = new SingleLogin(urlHeader, userName, passWord).login();

            String result = cookieResponse.getResult();
            if ("pass".equals(result)) {
                logger.info("登录完成");
                Map<String, String> loginCookies = cookieResponse.getCookies();
                responseSampler.setCookies(loginCookies);
                responseSampler.setLoginResult(true);
            } else {
                logger.error("登录失败");
                responseSampler.setLoginResult(false);
            }
            responseSampler.setRequestId(cookieResponse.getRequestId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseSampler;

    }

    @Override
    public ResponseSampler runPadLogin(RequestSampler requestInfo){
        ResponseSampler responseSampler = new ResponseSampler();
        try {

            requestInfo.setMethod("Post-Json");
            responseSampler = RequestFactory.build(requestInfo);

            if (responseSampler.getResponse().contains("\"ecode\":0")) {
                logger.info("登录完成");
                responseSampler.setLoginResult(true);
            } else {
                logger.error("登录失败");
                responseSampler.setLoginResult(false);
            }
            responseSampler.setRequestId(requestInfo.getRequestId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseSampler;
    }




    public RequestSampler replaceFormToken(RequestSampler req) {

        try {
            if (!req.getMethod().equals("Post-Json")) {
                String[] params = req.getParams().split("&");
                StringBuilder stringBuilder = new StringBuilder();
                for (String str : params) {
                    if (str.equals("token")) {
                        str = "token=" + req.getToken();
                    }
                    stringBuilder.append(str + "&");
                }
                if (stringBuilder.toString().endsWith("&")) {

                    req.setParams(stringBuilder.substring(0, stringBuilder.length() - 1));
                } else {
                    req.setParams(stringBuilder.toString());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return req;
    }


    @Override
    public ResponseSampler runAppLogin(String account, String pwd, String url) {

        RequestSampler requestSampler = new RequestSampler();
        ResponseSampler responseSampler = new ResponseSampler();
        try {
            //登录
            requestSampler.setRequestId(getRequestId("10"));
            requestSampler.setUrl(url + "/plusapi/signin");
            requestSampler.setMethod("Post-Json");
            Map<Object, Object> map = new HashMap<>();
            map.put("account", account);
            map.put("pwd", pwd);
            map.put("type", "1");
            Map<Object, Object> jsonObject = new HashMap<>();
            jsonObject.put("type", map);
            jsonObject.putAll(getJzAppParam());
            requestSampler.setBody(jsonObject);
            responseSampler = RequestFactory.build(requestSampler);
            if (responseSampler.getResponse().contains("\"ecode\":0")) {
                logger.info("登录成功");
                responseSampler.setLoginResult(true);
                JSONObject responseJson = JSONObject.parseObject(responseSampler.getResponse());
                String token = responseJson.getJSONObject("data").getJSONObject("parent").get("token").toString();
                String uid = responseJson.getJSONObject("data").getJSONObject("parent").get("uid").toString();
                logger.info("[token]==" + token);
                logger.info("[uid]==" + uid);
                responseSampler.setuName(uid);
                responseSampler.setToken(token);
            } else {
                logger.error("登录失败");
                responseSampler.setLoginResult(false);
            }
            responseSampler.setRequestId(requestSampler.getRequestId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseSampler;
    }



    @Override
    public String getKeyPwd(RequestSampler requestInfo) {
        String keyPassword = "";
        try {
            //获取加密的Key
            requestInfo.setMethod("Post-Json");
            ResponseSampler responseKey = RequestFactory.build(requestInfo);
            JSONObject jsonObject = JSONObject.parseObject(responseKey.getResponse());
            String key = JSONObject.parseObject(jsonObject.getString("data")).getString("key");
            if (null != key) {
                logger.info("获取key成功==" + key);
            } else {
                logger.error("获取key失败==" + key);
            }
            keyPassword = getPassWordKey(requestInfo.getPwd(), key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyPassword;
    }


    @Override
    public ResponseSampler appLogin(RequestSampler requestInfo) {
        return null;
    }

    @Override
    public ResponseSampler runAppCase(RequestSampler requestInfo) {
        return null;
    }

    @Override
    public ResponseSampler runWebCase(RequestSampler requestInfo) {
        return null;
    }


    @Override
    public ResponseSampler webLogin(RequestSampler requestInfo) {

        ResponseSampler responseSampler = new ResponseSampler();
        try {

            requestInfo.setMethod("Post-Form");
            responseSampler = RequestFactory.build(requestInfo);
            Map<String, String> loginCookies = responseSampler.getCookies();
            if (responseSampler.getToken() != null) {
                logger.info("登录完成==" + responseSampler.getResponse());
                responseSampler.setLoginResult(true);
            } else {
                logger.error("登录失败==" + responseSampler.getResponse());
                responseSampler.setLoginResult(false);
            }
            responseSampler.setCookies(loginCookies);
            responseSampler.setRequestId(requestInfo.getRequestId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseSampler;

    }


    protected Map<Object, Object> getParam() {
        Map<Object, Object> parameters = new HashMap<>();
        parameters.put("ua", "iphone");
        parameters.put("os", "ios");
        parameters.put("vs", "2.0.0");
        parameters.put("vc", "2911");
        parameters.put("sw", "480");
        parameters.put("sh", "800");
        parameters.put("serial", "803330");
        parameters.put("udid", "uqwbejqweqwoeqnwekjasdad");
        parameters.put("channel", "11");
        parameters.put("contype", "1");
        parameters.put("imei", "qwiuenqweqada");
        parameters.put("imsi", "qwiuenqweqada");
        parameters.put("mac", "qwe123123ada231");
        parameters.put("role", "0");


        return parameters;
    }



    /**
     * mall common params
     */

    protected Map<Object, Object> mallParam(RequestSampler requestSampler) {
        Map<Object, Object> params = new HashMap<>();
        params.put("systemId", requestSampler.getUname());
        params.put("uid", requestSampler.getUname());
        params.put("token", requestSampler.getToken());
        params.putAll(getJzAppParam());

        return params;
    }

    protected Map<Object, Object> getJzAppParam() {
        Map<Object, Object> parameters = new HashMap<>();
        parameters.put("vs", "8.1.0");
        parameters.put("vc", "322");
        parameters.put("vn", "3.2.2");
        parameters.put("ua", "MI 8 Lite");
        parameters.put("os", "Android");
        parameters.put("sw", "1080");
        parameters.put("sh", "2150");
        parameters.put("serial", "2dc92d11");
        parameters.put("channel", "okay");
        parameters.put("udid", "AE8A0DD43509C67DF6E505EC77C9C496");
        parameters.put("imei", "isFake");
        parameters.put("mac", "02:00:00:00:00:00");
        parameters.put("contype", 3);
        parameters.put("role", "0");
        parameters.put("app_id",1);
        Map ids = new HashMap();
        ids.put("jpush_id","170976fa8afccc1fb33");
        parameters.put("third_push_ids",ids);

        return parameters;
    }


    protected Map<Object, Object> getOkStudentParam() {
        Map<Object, Object> parameters = new HashMap<>();
        parameters.put("vs", "8.1.0");
        parameters.put("vc", "322");
        parameters.put("vn", "3.2.2");
        parameters.put("ua", "MI 8 Lite");
        parameters.put("os", "Android");
        parameters.put("sw", "1080");
        parameters.put("sh", "2150");
        parameters.put("serial", "2dc92d11");
        parameters.put("channel", "okay");
        parameters.put("udid", "AE8A0DD43509C67DF6E505EC77C9C496");
        parameters.put("imei", "isFake");
        parameters.put("mac", "02:00:00:00:00:00");
        parameters.put("contype", 3);
        parameters.put("role", "0");
        parameters.put("app_id", 1);
        parameters.put("course_type", 1);
        parameters.put("page_no", 1);
        parameters.put("token", "71f94c983fea48df9f7571334693a0b2");
        parameters.put("uid", 81951388142L);

        return parameters;
    }


}
