package security09.mapper;

import security09.model.SysUser;

public interface SysUserMapper {

    SysUser selectByUserName(String username);
}


