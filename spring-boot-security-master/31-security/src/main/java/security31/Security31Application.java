package security31;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 启动类
 */
@SpringBootApplication
@MapperScan("security31.mapper")
public class Security31Application {



	// 1. 可以get访问
	// http://localhost:8031/oauth/authorize?client_id=clientId&response_type=code&redirect_uri=http://localhost:8031/


	// 2. 必须post访问
	// http://localhost:8031/oauth/token?grant_type=authorization_code&code=授权码&redirect_uri=http://localhost:8031/&client_id=clientId&client_secret=secret


	// 3、隐藏模式
	// http://localhost:8031/oauth/authorize?client_id=clientId&response_type=token&redirect_uri=http://localhost:8031/&scope=all


	public static void main(String[] args) {
		SpringApplication.run(Security31Application.class, args);
	}
}


