package security112.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    /* 只有角色ONE才能访问 */
    @GetMapping("/one")
    @PreAuthorize("hasRole('ONE')")
    public String one(){
        return "auth/one";
    }

    /* 只有角色TWO才能访问 */
    @GetMapping("/two")
    @PreAuthorize("hasRole('TWO')")
    public String two(){
        return "auth/two";
    }

    /* 只有角色THREE才能访问 */
    @GetMapping("/three")
    @PreAuthorize("hasRole('THREE')")
    public String three(){
        return "auth/three";
    }

    /* 只有角色ADMIN才能访问 */
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin(){
        return "auth/admin";
    }

    /* 权限不足时默认展示的页面 */
    @GetMapping("/limit")
    public String limit(){
        return "auth/limit";
    }
}


