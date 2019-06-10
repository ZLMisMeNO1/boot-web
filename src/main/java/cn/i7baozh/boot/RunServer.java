package cn.i7baozh.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@MapperScan(basePackages = "cn.i7baozh.boot.mapper")
@SpringBootApplication(scanBasePackages = "cn.i7baozh")
public class RunServer {

	public static void main(String[] args) {
		SpringApplication.run(RunServer.class, args);
	}

}
