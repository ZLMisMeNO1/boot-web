package cn.i7baozh.boot.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: 初始化curator
 * @Package
 * @Description:
 * @author: baoqi.zhang
 * @date:
 */
@Slf4j
@Configuration
public class CuratorConfig {

    @Value("${zk.connection:localhost:2181}")
    private String zkConnectionStr;

    @Value("${zk.root:web}")
    private String zkRootStr;

    @Bean
    public CuratorFramework curatorFramework() {

        log.info("curatorFramework init.. connection:{}, root:{}", zkConnectionStr, zkRootStr);
        RetryPolicy retryPolicy = new RetryOneTime(1000);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .namespace(zkRootStr)
                .retryPolicy(retryPolicy)
                .connectString(zkConnectionStr)
                .build();
        client.start();

        return client;
    }
}
