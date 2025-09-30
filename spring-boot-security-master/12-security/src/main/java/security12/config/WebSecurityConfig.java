package security12.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import security12.handler.AuthLimitHandler;
import security12.handler.LoginExpireHandler;
import security12.handler.LoginFailureHandler;
import security12.handler.LoginSuccessHandler;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /* 开启跨域共享，  跨域伪造请求限制=无效 */
        http.cors().and().csrf().disable();

        /* 开启授权认证 */
        http.authorizeRequests().anyRequest().authenticated();

        /* 登录配置 */
        http.formLogin().usernameParameter("username").passwordParameter("password").loginProcessingUrl("/login");

        /* 登录失败后的处理 */
        http.formLogin().failureHandler(new LoginFailureHandler());

        /* 登录过期/未登录 处理 */
        http.exceptionHandling().authenticationEntryPoint(new LoginExpireHandler());

        /* 权限不足(没有赋予角色) 处理 */
        http.exceptionHandling().accessDeniedHandler(new AuthLimitHandler());

        /* 登录成功后的处理 */
        http.formLogin().successHandler(new LoginSuccessHandler());
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }

}