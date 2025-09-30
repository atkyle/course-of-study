package security11;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 启动类
 */
@SpringBootApplication
@MapperScan("security11.mapper")
public class Security11Application {

	public static void main(String[] args) {
		SpringApplication.run(Security11Application.class, args);
	}

}


