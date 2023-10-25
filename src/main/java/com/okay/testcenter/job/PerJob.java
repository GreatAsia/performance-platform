package com.okay.testcenter.job;

import com.alibaba.fastjson.JSONObject;
import com.okay.testcenter.common.constant.Receiver;
import com.okay.testcenter.domain.middle.MiddlePerformanceSet;
import com.okay.testcenter.service.middle.MiddlePerformanceService;
import com.okay.testcenter.service.middle.MiddlePerformanceSetService;
import com.okay.testcenter.service.tool.SendEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CompletableFuture;


/**
 * @author zhou
 * @date 2020/7/14
 */
public class PerJob extends BaseJob {
    private static Logger logger = LoggerFactory.getLogger(PerJob.class);
    @Autowired
    MiddlePerformanceSetService middlePerformanceSetService;
    @Autowired
    SendEmailService sendEmailService;
    @Autowired
    MiddlePerformanceService middlePerformanceService;

    private String title = "【压测结果】";
    private String item = "middle";

    @Override
    public void runMonitor(JSONObject jobParam) {

        int setId = Integer.valueOf(jobParam.getString("setId"));

        String receiveString  = Receiver.QARECEIVER;


        run(setId, receiveString);


    }

    /**
     * 运行用例，发送邮件
     *
     * @param setId
     * @param receiveString
     */
    private void run(int setId, String receiveString) {
        int runId = 0;
        try {
            MiddlePerformanceSet middlePerformanceSet = middlePerformanceSetService.findById(setId);
            title = title + middlePerformanceSet.getSet_name();
            CompletableFuture<Integer> completableFuture = middlePerformanceService.runJmeterSet(setId);
            CompletableFuture.allOf(completableFuture).join();
            runId = completableFuture.get();

        } catch (Exception e) {
            logger.error("执行压测发生错误:" + e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sendTo", receiveString);
            jsonObject.put("title", title);
            jsonObject.put("item", item);
            jsonObject.put("historyId", runId);
            sendEmailService.sendEmail(jsonObject);
        }

    }


}
