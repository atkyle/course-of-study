package security26.mapper;


import security26.model.SysUser;

public interface SysUserMapper {

    SysUser selectByUserName(String username);
}


