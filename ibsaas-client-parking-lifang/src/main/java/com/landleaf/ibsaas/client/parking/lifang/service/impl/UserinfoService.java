package com.landleaf.ibsaas.client.parking.lifang.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.ibsaas.client.parking.lifang.dao.UserinfoDao;
import com.landleaf.ibsaas.client.parking.lifang.domain.Chargerule;
import com.landleaf.ibsaas.client.parking.lifang.domain.Userinfo;
import com.landleaf.ibsaas.client.parking.lifang.enums.ExpireStatusEnum;
import com.landleaf.ibsaas.client.parking.lifang.service.IChargeruleService;
import com.landleaf.ibsaas.client.parking.lifang.service.IUserinfoService;
import com.landleaf.ibsaas.common.domain.parking.request.UserinfoDetailQueryDTO;
import com.landleaf.ibsaas.common.domain.parking.request.UserinfoListQueryDTO;
import com.landleaf.ibsaas.common.domain.parking.response.UserinfoResponseDTO;
import com.landleaf.ibsaas.common.utils.date.DateUtil;
import com.landleaf.ibsaas.common.utils.date.DateUtils;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserinfoService extends AbstractBaseService<UserinfoDao, Userinfo> implements IUserinfoService<Userinfo> {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserinfoDao userinfoDao;
    @Autowired
    private IChargeruleService chargeruleService;

    @Override
    public PageInfo pageQueryList(UserinfoListQueryDTO queryDTO) {
        List<UserinfoResponseDTO> result = Lists.newArrayList();
        PageHelper.startPage(queryDTO.getPage(), queryDTO.getLimit(), true);
        Example example = new Example(Userinfo.class);
        Example.Criteria criteria = example.createCriteria();

        //车牌号码
        if (!StringUtils.isEmpty(queryDTO.getCarCode())) {
            criteria.andLike("carCode", "%" + queryDTO.getCarCode() + "%");
        }
        //车主姓名
        if (!StringUtils.isEmpty(queryDTO.getUserName())) {
            criteria.andLike("userName","%" + queryDTO.getUserName() + "%");
        }
        //收费类型
        if (!StringUtils.isEmpty(queryDTO.getChargeTypeCode())) {
            criteria.andCondition("chargeRuleID=", queryDTO.getChargeTypeCode());
        }
        //到期时间
        if (!StringUtils.isEmpty(queryDTO.getExpireTime())) {
            String expireTime = queryDTO.getExpireTime();
            Date expireDate = DateUtils.strToDate(expireTime);
            criteria.andLessThanOrEqualTo("enddt", expireDate);
        }
        //到期状态
        if (!StringUtil.isEmpty(queryDTO.getExpireStatus())) {
            if (StringUtil.isEquals(ExpireStatusEnum.UNEXPIRED.getName(), queryDTO.getExpireStatus())) {
                //未过期
                criteria.andLessThanOrEqualTo("bgndt", new Date());
                criteria.andGreaterThanOrEqualTo("enddt", new Date());
            } else if (StringUtil.isEquals(ExpireStatusEnum.EXPIRED.getName(), queryDTO.getExpireStatus())) {
                criteria.andLessThanOrEqualTo("enddt", new Date());
            }
        }
        example.setOrderByClause("Enddt desc");
        List<Userinfo> userinfos = selectByExample(example);
        if (CollectionUtils.isEmpty(userinfos)) {
            userinfos = Lists.newArrayList();
        }

        PageInfo pageInfo = new PageInfo(userinfos);
        if (!CollectionUtils.isEmpty(userinfos)) {

            List<Chargerule> chargerules = chargeruleService.selectAll();
            Map<Integer, List<Chargerule>> chargeruleMap = Maps.newHashMap();
            if(!CollectionUtils.isEmpty(chargerules)){
               chargeruleMap = chargerules.stream().collect(Collectors.groupingBy(Chargerule::getChargeRuleId));
            }
            Map<Integer, List<Chargerule>> finalChargeruleMap = chargeruleMap;
            result = userinfos.stream().map(userinfo -> {
                UserinfoResponseDTO userinfoResponseDTO = new UserinfoResponseDTO();
                userinfoResponseDTO.setUniqueId(String.valueOf(userinfo.getRecordId()));
                userinfoResponseDTO.setBrand(userinfo.getCarLabel());
                userinfoResponseDTO.setCarCode(userinfo.getCarCode());
                userinfoResponseDTO.setColour(userinfo.getCarColor());
                userinfoResponseDTO.setContact(userinfo.getUserTel());
                try {
                    userinfoResponseDTO.setExpireStatus(ExpireStatusEnum.computedStatus(userinfo.getEnddt()));
                } catch (Exception e) {
                    log.error(e.getMessage(),e);
                }
                try {
                    userinfoResponseDTO.setExpireTime(DateUtil.format(userinfo.getEnddt()));
                } catch (Exception e) {
                    log.error(e.getMessage(),e);
                }
                userinfoResponseDTO.setUserName(userinfo.getUserName());
                List<Chargerule> tempRule = finalChargeruleMap.get(userinfo.getChargeRuleID());
                if(!CollectionUtils.isEmpty(tempRule)){
                    userinfoResponseDTO.setChargeTypeCode(String.valueOf(tempRule.get(0).getChargeRuleId()));
                    userinfoResponseDTO.setChargeTypeName(tempRule.get(0).getChargeRuleName());
                }
                return userinfoResponseDTO;
            }).collect(Collectors.toList());
            pageInfo.setList(result);
        }
        return pageInfo;
    }

    @Override
    public UserinfoResponseDTO queryInfo(UserinfoDetailQueryDTO queryDTO) {
        UserinfoResponseDTO result = new UserinfoResponseDTO();
        String uniqueId = queryDTO.getUniqueId();
        if(StringUtil.isInteger(uniqueId)){
            Userinfo queryRecord = new Userinfo();
            queryRecord.setRecordId(Integer.parseInt(uniqueId));
            List<Userinfo> queryList = select(queryRecord);
            if(!CollectionUtils.isEmpty(queryList)){
                List<Chargerule> chargerules = chargeruleService.selectAll();
                Map<Integer, List<Chargerule>> chargeruleMap = Maps.newHashMap();
                if(!CollectionUtils.isEmpty(chargerules)){
                    chargeruleMap = chargerules.stream().collect(Collectors.groupingBy(Chargerule::getChargeRuleId));
                }
                Map<Integer, List<Chargerule>> finalChargeruleMap = chargeruleMap;
                Userinfo tempUserinfo = queryList.get(0);
                result.setUniqueId(String.valueOf(tempUserinfo.getRecordId()));
                result.setBrand(tempUserinfo.getCarLabel());
                result.setCarCode(tempUserinfo.getCarCode());
                result.setColour(tempUserinfo.getCarColor());
                result.setContact(tempUserinfo.getUserTel());
                try {
                    result.setExpireStatus(ExpireStatusEnum.computedStatus(tempUserinfo.getEnddt()));
                } catch (Exception e) {
                    log.error(e.getMessage(),e);
                }
                try {
                    result.setExpireTime(DateUtil.format(tempUserinfo.getEnddt()));
                } catch (Exception e) {
                    log.error(e.getMessage(),e);
                }
                result.setUserName(tempUserinfo.getUserName());
                result.setRemark(tempUserinfo.getRemark());
                List<Chargerule> tempRule = finalChargeruleMap.get(tempUserinfo.getChargeRuleID());
                if(!CollectionUtils.isEmpty(tempRule)){
                    result.setChargeTypeCode(String.valueOf(tempRule.get(0).getChargeRuleId()));
                    result.setChargeTypeName(tempRule.get(0).getChargeRuleName());
                }
            }
        }
        return result;
    }

}
