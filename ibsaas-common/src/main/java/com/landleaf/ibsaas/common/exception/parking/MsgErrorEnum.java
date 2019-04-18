package com.landleaf.ibsaas.common.exception.parking;


public enum MsgErrorEnum implements ErrorCode{
	NOT_FOUND_PROCESSORSERVICE("MSG_100","根据msg和submsg没有找到对应的服务"),

	;

    private String code;
    private String msg;

    private MsgErrorEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }


}
