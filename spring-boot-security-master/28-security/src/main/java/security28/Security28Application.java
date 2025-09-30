package security28;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 启动类
 */
@SpringBootApplication
@MapperScan("security28.mapper")
public class Security28Application {



	// 1. 可以get访问
	// http://localhost:8028/oauth/authorize?client_id=clientId&response_type=code&redirect_uri=http://localhost:8028/


	// 2. 必须post访问
	// http://localhost:8028/oauth/token?grant_type=authorization_code&code=授权码&redirect_uri=http://localhost:8028/&client_id=clientId&client_secret=secret


	// 3、隐藏模式
	// http://localhost:8028/oauth/authorize?client_id=clientId&response_type=token&redirect_uri=http://localhost:8028/&scope=all


	// 4. 密码模式
	// http://localhost:8028/oauth/token?grant_type=password&username=admin&password=admin&client_id=clientId&client_secret=secret

	// 5. 凭证模式
	// http://localhost:8028/oauth/token?grant_type=client_credentials&client_id=clientId&client_secret=secret


	public static void main(String[] args) {
		SpringApplication.run(Security28Application.class, args);
	}
}


