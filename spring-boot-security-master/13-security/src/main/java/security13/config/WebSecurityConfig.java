package security13.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import security13.handler.*;
import security13.mapper.SysUserMapper;
import security13.model.SysUser;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /* 开启跨域共享，  跨域伪造请求限制=无效 */
        http.cors().and().csrf().disable();

        /* 开启授权认证 */
        http.authorizeRequests()
                /* 动态url权限 */
                .withObjectPostProcessor(new DefinedObjectPostProcessor())
                /* url决策 */
                .accessDecisionManager(accessDecisionManager())
                .anyRequest().authenticated();

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

        /* 退出成功处理器 */
        http.logout().logoutUrl("/logout").permitAll()
                .invalidateHttpSession(true)
                .logoutSuccessHandler(new LogoutHandler());
    }


    @Autowired(required = false)
    private SysUserMapper sysUserMapper;

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


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }










    /**
     * AffirmativeBased – 任何一个AccessDecisionVoter返回同意则允许访问
     * ConsensusBased – 同意投票多于拒绝投票（忽略弃权回答）则允许访问
     * UnanimousBased – 每个投票者选择弃权或同意则允许访问
     *
     * 决策管理
     */
    private AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();
        decisionVoters.add(new WebExpressionVoter());
        decisionVoters.add(new AuthenticatedVoter());
        decisionVoters.add(new RoleVoter());
        /* 路由权限管理 */
        decisionVoters.add(new UrlRoleAuthHandler());
        return new UnanimousBased(decisionVoters);
    }


    @Autowired
    private UrlRolesFilterHandler urlRolesFilterHandler;


    class DefinedObjectPostProcessor implements ObjectPostProcessor<FilterSecurityInterceptor> {
        @Override
        public <O extends FilterSecurityInterceptor> O postProcess(O object) {
            object.setSecurityMetadataSource(urlRolesFilterHandler);
            return object;
        }
    }
}