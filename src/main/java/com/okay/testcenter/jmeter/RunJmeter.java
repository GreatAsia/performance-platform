package com.okay.testcenter.jmeter;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.okay.testcenter.domain.middle.MiddlePerformanceResult;
import com.okay.testcenter.domain.middle.RequestSampler;
import com.okay.testcenter.service.middle.MiddlePerformanceResultService;
import com.okay.testcenter.tools.SpringUtils;
import org.apache.jmeter.JMeter;
import org.apache.jmeter.assertions.ResponseAssertion;
import org.apache.jmeter.assertions.gui.AssertionGui;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.CSVDataSet;
import org.apache.jmeter.config.RandomVariableConfig;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.DistributedRunner;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.control.Cookie;
import org.apache.jmeter.protocol.http.control.CookieManager;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.gui.CookiePanel;
import org.apache.jmeter.protocol.http.gui.HTTPArgumentsPanel;
import org.apache.jmeter.protocol.http.gui.HeaderPanel;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.protocol.http.util.HTTPArgument;
import org.apache.jmeter.report.dashboard.ReportGenerator;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.joor.Reflect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.util.*;

import static com.okay.testcenter.tools.GetTime.getTime;

/**
 * @author zhou
 * @date 2019/12/30
 */


public class RunJmeter {

    private static final Logger log = LoggerFactory.getLogger(RunJmeter.class);
    /**
     * 获取ApplicationContext的实例
     */
    private ApplicationContext applicationContext = SpringUtils.getApplicationContext();
    MiddlePerformanceResultService middlePerformanceResultService = applicationContext.getBean(MiddlePerformanceResultService.class);


    private String startTime;
    private String summerInfo = "";
    private static RequestSampler requestSampler;
    private static StandardJMeterEngine standardJMeterEngine;
    private static DistributedRunner distributedRunner;
    private String tokenStr = "token";
    private String uidStr = "uid";


    private static Boolean flag = true;

    public static Boolean getFlag() {
        return flag;
    }

    public static void setFlag(Boolean flag) {
        RunJmeter.flag = flag;
    }


    private RunJmeter() {
    }

    public static  RunJmeter getInstance(RequestSampler request){
        requestSampler = request;
        return RunJmeterFactory.instance;
    }

    static class RunJmeterFactory{
        private final static  RunJmeter instance = new RunJmeter();
    }

    public static void stop( ){

        if (StringUtil.isNotEmpty(requestSampler.getSlaveStr())) {
            if (distributedRunner != null) {
                distributedRunner.stop();
                log.info("Jmeter Distributed is Stop Now!!!");
            }

        } else {
            if (standardJMeterEngine != null) {
                standardJMeterEngine.stopTest();
                log.info("Jmeter is Stop Now!!!");
            }
        }

    }


    //参数化
    private CSVDataSet createCSVDataSet() {
        CSVDataSet csvDataSet = new CSVDataSet();

        csvDataSet.setProperty("filename", requestSampler.getJmeterDataPath());
        csvDataSet.setProperty("variableNames", "id,info");

        csvDataSet.setProperty("delimiter", ",");
        csvDataSet.setProperty("fileEncoding", "UTF-8");
        csvDataSet.setProperty("ignoreFirstLine", false);
        csvDataSet.setProperty("quotedData", false);
        csvDataSet.setProperty("recycle", true);
        csvDataSet.setProperty("shareMode", "shareMode.all");
        csvDataSet.setProperty("stopThread", false);

        csvDataSet.setName("CSV 数据文件设置");
        csvDataSet.setEnabled(true);
        csvDataSet.setProperty(TestElement.TEST_CLASS, CSVDataSet.class.getName());
        csvDataSet.setProperty(TestElement.GUI_CLASS, TestBeanGUI.class.getName());

        return csvDataSet;
    }

    /**
     * 创建线程组
     *
     * @return
     */
    public ThreadGroup createThreadGroup() {
        // Thread Group
        ThreadGroup threadGroup = new ThreadGroup();
        threadGroup.setSamplerController(createLoopController());
        threadGroup.setNumThreads(requestSampler.getThreads());
        threadGroup.setRampUp(0);
        threadGroup.setScheduler(true);
        threadGroup.setDuration(requestSampler.getRunTime());
        threadGroup.setDelay(0);
        threadGroup.setProperty("ThreadGroup.on_sample_error","continue");
        threadGroup.setName("线程组");
        threadGroup.setEnabled(true);
        threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
        threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());
        return threadGroup;
    }

    /**
     * 创建测试计划
     * <p>
     * return TestPlan
     */
    public TestPlan createTestPlan() {
        //创建测试计划
        TestPlan testPlan = new TestPlan();
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        //用户自定义变量
        testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());
        testPlan.setFunctionalMode(false);
        testPlan.setSerialized(true);
        testPlan.setTearDownOnShutdown(true);
        testPlan.setName("测试计划");
        testPlan.setEnabled(true);
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        return testPlan;
    }

    /**
     * 创建循环控制器
     *
     * @return
     */
    private LoopController createLoopController() {
        // Loop Controller
        LoopController loopController = new LoopController();
        loopController.setLoops(-1);
        loopController.setContinueForever(false);
        loopController.setName("控制器");
        loopController.setEnabled(true);
        loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
        loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
        loopController.initialize();
        return loopController;
    }

    private RandomVariableConfig createRandomVariableConfig() {

        RandomVariableConfig randomVariableConfig = new RandomVariableConfig();
        randomVariableConfig.setProperty("maximumValue", "999999999");
        randomVariableConfig.setProperty("minimumValue", "000000000");
        randomVariableConfig.setProperty("outputFormat", "9");
        randomVariableConfig.setProperty("perThread", true);
        randomVariableConfig.setProperty("randomSeed", "");
        randomVariableConfig.setProperty("variableName", "requestid");
        randomVariableConfig.setName("Random Variable");
        randomVariableConfig.setEnabled(true);


        randomVariableConfig.setProperty(TestElement.TEST_CLASS, RandomVariableConfig.class.getName());
        randomVariableConfig.setProperty(TestElement.GUI_CLASS, TestBeanGUI.class.getName());


        return randomVariableConfig;
    }

    public String start()  {


        //增加结果收集
        SummariserUtils summer = null;

        // 设置不适用gui的方式调用jmeter
        System.setProperty(JMeter.JMETER_NON_GUI, "true");
        // 设置jmeter.properties文件
        String path = requestSampler.getJmeterPath();
        File jmeterPropertiesFile = new File(path);

        if (!jmeterPropertiesFile.exists()) {
            log.error("jmeter.properties是否存在" + jmeterPropertiesFile.exists());
            return "fail";
        }
            JMeterUtils.loadJMeterProperties(jmeterPropertiesFile.getPath());
            // 创建测试计划
            TestPlan testPlan = createTestPlan();
            // 创建线程组
            ThreadGroup threadGroup = createThreadGroup();
            // 创建循环控制器
            LoopController loopController = createLoopController();
            // 线程组设置循环控制
            threadGroup.setSamplerController(loopController);
            // 创建http请求收集器
            HTTPSamplerProxy examplecomSampler = createHTTPSamplerProxy();
            //创建检查点
            ResponseAssertion responseAssertion = createResponseAssertion();
            //创建随机生成数
            RandomVariableConfig randomVariableConfig = createRandomVariableConfig();
            HashTree testPlanTree = new HashTree();
            // 将测试计划添加到测试配置树种
            HashTree threadGroupHashTree = testPlanTree.add(testPlan, threadGroup);
            // 将http请求采样器添加到线程组下

            threadGroupHashTree.add(examplecomSampler);
            threadGroupHashTree.add(responseAssertion);
            threadGroupHashTree.add(randomVariableConfig);
        if ("不用".equals(requestSampler.getMoreUser())) {
            //创建cookie
            CookieManager cookieManager = createCookieManager();
            //创建header
            HeaderManager headerManager = createHeaderManager();
            threadGroupHashTree.add(cookieManager);
            threadGroupHashTree.add(headerManager);
        } else {
            //创建读取参数化数据
            CSVDataSet csvDataSet = createCSVDataSet();
            //创建header
            HeaderManager moreHeaderManager = createMoreUserHeaderManager();
            threadGroupHashTree.add(moreHeaderManager);
            threadGroupHashTree.add(csvDataSet);
        }

            String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
            if (summariserName.length() > 0) {
                summer = new SummariserUtils(summariserName);
            }
            ResultCollectorUtil logger = new ResultCollectorUtil(summer);
        testPlanTree.add(testPlanTree.getArray()[0], logger);
        ReportGenerator reportGenerator = null;
        JmsterListenToTest testListener = new JmsterListenToTest(
                JmsterListenToTest.RunMode.REMOTE, true, reportGenerator);
        testPlanTree.add(testPlanTree.getArray()[0], testListener);

        try {
            // 判断jmeter使用分布式
            if (StringUtil.isNotEmpty(requestSampler.getSlaveStr())) {

                java.util.StringTokenizer st = new java.util.StringTokenizer(requestSampler.getSlaveStr(), ",");
                List<String> hosts = new LinkedList<>();
                while (st.hasMoreElements()) {
                    hosts.add((String) st.nextElement());
                }
                distributedRunner = new DistributedRunner();
                distributedRunner.setStdout(System.out);
                distributedRunner.setStdErr(System.err);
                distributedRunner.init(hosts, testPlanTree);
                distributedRunner.start();
            } else {
                //Jemter 引擎
                standardJMeterEngine = new StandardJMeterEngine();
                standardJMeterEngine.configure(testPlanTree);
                startTime = getTime();
                standardJMeterEngine.run();
            }

        } catch (Exception e) {
            standardJMeterEngine.stopTest();
            e.printStackTrace();
            log.error("压测遇到问题==" + e.getLocalizedMessage());
            return "fail";
        }
           if(false == flag){
               log.warn("手动停止运行,不存结果，flag==" + flag);
               return "fail";
           }
            log.info("压测完成,结束standardJMeterEngine");

            //获取聚合报告结果
            summerInfo = Reflect.on(summer).get("summerMessage");
            log.info("聚合报告:" + summerInfo);
            String errorData = summer.getErr();
            float throughtPut = Float.parseFloat(summerInfo.split("=")[2].split("/")[0].trim());
            int responseTime = Integer.parseInt(summerInfo.split("Avg:")[1].split("Min")[0].trim());
            String errorRate = summerInfo.split("\\(")[1].split("%")[0].trim();
        int max = Integer.parseInt(summerInfo.split("Max:")[1].split("Err")[0].trim());
        int min = Integer.parseInt(summerInfo.split("Min:")[1].split("Max")[0].trim());
        Long totalRequest = Long.parseLong(summerInfo.split("in")[0].split("=")[1].trim());

            MiddlePerformanceResult middlePerformanceResult = new MiddlePerformanceResult();
            middlePerformanceResult.setStart_time(startTime);
            middlePerformanceResult.setEnd_time(getTime());
            middlePerformanceResult.setCase_id(requestSampler.getMiddleCase().getId());
            middlePerformanceResult.setError_rate(errorRate);
            middlePerformanceResult.setResponse_time(responseTime);
            middlePerformanceResult.setThroughput(throughtPut);
            middlePerformanceResult.setRun_id(requestSampler.getRun_id());
            middlePerformanceResult.setSet_id(requestSampler.getSet_id());
            middlePerformanceResult.setRun_time(requestSampler.getRunTime());
            middlePerformanceResult.setThreads(requestSampler.getThreads());
            middlePerformanceResult.setError_data(errorData);
        middlePerformanceResult.setTotal_request(totalRequest);
        middlePerformanceResult.setMax_time(max);
        middlePerformanceResult.setMin_time(min);
        try {
            middlePerformanceResultService.insert(middlePerformanceResult);
        } catch (Exception e) {
            log.error("添加压测数据失败==" + e.getMessage());
            e.printStackTrace();
        }

        String fail = summerInfo.split("Err:")[1].split("\\(")[0].trim();
            int failCount = Integer.parseInt(fail);
            if (failCount > 0) {
                log.error("performance fail   failCount==" + failCount);
                return "fail";
            } else {
                log.info("performance pass   failCount==" + failCount);
                return "pass";
            }


    }

    /**
     * 设置检查点
     * return
     */

    private ResponseAssertion createResponseAssertion() {

        //设置检查点
        ResponseAssertion responseAssertion = new ResponseAssertion();
        responseAssertion.addTestString(requestSampler.getMiddleCase().getCheck_data());
        responseAssertion.setTestFieldResponseData();
        responseAssertion.setToContainsType();
        responseAssertion.setName("响应断言");
        responseAssertion.setEnabled(true);
        responseAssertion.setProperty(TestElement.TEST_CLASS, ResponseAssertion.class.getName());
        responseAssertion.setProperty(TestElement.GUI_CLASS, AssertionGui.class.getName());
        return responseAssertion;
    }

    private CookieManager createCookieManager() {

        String host = requestSampler.getUrl_header().split("//")[1].trim();
        CookieManager cookieManager = new CookieManager();

        Map<String, String> cookies = requestSampler.getCookies();
        Iterator cookieTt = cookies.entrySet().iterator();
        while (cookieTt.hasNext()) {
            Map.Entry entry = (Map.Entry) cookieTt.next();
            Cookie cookie = new Cookie();

            cookie.setName(entry.getKey().toString());
            cookie.setValue(entry.getValue().toString());
            cookie.setDomain(host);
            cookie.setPath("/");
            cookie.setSecure(true);
            cookie.setVersion(0);
            cookieManager.add(cookie);

            cookieManager.setName("cookie管理器");
            cookieManager.setEnabled(true);
            cookieManager.setProperty(TestElement.TEST_CLASS, CookieManager.class.getName());
            cookieManager.setProperty(TestElement.GUI_CLASS, CookiePanel.class.getName());
        }
        return cookieManager;
    }

    private HeaderManager createHeaderManager() {

        HeaderManager headerManager = new HeaderManager();
        Map<String, String> headers = requestSampler.getHeaders();

        Iterator it = headers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Header header = new Header();
            header.setName(entry.getKey().toString());
            if ("requestid".equals(entry.getKey().toString())) {
                header.setValue("${requestid}");
            } else {
                header.setValue(entry.getValue().toString());
            }
            Header content = new Header();
            content.setName("Content-Type");
            content.setValue(requestSampler.getContentType());
            headerManager.add(header);
            headerManager.add(content);
        }

        headerManager.setName("header管理器");
        headerManager.setEnabled(true);
        headerManager.setProperty(TestElement.TEST_CLASS, HeaderManager.class.getName());
        headerManager.setProperty(TestElement.GUI_CLASS, HeaderPanel.class.getName());

        return headerManager;

    }

    /**
     * 创建http采样器
     *
     * @return
     */
    private HTTPSamplerProxy createHTTPSamplerProxy() {
        HTTPSamplerProxy httpSamplerProxy = new HTTPSamplerProxy();
        //TODO:需要区分 get  post-form post-json
        if ("Post-Json".equals(requestSampler.getMethod())) {
            //post-json
            httpSamplerProxy.setPostBodyRaw(true);
            //添加body参数
            Map map = requestSampler.getBody();
            if ("不用".equals(requestSampler.getMoreUser())) {
                for (Object key : map.keySet()) {
                    if ("requestid".equals(key) || "requestId".equals(key)) {
                        map.put(key, "${requestid}");
                    }
                }

            } else {
                //替换上行的token/uid
                if (!map.toString().contains("token")) {
                    map.put(tokenStr, "${info}");
                    map.put(uidStr, "${id}");
                } else {
                    JSONObject reqData = JSON.parseObject(requestSampler.getBody().toString());
                    Set<String> sets = reqData.keySet();
                    for (String key : sets) {
                        if (key.equals(uidStr)) {
                            map.put(uidStr, "${id}");
                            continue;
                        }
                        if (key.equals(tokenStr)) {
                            map.put(tokenStr, "${info}");
                            continue;
                        }
                        if (map.get(key).toString().contains(tokenStr)) {

                            JSONObject replaceData = JSON.parseObject(map.get(key).toString());
                            Set<String> repSets = replaceData.keySet();
                            for (String repKey : repSets) {
                                if (repKey.equals(uidStr)) {
                                    replaceData.put(uidStr, "${id}");
                                    continue;
                                }
                                if (repKey.equals(tokenStr)) {
                                    replaceData.put(tokenStr, "${info}");
                                    continue;
                                }
                            }
                            map.put(key, replaceData);
                        }

                    }

                }
            }
            requestSampler.setBody(map);
            log.info("requestBody==" + JSONObject.toJSONString(requestSampler.getBody()));
            HTTPArgument httpArgument = new HTTPArgument();
            httpArgument.setAlwaysEncoded(false);
            httpArgument.setValue(requestSampler.getBody().toString());
            httpArgument.setMetaData("=");
            Arguments arguments = new Arguments();
            arguments.addArgument(httpArgument);
            arguments.setName("Body数据");
            arguments.setEnabled(true);
            arguments.setProperty(TestElement.TEST_CLASS, Arguments.class.getName());
            arguments.setProperty(TestElement.GUI_CLASS, HTTPArgumentsPanel.class.getName());
            httpSamplerProxy.setArguments(arguments);
            httpSamplerProxy.setMethod("POST");
            httpSamplerProxy.setPath(requestSampler.getUrl());
        } else if ("Post-Form".equals(requestSampler.getMethod())) {

            Arguments arguments = new Arguments();
            String[] paramArray = requestSampler.getParams().split("&");
            for (int i = 0; i < paramArray.length; i++) {


                String arg[] = paramArray[i].split("=");
                HTTPArgument httpArgument = new HTTPArgument();
                httpArgument.setAlwaysEncoded(false);

                httpArgument.setMetaData("=");
                httpArgument.setUseEquals(true);
                httpArgument.setName(arg[0]);
                if("requestid".equals(arg[0]) || "requestId".equals(arg[0])){
                    httpArgument.setValue("${requestid}");
                }else {
                    if(arg.length == 2){
                        httpArgument.setValue(arg[1]);
                    }

                }
                arguments.addArgument(httpArgument);
            }
            log.info("requestParams==" + JSONObject.toJSONString(arguments));
            arguments.setName("Paramters参数");
            arguments.setEnabled(true);
            arguments.setProperty(TestElement.TEST_CLASS, Arguments.class.getName());
            arguments.setProperty(TestElement.GUI_CLASS, HTTPArgumentsPanel.class.getName());

            httpSamplerProxy.setArguments(arguments);
            httpSamplerProxy.setPath(requestSampler.getUrl());
            httpSamplerProxy.setMethod("POST");
        } else {

            httpSamplerProxy.setPath(requestSampler.getUrl() + "?" + requestSampler.getParams());
            httpSamplerProxy.setMethod("GET");
        }
        httpSamplerProxy.setResponseTimeout("10000");
        httpSamplerProxy.setConnectTimeout("10000");

        httpSamplerProxy.setFollowRedirects(true);
        httpSamplerProxy.setAutoRedirects(false);
        httpSamplerProxy.setUseKeepAlive(true);
        httpSamplerProxy.setDoMultipart(false);

        httpSamplerProxy.setName(requestSampler.getCaseName());
        httpSamplerProxy.setEnabled(true);
        httpSamplerProxy.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
        httpSamplerProxy.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());
        return httpSamplerProxy;
    }

    private HeaderManager createMoreUserHeaderManager() {

        HeaderManager headerManager = new HeaderManager();
        Map<String, String> headers = requestSampler.getHeaders();

        Iterator it = headers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Header header = new Header();
            header.setName(entry.getKey().toString());
            if ("requestid".equals(entry.getKey().toString())) {
                header.setValue("${requestid}");
                headerManager.add(header);
                continue;
            }
            if ("token".equals(entry.getKey().toString())) {
                header.setValue("${info}");
                headerManager.add(header);
                continue;
            }

            header.setValue(entry.getValue().toString());
            headerManager.add(header);
        }
        Header content = new Header();
        content.setName("Content-Type");
        content.setValue(requestSampler.getContentType());
        headerManager.add(content);

        if ("老师".equals(requestSampler.getMoreUser())) {
            Header cookie = new Header();
            cookie.setName("Cookie");
            cookie.setValue("${info}");
            headerManager.add(cookie);
        }

        headerManager.setName("header管理器");
        headerManager.setEnabled(true);
        headerManager.setProperty(TestElement.TEST_CLASS, HeaderManager.class.getName());
        headerManager.setProperty(TestElement.GUI_CLASS, HeaderPanel.class.getName());

        return headerManager;

    }


}
