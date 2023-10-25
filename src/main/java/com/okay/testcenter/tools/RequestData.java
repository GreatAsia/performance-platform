package com.okay.testcenter.tools;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static io.restassured.RestAssured.given;


public class RequestData {

    private static final Logger logger = LoggerFactory.getLogger(PictureUtil.class);

    /**
     * POST请求方法
     *
     * @param path
     * @param parames
     */

    public String post(String path, Object parames) {
        Response response = null;
        logger.info("请求信息:" + parames);
        try {
            response = given()
                    .contentType(ContentType.URLENC)
                    .body(parames)
                    //.formParams(parames)
                    .expect()
                    .statusCode(200)
                    .when()
                    .post(path);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("请求失败:" + e.getLocalizedMessage());
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        logger.info("响应信息:" + response.asString());
        return response.asString();
    }

    /**
     * GET请求方法
     *
     * @param path
     * @param parames
     * @return
     */
    public String get(String path, Map<String, Object> parames) {
        Response response = null;
        logger.info("请求信息:" + parames);
        try {
            response = given()
                    .params(parames)
                    .expect()
                    .statusCode(200)
                    .when()
                    .get(path);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ExceptionUtils.getStackTrace(e));
            logger.error("请求失败:" + e.getLocalizedMessage());
        }
        logger.info("响应信息:" + response.asString());
        return response.asString();
    }


    /**
     * GET请求方法
     *
     * @param path
     * @return
     */
    public String get(String path) {
        Response response = null;
        try {
            response = given()
                    .expect()
                    .statusCode(200)
                    .when()
                    .get(path);

        } catch (Exception e) {
            logger.error("请求失败:" + e.getLocalizedMessage());
            logger.error(ExceptionUtils.getStackTrace(e));
            e.printStackTrace();

        }
        logger.info("响应信息:" + response.asString());
        return response.asString();
    }


}
