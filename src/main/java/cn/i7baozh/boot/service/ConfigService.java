package cn.i7baozh.boot.service;

/**
 * @Title: 配置中心
 * @Package
 * @Description:
 * @author: baoqi.zhang
 * @date:
 */
public interface ConfigService {

    /**
     * 获取配置项
     * @param key
     * @return
     */
    String getProperty(String key);

    /**
     * 设置配置项
     * @param key
     * @param value
     */
    void setProperty(String key ,String value);

    /**
     * 打印所有配置项
     */
    void printProperties();
}
