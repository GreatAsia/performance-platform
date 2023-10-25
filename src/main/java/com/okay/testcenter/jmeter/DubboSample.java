package com.okay.testcenter.jmeter;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSONObject;
import com.okay.testcenter.domain.dubbo.DubboCase;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.okay.testcenter.tools.ExceptionUtil.getMessage;

/**
 * @author zhou
 * @date 2020/8/21
 */
public class DubboSample extends AbstractJavaSamplerClient {

    private static final Logger logger = LoggerFactory.getLogger(DubboSample.class);

    private DubboCase dubboCase = new DubboCase();
    private String requestId;

    private static ReferenceConfig<GenericService> reference;
    private static ReferenceConfigCache cache;
    private GenericService genericService;
    private   String[] invokeParamTyeps ;
    private   Object[] invokeParams ;

    @Override
    public Arguments getDefaultParameters() {
        Arguments params = new Arguments();
        params.addArgument("", "");
        return params;
    }

    @Override
    public void setupTest(JavaSamplerContext arg0) {

        String dubboInfo = arg0.getParameter("dubboCase");
        requestId = arg0.getParameter("requestid");
        if (dubboInfo != null && dubboInfo != "") {
            dubboCase = JSONObject.parseObject(dubboInfo, DubboCase.class);
        } else {
            logger.error("dubboCase==" + dubboInfo);
        }
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("moco");
        //这里配置了dubbo的application信息*(demo只配置了name)*，因此demo没有额外的dubbo.xml配置文件
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(dubboCase.getAddress());
        //这里配置dubbo的注册中心信息，因此demo没有额外的dubbo.xml配置文件
        reference = new ReferenceConfig<GenericService>();
        reference.setApplication(applicationConfig);
        reference.setRegistry(registryConfig);
        reference.setVersion(dubboCase.getVersion());
        reference.setInterface(dubboCase.getInterFaceClassName());
        reference.setGeneric(true);
        reference.setTimeout(5000);
        /*ReferenceConfig实例很重，封装了与注册中心的连接以及与提供者的连接，
        需要缓存，否则重复生成ReferenceConfig可能造成性能问题并且会有内存和连接泄漏。
        API方式编程时，容易忽略此问题。
        这里使用dubbo内置的简单缓存工具类进行缓存*/
        try {
            cache = ReferenceConfigCache.getCache();
            genericService = cache.get(reference);
            // 用com.alibaba.dubbo.rpc.service.GenericService可以替代所有接口引用
            int len = dubboCase.getParamList().size();
            invokeParamTyeps = new String[len];
            invokeParams = new Object[len];
            for (int i = 0; i < len; i++) {
                invokeParamTyeps[i] = dubboCase.getParamList().get(i).get("ParamType") + "";
                invokeParams[i] = dubboCase.getParamList().get(i).get("Object");
            }
            logger.info("requestMethodName=={}",dubboCase.getMethodName());
            logger.info("requestParamTyep=={}",invokeParamTyeps);
            logger.info("requestParams=={}",invokeParams);

        } catch (Exception e) {
            if (cache != null) {
                cache.destroyAll();
            }
            if (reference != null) {
                reference.destroy();
            }

        }






    }


    @Override
    public SampleResult runTest(JavaSamplerContext arg0) {
        SampleResult sr = new SampleResult();
        sr.setSampleLabel("Dubbo压测");
        try {
            sr.sampleStart();
            String result =  genericService.$invoke(dubboCase.getMethodName(), invokeParamTyeps, invokeParams).toString();
            if ("fail".equals(result)) {
                sr.setSuccessful(false);
                return sr;
            }
            if (result.contains(dubboCase.getCheckData())) {
                sr.setSuccessful(true);
            } else {
                sr.setSuccessful(false);
                logger.error("response==" + result);
            }

        } catch (Exception e) {
            sr.setSuccessful(false);
            logger.error("压测错误==" + getMessage(e));
            e.printStackTrace();
        } finally {
            sr.sampleEnd();
        }

        return sr;
    }


    @Override
    public void teardownTest(JavaSamplerContext arg0) {
        //关闭注册服务,断开与dubbo的连接
        if (cache != null) {
            cache.destroyAll();
        }
        if (reference != null) {
            reference.destroy();
        }
        logger.info("teardownTest==" + JSONObject.toJSONString(arg0));
    }

//
//    public static void main(String[] args) {
//
//        Arguments params = new Arguments();
//        params.addArgument("a","1");
//        JavaSamplerContext arg0 = new JavaSamplerContext(params);
//        DubboSample dubboSample = new DubboSample();
//        dubboSample.setupTest(arg0);
//        dubboSample.setupTest(arg0);
//        dubboSample.teardownTest(arg0);
//
//    }
}
