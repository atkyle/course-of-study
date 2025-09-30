package security31.mapper;

import security31.model.Clients;

import java.util.Map;

public interface ClientsMapper {

    Clients selectById(String clientId);

    int insert(Map map);
}
