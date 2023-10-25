package com.okay.testcenter.impl.tool;

import com.alibaba.fastjson.JSONObject;
import com.okay.testcenter.domain.dubbo.DubboCaseHistory;
import com.okay.testcenter.domain.dubbo.DubboTestHistory;
import com.okay.testcenter.domain.middle.MiddleCase;
import com.okay.testcenter.domain.middle.MiddleInterface;
import com.okay.testcenter.domain.middle.MiddlePerformanceResult;
import com.okay.testcenter.service.dubbo.DubboInterfaceHistoryService;
import com.okay.testcenter.service.dubbo.DubboTestHistoryService;
import com.okay.testcenter.service.middle.MiddleCaseService;
import com.okay.testcenter.service.middle.MiddleInterfaceService;
import com.okay.testcenter.service.middle.MiddlePerformanceResultService;
import com.okay.testcenter.service.tool.SendEmailService;
import com.okay.testcenter.tools.GetTime;
import com.okay.testcenter.tools.MailInfo;
import com.okay.testcenter.tools.MailUtil;
import org.apache.commons.mail.EmailAttachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhou
 * @date 2019/11/3
 */


@Service("SendEmailService")
public class SendEmailServiceImpl implements SendEmailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${logging.path}")
    private String logPath;

    private int emailWidth = 800;
    private String projectName;
    @Resource
    MiddlePerformanceResultService middlePerformanceResultService;
    private String platformUrl = "http://okay-qaplatform.xk12.cn";



    @Resource
    DubboTestHistoryService dubboTestHistoryService;
    @Resource
    DubboInterfaceHistoryService dubboInterfaceHistoryService;
    @Resource
    MiddleCaseService middleCaseService;
    @Resource
    MiddleInterfaceService middleInterfaceService;
    private String title;




    private String commonHeader = "<!DOCTYPE html>    \n" +
            "<html>    \n" +
            "<head>\n" +
            "\n" +
            "<title>Test Report</title>\n" +
            "</head> " +
            "<body >" +
            "<table width=" + emailWidth + "cellspacing=\"0\" cellpadding=\"0\" border=\"1\" name=\"bmeMainBody\" ";

    private String passHeader = commonHeader + "bgcolor=\"#3CB371\"> ";
    private String normalHeader = commonHeader + "> ";

    private String failHeader = commonHeader + "bgcolor=\"#FF6347\"> ";


    private String tail = "</table></body></html>" +
            "<br>";
    private String normalTail = "</table></body></html>" +
            "<br>";
    private String failCase = failHeader + "<caption class=\"caption\"></h1><strong>FAIL用例</strong></h1></caption>";
    private String passCase = passHeader + "<caption class=\"caption\"></h1><strong>PASS用例</strong></h1></caption>";


    @Override
    public Boolean sendEmail(JSONObject request) {

        String errorLogPath = logPath + "/server-error." + GetTime.getYmd() + ".log";
        String sendTo = request.getString("sendTo");
        this.title = request.getString("title");
        //middle、app、dubbo
        String item = request.getString("item");
        int historyId = request.getInteger("historyId");

        logger.info("[收件人]==" + sendTo);
        logger.info("[附件]==" + errorLogPath);
        logger.info("[标题]==" + title);
        logger.info("[historyId]==" + historyId);


        MailInfo mailInfo = new MailInfo();
        List<String> toList = new ArrayList<>();
        String[] sendToArray = sendTo.split(";");
        if (sendToArray.length > 0) {
            for (int i = 0; i < sendToArray.length; i++) {

                toList.add(sendToArray[i]);
            }

        } else {
            logger.error("收件人错误,请检查==" + sendTo);
        }
        //添加附件
        EmailAttachment att = new EmailAttachment();
        att.setPath(errorLogPath);
        att.setName("errorlog.txt");
        List<EmailAttachment> atts = new ArrayList<EmailAttachment>();
        atts.add(att);
        mailInfo.setAttachments(atts);
        //收件人
        mailInfo.setToAddress(toList);

        mailInfo.setContent(getEmailContent(item, historyId));
        mailInfo.setSubject(title);
        Boolean result = MailUtil.sendEmail(mailInfo);

        return result;

    }


    private String getEmailContent(String item, int id) {
        String content = "";
        switch (item) {

            case "middle":
                content = getMiddleContent(id);
                break;
            case "dubbo":
                content = getDubboContent(id);
                break;
            default:
                logger.error("类型名称错误==" + item);
        }

        return content;

    }


    /**
     * 报告汇总中间层压测
     *
     * @param
     * @return
     */
    private String sunmaryInfo() {

//        title = "【第N次压测结果】项目名称+版本号";
        String split4 = "&nbsp&nbsp&nbsp&nbsp";
        String split6 = "&nbsp&nbsp&nbsp&nbsp";

        String sunmary = normalHeader +
                "<div style=\"text-align:left;\" class=\"caption\">Hi All:\n </div>\n<br>" +
                "<div style=\"text-align:left;\" class=\"caption\">" + split4 + "压测结果如下:</div>\n<br>" +
                "<div style=\"font-size:14px\">"+ split6 +"<strong>一、压测环境:</strong></div><br>" +
                "<div style=\"font-size:13px\">"+ split6 +"数据库地址:mysql-dev.wf：3306</div><br>" +
                "<div style=\"font-size:13px\">"+ split6 +"机器配置:xxx-svr(单节点 cpu 4核 4G  共2个节点)</div><br>" +
                "<div style=\"font-size:14px\">"+ split6 +"<strong>二、压测工具:</strong></div><br>" +
                "<div style=\"font-size:13px\">"+ split6 +"Jmeter</div><br>" +
                "<div style=\"font-size:14px\">"+ split6 +"<strong>三、注释:\n  </strong></div><br>" +
                "<div style=\"font-size:13px\">"+ split6 +"每个接口单独压测，测试结果将统计接口的平均事务响应时间和吞吐量\n" + " </div>" +
                "<div style=\"font-size:13px\">"+ split6 +"Ø  Jmeter平均事务响应时间：模拟客户端通过网络访问接口url到返回结果的时间\n" + " </div>" +
                "<div style=\"font-size:13px\">"+ split6 +"Ø  Jmeter吞吐量：每秒钟处理的请求数 </div><br>" +
                "<div style=\"font-size:14px\">"+ split6 +"<strong>四、压测结论:</strong></div><br>" +
                "<div style=\"font-size:13px\">  </div>" +
                "<div style=\"font-size:14px\">"+ split6 +"<strong>五、压测结果:</strong></div><br>" +
                normalTail;
        return sunmary;
    }

    /**
     * 压测表格结果
     * @param request
     * @param time
     * @param put
     * @param error
     * @param caseName
     * @param url
     * @return
     */
    private String bodyInfo(MiddlePerformanceResult request, Boolean time, Boolean put, Boolean error, String caseName, String url) {

        String resTime;
        String thrPut;
        String errRate;

        if (time) {
            resTime = "                    <td style=\"word-wrap: break-word;\">" + request.getResponse_time() + "</td>\n";
        } else {
            resTime = "                    <td style=\"word-wrap: break-word;background-color: #FF6347;\">" + request.getResponse_time() + "</td>\n";

        }

        if (put) {
            thrPut = "                    <td style=\"word-wrap: break-word;\">" + request.getThroughput() + "</td>\n";
        } else {
            thrPut = "                    <td style=\"word-wrap: break-word;background-color: #FF6347;\">" + request.getThroughput() + "</td>\n";
        }

        if (error) {
            errRate = "                    <td style=\"word-wrap: break-word;\">" + request.getError_rate() + "%</td>\n";
        } else {
            errRate = "                    <td style=\"word-wrap: break-word;background-color: #FF6347;\">" + request.getError_rate() + "%</td>\n";
        }

        String content = "                <thead class=\"thead-light\">\n" +
                "       <tr><td>" + caseName + "</td></tr>\n" +
                "                <tr>\n" +
                "                    <td>并发数量</td>\n" +
                "                    <td>压测时间</td>\n" +
                "                    <td>响应时间</td>\n" +
                "                    <td>TPS</td>\n" +
                "                    <td>错误率</td>\n" +
                "                    <td>URL</td>\n" +
                "\t\t\t\t\t<td>备注</td>\n" +
                "                </tr>\n" +
                "                </thead>\n" +
                "                <tbody id=\"tbody\">\n" +
                "                <tr>\n" +
                "                    <td style=\"word-wrap: break-word;\">" + request.getThreads() + "</td>\n" +
                "                    <td style=\"word-wrap: break-word;\">" + request.getRun_time() + "</td>\n" +
                resTime +
                thrPut +
                errRate +
                "                    <td style=\"word-wrap: break-word;\">" + url + "</td>\n" +
                "                    <td style=\"word-wrap: break-word;\">" + request.getError_data() + "</td>\n" +
                "                </tr>\n" +
                "\n" +
                "                </tbody>\n" +
                "</table><br>";


        content = "<table width=" + emailWidth + "cellspacing=\"0\" cellpadding=\"0\" border=\"1\"  style=\"table-layout:fixed\">" + content;

        return content;
    }


    /**
     * 获取中间层内容
     *
     * @param id 用例ID
     * @return
     */
    private String getMiddleContent(int id) {


        List<MiddlePerformanceResult> middlePerformanceResultList = middlePerformanceResultService.findByRunId(id);

        String contentHeader = sunmaryInfo();

        StringBuffer contentBody = new StringBuffer();


        for (MiddlePerformanceResult result : middlePerformanceResultList) {

            MiddleCase middleCase = middleCaseService.findMiddleCaseById(result.getCase_id());
            MiddleInterface middleInterface = middleInterfaceService.findMiddleInterfaceById(middleCase.getInterface_id());

            Boolean responseTime = result.getResponse_time() <= 2000;
            Boolean throughPut = result.getThroughput() >= 50;
            Boolean errorRate = Float.parseFloat(result.getError_rate()) <= 0;
            contentBody.append(bodyInfo(result, responseTime, throughPut, errorRate, middleCase.getName(), middleInterface.getUrl()));

        }
        contentBody.append(tail);


        return contentHeader + contentBody;

    }


    /**
     * 报告汇总后端
     *
     * @param history
     * @return
     */
    private String dubboSunmaryInfo(DubboTestHistory history) {

        String sunmary = passHeader +
                "<tr><td style=\"font-size:14px\">[运行ID]:" + history.getId() + "</td></tr>" +
                "<tr><td style=\"font-size:14px\">[服务名称]:" + history.getModelName() + "</td></tr>" +
                "<tr><td style=\"font-size:14px\">[开始时间]:" + history.getStartTime() + "</td></tr>" +
                "<tr><td style=\"font-size:14px\">[结束时间]:" + history.getEndTime() + "</td></tr>" +
                "<tr><td style=\"font-size:14px\">[测试平台地址]:" + platformUrl + "</td></tr>" +
                historyResult(history.getResult()) +
                "<tr><td style=\"font-size:14px\">[用例总数]:" + history.getTotalCase() + "</td></tr>" +
                "<tr><td style=\"font-size:14px\">[通过数量]:" + history.getPassCase() + "</td></tr>" +
                "<tr><td style=\"font-size:14px\";color:\"#ac2925\">[失败数量]:" + history.getFailCase() + "</td></tr>" +
                tail;
        return sunmary;
    }

    /**
     * 后端响应内容
     *
     * @param request
     * @return
     */
    private String dubboBodyInfo(DubboCaseHistory request) {

        String content = "<tr><td style=\"word-wrap: break-word;\" >[请求数据]:<xmp style=\"white-space:normal;\">" + request.getRequestData() + "</xmp></td></tr>" +
                "<tr><td style=\"word-wrap: break-word;\" >[响应数据]:<xmp style=\"white-space:normal;\">" + request.getResponseContent() + "</xmp></td></tr>" +
                "</table><br>";

        if ("FAIL".equals(request.getResult())) {
            content = "<table width=" + emailWidth + "cellspacing=\"0\" cellpadding=\"0\" border=\"1\"  style=\"background-color: #FF6347;table-layout:fixed\">" + content;
        } else {
            content = "<table width=" + emailWidth + "cellspacing=\"0\" cellpadding=\"0\" border=\"1\"  style=\"background-color: #3CB371;table-layout:fixed\">" + content;
        }
        return content;
    }


    //获取dubbo端报告内容
    private String getDubboContent(int id) {

        DubboTestHistory history = dubboTestHistoryService.findTestHistoryById(id);
        List<DubboCaseHistory> requestList = dubboInterfaceHistoryService.findHistoryByHistoryId(id);
        projectName = history.getModelName();
        this.title = projectName + "_" + title;
        String contentHeader = dubboSunmaryInfo(history);
        StringBuffer errorContentBody = new StringBuffer();
        errorContentBody.append(failCase);
        StringBuffer passContentBody = new StringBuffer();
        passContentBody.append(passCase);
        //添加一个变量,判断是否有失败用例
        int failDubboCaseCount = 0;

        for (DubboCaseHistory request : requestList) {
            if ("FAIL".equals(request.getResult())) {
                errorContentBody.append(dubboBodyInfo(request));
                failDubboCaseCount++;
            } else {
                passContentBody.append(dubboBodyInfo(request));

            }
        }
        passContentBody.append(tail);
        errorContentBody.append(tail);

        if (failDubboCaseCount > 0) {
            return contentHeader + errorContentBody + passContentBody;
        }
        return contentHeader + passContentBody;

    }





    private String historyResult(String isPass) {
        String result = "";
        if ("PASS".equals(isPass)) {

            result = "<tr><td style=\"font-size:14px\">[测试结果]:Pass</td></tr>";
        } else if ("FAIL".equals(isPass)) {

            result = "<tr><td style=\"font-size:14px\">[测试结果]:<b style=\"color: #dc3545; \">Fail</b></td></tr>" ;
        }
        return result;

    }


}
