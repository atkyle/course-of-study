package security10;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 启动类
 */
@SpringBootApplication
@MapperScan("security10.mapper")
public class Security10Application {

	public static void main(String[] args) {
		SpringApplication.run(Security10Application.class, args);
	}

}


