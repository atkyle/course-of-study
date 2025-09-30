package security112.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
public class SysUser implements UserDetails {

    private Long id;

    private String username;

    private String password;

    private List<SysRole> sysRoleList = new ArrayList<>();

    private List<SysMenu> sysMenuList = new ArrayList<>();


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    /* 获取当前用户对应的角色列表 */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return sysMenuList;
    }
}


