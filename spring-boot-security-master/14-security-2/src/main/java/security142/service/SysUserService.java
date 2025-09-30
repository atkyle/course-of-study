package security142.service;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import security142.common.Results;
import security142.model.SysUser;

import java.util.Date;

@Service
public class SysUserService {

    public Results login(String username, String password) {
        long userId = 1L;

        JSONObject json = new JSONObject();
        json.put("userId", userId);

        long nowTime = System.currentTimeMillis();

        String token = Jwts.builder()
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                .setSubject("JWT")
                .setIssuer(String.valueOf(userId))
                .setExpiration(new Date(nowTime + Results.EXPIRE_TIME)) // 过期时间
                .claim("userId", userId) // 往Payload中添加自定义的key和value
                .signWith(Results.SECRET_KEY) // 使用secretKey进行签名
                .compact(); // 生成JWT字符串

        Object jwt = token;
        return Results.success(jwt);
    }

    public SysUser selectLoginInfoByUserId(Long userId) {
        return new SysUser(1L, "admin", "$2a$10$WD3M6xRnSiBoTwMdVURpFe.zdJzGhwtZmQerYSiWc5X/p7ABEjXUq");
    }
}
