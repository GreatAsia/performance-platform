package com.okay.testcenter.impl.user;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.okay.testcenter.service.user.CacheService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author XieYangYang
 * @date 2019/11/22 9:58
 */

@Service("CacheService")
public class CacheServiceImpl implements CacheService {
    private Cache<String, Object> commonCache = null;

    @PostConstruct//代理此bean时会首先执行该初始化方法
    public void init() {
        commonCache = CacheBuilder.newBuilder()
                //设置缓存容器的初始化容量为10（可以存10个键值对）
                .initialCapacity(10)
                //最大缓存容量是100,超过100后会安装LRU策略-最近最少使用，具体百度-移除缓存项
                .maximumSize(100)
                //设置写入缓存后5分钟后过期
                .expireAfterWrite(360, TimeUnit.SECONDS).build();
    }

    @Override
    public void setCommonCache(String key, Object value) {
        commonCache.put(key, value);
    }

    @Override
    public Object getCommonCache(String key) {
        return commonCache.getIfPresent(key);
    }
}
