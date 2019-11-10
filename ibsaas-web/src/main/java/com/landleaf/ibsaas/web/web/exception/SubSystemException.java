package com.landleaf.ibsaas.web.web.exception;


import com.landleaf.ibsaas.common.exception.BusinessException;

/**
 * @Description: 用户信息操作相关异常
 */
public class SubSystemException extends BusinessException {

    private static final long serialVersionUID = -4456686339387898066L;

    public static final String BUSINESS_SYSTEM_CODE_NOT_EXISTS = "business.system.code.not.exists";

    public SubSystemException(String errCode) {
        super(errCode);
        super.errCode = errCode;
    }

    public SubSystemException(String errCode, String msg) {
        super(errCode, msg);
    }

    public SubSystemException(String errCode, Object ... args) {
        super(errCode, args);
    }
}
