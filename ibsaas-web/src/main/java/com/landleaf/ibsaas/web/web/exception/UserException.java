package com.landleaf.ibsaas.web.web.exception;


/**
 * @Description: 用户信息操作相关异常
 */
public class UserException extends BusinessException {

    private static final long serialVersionUID = -4456686339387898066L;

    public static final String BUSINESS_USER_LOGIN_USER_CODE_NOT_EXISTS = "business.user.login.usercode.not.exists";

    public static final String BUSINESS_USER_LOGIN_USER_PASSWORD_ERROR = "business.user.login.user.password.error";

    public static final String BUSINESS_USER_UPDATE_USER_NOT_EXISTS = "business.user.update.user.not.exists";

    public static final String BUSINESS_USER_RELEVANCE_ROLE_UPDATE_NOT_EXISTS = "business_user_relevance_role_update_not_exists";

    public UserException(String errCode) {
        super(errCode);
        super.errCode = errCode;
    }

    public UserException(String errCode, String msg) {
        super(errCode, msg);
    }

    public UserException(String errCode, Object... args) {
        super(errCode, args);
    }
}
