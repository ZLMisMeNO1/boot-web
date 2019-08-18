package cn.i7baozh.boot.lock;

/**
 * @Title:
 * @Package
 * @Description:
 * @author: baoqi.zhang
 * @date:
 */
public interface Lock {

    /**
     * 获取锁
     * @param lockName 锁名称
     * @param acquireTime 锁保留时间 单位毫秒
     * @param timeout 在经过毫秒后放弃获取锁
     * @return
     */
    String acquireLockWithTimeout(String lockName, Integer acquireTime, Integer timeout);


    /**
     * 释放锁
     * @param lockName 锁名称
     * @param identifier 获取到的锁标识
     * @return
     */
    boolean releaseLock(String lockName, String identifier);
}
