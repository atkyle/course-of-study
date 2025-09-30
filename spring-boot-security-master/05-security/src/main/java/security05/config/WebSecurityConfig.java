package security05.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import security05.handler.LoginSuccessHandler;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 关闭csrf
        http.csrf().disable();

        // 配置登录页面
        http.formLogin().loginPage("/login").permitAll();

        // 配置登录成功后的操作
        http.formLogin().successHandler(new LoginSuccessHandler());

        // 登出授权
        http.logout().permitAll();

        // 授权配置
        http.authorizeRequests()
                /* 所有静态文件可以访问 */
                .antMatchers("/js/**", "/css/**", "/images/**").permitAll()
                /* 所有 以/ad 开头的 广告页面可以访问 */
                .antMatchers("/ad/**").permitAll()
                .anyRequest().fullyAuthenticated();
    }


    /**
     * 加密密码
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("user").password(new BCryptPasswordEncoder().encode("123456")).roles("USER")
                .and()
                .withUser("admin").password(new BCryptPasswordEncoder().encode("admin")).roles("ADMIN");
    }


//    /**
//     * 明文密码并不是不配置 passwordEncoder  而是需要使用 NoOpPasswordEncoder类配置
//     * 也就是说 NoOpPasswordEncoder.getInstance().encode("123456")  的值就是 123456
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
//                .withUser("user").password("123456").roles("USER")
//                .and()
//                .withUser("admin").password("admin").roles("ADMIN");
//    }


//    /**
//     * 没有配置 passwordEncoder 并不是密码就是明文
//     * 而是 默认需要在密码前面加上{noop}
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password("{noop}123456").roles("USER")
//                .and()
//                .withUser("admin").password("{noop}admin").roles("ADMIN");
//    }


}

