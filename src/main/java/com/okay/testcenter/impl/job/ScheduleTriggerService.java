package com.okay.testcenter.impl.job;

import com.okay.testcenter.domain.Job;
import com.okay.testcenter.service.job.JobPerformanceService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScheduleTriggerService {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleTriggerService.class);
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private JobPerformanceService jobPerformanceService;

    //每天晚上23点用这个方法来更新quartz中的任务
//    @Scheduled(cron = "0 0 23 * * ?")
    public void refreshTrigger() {
        logger.info("触发更新定时任务");
        try {
            //查询出数据库中所有的定时任务
            List<Job> jobList = jobPerformanceService.findJobList();
            if (jobList != null) {
                for (Job scheduleJob : jobList) {

                    String scheduleClassName = "";
                    Map<String, Object> params = new HashMap<>();
                    if (scheduleJob.getJobName().contains("_")) {
                        String[] scheduleClassNameAndParams = scheduleJob.getJobName().split("_");
                        scheduleClassName = scheduleClassNameAndParams[0];
                        params.put("json", scheduleClassNameAndParams[1]);
                    } else {
                        scheduleClassName = scheduleJob.getJobName();
                    }

                    //该任务触发器目前的状态
                    String status = scheduleJob.getStatus();
                    TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
                    CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                    //说明本条任务还没有添加到quartz中
                    if (null == trigger) {
                        //如果是禁用，则不用创建触发器
                        if ("0".equals(status)) {
                            continue;
                        }

                        JobDetail jobDetail = null;
                        try {
                            //创建JobDetail（数据库中job_name存的任务全路径，这里就可以动态的把任务注入到JobDetail中）
                            jobDetail = JobBuilder.newJob((Class<? extends org.quartz.Job>) Class.forName(scheduleClassName)).withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup()).build();
                            jobDetail.getJobDataMap().putAll(params);

                            //表达式调度构建器
                            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCron());
                            ///设置定时任务的时间触发规则
                            trigger = TriggerBuilder.newTrigger().withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup()).withSchedule(scheduleBuilder).build();
                            //把trigger和jobDetail注入到调度器
                            scheduler.scheduleJob(jobDetail, trigger);
                        } catch (ClassNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    } else {  //说明查出来的这条任务，已经设置到quartz中了
                        // Trigger已存在，先判断是否需要删除，如果不需要，再判定是否时间有变化
                        //如果是禁用，从quartz中删除这条任务
                        if ("0".equals(status)) {
                            JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
                            scheduler.deleteJob(jobKey);
                            continue;
                        }
                        //获取数据库的
                        String searchCron = scheduleJob.getCron();
                        String currentCron = trigger.getCronExpression();
                        //说明该任务有变化，需要更新quartz中的对应的记录
                        if (!searchCron.equals(currentCron)) {
                            //表达式调度构建器
                            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(searchCron);
                            //按新的cronExpression表达式重新构建trigger
                            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

                            //按新的trigger重新设置job执行
                            scheduler.rescheduleJob(triggerKey, trigger);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("定时任务每日刷新触发器任务异常，异常信息：", e);
        }
    }


}
