package security13.mapper;

import java.util.List;

public interface SysMenuMapper {

    List<String> selectRoleNamesByUrl(String url);
}


