package security29.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import security29.mapper.ClientsMapper;
import security29.model.Clients;
import sun.reflect.generics.scope.Scope;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MyBatisClientDetailsService implements ClientDetailsService {

    private static final Logger log = LoggerFactory.getLogger(MyBatisClientDetailsService.class);

    @Autowired(required = false)
    private ClientsMapper clientsMapper;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        if (clientId==null||clientId.trim().length()<=0) {
            log.error(" clientId 为空");
            throw new ClientRegistrationException(" clientId 为空");
        }

        Clients clients = clientsMapper.selectById(clientId);
        if (clients != null){
            return clients;
        }
        throw new ClientRegistrationException("client 客户端 不存在!");
    }



    public Object insert(Map map){
        map.put("clientId", getId());

        String secret = getId()+getId();
        String secretEncode = new BCryptPasswordEncoder().encode(secret);
        map.put("clientSecret", secretEncode);

        map.put("scope","all");
        map.put("grantTypes","authorization_code,password,refresh_token");
        clientsMapper.insert(map);



        Map res = new HashMap(4);
        res.put("clientId", map.get("clientId"));
        res.put("clientSecret", secret);

        return res;
    }

    public static String getId(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}

