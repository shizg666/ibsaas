package com.landleaf.ibsaas.common.domain.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lokiy
 * @date 2019/6/3 18:06
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HvacMqMsg {

    private String clazzPath;

    private String reqBody;
}
