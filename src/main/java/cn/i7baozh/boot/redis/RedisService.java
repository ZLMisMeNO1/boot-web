package cn.i7baozh.boot.redis;

import cn.i7baozh.boot.lock.Lock;

/**
 * @Title: redis服务
 * @Package
 * @Description:
 * @author: baoqi.zhang
 * @date:
 */
public interface RedisService extends Lock {

    /**
     * 获取
     * @param key
     * @return
     */
    String get(String key);
}
