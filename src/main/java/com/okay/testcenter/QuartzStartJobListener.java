package com.okay.testcenter;

import com.okay.testcenter.impl.job.ScheduleTriggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 启动后触发定时任务
 * @author zhou
 * @date 2019/11/22
 */
@Component
public class QuartzStartJobListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(QuartzStartJobListener.class);

    @Autowired
    ScheduleTriggerService triggerService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contentRefreshedEnvnet) {

//        try {
//            triggerService.refreshTrigger();
//        }catch (Exception e){
//            logger.error("触发定时任务失败:" + e.getLocalizedMessage());
//            logger.error(ExceptionUtils.getStackTrace(e));
//            e.printStackTrace();
//        }
    }
}
