package com.okay.testcenter;

import com.okay.testcenter.common.constant.Constant;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author XieYangYang
 * @date 2019/11/25 13:25
 */
@Log4j
@Deprecated
public class LoginFormAuthenticationFilter extends FormAuthenticationFilter {

    private static Logger logger = LoggerFactory.getLogger(LoginFormAuthenticationFilter.class);

    /**
     * @param request
     * @return
     */
    /*protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = null;
        String password = null;
        ServletRequest localRequest=null;
        if (request.getContentType().equals("application/json")) {
            localRequest = request;
            JSONObject params = JSONObject.parseObject(getBodyData(localRequest));
            username = params.getString("username");
            password = params.getString("password");
        } else {
            username = getUsername(request);
            password = getPassword(request);
        }

        return createToken(username, password, request, response);
    }*/
    public static String getBodyData(ServletRequest request) {
        StringBuffer data = new StringBuffer();
        String line = null;
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            while (null != (line = reader.readLine()))
                data.append(line);
        } catch (IOException e) {
        } finally {
        }
        return data.toString();
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = SecurityUtils.getSubject();
        if (isLoginSubmission(request, response) && subject.isAuthenticated()) {
            try {
                Session session = subject.getSession();
                String backUrl = (String) session.getAttribute(Constant.BACK_URL_KEY);
                //session.removeAttribute(Constant.BACK_URL_KEY);
                if (backUrl == null) {
                    backUrl = getSuccessUrl();
                    return true;
                }
                //TODO:调用2次
                WebUtils.issueRedirect(request, response, backUrl);
            } catch (IOException e) {
                log.info("跳转出现异常，异常信息：" + e);
            }
            return true;
        }
        return super.isAccessAllowed(request, response, mappedValue) ||
                (!isLoginRequest(request, response) && isPermissive(mappedValue));


    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String requestURL = getPathWithinApplication(request);
        log.info("请求的url :" + requestURL);
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return this.executeLogin(request, response);
            } else {
                //允许访问login
                return true;
            }
        }
        this.saveRequestAndRedirectToLogin(request, response);
        return false;
    }


    /*@Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
        WebUtils.normalize(this.getSuccessUrl());
        return false;
    }*/

    //重写onLoginSuccess
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws IOException {
        String fallbackUrl = this.getSuccessUrl();
        String successUrl = null;
        SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
        if (savedRequest != null && savedRequest.getMethod().equalsIgnoreCase(AccessControlFilter.GET_METHOD)) {
            successUrl = savedRequest.getRequestUrl();
        }

        if (successUrl == null) {
            successUrl = fallbackUrl;
        }

        if (successUrl == null) {
            throw new IllegalStateException("Success URL not available via saved request or via the " +
                    "successUrlFallback method parameter. One of these must be non-null for " +
                    "issueSuccessRedirect() to work.");
        }

        //setSuccessUrl(successUrl);
        Session session = subject.getSession();
        session.setAttribute(Constant.BACK_URL_KEY, successUrl);
        return false;
    }
}
