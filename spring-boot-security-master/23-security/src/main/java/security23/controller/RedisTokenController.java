package security23.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class RedisTokenController {

    @Autowired
    public RedisTokenStore redisTokenStore;

    @GetMapping("redis/token/find/{clientId}")
    public Object find(@PathVariable String clientId){
        Collection<OAuth2AccessToken> tokens = redisTokenStore.findTokensByClientId(clientId);
        return tokens;
    }

    @GetMapping("redis/token/del/{clientId}")
    public Object del(@PathVariable String clientId){
        Collection<OAuth2AccessToken> tokens = redisTokenStore.findTokensByClientId(clientId);
        tokens.forEach(token-> redisTokenStore.removeAccessToken(token));
        return "删除成功";
    }
}

