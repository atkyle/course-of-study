package security13;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 启动类
 */
@SpringBootApplication
@MapperScan("security13.mapper")
public class Security13Application {
	public static void main(String[] args) {
		SpringApplication.run(Security13Application.class, args);
	}
}


