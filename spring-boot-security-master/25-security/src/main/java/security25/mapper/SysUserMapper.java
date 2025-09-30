package security25.mapper;


import security25.model.SysUser;

public interface SysUserMapper {

    SysUser selectByUserName(String username);
}


