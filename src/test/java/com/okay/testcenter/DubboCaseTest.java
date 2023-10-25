package com.okay.testcenter;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.dubbo.DubboCase;
import com.okay.testcenter.impl.dubbo.DubboCaseServiceImpl;
import com.okay.testcenter.tools.DubboFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

import static com.okay.testcenter.tools.ZookeeperConnect.getRequestType;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DubboCaseTest {

    private static final Logger logger = LoggerFactory.getLogger(DubboCaseTest.class);
    @Resource
    DubboCaseServiceImpl dubboService;

    @Test
    public void runDubbo() {


        String paramType = "com.noriental.usersvr.bean.request.SchoolRequest";
        String interfaceClass = "com.noriental.usersvr.service.okuser.SchoolYearService";
        String methodName = "findFutureYear";
        String address = "dev_registry.address";

        Map map = new HashMap<String, Object>();
        map.put("ParamType", paramType);  //后端接口参数类型
        //用以调用后端接口的实参
        Map<String, Object> getDataRequest = new HashMap<String, Object>();
        getDataRequest.put("orgId", "119");
        getDataRequest.put("orgType", "2");
        getDataRequest.put("reqId", "123456789");

        logger.info("requestInfo===" + getDataRequest);

        map.put("Object", getDataRequest);

        List<Map<String, Object>> paramInfos = new ArrayList<Map<String, Object>>();
        paramInfos.add(map);
        logger.info("requestInfo===" + paramInfos);
//        Object result = dubboService.runDubbo(address, interfaceClass, methodName, paramInfos,"v3.0.0");
//        logger.info("response===" + result);

    }


    public void test() {

        String address = "hotfix_registry.address";
        DubboFactory dubbo = DubboFactory.getInstance();

        Map map = new HashMap<String, Object>();
        map.put("ParamType", "com.noriental.usersvr.bean.request.SchoolRequest");  //后端接口参数类型
        //用以调用后端接口的实参
        Map<String, Object> getDataRequest = new HashMap<String, Object>();
        getDataRequest.put("orgId", "119");
        getDataRequest.put("orgType", "2");
        getDataRequest.put("reqId", "123456789");

        logger.info("requestInfo===" + getDataRequest);

        map.put("Object", getDataRequest);

        List<Map<String, Object>> paramInfos = new ArrayList<Map<String, Object>>();
        paramInfos.add(map);

//        Object result = dubbo.genericInvoke(address, "com.noriental.usersvr.service.okuser.SchoolYearService", "findFutureYear", paramInfos,"v3.0.0");
//        System.out.println("result===" + result);

    }

    @Test
    public void testJson() {
        String s = "{\"orgType\":\"2\", \"orgId\":\"119\", \"reqId\":\"123456789\"}";
        String[] list = s.replace("{", "").replace("}", "").split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        for (String str : list) {
            String[] param = str.split(":");
            map.put(param[0], param[1]);
        }
        System.out.println("map===" + map);
        System.out.println("mapJSOn===" + JSONObject.toJSONString(map));
    }

    @Test
    public void insertDubboInterface() {
        DubboCase dubboCase = new DubboCase();
        dubboCase.setCaseName("登录");
        dubboCase.setModel(1);
        dubboCase.setInterFaceClassName("com.noriental.usersvr.service.okuser.SchoolYearService");
        dubboCase.setMethodName("login ");
        dubboCase.setRequestType("com.noriental.usersvr.bean.request.SchoolRequest");
        dubboCase.setParams("{orgType:2,orgId:119,reqId:123456789}");
        dubboCase.setCheckData("code:0");
        dubboCase.setCreateTime("2018-12-19 17:04:22");
        dubboCase.setType(0);
        //dubboInterface.setEditTime("2018-12-19 17:04:22");
        logger.info("[dubboInterface]:" + JSONObject.toJSONString(dubboCase));
        dubboService.insertDubboInterface(dubboCase);
    }


    @Test
    public void updateDubboInterface() {
        DubboCase dubboCase = new DubboCase();
        dubboCase.setId(1);
        dubboCase.setCaseName("登录");
        dubboCase.setModel(1);
        dubboCase.setInterFaceClassName("com.noriental.usersvr.service.okuser.SchoolYearService");
        dubboCase.setMethodName("login ");
        dubboCase.setRequestType("com.noriental.usersvr.bean.request.SchoolRequest");
        dubboCase.setParams("{orgType:2,orgId:119,reqId:123456789}");
        dubboCase.setCheckData("code:0");
        dubboCase.setCreateTime("2018-12-19 17:04:22");
        dubboCase.setType(0);
        dubboCase.setEditTime("2018-12-19 17:04:22");
        logger.info("[dubboInterface]:" + dubboCase);
        dubboService.updateDubboInterface(dubboCase);
    }

    @Test
    public void deleteDubboInterface() {
        dubboService.deleteDubboInterfaceById(1);
    }


    @Test
    public void findDubboInterfaceById() {
        DubboCase dubboCase = dubboService.findDubboInterfaceById(3);
        logger.info("dubboInterface===" + JSONObject.toJSONString(dubboCase));
    }

    @Test
    public void findDubboInterfaceByModel() {
        PageInfo dubboInterface = dubboService.findDubboInterfaceListByModel(1, 1, 10);
        if (JSONObject.toJSONString(dubboInterface).equals("[]")) {
            logger.info("响应数据为空");
        }
        logger.info("dubboInterface===" + JSONObject.toJSONString(dubboInterface));
    }


    @Test
    public void findDubboInterfaceByModelId() {
        List<DubboCase> dubboCase = dubboService.findDubboInterfaceListByModel(1);
        if (JSONObject.toJSONString(dubboCase).equals("[]")) {
            logger.info("响应数据为空");
        }
        logger.info("dubboInterface===" + JSONObject.toJSONString(dubboCase));
    }


    @Test
    public void findDubboInterfaceByModelName() {
        List<DubboCase> dubboCase = dubboService.findDubboInterfaceListByModelName("user");
        if (JSONObject.toJSONString(dubboCase).equals("[]")) {
            logger.info("响应数据为空");
        }
        logger.info("dubboInterface===" + JSONObject.toJSONString(dubboCase));
    }


    @Test
    public void findDubboInterfaceList() {
        PageInfo pageInfo = dubboService.findDubboInterfaceList(1, 10);
        int totalPage = pageInfo.getPages();
        int pageSize = pageInfo.getPageSize();
        Long totalNum = pageInfo.getTotal();

        logger.info("totalPage===" + JSONObject.toJSONString(totalPage));
        logger.info("pageSize===" + JSONObject.toJSONString(pageSize));
        logger.info("totalNum===" + JSONObject.toJSONString(totalNum));
        logger.info("pageInfo===" + JSONObject.toJSONString(pageInfo));
    }


    @Test
    public void getTime() {

        String time = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        logger.info("time===" + time);

    }


    @Test
    public void testJSON() {

        String str = "{\"0\":\"zhangsan\",\"1\":\"lisi\",\"2\":\"wangwu\",\"3\":\"maliu\"}";
        Map mapType = JSON.parseObject(str, Map.class);
        logger.info("[json-map-3]" + mapType);
        Map mapObj = JSONObject.parseObject(str, Map.class);
        logger.info("[json-map-6]" + mapObj);

        Map map = new HashMap();
        map.put("id", 1);
        map.put("name", "test");
        Object object = JSONObject.toJSON(map);
        logger.info("[map]" + object);
        String objectString = JSONObject.toJSONString(map);
        logger.info("[map]" + objectString);

    }


    @Test
    public void findDubboList() {
        PageInfo dubboCase = dubboService.findDubboInterfaceListByModelAndEnv(1, 1, 1, 10);
        if (JSONObject.toJSONString(dubboCase).equals("[]")) {
            logger.info("响应数据为空");
        }
        logger.info("dubboCase===" + JSONObject.toJSONString(dubboCase));
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

    @Test
    public void testTelnet() throws IOException, InterruptedException {
        DubboCase dubboCase = new DubboCase();
        dubboCase.setAddress("10.60.0.64 25003");
        dubboCase.setInterFaceClassName("com.noriental.resourcesvr.exercise.service.ExerciseTypeService");
        dubboCase.setMethodName("findExerciseType");

        String type = getRequestType(dubboCase);
        logger.info("type==" + type);
    }


}
