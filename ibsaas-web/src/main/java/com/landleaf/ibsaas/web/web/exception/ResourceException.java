package com.landleaf.ibsaas.web.web.exception;


import com.landleaf.ibsaas.common.exception.BusinessException;

/**
 * @Description: 权限信息操作相关异常
 */
public class ResourceException extends BusinessException {


    private static final long serialVersionUID = 3649674297563680987L;

    public static final String BUSINESS_RESOURCE_UPDATE_RESOURCE_NOT_EXISTS = "business.resource.update.resource.not.exists";

    public static final String BUSINESS_RESOURCE_ENTRY_URI_CONFLICT = "business_resource_entry_uri_conflict";

    public ResourceException(String errCode) {
        super(errCode);
        super.errCode = errCode;
    }

    public ResourceException(String errCode, String msg) {
        super(errCode, msg);
    }

    public ResourceException(String errCode, Object ... args) {
        super(errCode, args);
    }
}
