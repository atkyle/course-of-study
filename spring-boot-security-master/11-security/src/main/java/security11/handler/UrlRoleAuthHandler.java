package security11.handler;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 角色 权限 路由处理
 */
public class UrlRoleAuthHandler implements AccessDecisionVoter<Object> {
    @Override
    public boolean supports(ConfigAttribute attribute) {
        if (null == attribute.getAttribute()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    /**
     * ACCESS_GRANTED – 同意
     * ACCESS_DENIED – 拒绝
     * ACCESS_ABSTAIN – 弃权
     */
    @Override
    public int vote(Authentication user, Object object, Collection<ConfigAttribute> urlRoles) {
        if (null == user) {
            return ACCESS_DENIED;
        }

        int result = ACCESS_ABSTAIN;
        Collection<? extends GrantedAuthority> userRoles = user.getAuthorities();

        /* 遍历链接中对应的权限 */
        for (ConfigAttribute urlRole : urlRoles) {
            if (this.supports(urlRole)) {
                /* 此处默认值为弃权，表示只要有一个角色对应上，用户就可以访问链接
                   如果值改为拒绝，表示必须全部角色包含才能访问链接
                 */
                result = ACCESS_ABSTAIN;
                /* 遍历用户中对应的角色列表 */
                for (GrantedAuthority userRole : userRoles) {
                    if (urlRole.getAttribute().equals(userRole.getAuthority())) {
                        return ACCESS_GRANTED;
                    }
                }
            }
        }
        return result;
    }
}
