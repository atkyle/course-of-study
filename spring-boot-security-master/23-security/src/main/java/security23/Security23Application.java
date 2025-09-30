package security23;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 启动类
 */
@SpringBootApplication
@MapperScan("security23.mapper")
public class Security23Application {
	public static void main(String[] args) {
		SpringApplication.run(Security23Application.class, args);
	}
}


