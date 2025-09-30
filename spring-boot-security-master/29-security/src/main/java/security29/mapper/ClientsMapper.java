package security29.mapper;

import security29.model.Clients;

import java.util.Map;

public interface ClientsMapper {

    Clients selectById(String clientId);

    int insert(Map map);
}
