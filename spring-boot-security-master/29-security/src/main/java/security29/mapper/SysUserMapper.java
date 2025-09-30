package security29.mapper;


import security29.model.SysUser;

public interface SysUserMapper {

    SysUser selectByUserName(String username);
}


