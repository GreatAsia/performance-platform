//package com.okay.testcenter;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.okay.testcenter.domain.user.LoginUser;
//import com.okay.testcenter.service.UserService;
//import com.okay.testcenter.tools.GetTime;
//import com.okay.testcenter.tools.JsonRecursion;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.annotation.Resource;
//import java.util.Set;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class UserTest {
//
//    @Resource
//    UserService userService;
//
//    private String requestInfo = "{\"parme\":{\"studentid\":\"81951367632\",\"uid\":\"99951328958\",\"studentpwd\":\"ERbItiEsZ4c=\",\"token\":\"83342ed792094e3eb7ee64ee9c94f2d4\"},\"role\":\"0\",\"os\":\"ios\",\"sw\":\"1242\",\"channel\":\"okay\",\"imsi\":\"\",\"contype\":\"0\",\"ua\":\"iPhone6Plus(A1522/A1524)_11.2.6\",\"vc\":\"2911\",\"mac\":\"\",\"serial\":\"92A3D1B3-C0CE-44B3-BF36-B324EDFD7C31\",\"sh\":\"2208\",\"vn\":\"2.9.0.5\",\"imei\":\"\",\"udid\":\"062838CA-5B44-494A-823B-38025403292C\",\"vs\":\"11.2.6\"}";
//
//    @Test
//    public void testFindByUserName() {
//
//        LoginUser user = userService.findByUserName("zhangyazhou");
//        System.out.println("[user]==" + JSONObject.toJSONString(user));
//    }
//
//    /**
//     * 一、
//     * 递归遍历JSON对象。
//     *
//     * @param objJson
//     */
//    public static void analysisJson(Object objJson) {
//
//        //如果obj为json数组
//        if (objJson instanceof JSONArray) {
//            JSONArray objArray = (JSONArray) objJson;
//            for (int i = 0; i < objArray.size(); i++) {
//                analysisJson(objArray.get(i));
//            }
//        }
//        //如果为json对象
//        else if (objJson instanceof JSONObject) {
//            JSONObject jsonObject = (JSONObject) objJson;
//            Set<String> it = jsonObject.keySet();
//            for (String key : it) {
//
//                if (key.equals("token")) {
//                    jsonObject.put("token", "test");
//                }
//                if (key.equals("uid")) {
//                    jsonObject.put("uid", "test");
//                }
//
//                Object object = jsonObject.get(key);
//                //如果得到的是数组
//                if (object instanceof JSONArray) {
//                    JSONArray objArray = (JSONArray) object;
//                    analysisJson(objArray);
//                }
//                //如果key中是一个json对象
//                else if (object instanceof JSONObject) {
//                    analysisJson((JSONObject) object);
//                }
//                //如果key中是其他
//                else {
//                    System.out.println("[" + key + "]:" + object.toString() + " ");
//                }
//            }
//        }
//    }
//
//    @Test
//    public void testJsonReplace() {
//
//        JSONObject jsonObject = JSONObject.parseObject(requestInfo);
//        new JsonRecursion().recursion(requestInfo, "token", "uid");
//        System.out.println("json==" + jsonObject);
//
//
//    }
//
//
//    @Test
//    public void testDate() {
//
//        String time = GetTime.getYmd();
//        System.out.println("time==" + time);
//
//    }
//
//
//}
