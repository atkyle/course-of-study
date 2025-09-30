package security14;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 启动类
 */
@SpringBootApplication
@MapperScan("security14.mapper")
public class Security14Application {
	public static void main(String[] args) {
		SpringApplication.run(Security14Application.class, args);
	}
}


