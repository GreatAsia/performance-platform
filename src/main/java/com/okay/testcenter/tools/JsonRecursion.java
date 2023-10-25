package com.okay.testcenter.tools;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @author zhou
 * @date 2019/11/14
 */
public class JsonRecursion {

       private String tokens;
       private String uids;
    private final static Logger logger = LoggerFactory.getLogger(JsonRecursion.class);
    /**
     *
     */
    public void recursion(Object objJson,String token,String uid){
        this.tokens = token;
        this.uids =  uid;
        logger.info("token==" + tokens);
        logger.info("uids==" + uids);
        analysisJson(objJson);

    }



    /** 一、
     * 递归遍历JSON对象。
     * @param objJson
     */
    public  void  analysisJson(Object objJson){


        //如果obj为json数组
        if(objJson instanceof JSONArray){
            JSONArray objArray = (JSONArray)objJson;
            for (int i = 0; i < objArray.size(); i++) {
                analysisJson(objArray.get(i));
            }
        }
        //如果为json对象
        else if(objJson instanceof JSONObject){
            JSONObject jsonObject = (JSONObject)objJson;
            Set<String> it = jsonObject.keySet();
            for (String key: it){

                if(key.equals("token")){
                    logger.info("token==" + tokens);
                    jsonObject.put("token",tokens);
                }
                if (key.equals("uid")){
                    logger.info("uids==" + uids);
                    jsonObject.put("uid",uids);
                }

                Object object = jsonObject.get(key);
                //如果得到的是数组
                if(object instanceof JSONArray){
                    JSONArray objArray = (JSONArray)object;
                    analysisJson(objArray);
                }
                //如果key中是一个json对象
                else if(object instanceof JSONObject){
                    analysisJson((JSONObject)object);
                }
                //如果key中是其他
                else{
                    logger.info("[" + key + "]:" + object.toString() + " ");
                }
            }
        }
    }
}
