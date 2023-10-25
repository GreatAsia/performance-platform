package com.okay.testcenter.jmeter;


import com.alibaba.fastjson.JSONObject;
import com.okay.testcenter.domain.dubbo.DubboCase;
import com.okay.testcenter.domain.dubbo.DubboPerformanceResult;
import com.okay.testcenter.domain.middle.RequestSampler;
import com.okay.testcenter.service.dubbo.DubboPerformanceResultService;
import com.okay.testcenter.tools.CreateDubboCase;
import com.okay.testcenter.tools.SpringUtils;
import org.apache.jmeter.JMeter;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.RandomVariableConfig;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.java.control.gui.JavaTestSamplerGui;
import org.apache.jmeter.protocol.java.sampler.JavaSampler;
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

import static com.okay.testcenter.tools.GetTime.getTime;

/**
 * @author zhou
 * @date 2019/12/30
 */


public class RunDubboJmeter {

    private static final Logger log = LoggerFactory.getLogger(RunDubboJmeter.class);
    private static RequestSampler requestSampler;
    private static StandardJMeterEngine standardJMeterEngine;
    private static Boolean flag = true;
    /**
     * 获取ApplicationContext的实例
     */
    private ApplicationContext applicationContext = SpringUtils.getApplicationContext();
    DubboPerformanceResultService dubboPerformanceResultService = applicationContext.getBean(DubboPerformanceResultService.class);
    private String startTime;
    private String summerInfo;

    private RunDubboJmeter() {
    }

    public static Boolean getFlag() {
        return flag;
    }

    public static void setFlag(Boolean flag) {
        RunDubboJmeter.flag = flag;
    }

    public static RunDubboJmeter getInstance(RequestSampler request) {
        requestSampler = request;
        return RunJmeterFactory.instance;
    }

    public static void stop() {
        if (standardJMeterEngine != null) {
            standardJMeterEngine.stopTest();
            log.info("Jmeter is Stop Now!!!");
        }
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

    /**
     * 创建dubbo请求
     *
     * @return
     */
    public JavaSampler createJavaSampler() {
        // Loop Controller
        DubboCase dubboCase = new CreateDubboCase().create(requestSampler.getDubboCase());
        JavaSampler javaSampler = new JavaSampler();
        Arguments arguments = new Arguments();
        arguments.addArgument("requestid", "${requestid}");
        arguments.addArgument("dubboCase", JSONObject.toJSONString(dubboCase));

        javaSampler.setArguments(arguments);
        javaSampler.setClassname("com.okay.testcenter.jmeter.DubboSample");
        javaSampler.setName("Dubbo");
        javaSampler.setEnabled(true);
        javaSampler.setProperty(TestElement.TEST_CLASS, JavaSampler.class.getName());
        javaSampler.setProperty(TestElement.GUI_CLASS, JavaTestSamplerGui.class.getName());

        return javaSampler;
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
        threadGroup.setProperty("ThreadGroup.on_sample_error", "continue");
        threadGroup.setName("线程组");
        threadGroup.setEnabled(true);
        threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
        threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());
        return threadGroup;
    }

    public String start() {

        //Jemter 引擎
        standardJMeterEngine = new StandardJMeterEngine();
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
        //创建随机生成数
        RandomVariableConfig randomVariableConfig = createRandomVariableConfig();
        // 创建dubbo请求收集器
        JavaSampler javaSampler = createJavaSampler();
        HashTree testPlanTree = new HashTree();
        // 将测试计划添加到测试配置树种
        HashTree threadGroupHashTree = testPlanTree.add(testPlan, threadGroup);
        // 将http请求采样器添加到线程组下
        threadGroupHashTree.add(javaSampler);
        threadGroupHashTree.add(randomVariableConfig);

        String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
        if (summariserName.length() > 0) {
            summer = new SummariserUtils(summariserName);
        }
        ResultCollectorUtil logger = new ResultCollectorUtil(summer);
        testPlanTree.add(testPlanTree.getArray(), logger);
        // 配置jmeter
        standardJMeterEngine.configure(testPlanTree);
        startTime = getTime();
        try {
            // 运行
            standardJMeterEngine.run();
        } catch (Exception e) {
            standardJMeterEngine.stopTest();
            log.error("压测遇到问题==" + e.getLocalizedMessage());
            return "fail";
        }
        if (false == flag) {
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

        DubboPerformanceResult result = new DubboPerformanceResult();
        result.setStart_time(startTime);
        result.setEnd_time(getTime());
        result.setCase_id(requestSampler.getCaseId());
        result.setError_rate(errorRate);
        result.setResponse_time(responseTime);
        result.setThroughput(throughtPut);
        result.setRun_id(requestSampler.getRun_id());
        result.setSet_id(requestSampler.getSet_id());
        result.setRun_time(requestSampler.getRunTime());
        result.setThreads(requestSampler.getThreads());
        result.setError_data(errorData);
        try {
            dubboPerformanceResultService.insert(result);
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

    static class RunJmeterFactory {
        private final static RunDubboJmeter instance = new RunDubboJmeter();
    }

}
