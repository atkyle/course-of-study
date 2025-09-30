package security14.mapper;


import security14.model.SysUser;

public interface SysUserMapper {

    SysUser selectByUserName(String username);
}


