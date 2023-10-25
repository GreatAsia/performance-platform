package com.okay.testcenter.performance.base;

import com.okay.testcenter.performance.tools.Save;
import com.okay.testcenter.performance.tools.Time;
import com.okay.testcenter.performance.tools.WriteRead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 并发类，用于启动压力脚本
 */
public class Concurrent extends Common {

    private static Logger logger = LoggerFactory.getLogger(Concurrent.class);

    public static String LONG_Path = new File("").getAbsolutePath() + "/";

    /**
     * 开始时间
     */
    private long startTime;

    /**
     * 结束时间
     */
    private long endTime;

    /**
     * 任务描述
     */
    public String desc = "FunTester";

    /**
     * 任务集
     */
    public List<ThreadBase> threads = new ArrayList<>();

    /**
     * 线程数
     */
    public int threadNum;

    /**
     * 执行失败总数
     */
    private int errorTotal;

    /**
     * 任务执行失败总数
     */
    private int failTotal;

    /**
     * 执行总数
     */
    private int excuteTotal;

    /**
     * 用于记录所有请求时间
     */
    public static Vector<Long> allTimes = new Vector<>();

    /**
     * 线程池
     */
    ExecutorService executorService;

    /**
     * 计数器
     */
    CountDownLatch countDownLatch;

    /**
     * @param thread    线程任务
     * @param threadNum 线程数
     */
    public Concurrent(ThreadBase thread, int threadNum) {
        this(threadNum);
        for (int i = 0; i < threadNum; i++) {
            ThreadBase clone = deepClone(thread);
            threads.add(clone);
        }
//        IntStream.range(0, threadNum).forEach(x -> threads.add(thread.clone()));
    }

    /**
     * @param threads 线程组
     */
    public Concurrent(List<ThreadBase> threads) {
        this(threads.size());
        this.threads = threads;
    }

    /**
     * @param thread    线程任务
     * @param threadNum 线程数
     * @param desc      任务描述
     */
    public Concurrent(ThreadBase thread, int threadNum, String desc) {
        this(thread, threadNum);
        this.desc = desc;
    }

    /**
     * @param threads 线程组
     * @param desc    任务描述
     */
    public Concurrent(List<ThreadBase> threads, String desc) {
        this(threads);
        this.desc = desc;
    }

    private Concurrent(int threadNum) {
        this.threadNum = threadNum;
        executorService = Executors.newFixedThreadPool(threadNum);
        countDownLatch = new CountDownLatch(threadNum);
    }

    private Concurrent() {

    }

    /**
     * 执行多线程任务
     */
    public PerformanceResultBean start() {
        startTime = Time.getTimeStamp();
        for (int i = 0; i < threadNum; i++) {
            ThreadBase thread = getThread(i);
            thread.setCountDownLatch(countDownLatch);
            executorService.execute(thread);
        }
        shutdownService(executorService, countDownLatch);
        endTime = Time.getTimeStamp();
        logger.info("总计" + threadNum + "个线程，共用时：" + Time.getTimeDiffer(startTime, endTime) + "秒！");
        for (int i = 0; i < threadNum; i++) {
            ThreadBase thread = threads.get(i);
            logger.warn("错误的线程数: {}", thread.errorNum);
        }
        threads.forEach(x -> {
            if (!x.status()) failTotal++;
            errorTotal += x.errorNum;
            excuteTotal += x.excuteNum;
        });
        return over();
    }

    /**
     * 关闭任务相关资源
     *
     * @param executorService 线程池
     * @param countDownLatch  计数器
     */
    private static void shutdownService(ExecutorService executorService, CountDownLatch countDownLatch) {
        try {
            countDownLatch.await();
            executorService.shutdown();
        } catch (InterruptedException e) {
            logger.warn("线程池关闭失败！", e);
        }
    }

    private PerformanceResultBean over() {
        Save.saveLongList(allTimes, threadNum);
        return countQPS(threadNum, desc, Time.getTimeByTimestamp(startTime), Time.getTimeByTimestamp(endTime));
    }

    ThreadBase getThread(int i) {
        return threads.get(i);
    }

    /**
     * 计算结果
     * <p>此结果仅供参考</p>
     *
     * @param name 线程数
     */
    public PerformanceResultBean countQPS(int name, String desc, String start, String end) {
        List<String> strings = WriteRead.readTxtFileByLine(LONG_Path + name);
        int size = strings.size();
        int sum = 0;
        for (int i = 0; i < size; i++) {
            int time = Integer.valueOf(strings.get(i));
            sum += time;
        }
        double qps = 1000.0 * size * name / sum;
        PerformanceResultBean resultBean = new PerformanceResultBean(desc, start, end, name, size, sum / size, qps, getPercent(excuteTotal, errorTotal), getPercent(threadNum, failTotal), excuteTotal);
        logger.info(resultBean.toString());
        return resultBean;
    }

    /**
     * 用于做后期的计算
     *
     * @param name
     * @param desc
     * @return
     */
    public PerformanceResultBean countQPS(int name, String desc) {
        return countQPS(name, desc, Time.getDate(), Time.getDate());
    }

    /**
     * 后期计算用
     *
     * @param name
     * @return
     */
    public PerformanceResultBean countQPS(int name) {
        return countQPS(name, "FunTester", Time.getDate(), Time.getDate());
    }

    /**
     * 获取一个百分比，两位小数
     *
     * @param total 总数
     * @param piece 成功数
     * @return 百分比
     */
    public static double getPercent(int total, int piece) {
        if (total == 0) return 0.00;
        int s = (int) (piece * (1.0) / total * 10000);
        double result = s * 1.0 / 100;
        return result;
    }

    /**
     * 通过将对象序列化成数据流实现深层拷贝的方法
     * <p>
     * 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
     * </p>
     *
     * @param t   需要被拷贝的对象,必需实现 Serializable接口,不然会报错
     * @param <T> 需要拷贝对象的类型
     * @return
     */
    public static <T> T deepClone(T t) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(t);
            // 将流序列化成对象
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (T) ois.readObject();
        } catch (IOException e) {
            logger.error("线程任务拷贝失败!", e);
        } catch (ClassNotFoundException e) {
            logger.error("未找到对应类!", e);
        }
        return null;
    }


}