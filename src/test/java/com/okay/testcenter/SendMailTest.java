package com.okay.testcenter;

import com.okay.testcenter.service.tool.SendEmailService;
import com.okay.testcenter.tools.GetTime;
import com.okay.testcenter.tools.MailInfo;
import com.okay.testcenter.tools.MailUtil;
import org.apache.commons.mail.EmailAttachment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @return void
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SendMailTest {


    @Autowired
    SendEmailService sendEmailService;
    @Value("${logging.path}")
    private String logPath;


    @Test
    public void testSend() {
        MailInfo mailInfo = new MailInfo();
        List<String> toList = new ArrayList<String>();
        toList.add("zhangyazhou@okay.cn");

        List<String> ccList = new ArrayList<String>();
        ccList.add("zhangyazhou@okay.cn");

        List<String> bccList = new ArrayList<String>();
        bccList.add("zhangyazhou@okay.cn");

        //添加附件
        EmailAttachment att = new EmailAttachment();
        att.setPath(logPath + "/error." + GetTime.getYmd() + ".log");
        att.setName("errorlog.txt");
        List<EmailAttachment> atts = new ArrayList<EmailAttachment>();
        atts.add(att);
        mailInfo.setAttachments(atts);

        mailInfo.setToAddress(toList);//收件人
        mailInfo.setCcAddress(ccList);//抄送人
        mailInfo.setBccAddress(bccList);//密送人

        mailInfo.setSubject("测试报告");
        mailInfo.setContent(getContent());
        MailUtil.sendEmail(mailInfo);

    }


    private static String getContent() {


//        String content = "<style type=\"text/css\">.table{border:1px solid #0058a3;   /* 表格边框 */  \nfont-family:Arial;  \nborder-collapse:collapse;   /* 边框重叠 */  \nbackground-color:#eaf5ff;   /* 表格背景色 */  \nfont-size:14px; }.td{border:1px solid #0058a3;   /* 单元格边框 */  \ntext-align:left;  \npadding-top:4px; padding-bottom:4px;  \npadding-left:10px; padding-right:10px;}.altrow{  \nbackground-color:#c7e5ff;   /* 隔行变色 */  \n} .caption{  \npadding-bottom:5px;  \nfont:bold 1.4em;  \ntext-align:left;  \n}  .th{  \nborder:1px solid #0058a3;   /* 行名称边框 */  \nbackground-color:#4bacff;   /* 行名称背景色 */  \ncolor:#FFFFFF;              /* 行名称颜色 */  \nfont-weight:bold;  \npadding-top:4px; padding-bottom:4px;  \npadding-left:12px; padding-right:12px;  \ntext-align:center;  \n}  </style>";
//
//        content = content + "<hr><div>[测试开始时间]:</div> <div>[测试结束时间]:</div><hr>" +
//                "<table><tr><td>1</td><td>2</td></tr></table>";


//        String contentHeader = "<hr><div>[运行ID]:" + history.getId() + "</div>" +
//                "<div>[开始时间]:" + history.getStartTime() + "</div>" +
//                "<div>[结束时间]:" + history.getEndTime() + "</div>" +
//                "<div>[测试平台地址]:172.18.4.55</div>" +
//                "<div>[测试结果]:" + history.getResult() + "</div>" +
//                "<div>[用例总数]:" + history.getTotalCase() + "</div>" +
//                "<div>[通过数量]:" + history.getPassCase() + "</div>" +
//                "<div>[失败数量]:" + history.getFailCase() + "</div><hr><p><p><p><p>";


        StringBuilder content = new StringBuilder("<html><head></head><body><h5>title</h5>");
        content.append("<table border=\"5\" style=\"border:solid 1px #E8F2F9;font-size=14px;;font-size:18px;\">");
        content.append("<tr style=\"background-color: #428BCA; color:#ffffff\"><th>column1</th><th>column2</th><th>column3</th></tr>");
//        for (T data : list) {
        content.append("<div><tr style=\"background-color: #428BCA; color:#ffffff\">[运行ID]:1</tr></div>");
        content.append("<div><tr style=\"background-color: #428BCA; color:#ffffff\">[开始时间]:</tr></div>"); //第一列
        content.append("<td>" + 2 + "</td>"); //第二列
        content.append("<td>" + 3 + "</td>"); //第三列
        content.append("</tr>");
//        }
        content.append("</table>");
        content.append("<h3>description</h3>");
        content.append("</body></html>");


        return content.toString();
    }


}

