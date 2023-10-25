package com.okay.testcenter.domain.middle;


import java.util.ArrayList;

/**
 * @author zhou
 * @date 2019/12/13
 */
public class MiddlePerformanceReport {

    private ArrayList errorRate;
    private ArrayList responseTime;
    private ArrayList throughPut;
    private ArrayList runTime;
    private String caseName;


    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }


    public ArrayList getErrorRate() {
        return errorRate;
    }

    public void setErrorRate(ArrayList errorRate) {
        this.errorRate = errorRate;
    }

    public ArrayList getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(ArrayList responseTime) {
        this.responseTime = responseTime;
    }

    public ArrayList getThroughPut() {
        return throughPut;
    }

    public void setThroughPut(ArrayList throughPut) {
        this.throughPut = throughPut;
    }

    public ArrayList getRunTime() {
        return runTime;
    }

    public void setRunTime(ArrayList runTime) {
        this.runTime = runTime;
    }


}
