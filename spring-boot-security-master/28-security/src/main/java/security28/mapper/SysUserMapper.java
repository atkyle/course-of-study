package security28.mapper;


import security28.model.SysUser;

public interface SysUserMapper {

    SysUser selectByUserName(String username);
}


