package com.landleaf.ibsaas.client.knight.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.ibsaas.client.knight.service.IEmplyService;
import com.landleaf.ibsaas.common.dao.knight.EmplyDao;
import com.landleaf.ibsaas.common.domain.knight.control.MjReguser;
import com.landleaf.ibsaas.common.domain.knight.emply.Emply;
import com.landleaf.ibsaas.common.domain.knight.emply.QueryEmplyDTO;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class EmplyService extends AbstractBaseService<EmplyDao, Emply> implements IEmplyService<Emply> {

    public static final Logger LOGGER = LoggerFactory.getLogger(EmplyService.class);


    @Override
    public PageInfo selectEmply(QueryEmplyDTO requestBody) {
        List<Emply> result = Lists.newArrayList();
        PageHelper.startPage(requestBody.getCurPage(), requestBody.getPageSize(), true);
        Example example = new Example(MjReguser.class);
        Example.Criteria criteria = example.createCriteria();

        Integer sysNo = requestBody.getSysNo();
        String employeeName = requestBody.getEmployeeName();
        //用户编号
        if (sysNo != null) {
            criteria.andCondition("sys_no=", sysNo);
        }
        //用户名称
        if (StringUtil.isNotEmpty(employeeName)) {
            criteria.andCondition("employee_name=", employeeName);
        }
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
}
