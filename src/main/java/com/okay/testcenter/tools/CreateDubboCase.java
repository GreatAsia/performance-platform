package com.okay.testcenter.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.okay.testcenter.domain.Env;
import com.okay.testcenter.domain.dubbo.DubboCase;
import com.okay.testcenter.service.middle.EnvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.*;


/**
 * @author zhou
 * @date 2020/8/26
 */
public class CreateDubboCase {
    private static final Logger logger = LoggerFactory.getLogger(CreateDubboCase.class);
    private ApplicationContext applicationContext = SpringUtils.getApplicationContext();
    EnvService envService = applicationContext.getBean(EnvService.class);

    public DubboCase create(DubboCase dubboCase) {

        Env env = envService.findEnvById(dubboCase.getEnvId());
        String zkAddress = "zookeeper://" + env.getAddress() + ":" + env.getPort();
        //运行case
        Map requestParams = JSON.parseObject(dubboCase.getParams().trim(), Map.class);
        Map twoMap = new HashMap();
        if (JSONObject.toJSONString(requestParams).contains("{")) {
            Iterator<Map.Entry<Object, Object>> entries = requestParams.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<Object, Object> entry = entries.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                if(value != null){
                    if (value.toString().contains("{")) {
                        Map result = JSON.parseObject(value.toString(), Map.class);
                        twoMap.put(key,  result);
                    } else {
                        twoMap.put(key, value);
                    }
                }else {
                    twoMap.put(key, value);
                }

            }
        } else {
            twoMap = requestParams;
        }

        logger.info("上行参数=={}",twoMap);
        Map map = new HashMap<String, Object>(2);
        map.put("ParamType", dubboCase.getRequestType());
        map.put("Object", twoMap);
        List<Map<String, Object>> params = new ArrayList<Map<String, Object>>();
        params.add(map);
        dubboCase.setParamList(params);
        dubboCase.setAddress(zkAddress);
        return dubboCase;
    }


}
