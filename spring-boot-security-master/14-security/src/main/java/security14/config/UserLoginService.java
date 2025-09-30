package security14.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import security14.mapper.SysUserMapper;
import security14.model.SysUser;

@Component
public class UserLoginService implements UserDetailsService {


    @Autowired(required = false)
    private SysUserMapper sysUserMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username==null||username.trim().length()<=0) {
            throw new UsernameNotFoundException("用户名为空");
        }

        SysUser sysUser = sysUserMapper.selectByUserName(username);
        if (sysUser != null){
            return sysUser;
        }
        throw new UsernameNotFoundException("用户不存在!");
    }
}
