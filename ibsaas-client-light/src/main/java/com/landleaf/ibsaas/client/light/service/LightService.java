package com.landleaf.ibsaas.client.light.service;

import com.landleaf.ibsaas.common.domain.light.dto.LightControlDTO;
import com.landleaf.ibsaas.common.domain.light.message.LightMsg;

public interface LightService {

    void lightHandle(LightMsg lightMsg);
}
