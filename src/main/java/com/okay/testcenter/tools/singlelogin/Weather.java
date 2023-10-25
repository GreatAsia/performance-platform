package com.okay.testcenter.tools.singlelogin;


import com.okay.testcenter.tools.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class Weather {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String, String> params = new HashMap<>();

    private String urlString;
    private String lt;
    private String execution;
    private String eventId;

    public Weather(String urlString) {
        this.urlString = urlString;
    }

    public static void main(String[] args) throws Exception {

        Weather client = new Weather("https://sso-hotfix.xk12.cn/login?service=https://jiaoshi-hotfix.xk12.cn/");
        client.run();
    }


    public Map<String, String> run() {

        try {
            URL url = new URL(urlString);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection
                    .getInputStream(), "utf8"));
            String line;

            while ((line = reader.readLine()) != null) {

                if (line.contains("name=\"lt\"")) {
                    lt = line.split("value=")[1].replace("\"", "").replace("/>", "").trim();
                    logger.info("[lt]==" + lt);
                }
                if (line.contains("name=\"execution\"")) {
                    execution = line.split("value=")[1].replace("\"", "").replace("/>", "").trim();
                    logger.info("[execution]==" + execution);
                }
                if (line.contains("name=\"_eventId\"")) {
                    eventId = line.split("value=")[1].replace("\"", "").replace("/>", "").trim();
                    logger.info("[_eventId]==" + eventId);
                }
            }

            params.put("lt", lt);
            params.put("execution", execution);
            params.put("_eventId", eventId);

        } catch (Exception e) {
            logger.error("获取lt/execution/_eventId失败:" + e.getLocalizedMessage());
            logger.error(ExceptionUtil.getMessage(e));
        }

        return params;
    }


}
