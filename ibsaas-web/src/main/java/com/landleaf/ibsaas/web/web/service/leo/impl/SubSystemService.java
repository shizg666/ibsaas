package com.landleaf.ibsaas.web.web.service.leo.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.ibsaas.common.domain.leo.SubSystem;
import com.landleaf.ibsaas.common.domain.leo.User;
import com.landleaf.ibsaas.common.service.leo.ICommonSubSystemService;
import com.landleaf.ibsaas.web.web.constant.IbsaasWebConstants;
import com.landleaf.ibsaas.web.web.context.user.UserContext;
import com.landleaf.ibsaas.web.web.dataprovider.IdGenerator;
import com.landleaf.ibsaas.web.web.dto.request.SelectorParamsDto;
import com.landleaf.ibsaas.web.web.dto.response.SelectorResultDto;
import com.landleaf.ibsaas.web.web.exception.SubSystemException;
import com.landleaf.ibsaas.web.web.service.leo.ISubSystemService;
import com.landleaf.ibsaas.web.web.vo.SubSystemQueryVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;


@Service
public class SubSystemService implements ISubSystemService {

    @Autowired
    private ICommonSubSystemService<SubSystem> commonSubSystemService;

    @Autowired
    private IdGenerator idGenerator;

    /**
     * 根据条件查询子系统列表
     *
     * @param queryVO
     * @return
     * @author wyl
     * @date 2017年08月16日19:41:35
     */
    public PageInfo listSubSystems(SubSystemQueryVO queryVO) {
        PageHelper.startPage(queryVO.getPage(), queryVO.getLimit(), true);
        Example example = new Example(SubSystem.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(queryVO.getSystemCode())) {
            criteria.andCondition("system_code=",queryVO.getSystemCode());
        }
        if (!StringUtils.isEmpty(queryVO.getSystemName())) {
            criteria.andCondition("system_name=",queryVO.getSystemName());
        }
        criteria.andCondition("is_active=", IbsaasWebConstants.ACTIVE);
        example.setOrderByClause("create_time desc");
        List<SubSystem> subSystems = commonSubSystemService.selectByExample(example);
        return new PageInfo(subSystems);
    }

    /**
     * 新增子系统
     *
     * @param subSystem
     * @return
     * @author wyl
     * @date 2017年08月17日08:38:16
     */
    public SubSystem addSubSystem(SubSystem subSystem) {
        Date oprTime = new Date();
        String id = idGenerator.nextId();
        subSystem.setId(id);
        subSystem.setCreateTime(oprTime);
        subSystem.setCreateUserCode((((User) UserContext.getCurrentUser()).getUserCode()));
        subSystem.setActive(IbsaasWebConstants.ACTIVE);
        subSystem.setVersionNo(oprTime.getTime());
        commonSubSystemService.save(subSystem);
        SubSystem queryParam = new SubSystem();
        queryParam.setId(subSystem.getId());
        return commonSubSystemService.selectOne(queryParam);
    }

    /**
     * 修改子系统
     *
     * @param subSystem
     * @return
     * @author wyl
     * @date 2017年08月17日08:38:50
     */
    @Transactional
    public SubSystem updateSubSystem(String id, SubSystem subSystem) {
        //先更新老数据为无效
        SubSystem updateEntity = new SubSystem();
        updateEntity.setActive(IbsaasWebConstants.INACTIVE);
        updateEntity.setModifyTime(new Date());
        updateEntity.setModifyUserCode(((User)UserContext.getCurrentUser()).getUserCode());
        Example example = new Example(SubSystem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition("id=",id).andCondition("is_active=",IbsaasWebConstants.ACTIVE);
        int res = commonSubSystemService.updateByExampleSelective(updateEntity,example);
        if(res == 0){
          throw new SubSystemException(SubSystemException.BUSINESS_SYSTEM_CODE_NOT_EXISTS);
        }
        //重新插入
        SubSystem targetSubSystem = new SubSystem();
        BeanUtils.copyProperties(subSystem,targetSubSystem);
        initAddParams(targetSubSystem);
        commonSubSystemService.save(targetSubSystem);
        //返回新插入数据
        SubSystem queryParam = new SubSystem();
        queryParam.setId(targetSubSystem.getId());
        return commonSubSystemService.selectOne(queryParam);
    }

    /**
     * 删除子系统
     *
     * @param id
     * @return
     * @author wyl
     * @date 2017年08月17日08:40:15
     */
    public void deleteSubSystem(String id) {
        Date oprTime = new Date();
        SubSystem updateParam = new SubSystem();
        updateParam.setId(id);
        updateParam.setActive(IbsaasWebConstants.INACTIVE);
        updateParam.setModifyTime(oprTime);
        updateParam.setModifyUserCode((((User)UserContext.getCurrentUser()).getUserCode()));
        updateParam.setVersionNo(oprTime.getTime());
        commonSubSystemService.updateByPrimaryKeySelective(updateParam);
    }

    /**
     * 按id查询子系统对象信息
     *
     * @param id
     * @return
     * @author Dy
     * @createDate 2017/8/22
     */
    public SubSystem querySubSystemById(String id) {
        SubSystem queryParam = new SubSystem();
        queryParam.setId(id);
        return commonSubSystemService.selectByPrimaryKey(queryParam);
    }

    /**
     *提供给系统选择下拉框公共组件
     *@params:
     *@return:
     *@author:wyl
     */
    public List<Map<String, String>> listSystemsToDisplay() {
        List results= Lists.newArrayList();
        SubSystem queryParam = new SubSystem();
        queryParam.setActive(IbsaasWebConstants.ACTIVE);
        List<SubSystem> subSystems = commonSubSystemService.select(queryParam);
        if(!CollectionUtils.isEmpty(subSystems)){
            subSystems.forEach(item->{
                Map<String, String> map= new HashMap<>();
                map.put("value",item.getSystemCode());
                map.put("displayName",item.getSystemName());
                results.add(map);
            });
        }
        return results;
    }

    /**
     * 通用分页模糊查询系统名称列表
     *
     * @param paramsDto
     * @return
     * @author Dy
     * @createDate 2017/8/27
     */
    @Override
    public PageInfo querySubsystemList(SelectorParamsDto paramsDto) {
        PageHelper.startPage(paramsDto.getPage(),paramsDto.getLimit());
        Example example = new Example(SubSystem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition("is_active=",IbsaasWebConstants.ACTIVE);
        if(StringUtils.isNotBlank(paramsDto.getQuery())){
            criteria.andLike("systemName","%" + paramsDto.getQuery() + "%");  //模糊查询
        }
        List<SubSystem> subSystemList = commonSubSystemService.selectByExample(example);
        List<SelectorResultDto> resultList = new ArrayList<>(subSystemList.size());
        if (!CollectionUtils.isEmpty(subSystemList)) {
            subSystemList.forEach((SubSystem item)->{
                SelectorResultDto resultDto = new SelectorResultDto();
                resultDto.setValue(item.getSystemName());
                resultDto.setDisplayName(item.getSystemName());
                resultList.add(resultDto);
            });
        }
        return new PageInfo(resultList);
    }

    @Override
    public List<Map<String, String>> querySubsystemCodeList() {
        List results= Lists.newArrayList();
        SubSystem queryParam = new SubSystem();
        queryParam.setActive(IbsaasWebConstants.ACTIVE);
        List<SubSystem> subSystems = commonSubSystemService.select(queryParam);
        if(!CollectionUtils.isEmpty(subSystems)){
            subSystems.forEach(item->{
                Map<String, String> map= new HashMap<>();
                map.put("value",item.getSystemCode());
                map.put("displayName",item.getSystemCode());
                results.add(map);
            });
        }
        return results;
    }

    private void initAddParams(SubSystem subSystem) {
        String oprUser = ((User) UserContext.getCurrentUser()).getUserCode();
        Date oprTime = new Date();
        subSystem.setId(idGenerator.nextId());
        subSystem.setModifyUserCode(oprUser);
        subSystem.setModifyTime(oprTime);
        subSystem.setActive(IbsaasWebConstants.ACTIVE);
    }
}
