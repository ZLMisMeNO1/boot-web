package cn.i7baozh.boot;

import cn.i7baozh.boot.lock.Lock;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

/**
 * @Title:
 * @Package
 * @Description:
 * @author: baoqi.zhang
 * @date:
 */
public class JedisTest {

    private Jedis jedis;

    /**
     * 设置锁成功返回
     */
    private static final String SET_LOCK_SUCCESS = "OK";

    /**
     * 释放锁成功返回
     */
    private static final Long RELEASE_LOCK_SUCCESS = 1L;

    @Before
    public void init() {
        jedis = new Jedis("localhost", 6379);
    }

    @Test
    public void testGet() {
        String name = jedis.get("name");
        System.out.println(name);
    }
    @Test
    public void testSet() {
        long timeoutMills = System.currentTimeMillis() + 10000;
        String identifier = UUID.randomUUID().toString();
        SetParams setParams = SetParams.setParams();
        setParams.nx();
        setParams.px(100000);
        String abc = jedis.set("abc1", identifier, setParams);
        System.out.println("OK".equals(abc));
        System.out.println(identifier);
    }
    @Test
    public void testRelease() {
        Long su = 1L;
        String key = "abc1";
        String identifier = "fe866d7e-d05f-43bc-a6af-4cd7296cd2ac";
        String script = "if redis.call('get',KEYS[1]) == ARGV[1] then  return redis.call('del',KEYS[1]) else return 0 end";
        Object eval = jedis.eval(script, Lists.newArrayList(key), Lists.newArrayList(identifier));
        System.out.println(eval == null);
        System.out.println(eval);
        System.out.println(su.equals(eval));
//        System.out.println(Boolean.TRUE.toString().equalsIgnoreCase(eval.toString()));
    }

    @Test
    public void testLock() throws Exception{
        final String lockName = "testLock1";
        final Integer acquireTime = 1000;
        final Integer timeout = 1000;

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> {
                jedis = new Jedis("localhost", 6379);
                String s = acquireLockWithTimeout(lockName, acquireTime, timeout,jedis);
                System.out.println(Thread.currentThread().getName() + "获取锁" + s);
                try {
                    MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (s != null) {
                    releaseLock(lockName, s, jedis);
                }

            });
        }
        executorService.awaitTermination(10, SECONDS);
    }

    public String acquireLockWithTimeout(String lockName, Integer acquireTime, Integer timeout, Jedis jedis) {

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
                MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (System.currentTimeMillis() < timeoutMills);

        return null;
    }

    public boolean releaseLock(String lockName, String identifier, Jedis jedis) {
        String script = "if redis.call('get',KEYS[1]) == ARGV[1] then  return redis.call('del',KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Lists.newArrayList(lockName), Lists.newArrayList(identifier));
        //成功返回1  失败返回0
        return RELEASE_LOCK_SUCCESS.equals(result);
    }


}
