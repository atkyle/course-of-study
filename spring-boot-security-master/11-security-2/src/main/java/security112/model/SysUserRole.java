package security112.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SysUserRole {
    private Long userId;
    private Long roleId;
}
