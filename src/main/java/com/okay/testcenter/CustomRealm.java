package com.okay.testcenter;

import com.alibaba.fastjson.JSONObject;
import com.okay.testcenter.common.constant.Constant;
import com.okay.testcenter.domain.bean.User;
import com.okay.testcenter.domain.user.UserResponse;
import com.okay.testcenter.service.user.UserService;
import com.okay.testcenter.tools.AppMd5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * @author XieYangYang
 * @date 2019/11/20 16:55
 */
@Slf4j
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;


    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String principal= (String) principalCollection.getPrimaryPrincipal();
        String[] principals=principal.split("-");
        authorizationInfo.addRole(principals[1]);
        return authorizationInfo;
    }


    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // log.info(JSONObject.toJSONString(authenticationToken));  // 调试临时添加
        UsernamePasswordToken authcToken = (UsernamePasswordToken) authenticationToken;
        UserResponse userResponse = null;
        try {
            User userParam = new User();
            userParam.setIsDelete("0");
            userParam.setUserName(authcToken.getUsername());
            userParam.setUserPassword(AppMd5Util.MD5toLower(String.valueOf(authcToken.getPassword())));
            userResponse = userService.findByUserRes(userParam);
        } catch (Exception e) {
            log.info("身份认证发生异常", e);
            throw new AuthenticationException("身份认证发生异常");
        }

        if (null == userResponse) {
            log.info("身份认证失败，用户名密码不匹配");
            throw new DisabledAccountException("身份认证失败，用户名密码不匹配");
        }
//        Session session = SecurityUtils.getSubject().getSession();
//        session.setAttribute(Constant.ONLINE_USER, JSONObject.toJSONString(userResponse));
//        session.setAttribute(Constant.ONLINE_ROLE, JSONObject.toJSONString(userResponse.getRoleResponse()));



        return new SimpleAuthenticationInfo(authcToken.getUsername()+"-"+userResponse.getRoleResponse().getRoleCode(), authcToken.getPassword(), getName());
    }

}
