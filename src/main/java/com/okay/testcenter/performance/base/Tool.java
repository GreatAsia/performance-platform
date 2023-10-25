package com.okay.testcenter.performance.base;

import com.alibaba.fastjson.JSONObject;
import com.okay.testcenter.domain.middle.RequestSampler;
import com.okay.testcenter.domain.middle.ResponseSampler;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Tool {

    private static final Logger logger = LoggerFactory.getLogger(Tool.class);

    private final static int CODE = 200;

    public static ResponseSampler postJson(RequestSampler requestSampler) {
        ResponseSampler responseSampler = new ResponseSampler();
        Response response = null;
        //判断header
        Common.JudeHeader(requestSampler);
        logger.info("[caseName]==" + requestSampler.getCaseName());
        logger.info("[requestUrl]==" + requestSampler.getUrl());
        logger.info("[requestId]==" + requestSampler.getRequestId());
        logger.info("[requestHeaders]==" + requestSampler.getHeaders());
        logger.info("[requestCookies]==" + requestSampler.getCookies());
        logger.info("[requestInfo]==" + JSONObject.toJSONString(requestSampler));

        try {
            response = given()
                    .cookies(requestSampler.getCookies())
                    .headers(requestSampler.getHeaders())
                    .contentType(requestSampler.getContentType())
                    .body(requestSampler.getParams())
                    .when().post(requestSampler.getUrl());

            logger.info("[responseCode]==" + response.getStatusCode());
            if (response.getStatusCode() == CODE) {
                responseSampler.setResponseCode(response.getStatusCode());
                responseSampler.setRequestId(requestSampler.getRequestId());
                responseSampler.setCookies(response.getCookies());
                responseSampler.setResponse(response.asString());
                logger.info("[responseHeaders]==" + JSONObject.toJSONString(response.getHeaders()));
                logger.info("[responseCookies]==" + JSONObject.toJSONString(response.getCookies()));
                logger.info("[responseInfo]==" + response.asString());

                //如果首次登录获取token
                if (requestSampler.getToken() == null) {
                    String token = response.jsonPath().getString("data.token");
                    if (token != null) {
                        logger.info("[token]==" + token);
                        responseSampler.setToken(token);
                    }
                }
            } else {
                responseSampler.setResponseCode(response.getStatusCode());
                responseSampler.setRequestId(requestSampler.getRequestId());
                responseSampler.setRequestId(requestSampler.getRequestId());
                responseSampler.setResponse(response.asString());

                logger.error("[responseErrorInfo]==" + response.asString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[requestFail]==" + e.getLocalizedMessage());
        }

        return responseSampler;
    }

    public static ResponseSampler get(RequestSampler requestSampler) {
        Response response = null;
        ResponseSampler responseSampler = new ResponseSampler();
        Common.JudeHeader(requestSampler);
        //处理params变成list
        Map<String, Object> paramsList = new HashMap<>();
        String param = requestSampler.getParams();
        if (!param.isEmpty()) {
            String[] list = param.split("&");
            for (int i = 0; i < list.length; i++) {
                String[] getParam = list[i].split("=");
                if (getParam.length > 1) {
                    paramsList.put(getParam[0], getParam[1]);
                } else {
                    paramsList.put(getParam[0], "");
                }

            }
            requestSampler.setParamsList(paramsList);


        } else {
            logger.error("get request params is null");
        }

        logger.info("[caseName]==" + requestSampler.getCaseName());
        logger.info("[requestUrl]==" + requestSampler.getUrl());
        logger.info("[requestId]==" + requestSampler.getRequestId());
        logger.info("[requestHeaders]==" + requestSampler.getHeaders());
        logger.info("[requestCookies]==" + requestSampler.getCookies());
        logger.info("[requestInfo]==" + requestSampler.getParamsList());

        try {
            response = given()
                    .cookies(requestSampler.getCookies())
                    .headers(requestSampler.getHeaders())
                    .params(requestSampler.getParamsList())
                    .when()
                    .get(requestSampler.getUrl());

            if (response.getStatusCode() == CODE) {
                responseSampler.setResponseCode(response.getStatusCode());
                responseSampler.setRequestId(requestSampler.getRequestId());
                responseSampler.setCookies(response.getCookies());
                responseSampler.setResponse(response.asString());
                logger.info("[responseHeaders]==" + JSONObject.toJSONString(response.getHeaders()));
                logger.info("[responseCookies]==" + JSONObject.toJSONString(response.getCookies()));
                logger.info("[responseInfo]==" + response.asString());
            } else {
                responseSampler.setResponseCode(response.getStatusCode());
                responseSampler.setRequestId(requestSampler.getRequestId());
                responseSampler.setResponse(response.asString());
                logger.error("[responseInfo]==" + response.asString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[requestFail]==" + e.getLocalizedMessage());
        }
        return responseSampler;
    }

    public static ResponseSampler postForm(RequestSampler requestSampler) {
        Response response = null;
        ResponseSampler responseSampler = new ResponseSampler();

        //判断header
        Common.JudeHeader(requestSampler);
        logger.info("[caseName]==" + requestSampler.getCaseName());
        logger.info("[requestUrl]==" + requestSampler.getUrl());
        logger.info("[requestId]==" + requestSampler.getRequestId());
        logger.info("[requestHeaders]==" + requestSampler.getHeaders());
        logger.info("[requestCookies]==" + requestSampler.getCookies());
        logger.info("[requestInfo]==" + JSONObject.toJSONString(requestSampler));

        try {

            response = given()
                    .cookies(requestSampler.getCookies())
                    .headers(requestSampler.getHeaders())
                    .contentType(requestSampler.getContentType())
                    .body(requestSampler.getParams())
                    .when()
                    .post(requestSampler.getUrl());

            logger.info("[responseCode]==" + response.getStatusCode());
            if (response.getStatusCode() == CODE) {
                responseSampler.setResponseCode(response.getStatusCode());
                responseSampler.setRequestId(requestSampler.getRequestId());
                responseSampler.setCookies(response.getCookies());
                responseSampler.setResponse(response.asString());
                logger.info("[responseHeaders]==" + JSONObject.toJSONString(response.getHeaders()));
                logger.info("[responseCookies]==" + JSONObject.toJSONString(response.getCookies()));
                logger.info("[responseInfo]==" + response.asString());

                //如果首次登录获取token
                if (requestSampler.getToken() == null) {
                    String token = response.jsonPath().getString("data.token");
                    if (token != null) {
                        logger.info("[token]==" + token);
                        responseSampler.setToken(token);
                    }
                }
            } else {
                responseSampler.setResponseCode(response.getStatusCode());
                responseSampler.setRequestId(requestSampler.getRequestId());
                responseSampler.setRequestId(requestSampler.getRequestId());
                responseSampler.setResponse(response.asString());

                logger.error("[responseErrorInfo]==" + response.asString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[requestFail]==" + e.getLocalizedMessage());
        }

        return responseSampler;
    }

    public static ResponseSampler postText(RequestSampler requestSampler) {
        ResponseSampler responseSampler = new ResponseSampler();

        Response response = null;
        //判断header
        Common.JudeHeader(requestSampler);
        logger.info("[caseName]==" + requestSampler.getCaseName());
        logger.info("[requestUrl]==" + requestSampler.getUrl());
        logger.info("[requestId]==" + requestSampler.getRequestId());
        logger.info("[requestHeaders]==" + requestSampler.getHeaders());
        logger.info("[requestCookies]==" + requestSampler.getCookies());
        logger.info("[requestInfo]==" + JSONObject.toJSONString(requestSampler));

        try {
            response = given()
                    .cookies(requestSampler.getCookies())
                    .headers(requestSampler.getHeaders())
                    .contentType(requestSampler.getContentType())
                    .body(requestSampler.getParams())
                    .when().post(requestSampler.getUrl());

            logger.info("[responseCode]==" + response.getStatusCode());
            if (response.getStatusCode() == CODE) {
                responseSampler.setResponseCode(response.getStatusCode());
                responseSampler.setRequestId(requestSampler.getRequestId());
                responseSampler.setCookies(response.getCookies());
                responseSampler.setResponse(response.asString());
                logger.info("[responseHeaders]==" + JSONObject.toJSONString(response.getHeaders()));
                logger.info("[responseCookies]==" + JSONObject.toJSONString(response.getCookies()));
                logger.info("[responseInfo]==" + response.asString());

                //如果首次登录获取token
                if (requestSampler.getToken() == null) {
                    String token = response.jsonPath().getString("data.token");
                    if (token != null) {
                        logger.info("[token]==" + token);
                        responseSampler.setToken(token);
                    }
                }
            } else {
                responseSampler.setResponseCode(response.getStatusCode());
                responseSampler.setRequestId(requestSampler.getRequestId());
                responseSampler.setRequestId(requestSampler.getRequestId());
                responseSampler.setResponse(response.asString());

                logger.error("[responseErrorInfo]==" + response.asString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[requestFail]==" + e.getLocalizedMessage());
        }

        return responseSampler;
    }


}
