package com.landleaf.ibsaas.client.knight.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.ibsaas.client.knight.dao.KQCardRecordDao;
import com.landleaf.ibsaas.client.knight.domain.KQCardRecord;
import com.landleaf.ibsaas.client.knight.domain.dto.control.QueryMjDoorOpenRecordDTO;
import com.landleaf.ibsaas.client.knight.service.IKQCardRecordService;
import com.landleaf.ibsaas.client.knight.utils.date.DateUtils;
import com.landleaf.ibsaas.client.knight.utils.string.StringUtil;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class KQCardRecordService extends AbstractBaseService<KQCardRecordDao, KQCardRecord> implements IKQCardRecordService<KQCardRecord> {

    public static final Logger LOGGER = LoggerFactory.getLogger(KQCardRecordService.class);

    @Override
    public PageInfo mjOpenDoorRecordByDb(QueryMjDoorOpenRecordDTO requestBody) {
        List<KQCardRecord> result = Lists.newArrayList();
        PageHelper.startPage(requestBody.getCurPage(), requestBody.getPageSize(), true);
        Example example = new Example(KQCardRecord.class);
        Example.Criteria criteria = example.createCriteria();

        String start = requestBody.getStart();
        String end = requestBody.getEnd();
        
        //开始时间
        if (StringUtil.isNotEmpty(start)) {
            Date startDate = DateUtils.convert(start);
            criteria.andGreaterThanOrEqualTo("cardTime",startDate);
        }
        //结束时间
        if (StringUtil.isNotEmpty(end)) {
            Date endDate = DateUtils.convert(end);
            criteria.andLessThan("cardTime", endDate);
        }
        example.setOrderByClause("update_time desc");
        List<KQCardRecord> kqCardRecords = selectByExample(example);
        if (CollectionUtils.isEmpty(kqCardRecords)) {
            kqCardRecords = Lists.newArrayList();
        }
        PageInfo pageInfo = new PageInfo(kqCardRecords);
        if (!CollectionUtils.isEmpty(kqCardRecords)) {
            result.addAll(kqCardRecords);
            pageInfo.setList(result);
        }
        return pageInfo;
    }
}
