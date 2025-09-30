package security06.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import security06.handler.AuthLimitHandler;
import security06.handler.LoginSuccessHandler;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

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
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("user").password(new BCryptPasswordEncoder().encode("123456")).roles("USER")
                .and()
                .withUser("admin").password(new BCryptPasswordEncoder().encode("admin")).roles("ADMIN")
                .and()
                .withUser("one").password(new BCryptPasswordEncoder().encode("one")).roles("ONE")
                .and()
                .withUser("two").password(new BCryptPasswordEncoder().encode("two")).roles("TWO")
                .and()
                .withUser("three").password(new BCryptPasswordEncoder().encode("three")).roles("THREE");
    }



}

