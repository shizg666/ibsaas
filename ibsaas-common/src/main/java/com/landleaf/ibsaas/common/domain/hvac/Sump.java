package com.landleaf.ibsaas.common.domain.hvac;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/7/18 18:00
 * @description: 集水坑
 */
@Data
@ApiModel("集水坑")
public class Sump extends BaseDevice implements Serializable {
}
