package com.okay.testcenter.tools;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.okay.testcenter.domain.dubbo.DubboCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DubboFactory {

    private static final Logger logger = LoggerFactory.getLogger(DubboFactory.class);
    private static ReferenceConfig<GenericService> reference;
    private static ReferenceConfigCache cache;

    private DubboFactory() {
    }

    public static DubboFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Object genericInvoke(DubboCase dubbo) {
        Object result = "";

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("moco");
        //这里配置了dubbo的application信息*(demo只配置了name)*，因此demo没有额外的dubbo.xml配置文件
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(dubbo.getAddress());
        //这里配置dubbo的注册中心信息，因此demo没有额外的dubbo.xml配置文件
        reference = new ReferenceConfig<GenericService>();
        reference.setApplication(applicationConfig);
        reference.setRegistry(registryConfig);
        reference.setVersion(dubbo.getVersion());
        reference.setInterface(dubbo.getInterFaceClassName());
        reference.setGeneric(true);
        reference.setTimeout(5000);
        /*ReferenceConfig实例很重，封装了与注册中心的连接以及与提供者的连接，
        需要缓存，否则重复生成ReferenceConfig可能造成性能问题并且会有内存和连接泄漏。
        API方式编程时，容易忽略此问题。
        这里使用dubbo内置的简单缓存工具类进行缓存*/
        try {
            cache = ReferenceConfigCache.getCache();
            GenericService genericService = cache.get(reference);
            // 用com.alibaba.dubbo.rpc.service.GenericService可以替代所有接口引用
            int len = dubbo.getParamList().size();
            String[] invokeParamTyeps = new String[len];
            Object[] invokeParams = new Object[len];
            for (int i = 0; i < len; i++) {
                invokeParamTyeps[i] = dubbo.getParamList().get(i).get("ParamType") + "";
                invokeParams[i] = dubbo.getParamList().get(i).get("Object");
            }
            logger.info("requestMethodName=={}",dubbo.getMethodName());
            logger.info("requestParamTyep=={}",invokeParamTyeps);
            logger.info("requestParams=={}",invokeParams);
            result = genericService.$invoke(dubbo.getMethodName(), invokeParamTyeps, invokeParams);
        } catch (Exception e) {
            if (cache != null) {
                cache.destroyAll();
            }
            if (reference != null) {
                reference.destroy();
            }
            return "fail";
        }

        return result;
    }

    public void destory() {
        //关闭注册服务,断开与dubbo的连接
        if (cache != null) {
            cache.destroyAll();
        }
        if (reference != null) {
            reference.destroy();
        }
    }

    private static class SingletonHolder {
        private static DubboFactory INSTANCE = new DubboFactory();
    }

}