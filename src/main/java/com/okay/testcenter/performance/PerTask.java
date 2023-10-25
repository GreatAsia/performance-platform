package com.okay.testcenter.performance;

import com.okay.testcenter.domain.middle.RequestSampler;
import com.okay.testcenter.performance.base.CaseInit;
import com.okay.testcenter.performance.base.ThreadLimitTimeCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;


/**
 * 性能测试任务类
 */
public class PerTask extends ThreadLimitTimeCount implements Serializable {

    private static final long serialVersionUID = 9050264328641071198L;


    private static Logger logger = LoggerFactory.getLogger(PerTask.class);

    public RequestSampler sampler;

    public int id;

    private PerTask() {

    }

    @Override
    protected void doing() throws Exception {
        CaseInit.build2(sampler);
//        logger.warn("34");
    }

    @Override
    protected void after() {

    }


    public PerTask(int id, int time) {
        super(time);
        this.id = id;
        this.sampler = new CaseInit(id).requestSampler;
    }

    public static void stop() {
        ThreadLimitTimeCount.stopAllThread();
    }

    @Override
    public PerTask clone() {
        PerTask perTask = new PerTask(id, time / 1000);
        perTask.sampler = this.sampler;
        return perTask;
    }


}
