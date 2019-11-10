package com.landleaf.ibsaas.common.domain.energy.vo;

import com.landleaf.ibsaas.common.domain.hvac.vo.HvacNodeVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/14 14:15
 * @description:
 */
@Data
@ApiModel("设备选择节点返回对象")
public class NodeChoiceVO {

    @ApiModelProperty("电表节点")
    private List<HvacNodeVO> electricNodes;

    @ApiModelProperty("水表节点")
    private List<HvacNodeVO> waterNodes;

}
