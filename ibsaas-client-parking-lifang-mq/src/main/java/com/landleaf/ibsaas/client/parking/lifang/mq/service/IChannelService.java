package com.landleaf.ibsaas.client.parking.lifang.mq.service;


import com.landleaf.ibsaas.common.domain.parking.request.ChannelListQueryDTO;
import com.landleaf.ibsaas.common.domain.parking.response.ChannelResponseDTO;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

import java.util.List;

/**
 * 通道相关操作
 *
 * @param <Channel>
 */
public interface IChannelService<Channel> extends IBaseService<Channel> {

    List<ChannelResponseDTO> queryChannelByType(ChannelListQueryDTO queryDTO);
}
