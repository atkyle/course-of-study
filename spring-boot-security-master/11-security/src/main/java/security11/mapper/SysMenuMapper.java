package security11.mapper;

import java.util.List;

public interface SysMenuMapper {

    List<String> selectRoleNamesByUrl(String url);
}


