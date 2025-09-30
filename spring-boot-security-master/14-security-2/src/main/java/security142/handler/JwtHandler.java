package security142.handler;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import security142.common.Results;
import security142.model.SysUser;
import security142.service.SysUserService;
import security142.utils.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class JwtHandler extends OncePerRequestFilter {

    @Autowired
    private SysUserService sysUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        String path = request.getRequestURI();
        if (path == null) {
            log.info("无路径，禁止访问");
            results(response, Results.accessDenied());
        } else if (path.startsWith("/login") || path.startsWith("/favicon.ico")) {
            chain.doFilter(request, response);
        } else {
            String token = request.getHeader("Authorization");
            if (StringUtils.isNotNull(token)) {
                Long userId = null;
                Long expireSecond = null;
                try {
                    Jws<Claims> claimsJws = Jwts.parserBuilder().requireSubject("JWT").setSigningKey(Results.SECRET_KEY).build().parseClaimsJws(token);
                    Claims body = claimsJws.getBody();
                    userId = body.get("userId", Long.class);
                    expireSecond = body.get("exp", Long.class);
                    log.info("JWT::正常业务接口,从token中解析userId=[{}] path=[{}] token=[{}]", userId, path, token);
                } catch (Exception e) {
                    log.error("JWT::正常业务接口,从token中解析userId异常，path=[{}]  token=[{}] errorMsg=[{}]", path, token, e.getMessage(), e);
                    results(response, Results.accessDenied());
                }
                if (userId == null || userId <= 0) {
                    log.info("JWT::正常业务接口,从token中解析userId错误，userId=[{}] path=[{}]  token=[{}]", userId, path, token);
                    results(response, Results.accessDenied());
                } else {
                    //设置当前登录用户为userId，这样可以直接获取当前登录用户的信息
                    SysUser user = sysUserService.selectLoginInfoByUserId(userId);
                    if (user == null) {
                        log.info("JWT::正常业务接口,从token中解析userId错误，userId=[{}] path=[{}]  token=[{}]", userId, path, token);
                        results(response, Results.accessDenied());
                    } else {

                        try {
                            if ((expireSecond * 1000) > System.currentTimeMillis()) {
                                //未到过期时间
                                log.info("JWT::正常业务接口,从token中解析userId正确，并且验证成功，userId=[{}] path=[{}]", userId, path);
                                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
                                chain.doFilter(request, response);
                            } else {
                                //已经到过期时间
                                results(response, Results.tokenExpired());
                            }
                        } catch (Throwable e) {
                            log.error("token错误或者过期，errorMsg=[{}]", e.getMessage(), e);
                            results(response, Results.accessDenied());
                        }
                    }
                }
            } else {
                log.info("JWT::正常业务接口,webToken为空,返回未授权或者授权过期，path={}  token={}", path, token);
                results(response, Results.tokenExpired());
            }
        }
    }

    private void results(HttpServletResponse response, Results results) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(results));
        writer.flush();
        writer.close();
    }
}
