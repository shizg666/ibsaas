package com.landleaf.ibsaas.web.web.service.light;


import com.landleaf.ibsaas.common.domain.light.message.LightMsg;
import com.landleaf.ibsaas.common.domain.light.vo.LightStateRequestVO;

public interface ILightService {

    void controlLight(LightMsg requestBody);

    String getTryLightState(String key, String adress, Long timeout) ;


    void getAsynLightState(LightStateRequestVO requestVO);
}
