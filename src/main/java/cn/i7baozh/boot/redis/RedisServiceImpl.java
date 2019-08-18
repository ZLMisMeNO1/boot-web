package cn.i7baozh.boot.redis;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;
import javax.annotation.PostConstruct;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Title:
 * @Package
 * @Description:
 * @author: baoqi.zhang
 * @date:
 */
@Slf4j
@Service
public class RedisServiceImpl implements RedisService {

    private Jedis jedis;

    /**
     * 设置锁成功返回
     */
    private static final String SET_LOCK_SUCCESS = "OK";

    /**
     * 释放锁成功返回
     */
    private static final Long RELEASE_LOCK_SUCCESS = 1L;

    @PostConstruct
    public void init() {
        //简易装置
        jedis = new Jedis("localhost", 6379);
        log.info("redis init end ..");
    }

    @Override
    public String acquireLockWithTimeout(String lockName, Integer acquireTime, Integer timeout) {

        long timeoutMills = System.currentTimeMillis() + timeout;
        String identifier = UUID.randomUUID().toString();
        SetParams params = SetParams.setParams();
        params.nx();
        params.px(acquireTime);
        do {
            if (SET_LOCK_SUCCESS.equals(jedis.set(lockName, identifier, params))) {
                return identifier;
            }
            try {
                //防止cpu持续自旋
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (System.currentTimeMillis() < timeoutMills);

        return null;
    }

    @Override
    public boolean releaseLock(String lockName, String identifier) {
        String script = "if redis.call('get',KEYS[1]) == ARGV[1] then  return redis.call('del',KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Lists.newArrayList(lockName), Lists.newArrayList(identifier));
        //成功返回1  失败返回0
        return RELEASE_LOCK_SUCCESS.equals(result);
    }

    @Override
    public String get(String key) {
        return jedis.get(key);
    }
}
