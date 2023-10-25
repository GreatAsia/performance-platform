package com.okay.testcenter.log;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 切割controller,打印日志
 */

@Aspect
@Component
public class HttpAspect {

    private static final Logger logger = LoggerFactory.getLogger(HttpAspect.class);
    private static final String UTF_8 = "utf-8";
    private static Long startTime;
    private static Long endTime;

    public static Map<String, Object> getKeyAndValue(Object obj) {
        Map<String, Object> map = new HashMap<>();
        // 得到类对象
        Class userCla = (Class) obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true);
            Object val = new Object();
            try {
                val = f.get(obj);
                // 得到此属性的值
                map.put(f.getName(), val);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return map;
    }

    @Pointcut("execution(public * com.okay.testcenter.controller..*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        startTime = System.currentTimeMillis();

        String method = request.getMethod();
        String queryString = request.getQueryString();
        String url = request.getRequestURL().toString();
        logger.info("=================请求内容===============");
        logger.info("url={}", url);
        logger.info("method={}", method);
        logger.info("contentType={}", request.getHeader("Content-Type"));
        logger.info("calss_method={}", joinPoint.getSignature().getDeclaringTypeName() +
                "." + joinPoint.getSignature().getName());

        Object[] args = joinPoint.getArgs();

        String params = "";

        try {
            //获取请求参数集合并进行遍历拼接
            if (args.length > 0) {
                if ("POST".equals(method)) {
                   /* Object object = args[0];
                    params = JSON.toJSONString(object, SerializerFeature.WriteMapNullValue);*/
                    Object[] arguments = new Object[args.length];
                    for (int i = 0; i < args.length; i++) {
                        if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile) {
                            //ServletRequest不能序列化，从入参里排除，否则报异常：java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
                            //ServletResponse不能序列化 从入参里排除，否则报异常：java.lang.IllegalStateException: getOutputStream() has already been called for this response
                            continue;
                        }
                        arguments[i] = args[i];
                    }
                    if (arguments != null) {
                        try {
                            params = JSONObject.toJSONString(arguments);
                        } catch (Exception e) {
                            params = arguments.toString();
                        }
                    }
                } else if ("GET".equals(method)) {
                    params = queryString;
                }
                if (params != null) {
                    params = URLDecoder.decode(params, UTF_8);
                }

            }
            logger.info("params={}", params);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("log error !!", e);
        }


    }

    @AfterReturning(returning = "object", pointcut = "log()")
    public void duReturning(Object object) {

        logger.info("=================响应内容===============");
        logger.info("response={}", JSON.toJSON(object));
        endTime = System.currentTimeMillis();
        logger.info("elapsedtime=" + (endTime - startTime) + "  ms");
        logger.info("******************分割线****************");
    }

    @After("log()")
    public void doAfter() {

    }


}