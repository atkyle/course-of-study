package security26;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 启动类
 */
@SpringBootApplication
@MapperScan("security26.mapper")
public class Security26Application {



	// 1. 可以get访问
	// http://localhost:8026/oauth/authorize?client_id=clientId&response_type=code&redirect_uri=http://localhost:8026/


	// 2. 必须post访问
	// http://localhost:8026/oauth/token?grant_type=authorization_code&code=授权码&redirect_uri=http://localhost:8026/&client_id=clientId&client_secret=secret


	public static void main(String[] args) {
		SpringApplication.run(Security26Application.class, args);
	}
}


