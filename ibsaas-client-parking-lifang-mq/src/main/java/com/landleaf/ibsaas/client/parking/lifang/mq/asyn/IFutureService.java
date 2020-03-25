package com.landleaf.ibsaas.client.parking.lifang.mq.asyn;

import com.landleaf.ibsaas.client.parking.lifang.mq.domain.ParkingMessage;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.Future;

public interface IFutureService {

    @Async
    Future<ParkingMessage> handlerMsg(ParkingMessage parkingMessage);
}
