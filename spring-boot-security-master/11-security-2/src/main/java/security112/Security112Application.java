package security112;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 启动类
 */
@SpringBootApplication
@MapperScan("security112.mapper")
public class Security112Application {

	public static void main(String[] args) {
		SpringApplication.run(Security112Application.class, args);
	}

}