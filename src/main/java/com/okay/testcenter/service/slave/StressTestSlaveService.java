package com.okay.testcenter.service.slave;

import com.okay.testcenter.domain.slave.StressTestSlaveEntity;

import java.util.List;
import java.util.Map;

/**
 * @author zhou
 * @date 2021/4/23
 */

public interface StressTestSlaveService {

    /**
     * 根据ID，查询子节点信息
     */
    StressTestSlaveEntity queryObject(Long slaveId);

    /**
     * 查询子节点列表
     */
    List<StressTestSlaveEntity> queryList(Map<String, Object> map);

    /**
     * 查询子节点列表
     */
    List<StressTestSlaveEntity> queryList();

    /**
     * 查询总数
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 保存
     */
    void save(StressTestSlaveEntity stressTestSlave);

    /**
     * 更新
     */
    void update(StressTestSlaveEntity stressTestSlave);

    /**
     * 批量删除
     */
    void deleteBatch(Long[] slaveIds);

    /**
     * 批量更新状态
     */
    void updateBatchStatus(Long slaveId, Integer status);

    /**
     * 手工强制批量更新状态
     */
    void updateBatchStatusForce(List<Long> slaveIds, Integer status);

    /**
     * 重启节点
     */
    void restartSingle(Long slaveId);

    /**
     * 根据后台节点进程状态，批量刷新节点状态
     */
    void batchReloadStatus();
}
