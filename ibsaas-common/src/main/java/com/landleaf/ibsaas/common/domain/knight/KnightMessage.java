package com.landleaf.ibsaas.common.domain.knight;

import com.landleaf.ibsaas.common.domain.Response;

public class KnightMessage<T> {

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

    public T getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(T requestBody) {
        this.requestBody = requestBody;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
