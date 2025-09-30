package security12.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("hello")
    @PreAuthorize("hasRole('HELLO')")
    public String hello(){
        return "hello";
    }

}
