package com.landleaf.ibsaas.client.parking.lifang.mq.domain;

import java.io.Serializable;

//立方门禁接口返回值
public class ParkingResponse<T> implements Serializable {

    private String result;
    private String resultInfo;
    /**
     * 正常返回参数
     */
    private T obj;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }


}
