package com.landleaf.ibsaas.client.knight.processor.knight;

import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.client.knight.domain.dto.KnightResponse;
import com.landleaf.ibsaas.client.knight.domain.dto.depart.AddDepartDTO;
import com.landleaf.ibsaas.client.knight.domain.dto.depart.DeleteDepartDTO;
import com.landleaf.ibsaas.client.knight.domain.dto.depart.QueryDepartDTO;
import com.landleaf.ibsaas.client.knight.processor.LiFangHttpProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息处理类
 */
@Component
public class KnightDepartMsgProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnightDepartMsgProcess.class);

    @Autowired
    private LiFangHttpProvider liFangHttpProvider;

    /**
     * 分页查询部门
     * @param requestBody
     * @return
     */
    public KnightResponse queryDepart(QueryDepartDTO requestBody) {
        LOGGER.info("收到【查询部门】请求,{}", JSON.toJSONString(requestBody));
        String param = JSON.toJSONString(requestBody);
        KnightResponse knightResponse = liFangHttpProvider.queryDepart(param);
        //返回数据
        return knightResponse;
    }
    /**
     * 添加部门
     * @param requestBody
     * @return
     */
    public KnightResponse addDepart(AddDepartDTO requestBody) {
        LOGGER.info("收到【添加部门】请求,{}", JSON.toJSONString(requestBody));
        String param = JSON.toJSONString(requestBody);
        KnightResponse knightResponse = liFangHttpProvider.addDepart(param);
        //返回数据
        return knightResponse;
    }
    /**
     * 删除部门
     * @param requestBody
     * @return
     */
    public KnightResponse deleteDepart(DeleteDepartDTO requestBody) {
        LOGGER.info("收到【删除部门】请求,{}", JSON.toJSONString(requestBody));
        String param = JSON.toJSONString(requestBody);
        KnightResponse knightResponse = liFangHttpProvider.deleteDepart(param);
        //返回数据
        return knightResponse;
    }

}
