package security31.mapper;


import security31.model.SysUser;

public interface SysUserMapper {

    SysUser selectByUserName(String username);
}


