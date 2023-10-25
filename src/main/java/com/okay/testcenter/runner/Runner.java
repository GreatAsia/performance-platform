package com.okay.testcenter.runner;


import com.okay.testcenter.domain.middle.RequestSampler;
import com.okay.testcenter.domain.middle.ResponseSampler;

/**
 * @author asia
 * @date 2019-10-21
 */
public interface Runner {

    /**
     * 获取请求ID
     *
     * @param productId 产品Id
     * @return requestId
     */
    String getRequestId(String productId);

    /**
     * 运行用例
     *
     * @param requestSampler 请求信息
     * @return 响应信息
     */
    ResponseSampler run(RequestSampler requestSampler);


    /**
     * 运行单点登录项目登录
     *
     * @param requestInfo 请求信息
     * @return 响应信息
     */
    ResponseSampler runSingleLogin(RequestSampler requestInfo);


    /**
     * 运行Pad项目登录
     *
     * @param requestInfo 请求信息
     * @return 响应信息
     */
    ResponseSampler runPadLogin(RequestSampler requestInfo);


    /**
     * 商城H5和OKAY+登录
     * @param account 账号
     * @param pwd 密码
     * @param url 域名
     * @return
     */
    ResponseSampler runAppLogin(String account, String pwd, String url);


    /**
     * 运行App项目登录
     *
     * @param requestInfo 请求信息
     * @return 加密的Key
     */
    String getKeyPwd(RequestSampler requestInfo);


    /**
     * WEB登录
     *
     * @param requestInfo 请求信息
     * @return 响应信息
     */
    ResponseSampler webLogin(RequestSampler requestInfo);

    /**
     * WEB登录
     *
     * @param requestInfo 请求信息
     * @return 响应信息
     */
    ResponseSampler appLogin(RequestSampler requestInfo);

    /**
     * 运行APP用例
     *
     * @param requestInfo 请求信息
     * @return 响应信息
     */
    ResponseSampler runAppCase(RequestSampler requestInfo);


    /**
     * 运行WEB用例
     *
     * @param requestInfo 请求信息
     * @return 响应信息
     */
    ResponseSampler runWebCase(RequestSampler requestInfo);
}
