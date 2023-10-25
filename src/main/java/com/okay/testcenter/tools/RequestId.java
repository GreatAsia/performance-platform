package com.okay.testcenter.tools;

import java.text.NumberFormat;
import java.util.UUID;

/**
 * Created by Administrator on 2018/5/8.
 */
public class RequestId {

    private String requestId;


    public String generateRequestId(String proId) {
        if (proId == null) {
            proId = "00";
        }
        NumberFormat nf = NumberFormat.getInstance();
        //设置是否使用分组
        nf.setGroupingUsed(false);
        //设置最大整数位数
        nf.setMaximumIntegerDigits(10);
        //设置最小整数位数
        nf.setMinimumIntegerDigits(10);
        requestId = "qa_" + proId + nf.format(UUID.randomUUID().hashCode() & (0x7fffffff));
        return requestId;
    }
}
