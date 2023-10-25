package com.okay.testcenter.tools;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class GetTime {

    private static final Logger logger = LoggerFactory.getLogger(GetTime.class);


    public static String getTime() {

        String time = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date());

        return time;
    }

    public static String getTime(Long addTime) {

        String time = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(System.currentTimeMillis() + addTime);

        return time;
    }

    /**
     * @return 获取年月日
     */
    public static String getYmd() {

        String time = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date());

        return time;
    }

}
