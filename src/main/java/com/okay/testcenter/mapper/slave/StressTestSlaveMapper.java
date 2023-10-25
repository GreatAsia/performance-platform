package com.okay.testcenter.mapper.slave;

import com.okay.testcenter.domain.slave.StressTestSlaveEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author zhou
 * @date 2021/4/23
 */
@Mapper
public interface StressTestSlaveMapper extends BaseMapper<StressTestSlaveEntity> {

    /**
     * 批量更新
     */
    int updateBatch(Map<String, Object> map);
}
