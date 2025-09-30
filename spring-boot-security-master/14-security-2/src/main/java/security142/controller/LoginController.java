package security142.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import security142.common.Results;
import security142.service.SysUserService;
import security142.utils.StringUtils;

/**
 * @author guoyibin
 */
@RestController
public class LoginController {

    @Autowired
    private SysUserService sysUserService;


    @PostMapping("/login")
    public Results login(@RequestParam(required = false) String username, @RequestParam(required = false) String password) {
        if (StringUtils.isNull(username)) {
            return Results.paramError("用户名不能为空");
        }

        if (StringUtils.isNull(password)) {
            return Results.paramError("密码不能为空");
        }

        return sysUserService.login(username, password);
    }

}