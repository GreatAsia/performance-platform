package com.okay.testcenter.impl.tool;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.domain.middle.AddUserDetail;
import com.okay.testcenter.domain.middle.AddUserHistory;
import com.okay.testcenter.mapper.tool.AddUserDetailMapper;
import com.okay.testcenter.mapper.tool.AddUserHistoryMapper;
import com.okay.testcenter.service.tool.AddUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 * @author zhou
 * @date 2020/3/2
 */
@Service("AddUserDetailService")
public class AddUserDetailServiceImpl implements AddUserDetailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    AddUserDetailMapper addUserDetailMapper;
    @Resource
    AddUserHistoryMapper addUserHistoryMapper;
    @Value("${jmeter.data.path}")
    String path;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertDetail(AddUserDetail addUserDetail) {
        addUserDetailMapper.insertDetail(addUserDetail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDetail(int id) {
        addUserDetailMapper.deleteDetail(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDetail(AddUserDetail addUserDetail) {
        addUserDetailMapper.updateDetail(addUserDetail);
    }

    @Override
    public AddUserDetail findDetailByAccount(int account) {
        return addUserDetailMapper.findDetailByAccount(account);
    }

    @Override
    public List<AddUserDetail> findDetailByHistoryId(int historyId) {
        return addUserDetailMapper.findDetailByHistoryId(historyId);
    }

    @Override
    public PageInfo findDetailList(int currentPage, int pageSize) {

        PageHelper.startPage(currentPage, pageSize);
        List<AddUserDetail> addUserDetailList = addUserDetailMapper.findDetailList();
        PageInfo pageInfo = new PageInfo(addUserDetailList);
        return pageInfo;
    }

    @Override
    public List<AddUserDetail> findDetailList() {
        return addUserDetailMapper.findDetailList();
    }

    @Override
    public void createFile(int envId, String type) {

        try {
            logger.info("type==" + type);
            logger.info("envId==" + envId);
            AddUserHistory addUserHistory = addUserHistoryMapper.findHistoryByUserTypeAndEnvId(type, envId);
            List<AddUserDetail> addUserDetailList = addUserDetailMapper.findDetailByHistoryId(addUserHistory.getId());
            logger.info("filePath==" + path);
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            boolean isCreatefile = file.createNewFile();
            logger.info("createFile==" + isCreatefile);
            String os = System.getProperty("os.name");
            if(!os.toLowerCase().startsWith("win")) {
                Runtime.getRuntime().exec("chmod 777 -R " + path);
            }

            StringBuilder stringBuilder = new StringBuilder();
            //在表中存放查询到的数据放入对应的列
            for (AddUserDetail detail : addUserDetailList) {
                if ((detail.getToken() != null) && (detail.getToken() != "") && ("学生".equals(type))) {
                    stringBuilder.append(detail.getAccount() + "," + detail.getToken() + "\n");
                } else if ((detail.getCookies() != null) && (detail.getCookies() != "") && ("老师".equals(type))) {
                    stringBuilder.append(detail.getAccount() + "," + detail.getCookies() + "\n");

                }
            }
            logger.info("accont info :"+ stringBuilder.toString());
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(stringBuilder.toString());
            fileWriter.flush();
            fileWriter.close();

        } catch (Exception e) {
            logger.error("weite userData file error=={}",e.getMessage());
            e.printStackTrace();
        }

    }
}
