package security112.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
public class SysMenu implements GrantedAuthority {

    private Long id;

    private Long parentId;

    private String name;

    private String url;

    private String authority;
}