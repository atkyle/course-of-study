package security23.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import security23.mapper.ClientsMapper;
import security23.model.Clients;

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


}

