package com.okay.testcenter.performance.tools;

import com.alibaba.fastjson.JSONObject;
import com.okay.testcenter.performance.base.Common;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 用来保存数据的类，如果文件已经存在会删除原来的文件
 */
public class Save {


    private static Logger logger = LoggerFactory.getLogger(Save.class);

    /**
     * 保存信息，每次回删除文件，默认当前工作空间
     *
     * @param content 内容
     */
    public static void info(String content) {
        info("long.log", content);
    }

    public static void info(String name, String content) {
        File dirFile = new File(new File("").getAbsoluteFile() + "/" + name);
        if (dirFile.exists()) dirFile.delete();
        WriteRead.writeText(dirFile, content);
        logger.info("数据保存成功！文件名：{}", name);
    }

    /**
     * 保存list数据到本地文件，name不需要后缀
     */
    public static void saveLongList(List<Long> data, Object name) {
        List<String> list = new ArrayList<>();
        data.forEach(num -> list.add(num.toString()));
        saveStringList(list, name.toString());
    }

    /**
     * 保存list数据到本地文件，name不需要后缀
     */
    public static void saveIntegerList(List<Integer> data, String name) {
        List<String> list = new ArrayList<>();
        data.forEach(num -> list.add(num.toString()));
        saveStringList(list, name);
    }

    /**
     * 保存list数据到本地文件，name不需要后缀
     */
    public static void saveDoubleList(List<Double> data, String name) {
        List<String> list = new ArrayList<>();
        data.forEach(num -> list.add(num.toString()));
        saveStringList(list, name);
    }

    /**
     * 保存list数据，long类型无法覆盖
     *
     * @param data
     * @param name
     */
    public static void saveList(List<Object> data, String name) {
        List<String> list = new ArrayList<>();
        data.forEach(num -> list.add(num.toString()));
        saveStringList(list, name);
    }

    /**
     * 保存list数据到本地文件,name不需要后缀
     */
    public static void saveStringList(List<String> data, String name) {
        String join = StringUtils.join(data, Common.LINE);
        info(name, join);
    }

    /**
     * 保存set数据到本地文件,name不需要后缀
     */
    public static void saveStringList(Set<String> data, String name) {
        List<String> objects = new ArrayList<>(data);
        saveStringList(objects, name);
    }

    /**
     * 保存json数据到本地文件，name不需要后缀
     */
    public static void saveJson(JSONObject data, String name) {
        StringBuffer buffer = new StringBuffer();
        data.keySet().forEach(x -> buffer.append(Common.LINE + x.toString() + "|" + data.getString(x.toString())));
        info(name, buffer.toString().substring(2));
    }


}
