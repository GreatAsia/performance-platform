package com.okay.testcenter.impl.slave;


import com.okay.testcenter.domain.slave.StressTestSlaveEntity;
import com.okay.testcenter.mapper.slave.StressTestSlaveMapper;
import com.okay.testcenter.service.slave.StressTestSlaveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("stressTestSlaveService")
public class StressTestSlaveServiceImpl implements StressTestSlaveService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    StressTestSlaveMapper stressTestSlaveDao;

    @Override
    public StressTestSlaveEntity queryObject(Long slaveId) {
        return stressTestSlaveDao.queryObject(slaveId);
    }

    @Override
    public List<StressTestSlaveEntity> queryList(Map<String, Object> map) {
        return stressTestSlaveDao.queryList(map);
    }

    @Override
    public List<StressTestSlaveEntity> queryList() {
        return stressTestSlaveDao.list();
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return stressTestSlaveDao.queryTotal(map);
    }

    @Override
    public void save(StressTestSlaveEntity stressTestSlave) {
        stressTestSlaveDao.save(stressTestSlave);
    }

    @Override
    public void update(StressTestSlaveEntity stressTestSlave) {
        stressTestSlaveDao.update(stressTestSlave);
    }

    @Override
    public void deleteBatch(Long[] slaveIds) {
        stressTestSlaveDao.deleteBatch(slaveIds);
    }

    @Override
    public void updateBatchStatus(Long slaveId, Integer status) {

    }

    @Override
    public void updateBatchStatusForce(List<Long> slaveIds, Integer status) {

    }

    @Override
    public void restartSingle(Long slaveId) {

    }

    @Override
    public void batchReloadStatus() {

    }


}
