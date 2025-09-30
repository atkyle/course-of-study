package security23.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class InMemoryTokenController {

    @Autowired
    public InMemoryTokenStore inMemoryTokenStore;

    @GetMapping("mem/token/find/{clientId}")
    public Object find(@PathVariable String clientId){
        Collection<OAuth2AccessToken> tokens = inMemoryTokenStore.findTokensByClientId(clientId);
        return tokens;
    }

    @GetMapping("mem/token/del/{clientId}")
    public Object del(@PathVariable String clientId){
        Collection<OAuth2AccessToken> tokens = inMemoryTokenStore.findTokensByClientId(clientId);
        tokens.forEach(token-> inMemoryTokenStore.removeAccessToken(token));
        return "删除成功";
    }
}

