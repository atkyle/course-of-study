package security13.mapper;


import security13.model.SysUser;

public interface SysUserMapper {

    SysUser selectByUserName(String username);
}


