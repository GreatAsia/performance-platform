package com.okay.testcenter.request;

import com.alibaba.fastjson.JSONObject;
import com.okay.testcenter.domain.middle.RequestSampler;
import com.okay.testcenter.domain.middle.ResponseSampler;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RequestBase implements Request {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    ResponseSampler responseSampler = new ResponseSampler();
    private final static int CODE = 200;

    public RequestBase() {
        //设置https请求忽略证书
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Override
    public ResponseSampler post(RequestSampler requestSampler) {
        Response response = null;
        //判断header
        JudeHeader(requestSampler);
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

                responseSampler.setCookies(response.getCookies());
                responseSampler.setResponse(response.asString());
                logger.info("[responseHeaders]==" + JSONObject.toJSONString(response.getHeaders()));
                logger.info("[responseCookies]==" + JSONObject.toJSONString(response.getCookies()));
                logger.info("[responseInfo]==" + response.asString());

            } else {
                logger.error("[resquestInfo]==" + JSONObject.toJSONString(requestSampler));
                logger.error("[responseInfo]==" + response.asString());
            }

            responseSampler.setResponseCode(response.getStatusCode());
            responseSampler.setRequestId(requestSampler.getRequestId());
            responseSampler.setResponse(response.asString());
            responseSampler.setRequestSampler(requestSampler);

        } catch (Exception e) {
            logger.error("[requestFail]==" + e.getLocalizedMessage());
            logger.error(ExceptionUtils.getStackTrace(e));
            e.printStackTrace();

        }

        return responseSampler;
    }


    /**
     * GET请求方法
     */
    @Override
    public ResponseSampler get(RequestSampler requestSampler) {
        Response response = null;
        ResponseSampler responseSampler = new ResponseSampler();
        JudeHeader(requestSampler);
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
            logger.info("get request params is null");
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
            responseSampler.setCookies(response.getCookies());
            responseSampler.setResponse(response.asString());
            logger.info("[responseHeaders]==" + JSONObject.toJSONString(response.getHeaders()));
            logger.info("[responseCookies]==" + JSONObject.toJSONString(response.getCookies()));
            logger.info("[responseInfo]==" + response.asString());
        } else {
            logger.error("[requestInfo]==" + JSONObject.toJSONString(requestSampler));
            logger.error("[responseInfo]==" + response.asString());
        }
            responseSampler.setResponseCode(response.getStatusCode());
            responseSampler.setRequestId(requestSampler.getRequestId());
            responseSampler.setResponse(response.asString());

        } catch (Exception e) {
            logger.error("[requestFail]==" + e.getLocalizedMessage());
            logger.error(ExceptionUtils.getStackTrace(e));
            e.printStackTrace();

        }
        responseSampler.setRequestSampler(requestSampler);
        return responseSampler;
    }




    //获取当前的时间
    public String getTime() {
        return System.currentTimeMillis() + "";
    }


    //判断请求header是否为空
    public void JudeHeader(RequestSampler requestSampler) {

        if (requestSampler.getHeaders().isEmpty()) {
            Map<String, String> headers = new HashMap<>();
            headers.put("requestid", requestSampler.getRequestId());
            headers.put("token", requestSampler.getToken());
            requestSampler.setHeaders(headers);
        }


    }
}
