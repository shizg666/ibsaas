package com.landleaf.ibsaas.client.light.handle.light.reponse;

import com.landleaf.ibsaas.common.domain.light.message.LightMsgResponse;

public interface LightResponse {

    LightMsgResponse getReponse(String message);

    void handle(LightMsgResponse message);
}
