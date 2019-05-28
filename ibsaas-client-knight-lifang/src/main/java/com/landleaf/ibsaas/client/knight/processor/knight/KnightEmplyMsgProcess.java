package com.landleaf.ibsaas.client.knight.processor.knight;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.landleaf.ibsaas.client.knight.domain.dto.KnightResponse;
import com.landleaf.ibsaas.client.knight.domain.dto.emply.*;
import com.landleaf.ibsaas.client.knight.processor.LiFangHttpProvider;
import com.landleaf.ibsaas.client.knight.service.IEmplyService;
import com.landleaf.ibsaas.client.knight.service.IEmplyTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 人员相关操作
 */
@Component
public class KnightEmplyMsgProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnightEmplyMsgProcess.class);

    @Autowired
    private LiFangHttpProvider liFangHttpProvider;
    @Autowired
    private IEmplyService emplyService;
    @Autowired
    private IEmplyTypeService emplyTypeService;

    /**
     * 查询全部人员
     *
     * @param requestBody
     * @return
     */
    public KnightResponse getAllEmplyList(QueryEmplyDTO requestBody) {
        LOGGER.info("收到【查询人部人员】请求,{}", JSON.toJSONString(requestBody));
        String param = JSON.toJSONString(requestBody);
        KnightResponse knightResponse = liFangHttpProvider.getAllEmplyList(param);
        //返回数据
        return knightResponse;
    }

    /**
     * 查询人员类型--通过数据库
     *
     * @param requestBody
     * @return
     */
    public KnightResponse queryAllEmplyType(EmplyTypeDTO requestBody) {
        LOGGER.info("收到【查询人员类型】请求,{}", JSON.toJSONString(requestBody));
        String param = JSON.toJSONString(requestBody);
        KnightResponse knightResponse = new KnightResponse();
        List<EmplyTypeDTO> data= emplyTypeService.queryAllEmplyType();
        knightResponse.setObj(data);
        knightResponse.setResult("200");
        knightResponse.setResultInfo("操作成功");
        //返回数据
        return knightResponse;
    }
    /**
     * 分页查询人员--通过数据库
     *
     * @param requestBody
     * @return
     */
    public KnightResponse selectEmply(QueryEmplyDTO requestBody) {
        LOGGER.info("收到【查询人员】请求,{}", JSON.toJSONString(requestBody));
        String param = JSON.toJSONString(requestBody);
        KnightResponse knightResponse = new KnightResponse();
        PageInfo pageInfo= emplyService.selectEmply(requestBody);


        knightResponse.setObj(pageInfo);
        knightResponse.setResult("200");
        knightResponse.setResultInfo("操作成功");
        //返回数据
        return knightResponse;
    }

    /**
     * 添加人员
     *
     * @param requestBody
     * @return
     */
    public KnightResponse addEmply(AddEmplyDTO requestBody) {
        LOGGER.info("收到【添加人员】请求,{}", JSON.toJSONString(requestBody));
        String param = JSON.toJSONString(requestBody);
        KnightResponse knightResponse = liFangHttpProvider.addEmply(param);
        //返回数据
        return knightResponse;
    }

    /**
     * 删除人员
     *
     * @param requestBody
     * @return
     */
    public KnightResponse deleteEmplyByInterface(DeleteEmplyDTO requestBody) {
        LOGGER.info("收到【删除人员】请求,{}", JSON.toJSONString(requestBody));
        String param = JSON.toJSONString(requestBody);
        KnightResponse knightResponse = liFangHttpProvider.deleteEmply(param);
        //返回数据
        return knightResponse;
    }
    /**
     * 删除人员---走数据库自定义流程
     * 1、若有卡先删除卡，再回收卡
     * 2、无卡直接删除
     *
     * @param requestBody
     * @return
     */
    public KnightResponse deleteEmply(DeleteEmplyDTO requestBody) {
        LOGGER.info("收到【删除人员】请求,{}", JSON.toJSONString(requestBody));
        String param = JSON.toJSONString(requestBody);
        KnightResponse knightResponse = new KnightResponse();
        int count=emplyService.deleteEmply(Integer.parseInt(requestBody.getSysNo()));
        knightResponse.setResult("200");
        knightResponse.setResultInfo("操作成功");
        //返回数据
        return knightResponse;
    }
    /**
     * 发卡
     *
     * @param requestBody
     * @return
     */
    public KnightResponse sendCard(SendCardDTO requestBody) {
        LOGGER.info("收到【发卡】请求,{}", JSON.toJSONString(requestBody));
        Map<String,Object> map = Maps.newHashMap();
        map.put("SysNo",requestBody.getSysNo());
        map.put("Serial",requestBody.getSerial());
        String param = JSON.toJSONString(map);
        KnightResponse knightResponse = liFangHttpProvider.sendCard(param);
        //返回数据
        return knightResponse;
    }
    /**
     * 删卡
     *
     * @param requestBody
     * @return
     */
    public KnightResponse deleteCard(DeleteCardDTO requestBody) {
        LOGGER.info("收到【删卡】请求,{}", JSON.toJSONString(requestBody));

        Map<String,Object> map = Maps.newHashMap();
        map.put("SysNo",requestBody.getSysNo());
        String param = JSON.toJSONString(map);
        KnightResponse knightResponse = liFangHttpProvider.deleteCard(param);
        //返回数据
        return knightResponse;
    }
    /**
     * 回收卡
     *
     * @param requestBody
     * @return
     */
    public KnightResponse recycleCard(RecycleCardDTO requestBody) {
        LOGGER.info("收到【回收卡】请求,{}", JSON.toJSONString(requestBody));

        Map<String,Object> map = Maps.newHashMap();
        map.put("Serial",requestBody.getSerial());
        String param = JSON.toJSONString(map);
        KnightResponse knightResponse = liFangHttpProvider.recycleCard(param);
        //返回数据
        return knightResponse;
    }
    /**
     * 修改人员
     *
     * @param requestBody
     * @return
     */
    public KnightResponse updateEmply(UpdateEmplyDTO requestBody) {
        LOGGER.info("收到【修改人员】请求,{}", JSON.toJSONString(requestBody));
        String param = JSON.toJSONString(requestBody);
        KnightResponse knightResponse = liFangHttpProvider.updateEmply(param);
        //返回数据
        return knightResponse;
    }

}
