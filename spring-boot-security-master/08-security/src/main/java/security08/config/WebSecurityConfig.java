package security08.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import security08.handler.AuthLimitHandler;
import security08.handler.LoginSuccessHandler;
import security08.mapper.SysUserMapper;
import security08.model.SysUser;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired(required = false)
    private SysUserMapper sysUserMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 关闭csrf
        http.csrf().disable();

        // 配置登录页面
        http.formLogin().loginPage("/login").permitAll();

        // 配置登录成功后的操作
        http.formLogin().successHandler(new LoginSuccessHandler());

        // 用户权限不足处理器
        http.exceptionHandling().accessDeniedHandler(new AuthLimitHandler());

        // 登出授权
        http.logout().permitAll();

        // 授权配置
        http.authorizeRequests()
                /* 所有静态文件可以访问 */
                .antMatchers("/js/**","/css/**","/images/**").permitAll()
                /* 所有 以/ad 开头的 广告页面可以访问 */
                .antMatchers("/ad/**").permitAll()
                .anyRequest().fullyAuthenticated();
    }



    @Override
    protected UserDetailsService userDetailsService() {
        return username -> {
            if (username==null||username.trim().length()<=0) {
                throw new UsernameNotFoundException("用户名为空");
            }

            SysUser sysUser = sysUserMapper.selectByUserName(username);
            if (sysUser != null){
                return sysUser;
            }
            throw new UsernameNotFoundException("用户不存在!");
        };
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }
}

