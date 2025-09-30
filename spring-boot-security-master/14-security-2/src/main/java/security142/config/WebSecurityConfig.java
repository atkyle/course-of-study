package security142.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import security142.handler.JwtHandler;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtHandler jwtHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* 开启跨域共享,跨域伪造请求限制=无效 */
        http.cors().and().csrf().disable();
        /* rest 无状态 无session */
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //表单登录无效
        http.formLogin().disable();

        /* 开启授权认证 */
        http.authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .anyRequest().authenticated();

        /* 配置token验证过滤器 */
        http.addFilterBefore(jwtHandler, UsernamePasswordAuthenticationFilter.class);
    }
}