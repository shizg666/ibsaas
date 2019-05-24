package com.landleaf.ibsaas.client.knight.service.impl;

import com.google.common.collect.Lists;
import com.landleaf.ibsaas.client.knight.dao.EmplyTypeDao;
import com.landleaf.ibsaas.client.knight.domain.EmplyType;
import com.landleaf.ibsaas.client.knight.domain.dto.emply.EmplyTypeDTO;
import com.landleaf.ibsaas.client.knight.service.IEmplyTypeService;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmplyTypeService extends AbstractBaseService<EmplyTypeDao, EmplyType> implements IEmplyTypeService<EmplyType> {

    public static final Logger LOGGER = LoggerFactory.getLogger(EmplyTypeService.class);

    @Override
    public List<EmplyTypeDTO> queryAllEmplyType() {
        List<EmplyTypeDTO> result = Lists.newArrayList();
        List<EmplyType> emplyTypes = selectAll();
        if(!CollectionUtils.isEmpty(emplyTypes)){
            result = emplyTypes.stream().map(i -> {
                EmplyTypeDTO emplyTypeDTO = new EmplyTypeDTO();
                BeanUtils.copyProperties(i, emplyTypeDTO);
                return emplyTypeDTO;
            }).collect(Collectors.toList());
        }
        return result;
    }
}
