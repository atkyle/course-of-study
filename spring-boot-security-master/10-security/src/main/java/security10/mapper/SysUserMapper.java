package security10.mapper;

import security10.model.SysUser;

public interface SysUserMapper {

    SysUser selectByUserName(String username);
}


