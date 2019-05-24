package com.landleaf.ibsaas.web.web.exception;


import com.landleaf.ibsaas.common.exception.BusinessException;

/**
 * @Description: 角色信息操作相关异常
 */
public class RoleException extends BusinessException {

    private static final long serialVersionUID = 1163440808772289752L;

    public static final String BUSINESS_ROLE_UPDATE_ROLE_NOT_EXISTS = "business.role.update.role.not.exists";
    public static final String BUSINESS_ROLE_RELEVANCE_RESOURCE_UPDATE_NOT_EXISTS = "business_role_relevance_resource_update_not_exists";

    public RoleException(String errCode) {
        super(errCode);
        super.errCode = errCode;
    }

    public RoleException(String errCode, String msg) {
        super(errCode, msg);
    }

    public RoleException(String errCode, Object... args) {
        super(errCode, args);
    }
}
