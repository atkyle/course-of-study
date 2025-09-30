package security112.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysMenuController {

    /* 只有角色 ROLE_SYS_MENU_LIST 才能访问 */
    @GetMapping("/sys/menu/list")
    @PreAuthorize("hasRole('ROLE_SYS_MENU_LIST')")
    public String sysMenuList(){
        return "hello sys menu list";
    }

}
