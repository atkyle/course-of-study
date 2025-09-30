package security11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    /* 只有角色ONE才能访问 */
    @GetMapping("/one")
    public String one(){
        return "auth/one";
    }

    /* 只有角色TWO才能访问 */
    @GetMapping("/two")
    public String two(){
        return "auth/two";
    }

    /* 只有角色THREE才能访问 */
    @GetMapping("/three")
    public String three(){
        return "auth/three";
    }

    /* 权限不足时默认展示的页面 */
    @GetMapping("/limit")
    public String limit(){
        return "auth/limit";
    }
}


