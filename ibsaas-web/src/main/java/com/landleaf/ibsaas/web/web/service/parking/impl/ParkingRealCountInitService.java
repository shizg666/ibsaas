package com.landleaf.ibsaas.web.web.service.parking.impl;

import com.landleaf.ibsaas.common.dao.parking.ParkingRealCountInitDao;
import com.landleaf.ibsaas.common.domain.leo.User;
import com.landleaf.ibsaas.common.domain.parking.ParkingRealCountInit;
import com.landleaf.ibsaas.common.domain.parking.request.UsercrdtmRealCountQueryDTO;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.common.utils.date.DateUtil;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.constant.IbsaasWebConstants;
import com.landleaf.ibsaas.web.web.constant.MessageConstants;
import com.landleaf.ibsaas.web.web.context.user.UserContext;
import com.landleaf.ibsaas.web.web.dataprovider.IdGenerator;
import com.landleaf.ibsaas.web.web.exception.ParkingException;
import com.landleaf.ibsaas.web.web.service.parking.IParkingRealCountInitService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class ParkingRealCountInitService extends AbstractBaseService<ParkingRealCountInitDao, ParkingRealCountInit> implements IParkingRealCountInitService<ParkingRealCountInit> {
    @Autowired
    private ParkingRealCountInitDao parkingRealCountInitDao;
    @Autowired
    private IdGenerator idGenerator;

    @Override
    public UsercrdtmRealCountQueryDTO queryRealCountInitParams(String clientId) {
        UsercrdtmRealCountQueryDTO result = new UsercrdtmRealCountQueryDTO();

        ParkingRealCountInit query = new ParkingRealCountInit();
        query.setActive(IbsaasWebConstants.ACTIVE);
        query.setClientId(clientId);
        List<ParkingRealCountInit> parkingRealCountInits = select(query);
        ParkingRealCountInit init = new ParkingRealCountInit();
        if (CollectionUtils.isEmpty(parkingRealCountInits)) {
            //初始插入一条
            init.setTotal(19);
            init.setResetTime(DateUtil.format(DateUtil.getDateByThatDayFirstMilliSecond(new Date())));
            init.setOccupyCount(0);
            init.setRemainCount(19);
            init.setClientId(clientId);
            //新增初始化参数
            Date oprTime = new Date();
            String id = idGenerator.nextId();
            init.setId(id);
            init.setCreateTime(oprTime);
            init.setCreateUserCode("1");
            init.setActive(IbsaasWebConstants.ACTIVE);
            init.setVersionNo(oprTime.getTime());
            save(init);
        } else {
            init = parkingRealCountInits.get(0);
        }
        BeanUtils.copyProperties(init, result);
        return result;
    }

    @Override
    public ParkingRealCountInit updateRealCountInitParams(UsercrdtmRealCountQueryDTO dto) {

        ParkingRealCountInit query = new ParkingRealCountInit();
        query.setId(dto.getId());
        ParkingRealCountInit exist = selectOne(query);
        if (exist == null) {
            throw new ParkingException(ParkingException.PARKINGREALCOUNTINIT_UPDATE_NOT_EXISTS);
        }
        //直接在原数据基础上更新
        ParkingRealCountInit update = new ParkingRealCountInit();
        BeanUtils.copyProperties(dto,update);

        update.setId(exist.getId());
        update.setActive(exist.getActive());
        Date oprTime = new Date();
        update.setVersionNo(oprTime.getTime());
        update.setCreateTime(exist.getCreateTime());
        update.setCreateUserCode(exist.getCreateUserCode());
        update.setModifyTime(oprTime);
        update.setModifyUserCode(((User) UserContext.getCurrentUser()).getUserCode());

        Example example = new Example(ParkingRealCountInit.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition("id=", exist.getId());
        int res = updateByExampleSelective(update, example);
        if (res == 0) {
            throw new ParkingException(ParkingException.PARKINGREALCOUNTINIT_UPDATE_NOT_EXISTS);
        }
        //返回新插入数据
        ParkingRealCountInit queryParam = new ParkingRealCountInit();
        queryParam.setId(update.getId());
        return selectOne(queryParam);
    }

    @Override
    public ParkingRealCountInit saveRealCountInitParams(UsercrdtmRealCountQueryDTO dto) {
        //校验必须参数
        if (dto == null
                || StringUtil.isEmpty(dto.getResetTime())) {
            throw new BusinessException(MessageConstants.COMMON_ADD_RESOURCE_BADREQUEST);
        }
        //新增初始化参数
        Date oprTime = new Date();
        String id = idGenerator.nextId();
        ParkingRealCountInit save = new ParkingRealCountInit();
        BeanUtils.copyProperties(dto, save);
        save.setId(id);
        save.setCreateTime(oprTime);
        save.setCreateUserCode(((User) UserContext.getCurrentUser()).getUserCode());
        save.setActive(IbsaasWebConstants.ACTIVE);
        save.setVersionNo(oprTime.getTime());
        save(save);
        return selectOne(save);
    }

}
