package com.landleaf.ibsaas.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 统一返回参数
 * @param <T>
 */
@ApiModel(value = "返回对象实体")
public class Response<T> implements Serializable {

    /**
     * 要求前端进行重定向的编码
     */
    public static final String ERROR_REDIRECT = "302";

    /**
     * 无权访问指定资源
     */
    public static final String ERROR_CODE_NOT_RIGHT_TO_ACCESS = "403";

    /**
     * 校验类异常编码
     */
    public static final String ERROR_CODE_VALIDATE = "10000";

    /**
     * 业务异常类异常编码
     */
    public static final String ERROR_CODE_BUSINESS_EXCEPTION = "20000";

    /**
     * 未捕获类异常编码
     */
    public static final String ERROR_CODE_UNHANDLED_EXCEPTION = "90000";

    /**
     * 请求id，系统异常时需要将此参数传递到前台去
     */
    private String requestId;

    /**
     * 请求是否处理成功
     */
    @ApiModelProperty(value="请求是否处理成功")
    private boolean success;

    /**
     * 返回结果有业务异常
     */
    private boolean hasBusinessException;

    /**
     * 业务异常错误代码
     */
    private String errorCode;

    /**
     * 业务异常错误信息
     */
    private String errorMsg;

    /**
     * 提示消息，需要进行国际化
     */
    @ApiModelProperty(value="提示消息")
    private String message;

    /**
     * 正常返回参数
     */
    @ApiModelProperty(value="数据")
    private T result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isHasBusinessException() {
        return hasBusinessException;
    }

    public void setHasBusinessException(boolean hasBusinessException) {
        this.hasBusinessException = hasBusinessException;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    /**
     * 构建无权访问的返回实体
     * @param errorMessage
     * @return
     * @author 陈宇霖
     * @date 2017年09月27日11:07:30
     */
    public static Response buildNoRightToAccessResponse(String errorMessage) {
        Response result = new Response();
        result.setSuccess(false);
        result.setHasBusinessException(true);
        result.setErrorCode(Response.ERROR_CODE_NOT_RIGHT_TO_ACCESS);
        result.setErrorMsg(errorMessage);
        result.setMessage(errorMessage);
        return result;
    }

}
