package com.landleaf.ibsaas.screen.model.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/12/12 15:27
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("返回对象")
public class ResponseResult implements Serializable {

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
    private Object result;


    public static ResponseResult success(Object object){
        ResponseResult response = new ResponseResult();
        response.setSuccess(true);
        response.setHasBusinessException(false);
        response.setResult(object);
        return response;
    }



}
