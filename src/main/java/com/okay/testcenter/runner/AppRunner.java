package com.okay.testcenter.runner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.okay.testcenter.domain.middle.RequestSampler;
import com.okay.testcenter.domain.middle.ResponseSampler;
import com.okay.testcenter.request.RequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author zhou
 * @date 2020/7/15
 */
public class AppRunner extends RunnerBase {

    private final static Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private String tokenStr = "token";
    private String uidStr = "uid";
    private String mall = "smart-mall-server";
    private String freeStyle =  "ailearn-freeStyle-interface";


    @Override
    public ResponseSampler appLogin(RequestSampler req) {
        ResponseSampler responseSampler = new ResponseSampler();
        try {
            String username = req.getUname();
            String password = req.getPwd();

            String url_header = req.getUrl_header();
            if (mall.equals(req.getProjectName())) {
                //需要OKAY+域名
                if (url_header.contains("hotfix")) {
                    url_header = "https://jzapp-hotfix.xk12.cn";
                } else if (url_header.contains("dev")) {
                    url_header = "https://jzapp-dev.xk12.cn";
                } else if (url_header.contains("okay.cn")) {
                    url_header = "https://jzapp.okjiaoyu.cn";
                }
            }
            if (freeStyle.equals(req.getProjectName())) {
                //学生pad域名
                if (url_header.contains("stress")) {
                    url_header = "https://stupad-stress.xk12.cn";
                } else if (url_header.contains("dev")) {
                    url_header = "https://stupad-dev.xk12.cn";
                } else if (url_header.contains("okjiaoyu")) {
                    url_header = "https://stupad.okjiaoyu.cn";
                }
            }

            if (req.getSecretUrl() != null) {
                //密码加密
                RequestSampler passwordKey = new RequestSampler();
                passwordKey.setUrl(url_header + req.getSecretUrl());
                passwordKey.setRequestId(getRequestId(req.getRequestPre()));
                passwordKey.setPwd(password);
                password = getKeyPwd(passwordKey);
            }
            req.setUrl(url_header + req.getLoginUrl());
            req.setRequestId(getRequestId(req.getRequestPre()));

            String loginInfo = req.getLoginParam().replace("${mname}", username).replace("${mpwd}", password);
            Map params = JSONObject.parseObject(loginInfo);
            req.setUname(username);
            req.setBody(params);
            req.setMethod("Post-Json");
            responseSampler = RequestFactory.build(req);

            if (responseSampler.getResponse().contains(tokenStr)) {
                responseSampler = getResponseInfo(responseSampler);
                logger.info(req.getProjectName() + "登录完成");
                responseSampler.setLoginResult(true);
            } else {
                logger.error(req.getProjectName() + "登录失败");
                responseSampler.setLoginResult(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseSampler;


    }

    @Override
    public ResponseSampler runAppCase(RequestSampler req) {

        try {
            req.setRequestId(getRequestId(req.getRequestPre()));
            Map<String, String> headers = new HashMap<>();
            headers.put("requestid", req.getRequestId());
            headers.put("token", req.getToken());
            req.setHeaders(headers);
            //替换上行的token/uid
            if("Post-Json".equals(req.getMethod())){
                Map map = req.getBody();
                if (!map.toString().contains("token")) {
                    map.put(tokenStr, req.getToken());
                    map.put(uidStr, req.getUname());
                } else {
                    JSONObject reqData = JSON.parseObject(req.getBody().toString());
                    Set<String> sets = reqData.keySet();
                    for (String key : sets) {
                        if (key.equals(uidStr)) {
                            map.put(uidStr, req.getUname());
                            continue;
                        }
                        if (key.equals(tokenStr)) {
                            map.put(tokenStr, req.getToken());
                            continue;
                        }
                        if (map.get(key).toString().contains(tokenStr)) {

                            JSONObject replaceData = JSON.parseObject(map.get(key).toString());
                            Set<String> repSets = replaceData.keySet();
                            for (String repKey : repSets) {
                                if (repKey.equals(uidStr)) {
                                    replaceData.put(uidStr, req.getUname());
                                    continue;
                                }
                                if (repKey.equals(tokenStr)) {
                                    replaceData.put(tokenStr, req.getToken());
                                    continue;
                                }
                            }
                            map.put(key, replaceData);
                        }

                    }

                }
                req.setBody(map);

            }else {

                req =  replaceFormToken(req);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return run(req);

    }

    private ResponseSampler getResponseInfo(ResponseSampler responseSampler) {
        JSONObject data = JSON.parseObject(responseSampler.getResponse()).getJSONObject("data");
        String uid = "";
        String token = "";
        Set<String> sets = data.keySet();
        for (String key : sets) {
            if (key.equals("uId")) {
                uid = data.get("uId").toString();
                continue;
            }
            if (key.equals("uid")) {
                uid = data.get("uid").toString();
                continue;
            }
            if (key.equals("token")) {
                token = data.get("token").toString();
                continue;
            }
            if (key.equals("parent")) {
                token = data.getJSONObject("parent").get("token").toString();
                uid = data.getJSONObject("parent").get("uid").toString();
                break;
            }

        }
        logger.info("[token]==" + token);
        logger.info("[uid]==" + uid);
        responseSampler.setuName(uid);
        responseSampler.setToken(token);

        return responseSampler;
    }
}
