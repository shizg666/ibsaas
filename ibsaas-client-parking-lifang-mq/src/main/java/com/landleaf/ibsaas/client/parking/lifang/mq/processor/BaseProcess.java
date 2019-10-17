package com.landleaf.ibsaas.client.parking.lifang.mq.processor;

import com.landleaf.ibsaas.client.parking.lifang.mq.domain.ParkingResponse;

public class BaseProcess {


    public ParkingResponse returnSuccess(Object obj){
        ParkingResponse parkingResponse = new ParkingResponse();
        parkingResponse.setObj(obj);
        parkingResponse.setResult("200");
        parkingResponse.setResultInfo("操作成功");
        return parkingResponse;
    }
}
