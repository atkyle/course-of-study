package security11.mapper;

import security11.model.SysUser;

public interface SysUserMapper {

    SysUser selectByUserName(String username);
}


