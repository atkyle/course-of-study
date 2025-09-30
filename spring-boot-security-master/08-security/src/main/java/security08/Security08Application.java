package security08;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 启动类
 */
@SpringBootApplication
@MapperScan("security08.mapper")
public class Security08Application {

	public static void main(String[] args) {
		SpringApplication.run(Security08Application.class, args);
	}

}


