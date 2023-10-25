package com.okay.testcenter.service.user;

/**
 * @author XieYangYang
 * @date 2019/11/22 9:57
 */
public interface CacheService {

    public void setCommonCache(String key, Object value);

    public Object getCommonCache(String key);
}
