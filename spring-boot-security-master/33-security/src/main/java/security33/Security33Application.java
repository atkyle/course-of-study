package security33;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * 启动类
 */
@EnableFeignClients
@SpringBootApplication
public class Security33Application {
	public static void main(String[] args) {
		SpringApplication.run(Security33Application.class, args);
	}
}


