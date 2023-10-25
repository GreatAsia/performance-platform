package com.okay.testcenter.impl.tool;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.*;
import com.okay.testcenter.mapper.tool.AddUserHistoryMapper;
import com.okay.testcenter.service.middle.MiddleProjectService;
import com.okay.testcenter.service.middle.MiddleTestHistoryService;
import com.okay.testcenter.service.tool.AddUserDetailService;
import com.okay.testcenter.service.tool.AddUserHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.okay.testcenter.runner.RunnerFactory.prepare;
import static com.okay.testcenter.tools.GetTime.getTime;

/**
 * @author zhou
 * @date 2020/3/2
 */
@Service("AddUserHistoryService")
public class AddUserHistoryServiceImpl implements AddUserHistoryService {

    @Resource
    AddUserHistoryMapper addUserHistoryMapper;
    @Resource
    AddUserDetailService addUserDetailService;
    @Resource
    MiddleTestHistoryService middleTestHistoryService;
    @Resource
    MiddleProjectService middleProjectService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertHistory(AddUserHistory addUserHistory) {
        int id = addUserHistoryMapper.insertHistory(addUserHistory);
        return id;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteHistory(int id) {
        addUserHistoryMapper.deleteHistory(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateHistory(AddUserHistory addUserHistory) {
        addUserHistoryMapper.updateHistory(addUserHistory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateHistoryData(int historyId) {

        AddUserHistory addUserHistory = addUserHistoryMapper.findHistoryById(historyId);
        List<AddUserDetail> addUserDetailList = addUserDetailService.findDetailByHistoryId(historyId);

        int projectId;
        for (AddUserDetail detail : addUserDetailList) {
            //TODO:需要考虑其他项目
            if ("老师".equals(addUserHistory.getUserType())) {
                projectId = 1;
            } else {
                projectId = 2;
            }
            MiddleProject middleProject = middleProjectService.findMiddleProjectById(projectId);
            RequestSampler requestSampler = middleTestHistoryService.findLoginInfoByProjectAndEnv(projectId, addUserHistory.getEnvId());
            if (requestSampler == null) {
                throw new NullPointerException("请检查中间层信息管理模块账号信息是否为空");
            }
            requestSampler.setUname(String.valueOf(detail.getAccount()));
            requestSampler.setPwd(detail.getPwd());
            requestSampler.setProject_id(projectId);
            requestSampler.setLoginParam(middleProject.getLoginParam());
            requestSampler.setSecretUrl(middleProject.getSecretUrl());
            requestSampler.setLoginType(middleProject.getLoginType());
            requestSampler.setRunType(middleProject.getRunType());
            requestSampler.setLoginUrl(middleProject.getLoginUrl());
            requestSampler.setProjectName(middleProject.getName());
            requestSampler.setRequestPre(middleProject.getRequestPre());
            ResponseSampler loginInfo = prepare(requestSampler);
            if (("老师".equals(addUserHistory.getUserType())) && (!loginInfo.getCookies().isEmpty())) {
                String cookie = loginInfo.getCookies().toString();
                detail.setCookies(cookie.replace(",",";").substring(1,cookie.length() -1 ));
                detail.setToken("");
            } else if (("学生".equals(addUserHistory.getUserType())) && (loginInfo.getToken() != null)) {
                detail.setToken(loginInfo.getToken());
                detail.setCookies("");
            }
            detail.setUpdateTime(getTime());
            addUserDetailService.updateDetail(detail);

        }


    }


    @Override
    public AddUserHistory findHistoryById(int id) {
        return addUserHistoryMapper.findHistoryById(id);
    }

    @Override
    public AddUserHistory findHistoryByUserTypeAndEnvId(String userType, int envId) {
        return addUserHistoryMapper.findHistoryByUserTypeAndEnvId(userType, envId);
    }

    @Override
    public PageInfo findHistoryList(int currentPage, int pageSize) {

        PageHelper.startPage(currentPage, pageSize);
        List<AddUserHistory> addUserHistoryList = addUserHistoryMapper.findHistoryList();
        PageInfo pageInfo = new PageInfo(addUserHistoryList);
        return pageInfo;
    }

    @Override
    public List<AddUserHistory> findHistoryList() {
        return addUserHistoryMapper.findHistoryList();
    }
}
