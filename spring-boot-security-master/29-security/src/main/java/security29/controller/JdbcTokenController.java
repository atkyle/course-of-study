package security29.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class JdbcTokenController {

    @Autowired
    public JdbcTokenStore jdbcTokenStore;

    @GetMapping("jdbc/token/find/{username}")
    public Object find(@PathVariable String username){
        Collection<OAuth2AccessToken> tokens = jdbcTokenStore.findTokensByUserName(username);
        return tokens;
    }

    @GetMapping("jdbc/token/del/{clientId}")
    public Object del(@PathVariable String clientId){
        Collection<OAuth2AccessToken> tokens = jdbcTokenStore.findTokensByClientId(clientId);
        tokens.forEach(token-> jdbcTokenStore.removeAccessToken(token));
        return "删除成功";
    }
}

