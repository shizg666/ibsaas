package com.landleaf.ibsaas.client.parking.lifang.service;


import com.github.pagehelper.PageInfo;
import com.landleaf.ibsaas.common.domain.parking.request.UserinfoDetailQueryDTO;
import com.landleaf.ibsaas.common.domain.parking.request.UserinfoListQueryDTO;
import com.landleaf.ibsaas.common.domain.parking.response.UserinfoResponseDTO;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

/**
 * 车辆相关操作
 *
 * @param <Userinfo>
 */
public interface IUserinfoService<Userinfo> extends IBaseService<Userinfo> {


    /**
     * 分页查询停车列表
     * @param queryDTO
     * @return
     */
    PageInfo pageQueryList(UserinfoListQueryDTO queryDTO);

    UserinfoResponseDTO queryInfo(UserinfoDetailQueryDTO queryDTO);
}
