package cn.i7baozh.boot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @Title:
 * @Package
 * @Description:
 * @author: baoqi.zhang
 * @date:
 */
@Slf4j
@Configuration
public class DruidConfig {

    /**
     * 初始化
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        log.info("druid 开始初始化 -------------------");
        return new DruidDataSource();
    }

    @Bean
    @ConditionalOnMissingBean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setServlet(new StatViewServlet());
        registrationBean.addUrlMappings("/druid/*");

        Map<String, String> initParam = Maps.newHashMap();
        //白名单
//        initParam.put("allow", "127.0.0.1");
        //黑名单
//        initParam.put("deny", "localhost");
        //登录名及密码
        initParam.put("loginUsername", "admin");
        initParam.put("loginPassword", "123456");
        //是否能够重置数据
        initParam.put("resetEnable", "true");
        registrationBean.setInitParameters(initParam);
        return registrationBean;
    }

    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean druidFilterBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new WebStatFilter());
        registrationBean.addUrlPatterns("/**");
        registrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return registrationBean;
    }
}
