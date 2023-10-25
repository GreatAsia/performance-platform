package com.okay.testcenter.impl.dubbo;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.dubbo.DubboCase;
import com.okay.testcenter.domain.dubbo.DubboPerformance;
import com.okay.testcenter.domain.dubbo.DubboPerformanceSet;
import com.okay.testcenter.domain.middle.RequestSampler;
import com.okay.testcenter.jmeter.RunDubboJmeter;
import com.okay.testcenter.jmeter.RunJmeter;
import com.okay.testcenter.mapper.dubbo.DubboCaseMapper;
import com.okay.testcenter.mapper.dubbo.DubboPerformanceMapper;
import com.okay.testcenter.mapper.dubbo.DubboPerformanceResultMapper;
import com.okay.testcenter.mapper.dubbo.DubboPerformanceSetMapper;
import com.okay.testcenter.service.dubbo.DubboPerformanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.okay.testcenter.tools.ExceptionUtil.getMessage;

/**
 * @author zhou
 * @date 2020/8/20
 */
@Service("DubboPerformanceService")
public class DubboPerformanceServiceImpl implements DubboPerformanceService {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    DubboPerformanceMapper dubboPerformanceMapper;
    @Resource
    DubboPerformanceResultMapper dubboPerformanceResultMapper;
    @Resource
    DubboPerformanceSetMapper dubboPerformanceSetMapper;
    @Resource
    DubboCaseMapper dubboCaseMapper;


    @Value("${jmeter.path}")
    private String jmeterPath;

    @Override
    public void runSingle(int id, int threads, int time) {

    }

    @Override
    public void runJmeter(int caseId, int threads, int runTime, int runId) {

        try {
            DubboCase dubboCase = dubboCaseMapper.findDubboInterfaceById(caseId);
            RequestSampler requestInfo = new RequestSampler();
            requestInfo.setDubboCase(dubboCase);
            requestInfo.setCaseId(caseId);
            requestInfo.setThreads(threads);
            requestInfo.setRunTime(runTime);
            requestInfo.setRun_id(runId);
            requestInfo.setJmeterPath(jmeterPath);
            RunDubboJmeter.setFlag(true);
            RunDubboJmeter.getInstance(requestInfo).start();
        } catch (Exception e) {
            logger.error("压测出现异常==" + getMessage(e));
            e.printStackTrace();
        }


    }

    @Override
    public CompletableFuture<Integer> runJmeterSet(int setId) {

        Integer runId = dubboPerformanceResultMapper.getLastRunId();
        if (runId == null) {
            runId = 1;
        }else {
            runId ++;
        }

        DubboPerformanceSet dubboSet = dubboPerformanceSetMapper.findById(setId);
        String planSet = dubboSet.getPlan_set();
        String[] sets = new String[]{};
        try {
            if (planSet.contains(",")) {
                sets = planSet.split(",");
            } else {
                sets = new String[]{planSet};
            }
            RunJmeter.setFlag(true);

            for (int i = 0; i < sets.length; i++) {

                if (false == RunJmeter.getFlag()) {
                    break;
                }
                int runCount = i + 1;
                String type = "";
                logger.info("运行第" + runCount + "个压测");

                DubboPerformance dubboPerformance = dubboPerformanceMapper.findById(Integer.parseInt(sets[i]));
                DubboCase dubboCase = dubboCaseMapper.findDubboInterfaceById(dubboPerformance.getCase_id());
                RequestSampler requestInfo = new RequestSampler();
                requestInfo.setDubboCase(dubboCase);
                requestInfo.setCaseId(dubboPerformance.getCase_id());
                requestInfo.setThreads(dubboPerformance.getThreads());
                requestInfo.setRunTime(dubboPerformance.getTimes());
                requestInfo.setRun_id(runId.intValue());
                requestInfo.setSet_id(setId);
                requestInfo.setJmeterPath(jmeterPath);
                RunDubboJmeter.setFlag(true);
                RunDubboJmeter.getInstance(requestInfo).start();
                logger.info("第" + runCount + "次压测完成");

                if (i < sets.length - 1) {
                    logger.info("等待60秒");
                    Thread.sleep(60000);
                }
            }

        } catch (Exception e) {
            logger.error("压测集合有报错==" + getMessage(e));

        }

        return CompletableFuture.completedFuture(runId);


    }

    @Override
    public void stopJmeter() {
        RunJmeter.setFlag(false);
        RunJmeter.stop();
    }

    @Override
    public void insert(DubboPerformance dubboPerformance) {
        dubboPerformanceMapper.insert(dubboPerformance);
    }

    @Override
    public void update(DubboPerformance dubboPerformance) {
        dubboPerformanceMapper.update(dubboPerformance);
    }

    @Override
    public void delete(int id) {
        dubboPerformanceMapper.delete(id);
    }

    @Override
    public List<DubboPerformance> findByCaseId(int caseId) {
        return dubboPerformanceMapper.findByCaseId(caseId);
    }

    @Override
    public DubboPerformance findById(int id) {
        return dubboPerformanceMapper.findById(id);
    }

    @Override
    public List<DubboPerformance> findList() {
        return dubboPerformanceMapper.findList();
    }

    @Override
    public PageInfo findList(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<DubboPerformance> list = dubboPerformanceMapper.findList();
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
}
