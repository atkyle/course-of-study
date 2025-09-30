package security33.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("32-security")
public interface HelloService {

    @GetMapping("/user/get")
    String get();

}
