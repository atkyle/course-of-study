package security35.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HelloController {

    @GetMapping("/index")
    public String index() {
        return "Welcome to the index! 8035";
    }

    @GetMapping("/user")
    public Principal principal(Principal user) {
        return user;
    }
}

