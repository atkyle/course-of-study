package security37;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

/**
 * 启动类
 */
@EnableOAuth2Sso
@SpringBootApplication
public class Security37Application {
	public static void main(String[] args) {
		SpringApplication.run(Security37Application.class, args);
	}
}



