package security37.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class IndexController {

    @GetMapping("/index")
    public String index() {
        return "Welcome to the index!";
    }

    @GetMapping("/user")
    public Principal principal(Principal user) {
        return user;
    }


}
