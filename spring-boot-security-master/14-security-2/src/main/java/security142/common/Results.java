package security142.common;

import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

/**
 * 接口返回通用封装
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Results<T> {

    public static final String SECRET = "12345678901234567890123456789012345678901234567890";
    public static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Results.SECRET.getBytes(StandardCharsets.UTF_8));

    public static final Long EXPIRE_TIME = 1000L * 60 * 60 * 24;

    private int code;
    private String msg;
    private T data;

    //=======================================================================================================

    /**
     * 成功code
     */
    private static final int SUCCESS_CODE = 200;

    /**
     * 成功 msg
     */
    private static final String SUCCESS_MSG = "SUCCESS";

    public static Results success() {
        return new Results(SUCCESS_CODE, SUCCESS_MSG, null);
    }

    public static Results success(String msg) {
        return new Results(SUCCESS_CODE, msg, null);
    }

    public static Results success(Object data) {
        return new Results(SUCCESS_CODE, SUCCESS_MSG, data);
    }

    //=======================================================================================================


    /**
     * 参数错误 code
     */
    private static final int PARAM_ERROR_CODE = 203;

    public static Results paramError(String message) {
        return new Results(PARAM_ERROR_CODE, message, null);
    }

    //=======================================================================================================


    /**
     * 拒绝访问
     */
    private static final int STATE_ACCESS_DENIED = 403;
    /**
     * 拒绝访问
     */
    private static final String MESSAGE_ACCESS_DENIED = "没有权限，拒绝访问";

    public static Results accessDenied() {
        return new Results(STATE_ACCESS_DENIED, MESSAGE_ACCESS_DENIED, null);
    }

    //=======================================================================================================

    /**
     * Token无效或者已经过期
     */
    private static final int STATE_EXPIRE = 409;

    /**
     * Token无效或者已经过期
     */
    private static final String MESSAGE_EXPIRE = "token无效或者已经过期";

    public static Results tokenExpired() {
        return new Results(STATE_EXPIRE, MESSAGE_EXPIRE, null);
    }
}

