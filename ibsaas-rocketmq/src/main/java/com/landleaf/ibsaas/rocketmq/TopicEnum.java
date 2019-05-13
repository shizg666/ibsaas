package com.landleaf.ibsaas.rocketmq;

/**
 * web服务端消费返回数据 topic
 */
public enum TopicEnum {
    KNIGHT_TOPIC("knight","门禁数据返回消费topic"),
	;

	private String code;
    private String msg;



    private TopicEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

}
