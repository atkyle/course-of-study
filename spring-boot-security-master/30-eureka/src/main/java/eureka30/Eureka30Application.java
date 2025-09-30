package eureka30;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class Eureka30Application {

    public static void main(String[] args) {
        SpringApplication.run(Eureka30Application.class, args);
    }

}
