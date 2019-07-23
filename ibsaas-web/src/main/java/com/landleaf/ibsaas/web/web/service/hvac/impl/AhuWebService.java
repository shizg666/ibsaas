package com.landleaf.ibsaas.web.web.service.hvac.impl;

import cn.hutool.json.JSONUtil;
import com.landleaf.ibsaas.common.constant.IbsaasConstant;
import com.landleaf.ibsaas.common.domain.hvac.dto.AhuDTO;
import com.landleaf.ibsaas.common.domain.hvac.dto.FanCoilDTO;
import com.landleaf.ibsaas.common.domain.hvac.vo.AhuVO;
import com.landleaf.ibsaas.common.domain.mq.HvacMqMsg;
import com.landleaf.ibsaas.common.enums.hvac.BacnetDeviceTypeEnum;
import com.landleaf.ibsaas.common.enums.hvac.ahu.AhuHandAutomaticallyEnum;
import com.landleaf.ibsaas.common.enums.hvac.ahu.AhuSeasonModeEnum;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.rocketmq.TagConstants;
import com.landleaf.ibsaas.rocketmq.TopicConstants;
import com.landleaf.ibsaas.web.rocketmq.WebMqProducer;
import com.landleaf.ibsaas.web.web.service.hvac.BaseDeviceService;
import com.landleaf.ibsaas.web.web.service.hvac.IAhuWebService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/7/4 10:05
 * @description: ahu逻辑处理类
 */
@Service
@Slf4j
public class AhuWebService extends BaseDeviceService implements IAhuWebService {

    @Autowired
    private RedisHandle redisHandle;

    @Autowired
    private WebMqProducer webMqProducer;

    @Value("${bacnet.place.id}")
    private String placeId;


    @Override
    public List<AhuVO> overview() {
        return redisHandle.getMapField(placeId, String.valueOf(BacnetDeviceTypeEnum.AHU.getDeviceType()));
    }

    @Override
    public void update(AhuDTO ahuDTO) {
        checkWritePermission(ahuDTO);
        ahuUpdateSpecialLogic(ahuDTO);
        HvacMqMsg msg = new HvacMqMsg(AhuDTO.class.getName(), JSONUtil.toJsonStr(ahuDTO));
        webMqProducer.sendMessage(JSONUtil.toJsonStr(msg),
                TopicConstants.TOPIC_HVAC_WRITE,
                TagConstants.TAGS_DEFAULT);
    }


    /**
     * ahu修改时特殊判定逻辑
     * @param ahuDTO
     */
    private void ahuUpdateSpecialLogic(AhuDTO ahuDTO){
        //如果是控制开关机 不做限制
        if(StringUtils.isNotBlank(ahuDTO.getAhuOnOff())){
            return;
        }
        //ahu当前状态
        AhuVO ahuVO = overview().get(0);
        if(String.valueOf(IbsaasConstant.IN_ACTIVE).equals(ahuVO.getAhuOnOff())){
            throw new BusinessException("风机关闭状态下,所有运行设置不生效");
        }
        //只有在“水阀控制模式”为“手动模式”时可编辑
        if(StringUtils.isNotBlank(ahuDTO.getAhuWaterValveFeedback())){
            if(!String.valueOf(AhuHandAutomaticallyEnum.HAND_MODE.getState()).equals(ahuVO.getAhuWaterValveMode())){
                throw new BusinessException("水阀开度反馈,只有在“水阀控制模式”为“手动模式”时可编辑");
            }
            return;
        }
        //风机手动状态下，“风机频率调节控制”不可调；
        //风机自动状态下，“风机频率调节控制”可调
        if(StringUtils.isNotBlank(ahuDTO.getAhuWaterValveAdjust())){
            if(!String.valueOf(AhuHandAutomaticallyEnum.AUTO_MODE.getState()).equals(ahuVO.getAhuHandAutomatically())){
                throw new BusinessException("风机频率调节控制,只有在风机自动状态下才能调节");
            }
            return;
        }

        //弹框设定“加湿湿度值”，一般默认50%
        //只有在冬季模式下，才可以开启“加湿”
        if(StringUtils.isNotBlank(ahuDTO.getAhuHumValveState())
                && String.valueOf(IbsaasConstant.ACTIVE).equals(ahuDTO.getAhuHumValveState())){
            if(!String.valueOf(AhuSeasonModeEnum.WINTER_MODE.getState()).equals(ahuVO.getAhuSeasonMode())){
                throw new BusinessException("只有在冬季模式下，才可以开启“加湿”");
            }
            return;
        }
    }
}
