package security11.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import security11.mapper.SysMenuMapper;

import java.util.Collection;
import java.util.List;

/**
 * 从数据库中获取url对应的role信息
 */
@Service
public class UrlRolesFilterHandler implements FilterInvocationSecurityMetadataSource {

    @Autowired(required = false)
    private SysMenuMapper selectRolesByUrl;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        List<String> roleNames = selectRolesByUrl.selectRoleNamesByUrl(requestUrl);
        String[] names = new String[roleNames.size()];
        for (int i = 0; i < roleNames.size(); i++) {
            names[i] = roleNames.get(i);
        }
        return SecurityConfig.createList(names);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
