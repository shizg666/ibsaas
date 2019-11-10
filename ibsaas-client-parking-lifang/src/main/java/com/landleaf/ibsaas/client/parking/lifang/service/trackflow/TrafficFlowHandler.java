package com.landleaf.ibsaas.client.parking.lifang.service.trackflow;


import com.landleaf.ibsaas.common.domain.parking.request.UsercrdtmInHistoryQueryDTO;

import java.util.List;

public interface TrafficFlowHandler {


	List<UsercrdtmInHistoryQueryDTO> handle(String code, UsercrdtmInHistoryQueryDTO queryDTO);
}
