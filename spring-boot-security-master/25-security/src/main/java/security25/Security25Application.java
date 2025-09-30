package security25;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 启动类
 */
@SpringBootApplication
@MapperScan("security25.mapper")
public class Security25Application {



	// 1. 可以get访问
	// http://localhost:8025/oauth/authorize?client_id=clientId&response_type=code&redirect_uri=http://localhost:8025/


	// 2. 必须post访问
	// http://localhost:8025/oauth/token?grant_type=authorization_code&code=授权码&redirect_uri=http://localhost:8025/&client_id=clientId&client_secret=secret

	// 3. 刷新token
	// http://localhost:8025/oauth/token?grant_type=refresh_token&client_id=clientId&client_secret=secret&refresh_token=刷新令牌


	public static void main(String[] args) {
		SpringApplication.run(Security25Application.class, args);
	}
}


