package security31.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import security31.service.MyBatisClientDetailsService;
import security31.service.MyJdbcAuthorizationCodeServices;
import security31.service.UserLoginService;

import javax.sql.DataSource;
import java.security.KeyPair;

/**
 * 授权服务配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyBatisClientDetailsService myBatisClientDetailsService;

    /**
     * 配置客户端管理
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(myBatisClientDetailsService);
    }

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcTokenStore jdbcTokenStore(){
        return new JdbcTokenStore(dataSource);
    }

    @Autowired
    private UserLoginService userLoginService;


    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        return new MyJdbcAuthorizationCodeServices(dataSource);
    }


    /** 配置token管理 */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints.tokenStore(jdbcTokenStore())
                 //配置令牌生成
                 .accessTokenConverter(accessTokenConverter())
                 //通过注入密码授权被打开AuthenticationManager
                 .authenticationManager(authenticationManager)
                 //该字段设置设置refresh token是否重复使用,true:reuse;false:no reuse.
                 .reuseRefreshTokens(false)
                 //刷新令牌授权将包含对用户详细信息的检查，以确保该帐户仍然活动,因此需要配置userDetailsService
                 .userDetailsService(userLoginService)
                 // 配置jdbc管理授权码
                 .authorizationCodeServices(authorizationCodeServices());
    }

    /** 配置jwt转换器 */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("oauth2.jks"), "oauth2".toCharArray()).getKeyPair("oauth2");
        converter.setKeyPair(keyPair);
        return converter;
    }



    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()") //允许所有人请求令牌
                .checkTokenAccess("isAuthenticated()") //已验证的客户端才能请求check_token端点
                .allowFormAuthenticationForClients();
    }


}
