package security21;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 启动类
 */
@SpringBootApplication
@MapperScan("security21.mapper")
public class Security21Application {
	public static void main(String[] args) {
		SpringApplication.run(Security21Application.class, args);
	}
}


