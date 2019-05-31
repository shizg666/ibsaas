package com.landleaf.ibsaas.client.hvac.controller;

import com.landleaf.ibsaas.client.hvac.util.LocaleMessageSourceEx;
import com.landleaf.ibsaas.common.domain.PageResponse;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import com.landleaf.ibsaas.common.exception.AccessNotAllowException;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.common.privoder.TcpConnectManager;
import com.landleaf.ibsaas.common.utils.date.DateUtil;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author wyl
 * @version 1.0
 * @description ontroller增强，用于处理异常、分页等通用信息
 * @date 2019/3/21 0021 9:18
 */
public class Basic2Controller {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 用于国际化消息生成
     */
    @Resource
    protected LocaleMessageSourceEx localeMessageSourceEx;

    /**
     * @return com.landleaf.leo.web.dto.response.Response
     * @description 请求成功，不带返回参数
     * @author wyl
     * @date 2019/3/21 0021 9:20
     * @version 1.0
     */
    public Response returnSuccess() {
        return returnSuccess(null);
    }

    /**
     * @param successMsg
     * @description 带成功提示的返回
     * @author wyl
     * @date 2019/3/21 0021 9:21
     * @version 1.0
     */
    public Response returnSuccess(String successMsg) {
        return returnSuccess(null, successMsg);
    }

    /**
     * @param object
     * @return com.landleaf.leo.web.dto.response.Response
     * @description 请求成功带返回参数
     * @author wyl
     * @date 2019/3/21 0021 9:21
     * @version 1.0
     */
    public Response returnSuccess(Object object) {
        return returnSuccess(object, null);
    }

    /**
     * @param object
     * @param successMsg
     * @return com.landleaf.leo.web.dto.response.Response
     * @description 带成功提示和返回参数的结果
     * @author wyl
     * @date 2019/3/21 0021 9:21
     * @version 1.0
     */
    public Response returnSuccess(Object object, String successMsg) {
        Response response = new Response();
        response.setSuccess(true);
        response.setHasBusinessException(false);
        response.setMessage(localeMessageSourceEx.getMessage(successMsg));
        response.setResult(object);
        return response;
    }

    /**
     * @param object
     * @param totalCount
     * @return PageResponse
     * @description 分页查询请求返回参数
     * @author wyl
     * @date 2019/3/21 0021 9:21
     * @version 1.0
     */
    public PageResponse returnSuccess(Object object, long totalCount) {
        PageResponse response = new PageResponse();
        response.setSuccess(true);
        response.setHasBusinessException(false);
        response.setResult(object);
        response.setTotalCount(totalCount);
        return response;
    }

    /**
     * @param errorMsg
     * @return com.landleaf.leo.web.dto.response.Response
     * @description 返回参数校验失败信息
     * @author wyl
     * @date 2019/3/21 0021 9:21
     * @version 1.0
     */
    public Response returnValidateError(String errorMsg) {
        Response response = new Response();
        response.setSuccess(false);
        response.setHasBusinessException(true);
        response.setErrorCode(Response.ERROR_CODE_VALIDATE);
        response.setMessage(localeMessageSourceEx.getMessage(errorMsg));
        response.setErrorCode(Response.ERROR_CODE_VALIDATE);
        response.setErrorMsg(errorMsg);
        return response;
    }

    /**
     * @param bindingResult
     * @return com.landleaf.leo.web.dto.response.Response
     * @description 返回参数校验失败
     * @author wyl
     * @date 2019/3/21 0021 9:22
     * @version 1.0
     */
    public Response returnValidateError(BindingResult bindingResult) {
        Response response = new Response();
        response.setSuccess(false);
        response.setHasBusinessException(true);
        StringBuffer errorMsg = new StringBuffer();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            errorMsg.append(localeMessageSourceEx.getMessage(fieldError.getDefaultMessage(), fieldError.getArguments())).append("\n");
        });
        response.setMessage(errorMsg.toString());
        response.setErrorCode(Response.ERROR_CODE_VALIDATE);
        response.setErrorMsg(errorMsg.toString());
        return response;
    }

    /**
     * @param exception
     * @return com.landleaf.leo.web.dto.response.Response
     * @description 统一的请求异常处理，所有异常都转换为json输入到前台，前端根据返回结果进行判断如何展示异常信息
     * @author wyl
     * @date 2019/3/21 0021 9:22
     * @version 1.0
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response handlerException(Exception exception) {
        log.error(exception.getMessage(),exception);
        Response response = new Response();
        response.setSuccess(false);
        if (exception instanceof AccessNotAllowException) { //无权访问
            response = Response.buildNoRightToAccessResponse(localeMessageSourceEx.getMessage(((AccessNotAllowException) exception).getErrorCode()));
        } else if (exception instanceof BusinessException) {    //业务异常
            response.setHasBusinessException(true);
            response.setMessage(localeMessageSourceEx.getMessage(((BusinessException) exception).getErrorCode(), ((BusinessException) exception).getErrorArguments()));
            response.setErrorCode(Response.ERROR_CODE_BUSINESS_EXCEPTION);
            response.setErrorMsg(localeMessageSourceEx.getMessage(((BusinessException) exception).getErrorCode(), ((BusinessException) exception).getErrorArguments()));
        } else if (exception instanceof MethodArgumentNotValidException) { //参数校验失败异常
            response.setHasBusinessException(true);
            BindingResult bindingResult = ((MethodArgumentNotValidException) exception).getBindingResult();
            StringBuffer errorMsg = new StringBuffer();
            bindingResult.getFieldErrors().forEach(fieldError -> {
                errorMsg.append(fieldError.getDefaultMessage()).append("\n");
            });
            response.setMessage(errorMsg.toString());
            response.setErrorCode(Response.ERROR_CODE_VALIDATE);
            response.setErrorMsg(errorMsg.toString());
        } else { //未捕获异常
            response.setHasBusinessException(false);
            response.setMessage("系统异常");
            response.setErrorCode(Response.ERROR_CODE_UNHANDLED_EXCEPTION);
//            response.setErrorMsg(ExceptionUtils.getStack(exception));
        }
        return response;
    }


    public ChannelHandlerContext getChannelHandlerContext(String clientId){
        TcpConnectManager instance = TcpConnectManager.getInstance();
        ChannelHandlerContext ctx = instance.getCtx(clientId);
        if (ctx == null) {
            log.error("与TCP服务端连接已端开,未获取到会话");
            throw new BusinessException(String.format("[clientId]:%s,%s",clientId,"会话丢失！"));
        }
        return ctx;
    }

    public void writeAndFlush(ChannelHandlerContext ctx, TCPMessage data){
        ctx.writeAndFlush(data);
        log.info("开始请求数据--->starttime:[{}]", DateUtil.format(new Date()));
    }

}
