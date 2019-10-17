package com.landleaf.ibsaas.client.parking.lifang.mq.service.impl;

import com.google.common.collect.Lists;
import com.landleaf.ibsaas.client.parking.lifang.mq.dao.ChargeruleDao;
import com.landleaf.ibsaas.client.parking.lifang.mq.domain.Chargerule;
import com.landleaf.ibsaas.client.parking.lifang.mq.service.IChargeruleService;
import com.landleaf.ibsaas.common.domain.parking.response.ChargeruleResponseDTO;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChargeruleService extends AbstractBaseService<ChargeruleDao, Chargerule> implements IChargeruleService<Chargerule> {
    @Autowired
    private ChargeruleDao chargeruleDao;


    @Override
    public List<ChargeruleResponseDTO> queryAllChargerule() {
        List<ChargeruleResponseDTO> result = Lists.newArrayList();
        List<Chargerule> chargerules = selectAll();
        if (!CollectionUtils.isEmpty(chargerules)) {
            result = chargerules.stream().map(chargerule -> {
                ChargeruleResponseDTO responseDTO = new ChargeruleResponseDTO();
                responseDTO.setChargeTypeCode(String.valueOf(chargerule.getChargeRuleId()));
                responseDTO.setChargeTypeName(chargerule.getChargeRuleName());
                responseDTO.setUniqueId(String.valueOf(chargerule.getChargeRuleId()));
                return responseDTO;
            }).collect(Collectors.toList());
        }
        return result;
    }
}
