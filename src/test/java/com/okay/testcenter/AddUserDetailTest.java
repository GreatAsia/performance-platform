package com.okay.testcenter;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.okay.testcenter.domain.middle.AddUserDetail;
import com.okay.testcenter.service.tool.AddUserDetailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddUserDetailTest {


    private static final Logger logger = LoggerFactory.getLogger(AddUserDetailTest.class);
    @Resource
    AddUserDetailService addUserDetailService;


    @Test
    public void testInsert() {
        AddUserDetail addUserDetail = new AddUserDetail();
        addUserDetail.setAccount(12345678);
        addUserDetail.setCookies("cookies");
        addUserDetail.setHistoryId(1);
        addUserDetail.setPwd("123456");
        addUserDetail.setToken("asdasdaeweqweqweqeq");
        addUserDetail.setUserType("学生");
        addUserDetailService.insertDetail(addUserDetail);
    }

    @Test
    public void testUpdate() {

        AddUserDetail addUserDetail = new AddUserDetail();
        addUserDetail.setId(1);
        addUserDetail.setAccount(123456780);
        addUserDetail.setCookies("cookies");
        addUserDetail.setHistoryId(1);
        addUserDetail.setPwd("123456");
        addUserDetail.setToken("asdasdaeweqweqweqeq");
        addUserDetail.setUserType("学生");
        addUserDetailService.updateDetail(addUserDetail);
    }


    @Test
    public void findById() {

        AddUserDetail addUserDetail = addUserDetailService.findDetailByAccount(123456780);
        logger.info("[addUserDetail]" + JSONObject.toJSONString(addUserDetail));
    }


    @Test
    public void findByHistoryId() {

        List<AddUserDetail> addUserDetailList = addUserDetailService.findDetailByHistoryId(13);
        logger.info("[addUserDetailList]" + JSONObject.toJSONString(addUserDetailList));
    }

    @Test
    public void testCreateFile() {

        addUserDetailService.createFile(1, "学生");

    }


    @Test
    public void findList() {

        List<AddUserDetail> addUserDetailList = addUserDetailService.findDetailList();
        logger.info("[addUserDetailList]" + JSONObject.toJSONString(addUserDetailList));
    }



    @Test
    public void testSplit(){

        String str = "x=y";
        String[] arg = str.split("=");
        System.out.println("length=" + arg.length );
    }


    @Test
    public void testNull() {
        AddUserDetail addUserDetail = new AddUserDetail();
        if ("".length() == 0) {
            logger.info("addUserDetail==" + addUserDetail);
        } else {
            logger.info("addUserDetail==null==" + addUserDetail);
        }

    }

}
