package com.landleaf.ibsaas.client.hvac.util;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.util.Locale;

@Data
public class JsonResult {

    private static final String SUCCESS = "success";

    private boolean success = true;
    private String businessCode = "1";
    private String errorCode = "1";
    private String msg = "";
    private Object data;

    public static JsonResult getResult(Object data) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setData(data);
        return jsonResult;
    }

    public static JsonResult success() {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setData(SUCCESS);
        return jsonResult;
    }

    public static JsonResult error(String errorCode, MessageSource messageSource, Object... params) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setSuccess(false);
        jsonResult.setBusinessCode("0");
        jsonResult.setErrorCode(errorCode);
        jsonResult.setMsg(messageSource.getMessage(errorCode, params, errorCode, Locale.SIMPLIFIED_CHINESE));
        return jsonResult;
    }

    public static JsonResult error(String errorCode, String errorMsg) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setSuccess(false);
        jsonResult.setBusinessCode("0");
        jsonResult.setErrorCode(errorCode);
        jsonResult.setMsg(errorMsg);
        return jsonResult;
    }

    public static JsonResult errorData(String errorCode, MessageSource messageSource, Object data, Object... params) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setSuccess(false);
        jsonResult.setBusinessCode("0");
        jsonResult.setErrorCode(errorCode);
        jsonResult.setMsg(messageSource.getMessage(errorCode, params, errorCode, Locale.SIMPLIFIED_CHINESE));
        jsonResult.setData(data);
        return jsonResult;
    }

    public JsonResult() {
        super();
    }

    public JsonResult(boolean success) {
        super();
        this.success = success;
    }

    public  String toJson() {
        String json = JSON.toJSONString(this);
        if (StringUtils.isEmpty(json)) {
            json = "{\"success\":false,\"errCode\":\"serialize.error\",\"message\":\"serialize.error\",\"data\":\"\"}";
        }
        return json;
    }

    public void setError(String errorCode, MessageSource messageSource, Object... params) {
        this.setSuccess(false);
        this.setBusinessCode("0");
        this.setErrorCode(errorCode);
        this.setMsg(messageSource.getMessage(errorCode, params, errorCode, Locale.SIMPLIFIED_CHINESE));
    }

}