package com.okay.testcenter.performance.base;

import com.okay.testcenter.domain.middle.RequestSampler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Common {

    public static String LINE = "\r\n";

    /**
     * 获取时间戳，13位long类型
     *
     * @return
     */
    public static long getTimeStamp() {
        return System.currentTimeMillis();
    }

    /**
     * 根据时间戳返回对应的时间，并且输出
     *
     * @param time long 时间戳
     * @return 返回时间
     */
    public static String getTimeByTimestamp(long time) {
        Date now = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = format.format(now);
        return nowTime;
    }

    /**
     * 获取当前时间，返回date类型
     *
     * @return
     */
    public static String getDate() {
        Date time = new Date();
        SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String c = now.format(time);
        return c;
    }
    //判断请求header是否为空
    public static void JudeHeader(RequestSampler requestSampler) {

        if (requestSampler.getHeaders().isEmpty()) {
            Map<String, String> headers = new HashMap<>();
            headers.put("requestid", requestSampler.getRequestId());
            headers.put("token", requestSampler.getToken());
            requestSampler.setHeaders(headers);
        }


    }
    /**
     * 重载，用long类型取代date
     *
     * @param start
     * @param end
     * @return
     */
    public static double getTimeDiffer(long start, long end) {
        return (end - start) * 1.0 / 1000;
    }
}
