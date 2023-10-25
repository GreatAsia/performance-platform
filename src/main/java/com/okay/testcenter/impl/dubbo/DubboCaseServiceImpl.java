package com.okay.testcenter.impl.dubbo;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.Env;
import com.okay.testcenter.domain.dubbo.DubboCase;
import com.okay.testcenter.domain.dubbo.DubboCaseHistory;
import com.okay.testcenter.domain.dubbo.DubboModule;
import com.okay.testcenter.domain.dubbo.DubboTestHistory;
import com.okay.testcenter.mapper.dubbo.DubboCaseMapper;
import com.okay.testcenter.mapper.dubbo.DubboInterfaceHistoryMapper;
import com.okay.testcenter.mapper.dubbo.DubboModelMapper;
import com.okay.testcenter.mapper.dubbo.DubboTestHistoryMapper;
import com.okay.testcenter.mapper.middle.EnvMapper;
import com.okay.testcenter.service.dubbo.DubboCaseService;
import com.okay.testcenter.service.middle.EnvService;
import com.okay.testcenter.tools.CreateDubboCase;
import com.okay.testcenter.tools.DubboFactory;
import com.okay.testcenter.tools.ZookeeperConnect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.okay.testcenter.tools.ExceptionUtil.getMessage;
import static com.okay.testcenter.tools.GetTime.getTime;


@Service("DubboCaseService")
public class DubboCaseServiceImpl implements DubboCaseService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    DubboCaseMapper dubboCaseMapper;
    @Resource
    EnvMapper envMapper;
    @Resource
    DubboTestHistoryMapper dubboTestHistoryMapper;
    @Resource
    DubboInterfaceHistoryMapper dubboInterfaceHistoryMapper;
    @Resource
    DubboModelMapper dubboModelMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertDubboInterface(DubboCase dubboCase) {

        DubboModule dubboModule = dubboModelMapper.findModelByName(dubboCase.getModelName());
        int moduleId = 0;
        //先判断模块是否存在
        if(dubboModule == null){
            DubboModule insertDubboModule = new DubboModule();
            insertDubboModule.setName(dubboCase.getModelName());
            dubboModelMapper.insertModel(insertDubboModule);
            moduleId = dubboModelMapper.findModelByName(dubboCase.getModelName()).getId();
        }else {
            moduleId = dubboModule.getId();
        }
        dubboCase.setModel(moduleId);
        dubboCaseMapper.insertDubboInterface(dubboCase);


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDubboInterface(DubboCase dubboCase) {
        dubboCaseMapper.updateDubboInterface(dubboCase);

    }

    @Override
    public DubboCase findDubboInterfaceById(int id) {
        return dubboCaseMapper.findDubboInterfaceById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDubboInterfaceById(int id) {
        dubboCaseMapper.deleteDubboInterfaceById(id);
    }

    @Override
    public PageInfo findDubboInterfaceList(int currentPage, int pageSize) {

        PageHelper.startPage(currentPage, pageSize);
        List<DubboCase> dubboCaseList = dubboCaseMapper.findDubboInterfaceList();
        PageInfo pageInfo = new PageInfo(dubboCaseList);
        return pageInfo;
    }

    @Override
    public List<DubboCase> findDubboInterfaceList() {

        List<DubboCase> dubboCase = dubboCaseMapper.findDubboInterfaceList();
        return dubboCase;
    }

    @Override
    public PageInfo findDubboInterfaceListByModel(int model, int currentPage, int pageSize) {

        PageHelper.startPage(currentPage, pageSize);
        List<DubboCase> dubboCaseList = dubboCaseMapper.findDubboInterfaceListByModel(model);
        PageInfo pageInfo = new PageInfo(dubboCaseList);
        return pageInfo;

    }

    @Override
    public List<DubboCase> findDubboInterfaceListByModel(int model) {

        List<DubboCase> dubboCaseList = dubboCaseMapper.findDubboInterfaceListByModel(model);
        return dubboCaseList;
    }

    @Override
    public List<DubboCase> findDubboInterfaceListByModelName(String modelName) {

        List<DubboCase> dubboCaseList = dubboCaseMapper.findDubboInterfaceListByModelName(modelName);
        return dubboCaseList;
    }

    @Override
    public PageInfo findDubboInterfaceListByModelName(String modelName, int currentPage, int pageSize) {

        PageHelper.startPage(currentPage, pageSize);
        List<DubboCase> dubboCaseList = dubboCaseMapper.findDubboInterfaceListByModelName(modelName);
        PageInfo pageInfo = new PageInfo(dubboCaseList);
        return pageInfo;
    }

    @Override
    public DubboCaseHistory runDubboById(JSONObject request) {

        DubboCaseHistory dubboCaseHistory = new DubboCaseHistory();
        DubboTestHistory dubboTestHistory = new DubboTestHistory();
        try {
            String address = request.get("address").toString();
            int id = Integer.parseInt(request.get("id").toString());
            DubboCase dubboCase = dubboCaseMapper.findDubboInterfaceById(id);

            dubboCase = new CreateDubboCase().create(dubboCase);
            //设置开始时间
            String startTime = getTime();
            DubboFactory dubbo = DubboFactory.getInstance();
            Object result = dubbo.genericInvoke(dubboCase);
            dubbo.destory();
            if ("fail".equals(result)) {
                return dubboCaseHistory;
            }

            logger.info("responseResult===" + JSONObject.toJSON(result));
            String historyResult = result.toString().contains(dubboCase.getCheckData()) + "";

            dubboTestHistory.setTotalCase(1);
            if ("true".equals(historyResult)) {

                historyResult = "PASS";
                dubboTestHistory.setPassCase(1);
                dubboTestHistory.setFailCase(0);
            } else {
                historyResult = "FAIL";
                dubboTestHistory.setPassCase(0);
                dubboTestHistory.setFailCase(1);
            }
            Map requestData = new HashMap(1);
            requestData.put("requestParam", JSONObject.toJSON(dubboCase));
            //Dubbo接口历史数据
            dubboCaseHistory.setCaseName(dubboCase.getCaseName());
            dubboCaseHistory.setModelId(dubboCase.getModel());
            dubboCaseHistory.setEnv(address);
            dubboCaseHistory.setStartTime(startTime);
            dubboCaseHistory.setEndTime(getTime());
            dubboCaseHistory.setRequestData(requestData.toString());
            dubboCaseHistory.setResponseContent(result.toString());
            dubboCaseHistory.setResult(historyResult);

            dubboTestHistory.setResult(historyResult);
            dubboTestHistory.setEnv(address);
            dubboTestHistory.setStartTime(dubboCaseHistory.getStartTime());
            dubboTestHistory.setEndTime(getTime());
            dubboTestHistory.setModelId(dubboCase.getModel());

            dubboTestHistoryMapper.insertTestHistory(dubboTestHistory);
            int historyId = dubboTestHistoryMapper.getLastTestHistoryId();
            dubboCaseHistory.setHistoryId(historyId);
            dubboInterfaceHistoryMapper.insertHistory(dubboCaseHistory);
        } catch (Exception e) {
            logger.error(getMessage(e));
        }


        return dubboCaseHistory;

    }

    @Override
    public Object runDubboByModule(JSONObject request) {

        String modelName = request.get("modelName").toString();
        String address = request.get("address").toString();
        DubboFactory dubbo = DubboFactory.getInstance();
        //Dubbo接口历史数据
        List<DubboCaseHistory> dubboInterfaceHistories = new ArrayList<>();
        List<DubboCaseHistory> dubboInterfaceError = new ArrayList<>();
        int totalCase = 0;
        int passCase = 0;
        int failCase = 0;
        String historyResult = "true";
        String startTime = "";
        try {
            List<DubboCase> dubboCaseList = dubboCaseMapper.findDubboInterfaceListByModelName(modelName);
            for (int i = 0; i < dubboCaseList.size(); i++) {
                totalCase = totalCase + 1;
                //设置开始时间
                startTime = getTime();
                DubboCase dubboCase = new CreateDubboCase().create(dubboCaseList.get(i));
                Object result = dubbo.genericInvoke(dubboCase);
                if ("fail".equals(result)) {
                    return dubboInterfaceError;
                }
                logger.info("responseResult===" + result);
                historyResult = result.toString().contains(dubboCase.getCheckData()) + "";
                if ("true".equals(historyResult)) {
                    passCase = passCase + 1;
                    historyResult = "PASS";
                } else {
                    failCase = failCase + 1;
                    historyResult = "FAIL";
                }
                Map requestData = new HashMap(1);
                requestData.put("requestParams", JSONObject.toJSON(dubboCase));
                DubboCaseHistory dubboCaseHistory = new DubboCaseHistory();
                dubboCaseHistory.setCaseName(dubboCase.getCaseName());
                dubboCaseHistory.setModelId(dubboCase.getModel());
                dubboCaseHistory.setEnv(String.valueOf(dubboCase.getEnvId()));
                dubboCaseHistory.setStartTime(startTime);
                dubboCaseHistory.setEndTime(getTime());
                dubboCaseHistory.setRequestData(requestData.toString());
                dubboCaseHistory.setResponseContent(result.toString());
                dubboCaseHistory.setResult(historyResult);
                dubboInterfaceHistories.add(dubboCaseHistory);
            }
            dubbo.destory();
            String testResult = "";
            if (failCase == 0) {
                testResult = "PASS";
            } else {
                testResult = "FAIL";
            }
            DubboTestHistory dubboTestHistory = new DubboTestHistory();
            dubboTestHistory.setEnv(address);
            dubboTestHistory.setResult(testResult);
            dubboTestHistory.setTotalCase(totalCase);
            dubboTestHistory.setPassCase(passCase);
            dubboTestHistory.setFailCase(failCase);
            dubboTestHistory.setStartTime(startTime);
            dubboTestHistory.setEndTime(getTime());
            dubboTestHistory.setModelId(dubboCaseList.get(0).getModel());
            dubboTestHistoryMapper.insertTestHistory(dubboTestHistory);
            int historyId = dubboTestHistoryMapper.getLastTestHistoryId();

            for (DubboCaseHistory history : dubboInterfaceHistories) {
                if ("FAIL".equals(history.getResult())) {
                    dubboInterfaceError.add(history);
                }
                history.setHistoryId(historyId);
                dubboInterfaceHistoryMapper.insertHistory(history);
            }
        } catch (Exception e) {
            logger.error(getMessage(e));
        }
        return dubboInterfaceError;
    }


    @Override
    public PageInfo findDubboInterfaceListByModelAndEnv(int model, int envId, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<DubboCase> dubboCaseList = dubboCaseMapper.findDubboInterfaceListByModelAndEnv(model, envId, currentPage, pageSize);
        PageInfo pageInfo = new PageInfo(dubboCaseList);
        return pageInfo;
    }

    @Override
    public List<DubboCase> findDubbocaseByName(String name) {
        return dubboCaseMapper.findDubbocaseByName(name);
    }


}
