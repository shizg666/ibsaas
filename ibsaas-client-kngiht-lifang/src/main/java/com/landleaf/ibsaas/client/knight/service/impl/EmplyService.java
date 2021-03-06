package com.landleaf.ibsaas.client.knight.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.ibsaas.client.knight.dao.EmplyDao;
import com.landleaf.ibsaas.client.knight.domain.Emply;
import com.landleaf.ibsaas.client.knight.domain.dto.KnightResponse;
import com.landleaf.ibsaas.client.knight.domain.dto.emply.DeleteCardDTO;
import com.landleaf.ibsaas.client.knight.domain.dto.emply.QueryEmplyDTO;
import com.landleaf.ibsaas.client.knight.domain.dto.emply.RecycleCardDTO;
import com.landleaf.ibsaas.client.knight.processor.LiFangHttpProvider;
import com.landleaf.ibsaas.client.knight.processor.knight.KnightEmplyMsgProcess;
import com.landleaf.ibsaas.client.knight.service.IEmplyService;
import com.landleaf.ibsaas.client.knight.utils.string.StringUtil;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class EmplyService extends AbstractBaseService<EmplyDao, Emply> implements IEmplyService<Emply> {

    public static final Logger LOGGER = LoggerFactory.getLogger(EmplyService.class);

    @Autowired
    private LiFangHttpProvider liFangHttpProvider;
    @Autowired
    private KnightEmplyMsgProcess knightEmplyMsgProcess;
    @Override
    public PageInfo selectEmply(QueryEmplyDTO requestBody) {
        List<Emply> result = Lists.newArrayList();
        PageHelper.startPage(requestBody.getCurPage(), requestBody.getPageSize(), true);
        Example example = new Example(Emply.class);
        Example.Criteria criteria = example.createCriteria();

        Integer sysNo = requestBody.getSysNo();
        String employeeName = requestBody.getEmployeeName();
        //用户编号
        if (sysNo != null) {
            criteria.andCondition("sys_no=", sysNo);
        }
        //用户名称
        if (StringUtil.isNotEmpty(employeeName)) {
            criteria.andLike("employeeName","%"+employeeName+"%");
        }
        criteria.andCondition("is_delete=",0);
        example.setOrderByClause("sys_no desc");
        List<Emply> emplies = selectByExample(example);
        if (CollectionUtils.isEmpty(emplies)) {
            emplies = Lists.newArrayList();
        }
        PageInfo pageInfo = new PageInfo(emplies);
        if (!CollectionUtils.isEmpty(emplies)) {
            result.addAll(emplies);
            pageInfo.setList(result);
        }
        return pageInfo;
    }

    @Override
    public int deleteEmply(Integer sysNo) {
        Emply emply = new Emply();
        emply.setSysNo(sysNo);
        Emply existEmply = selectOne(emply);
        if(existEmply==null){
            return 0;
        }
        String serial = existEmply.getSerial();
        if(StringUtil.isNotEmpty(serial)){
            try {
                //先删除卡
                DeleteCardDTO deleteCardDTO = new DeleteCardDTO();
                deleteCardDTO.setSysNo(sysNo);
                KnightResponse deleteResponse = knightEmplyMsgProcess.deleteCard(deleteCardDTO);
                LOGGER.info("删卡结果:{}", JSON.toJSONString(deleteResponse));
                //再回收卡
                RecycleCardDTO recycleCardDTO = new RecycleCardDTO();
                recycleCardDTO.setSerial(serial);
                KnightResponse recycleResponse = knightEmplyMsgProcess.recycleCard(recycleCardDTO);
                LOGGER.info("回收卡结果:{}", JSON.toJSONString(recycleResponse));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Example example = new Example(Emply.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition("sys_no=",sysNo);
        Emply updateEmply = new Emply();
        updateEmply.setSysNo(sysNo);
        updateEmply.setDeleted(1);
        return updateByExampleSelective(updateEmply,example);
    }
}
