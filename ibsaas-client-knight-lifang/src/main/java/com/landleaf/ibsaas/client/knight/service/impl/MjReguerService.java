package com.landleaf.ibsaas.client.knight.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.ibsaas.client.knight.service.IMjReguerService;
import com.landleaf.ibsaas.common.dao.knight.MjReguerDao;
import com.landleaf.ibsaas.common.domain.knight.control.*;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MjReguerService extends AbstractBaseService<MjReguerDao, MjReguser> implements IMjReguerService<MjReguser> {

    public static final Logger LOGGER = LoggerFactory.getLogger(MjReguerService.class);

    //注册人员
    @Override
    public int registeruser(RegisterUserByDbDTO requestBody) {
        MjReguser mjReguser = new MjReguser();
        BeanUtils.copyProperties(requestBody, mjReguser);
        mjReguser.setGroupId(1);
        mjReguser.setDevType(0);//0 门禁设备 1 人脸设备
        mjReguser.setOperationType(1);//0正常，1挂失
        mjReguser.setUpdateTime(new Date());
        boolean isExist = checkUnique(mjReguser.getId(), mjReguser.getSysNo(), mjReguser.getDoorId(), mjReguser.getDeviceId());
        if (isExist) {
//            throw new BusinessException("该用户已经存在此权限!");
            return 0;
        }
        int count = save(mjReguser);
        return count;

    }

    //解除权限
    @Override
    public int unregisteruser(UnRegisterUserByDbDTO requestBody) {

        Integer id = requestBody.getId();
        MjReguser queryMjReguser = new MjReguser();
        queryMjReguser.setId(id);
        MjReguser existMjReguser = selectOne(queryMjReguser);
        if (existMjReguser == null) {
//            throw new BusinessException("权限不存在!");
            return 0;
        }
        Example example = new Example(MjReguser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition("id=", id);
        return deleteByExample(example);
    }

    @Override
    public PageInfo queryRegisteruserByDb(QueryRegisterUserByDbDTO requestBody) {
        List<MjReguser> result = Lists.newArrayList();
        PageHelper.startPage(requestBody.getCurPage(), requestBody.getPageSize(), true);
        Example example = new Example(MjReguser.class);
        Example.Criteria criteria = example.createCriteria();

        Integer id = requestBody.getId();
        Integer deviceId = requestBody.getDeviceId();
        Integer doorId = requestBody.getDoorId();
        Integer sysNo = requestBody.getSysNo();
        //权限ID
        if (id != null) {
            criteria.andCondition("id=", id);
        }
        //设备ID
        if (deviceId != null) {
            criteria.andCondition("device_id=", deviceId);
        }
        //门ID
        if (doorId != null) {
            criteria.andCondition("door_id=", doorId);
        }
        //用户编号
        if (sysNo != null) {
            criteria.andCondition("sys_no=", sysNo);
        }
        example.setOrderByClause("update_time desc");
        List<MjReguser> mjRegusers = selectByExample(example);
        if (CollectionUtils.isEmpty(mjRegusers)) {
            mjRegusers = Lists.newArrayList();
        }
        PageInfo pageInfo = new PageInfo(mjRegusers);
        if (!CollectionUtils.isEmpty(mjRegusers)) {
            result.addAll(mjRegusers);
            pageInfo.setList(result);
        }
        return pageInfo;
    }


    //校验该用户该设备下权限是否已经存在
    private boolean checkUnique(Integer id, Integer sysNo, Integer doorId, Integer deviceId) {
        Example example = new Example(MjReguser.class);
        Example.Criteria criteria = example.createCriteria();
        List<Integer> ids = Lists.newArrayList();
        if (id != null && id.intValue() > 0) {
            ids.add(id);
            criteria.andNotIn("id", ids);
        }
        criteria.andCondition("sys_no=", sysNo);
        criteria.andCondition("door_id=", doorId);
        criteria.andCondition("device_id=", deviceId);
        return selectCountByExample(example) > 0;

    }
}
