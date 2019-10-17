package com.landleaf.ibsaas.web.web.service.light;


import com.landleaf.ibsaas.common.domain.light.dto.LightControlDTO;
import com.landleaf.ibsaas.common.domain.light.message.LightMsg;

public interface ILightService {

    void controlLight(LightMsg requestBody);

    String getTryLightState(String key, String adress, Long timeout) ;

}
