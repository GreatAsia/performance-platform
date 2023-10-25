package com.okay.testcenter.performance.base;

import java.io.Serializable;

/**
 * 性能测试结果集
 */
public class PerformanceResultBean implements Serializable,Cloneable {

    private static final long serialVersionUID = 3790256074261345359L;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRt() {
        return rt;
    }

    public void setRt(int rt) {
        this.rt = rt;
    }

    public double getQps() {
        return qps;
    }

    public void setQps(double qps) {
        this.qps = qps;
    }

    public double getErrorRate() {
        return errorRate;
    }

    public void setErrorRate(double errorRate) {
        this.errorRate = errorRate;
    }

    public double getFailRate() {
        return failRate;
    }

    public void setFailRate(double failRate) {
        this.failRate = failRate;
    }

    public int getExcuteTotal() {
        return excuteTotal;
    }

    public void setExcuteTotal(int excuteTotal) {
        this.excuteTotal = excuteTotal;
    }

    String desc;

    String startTime;

    String endTime;

    int threads;

    int total;

    int rt;

    double qps;

    double errorRate;

    double failRate;

    int excuteTotal;

    PerformanceResultBean(String desc, String startTime, String endTime, int threads, int total, int rt, double qps, double errorRate, double failRate, int excuteTotal) {
        this.desc = desc;
        this.startTime = startTime;
        this.endTime = endTime;
        this.threads = threads;
        this.total = total;
        this.rt = rt;
        this.qps = qps;
        this.errorRate = errorRate;
        this.failRate = failRate;
        this.excuteTotal = excuteTotal;
    }

    @Override
    public String toString() {
        return "PerformanceResultBean{" +
                "desc='" + desc + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", threads=" + threads +
                ", total=" + total +
                ", rt=" + rt +
                ", qps=" + qps +
                ", errorRate=" + errorRate +
                ", failRate=" + failRate +
                ", excuteTotal=" + excuteTotal +
                '}';
    }


}
