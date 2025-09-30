package security11.config;

import org.springframework.beans.factory.annotation.Autowired;
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
import security11.handler.AuthLimitHandler;
import security11.handler.LoginSuccessHandler;
import security11.handler.UrlRoleAuthHandler;
import security11.handler.UrlRolesFilterHandler;
import security11.mapper.SysUserMapper;
import security11.model.SysUser;

import java.util.ArrayList;
import java.util.List;

@Configuration
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
                /* 动态url权限 */
                .withObjectPostProcessor(new DefinedObjectPostProcessor())
                /* url决策 */
                .accessDecisionManager(accessDecisionManager())
                .anyRequest().authenticated();
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

