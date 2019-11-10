package com.landleaf.ibsaas.client.parking.lifang.service.impl;

import com.google.common.collect.Lists;
import com.landleaf.ibsaas.client.parking.lifang.dao.ChannelDao;
import com.landleaf.ibsaas.client.parking.lifang.domain.Channel;
import com.landleaf.ibsaas.client.parking.lifang.enums.ChannelTypeEnum;
import com.landleaf.ibsaas.client.parking.lifang.service.IChannelService;
import com.landleaf.ibsaas.common.domain.parking.request.ChannelListQueryDTO;
import com.landleaf.ibsaas.common.domain.parking.response.ChannelResponseDTO;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChannelService extends AbstractBaseService<ChannelDao, Channel> implements IChannelService<Channel> {
    @Autowired
    private ChannelDao channelDao;


    @Override
    public List<ChannelResponseDTO> queryChannelByType(ChannelListQueryDTO queryDTO) {
        List<ChannelResponseDTO> result = Lists.newArrayList();
        Example example = new Example(Channel.class);
        Example.Criteria criteria = example.createCriteria();
        String channelType = queryDTO.getChannelType();
        try {
            if(StringUtil.isNotEmpty(channelType)){
                Integer inOrOut = null;
                if(StringUtil.isEquals(channelType, ChannelTypeEnum.IN.name)){
                    inOrOut=ChannelTypeEnum.IN.type;
                }else if(StringUtil.isEquals(channelType,ChannelTypeEnum.OUT.name)){
                    inOrOut=ChannelTypeEnum.OUT.type;
                }
                if(null!=inOrOut){
                    criteria.andCondition("inOrOut=",inOrOut);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Channel> channels = channels = selectByExample(example);
        if (!CollectionUtils.isEmpty(channels)) {
            result = channels.stream().map(channel -> {
                ChannelResponseDTO responseDTO = new ChannelResponseDTO();
                responseDTO.setChannelCode(String.valueOf(channel.getChannelId()));
                responseDTO.setChannelName(channel.getChannelName());
                responseDTO.setUniqueId(String.valueOf(channel.getChannelId()));
                try {
                    responseDTO.setChannelType(ChannelTypeEnum.getInstByType(channel.getInOrOut()).getName());
                } catch (Exception e) {
                    responseDTO.setChannelType(ChannelTypeEnum.IN.name);
                }
                return responseDTO;
            }).collect(Collectors.toList());
        }
        return result;
    }
}
