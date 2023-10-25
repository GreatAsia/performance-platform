package com.okay.testcenter;


import com.okay.testcenter.tools.Base64Utils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author XieYangYang
 * @date 2019/11/20 16:58
 */
public class shiroConfig_cp {

    @Value("${shiro.redis.host}")
    private String redisHost;

    @Value("${shiro.redis.port}")
    private int redisPort;

    @Value("${shiro.redis.password}")
    private String redisPassword;


    @Value("${shiro.redis.DB}")
    private int redisDB;

    @Value("${shiro.redis.timeout}")
    private int redisTimeout;

    @Bean
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();     // crazycake 实现
        redisManager.setHost(redisHost+":"+redisPort);
        if(!redisPassword.equals("")&& redisPassword !=null){
            redisManager.setPassword(redisPassword);
        }
        redisManager.setDatabase(redisDB);  // 第一个库
        redisManager.setTimeout(redisTimeout);
        return redisManager;
    }

    @Bean
    public JavaUuidSessionIdGenerator sessionIdGenerator(){
        return new JavaUuidSessionIdGenerator();
    }

    @Bean
    public RedisSessionDAO sessionDAO(){
        RedisSessionDAO sessionDAO = new RedisSessionDAO(); // crazycake 实现
        sessionDAO.setRedisManager(redisManager());
        // sessionDAO.setSessionIdGenerator(sessionIdGenerator()); //  Session ID 生成器
        return sessionDAO;
    }

    @Bean
    public SimpleCookie cookie(){
        SimpleCookie cookie = new SimpleCookie("SHAREJSESSIONID"); //  cookie的name,对应的默认是 JSESSIONID
        cookie.setHttpOnly(true);
        cookie.setPath("/");        //  path为 / 用于多个系统共享JSESSIONID
        return cookie;
    }

    /**
     * SessionManager session管理
     */
    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(redisTimeout*1000);    // 设置session超时
        sessionManager.setDeleteInvalidSessions(true);      // 删除无效session
        sessionManager.setSessionIdCookie(cookie());            // 设置JSESSIONID
        sessionManager.setSessionDAO(sessionDAO());         // 设置sessionDAO
        sessionManager.setSessionIdUrlRewritingEnabled(false);  //去除url的JSESSIONID
        return sessionManager;
    }


//    public SimpleCookie simpleCookie(){
//
//    }

    @Bean
    public RedisCacheManager redisCacheManager(){
        RedisCacheManager cacheManager = new RedisCacheManager();   // crazycake 实现
        cacheManager.setRedisManager(redisManager());
        cacheManager.setExpire(redisTimeout);
        return cacheManager;
    }



    //不加这个注解不生效
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    //将自己的验证方式加入容器
    @Bean
    public CustomRealm myShiroRealm() {
        CustomRealm customRealm = new CustomRealm();
        return customRealm;
    }

    //权限管理，配置
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // realm自定义数据操作
        securityManager.setRealm(myShiroRealm());
        // 注入记住我管理器;
        securityManager.setRememberMeManager(rememberMeManager());
        // session管理器
        securityManager.setSessionManager(sessionManager());
        // 缓存管理器
        securityManager.setCacheManager(redisCacheManager());
        return securityManager;
    }

    /**
     * cookie对象;
     *
     * @return
     */
    public SimpleCookie rememberMeCookie() {
        // 这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        // <!-- 记住我cookie生效时间5天 ,单位秒;-->
        simpleCookie.setMaxAge(3600*24*5);
        return simpleCookie;
    }

    /**
     * cookie管理对象;记住我功能
     *
     * @return
     */
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        try {
            cookieRememberMeManager.setCipherKey(Base64Utils.decode("3AvVhmFLUs0KTA3Kprsdag=="));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cookieRememberMeManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //自定义拦截器
        Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
        //访问权限配置
        // filtersMap.put("loginUrl", new LoginFormAuthenticationFilter());
        filtersMap.put("reqUrl", new LoginInterceptor());

        shiroFilterFactoryBean.setFilters(filtersMap);
        Map<String, String> map = new LinkedHashMap<>();
        //登出
        // map.put("/logout", "logout");
        map.put("/404", "anon");
        map.put("/405", "anon");
        map.put("/css/**", "anon");
        map.put("/image/**", "anon");
        map.put("/js/**", "anon");
        map.put("/", "anon");
        map.put("/favicon.ico", "anon");
        shiroFilterFactoryBean.setLoginUrl("/login");
        // map.put("/login", "loginUrl");

        //兼容之前
        //排除路径
        map.put("/singleLogin", "anon");
        // job
        map.put("/job/refreshTrigger", "anon");
        //log
        map.put("/websocket", "anon");
        map.put("/login", "anon");
        //对所有用户认证
        // map.put("/**", "reqUrl");
         map.put("/api/home", "roles[user_develop]");
         map.put("/**", "user");
        //错误页面，无权限跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/405");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }

    //加入注解的使用，不加入这个注解不生效
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//        return authorizationAttributeSourceAdvisor;
//    }

}
