package com.okay.testcenter;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DubboTest {

    public static <T> T getDubboService(String interfStr, String zookeeperUrl, String appName) throws ClassNotFoundException {
        ApplicationConfig application = new ApplicationConfig();
        application.setName(appName);
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(zookeeperUrl);
        Class<?> interf = Class.forName(interfStr);
        ReferenceConfig<T> rc = new ReferenceConfig<T>();
        rc.setApplication(application);
        rc.setInterface(interf);
        return rc.get();

    }

    @Test
    public void testDubboInvoke() {
        Object result;

        try {
            result = getDubboService("com.noriental.usersvr.service.okuser.SchoolYearService", "zookeeper://10.10.6.3:2181", "group");
            System.out.println("[result]==" + JSONObject.toJSONString(result));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testMap() {
        String str = "{\"completeStatus\":0,\"containsExpired\":true,\"pageBounds\":{\"containsTotalCount\":true,\"limit\":90,\"offset\":0,\"orders\":[],\"page\":1},\"publishType\":6,\"studentId\":81112019,\"subjectId\":0,\"year\":2017}";
        Map requestParams = JSON.parseObject(str, Map.class);
        System.out.println("requestParams==" + requestParams);
        Map twoMap = new HashMap();
        if (JSONObject.toJSONString(requestParams).contains("{")) {
            Iterator<Map.Entry<Object, Object>> entries = requestParams.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<Object, Object> entry = entries.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                System.out.println(key + ":" + value);
                if (value.toString().contains("{")) {

                    Map result = JSON.parseObject(value.toString(), Map.class);
                    twoMap.put(key,"PageBounds" + result);

                }else {
                    twoMap.put(key, value);
                }

            }
        }
        System.out.println("twoMap==" + twoMap);


    }

    @Test
    public void testjson(){
        String json  = "{\"studentId\":\"82951483905\", \"publishType\":6, \"publishTypes\":\"\", \"startDate\":\"2020-01-23 15:10:10\", \"subjectId\":0, \"completeStatus\":0, \"year\":2021, \"containsExpired\":true, \"pageBounds\":\"\", \"maxId\":100, \"minId\":0, \"status\":0, \"isPublish\":1, \"startDeadline\":null, \"endDeadline\":\"\"}";
        Map result = JSON.parseObject(json, Map.class);
        System.out.println("result==" + result);
    }






    @Test
    public void testRequestType(){
        String str = "com.noriental.validate.bean.CommonResponse unfreezeClass(com.noriental.usersvr.bean.request.klass.ClassOperateSvrRequest)\n" +
                "com.noriental.validate.bean.CommonResponse freezeEndClass(com.noriental.usersvr.bean.request.klass.ClassOperateSvrRequest)\n" +
                "com.noriental.validate.bean.CommonResponse findClassOperationRecord(com.noriental.usersvr.bean.request.klass.ClassOperateSvrRequest)\n" +
                "com.noriental.validate.bean.CommonResponse freezeClass(com.noriental.usersvr.bean.request.klass.ClassOperateSvrRequest)";
        String[] methods =  str.split("\n");
        for (String method :methods){

            String methodInfo = method.split(" ")[1];
            String requestType = methodInfo.split("\\)")[0].split("\\(")[1];
            String methodName = methodInfo.split("\\(")[0];
            if("freezeClass".equals(methodName)){
                System.out.println("methodName==" + methodName);
                System.out.println("requestType==" + requestType);
            }

        }


    }
}