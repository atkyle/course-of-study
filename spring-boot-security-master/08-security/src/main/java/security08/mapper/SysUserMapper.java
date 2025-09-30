package security08.mapper;

import security08.model.SysUser;

public interface SysUserMapper {

    SysUser selectByUserName(String username);
}


