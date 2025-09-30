package security112.mapper;

import security112.model.SysUser;

public interface SysUserMapper {

    SysUser selectByUserName(String username);
}


