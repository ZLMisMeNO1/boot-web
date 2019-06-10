package cn.i7baozh.boot;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class BootWebApplicationTests {

	@Autowired
	private DataSource dataSource;

	@Test
	public void contextLoads() {
		System.out.println(dataSource);
	}

}
