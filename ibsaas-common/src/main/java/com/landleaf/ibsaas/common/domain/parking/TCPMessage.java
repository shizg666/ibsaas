package com.landleaf.ibsaas.common.domain.parking;

import com.landleaf.ibsaas.common.domain.Response;

//内部tcp消息实体
public class TCPMessage<T> {

    public String createTime;//创建时间
    public String from;//消息源头
    public String to;//消息目的地
    public String msgId;//消息编号
    public String msgName;//消息名称
    public String subMsgName;//子消息名称

    public T requestBody;

    public Response response;


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgName() {
        return msgName;
    }

    public void setMsgName(String msgName) {
        this.msgName = msgName;
    }

    public String getSubMsgName() {
        return subMsgName;
    }

    public void setSubMsgName(String subMsgName) {
        this.subMsgName = subMsgName;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public T getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(T requestBody) {
        this.requestBody = requestBody;
    }

    public TCPMessage() {
    }

    public void build(String createTime, String from, String to, String msgId, String msgName, String subMsgName, T requestBody, Response response) {
        this.createTime = createTime;
        this.from = from;
        this.to = to;
        this.msgId = msgId;
        this.msgName = msgName;
        this.subMsgName = subMsgName;
        this.requestBody = requestBody;
        this.response = response;
    }



}
