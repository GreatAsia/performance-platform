package com.okay.testcenter.controller.login;


import com.okay.testcenter.domain.RetResponse;
import com.okay.testcenter.domain.RetResult;
import com.okay.testcenter.domain.user.UserRequest;
import com.okay.testcenter.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Api(description = "登录接口")
@Controller

public class LoginController {


    @Autowired
    UserService userService;

    @GetMapping(value = "/")
    public void defIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebUtils.issueRedirect(request, response, "/login");
    }

    @RequestMapping( "/login")
    public String index() {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated() || currentUser.isRemembered()) {
            Object jump= currentUser.getSession().getAttribute("shiroSavedRequest");
            String jumpUrl = jump==null?"/api/home": ((SavedRequest)jump).getRequestUrl();
            return "redirect:"+jumpUrl;
        } else {
            return "html/login";
        }

    }

    @ApiOperation(value = "登录", notes = "登录")
    @PostMapping(value = "/login")
    @ResponseBody
    public RetResult login(UserRequest user) {

        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword() ,true);
        Subject currentUser = SecurityUtils.getSubject();
        /*if (!currentUser.isAuthenticated()) {
            currentUser.login(token);
        }*/

        try {
            currentUser.login(token);
        } catch (UnknownAccountException uae) {
//            log.info("There is no user with username of " + token.getPrincipal());
            return (new RetResponse()).makeErrRsp("用户认证失败");
        } catch (IncorrectCredentialsException ice) {
//            log.info("Password for account " + token.getPrincipal() + " was incorrect!");
            return (new RetResponse()).makeErrRsp("用户名密码不匹配");
        } catch (LockedAccountException lae) {
//            log.info("The account for username " + token.getPrincipal() + " is locked.  " +
//                    "Please contact your administrator to unlock it.");
            return (new RetResponse()).makeErrRsp("当前用户已锁定，请等待5分钟后重试");
        }
        // ... catch more exceptions here (maybe custom ones specific to your application?
        catch (AuthenticationException ae) {
            //unexpected condition?  error?
            return (new RetResponse()).makeErrRsp("授权失败");
        }
        Object jump= currentUser.getSession().getAttribute("shiroSavedRequest");
        String jumpUrl = jump==null?"/api/home": ((SavedRequest)jump).getRequestUrl();
        Map<String, String> jumpMap = new HashMap<>();
        jumpMap.put("jumpUrl",jumpUrl);
        return (new RetResponse()).makeRsp(200,"登录成功",jumpMap);
    }

    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return "redirect:/login";


    }

    @GetMapping(value = "/400")
    public String to400() {
        return "html/page-error-400";
    }

    @GetMapping(value = "/404")
    public String to404() {
        return "html/page-error-404";
    }


    @GetMapping(value = "/405")
    public String to405() {
        return "html/page-error-405";
    }


}
