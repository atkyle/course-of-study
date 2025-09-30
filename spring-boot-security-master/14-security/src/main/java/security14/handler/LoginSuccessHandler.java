package security14.handler;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import security14.model.Jwt;
import security14.model.SysUser;
import security14.utils.JwtUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功 handler
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 获取登录成功信息
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        boolean loginBoolean = true;

        SysUser user = (SysUser) authentication.getPrincipal();
        user.setPassword(null);
        long now = System.currentTimeMillis();

        JSONObject payload = new JSONObject();
        payload.put("iss","sys"); //签发人
        payload.put("aud",user.getUsername()); //受众
        payload.put("exp",now + JwtUtils.EXPIRE_TIME); //过期时间
        payload.put("nbf",now); //生效时间
        payload.put("iat",now); //签发时间
        payload.put("jti", user.getId()); //编号
        payload.put("sub","JWT-TEST"); //主题
        payload.put("user",user); //用户对象

        try {
            response.setHeader(JwtUtils.HEADER_TOKEN_NAME, new Jwt(payload.toJSONString()).toString() );
        } catch (Exception e) {
            loginBoolean = false;
        }
        if (loginBoolean){
            response.getWriter().write("{\"code\": \"200\", \"msg\": \"登录成功\"}");
        }else{
            response.getWriter().write("{\"code\": \"500\", \"msg\": \"登录失败\"}");
        }
    }
}

