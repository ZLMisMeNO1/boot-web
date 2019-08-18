package cn.i7baozh.boot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;

/**
 * @Title: 初始化redis
 * @Package
 * @Description:
 * @author: baoqi.zhang
 * @date:
 */
@Slf4j
@Configuration
public class RedisConfig {

    @Value("${redis.connection:localhost:6379}")
    private String redisConnectionString;

    @Bean
    public Jedis jedis() {

        log.info("init redis ... connection:{}" ,redisConnectionString);
        HostAndPort hostAndPort = HostAndPort.parseString(redisConnectionString);

        return new Jedis(hostAndPort.getHost(), hostAndPort.getPort());
    }
}
