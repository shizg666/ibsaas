package com.landleaf.ibsaas.web.web.service.leo.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.landleaf.ibsaas.common.dao.leo.RoleDao;
import com.landleaf.ibsaas.common.dao.leo.RoleResourceDao;
import com.landleaf.ibsaas.common.dao.leo.UserRoleDao;
import com.landleaf.ibsaas.common.domain.leo.*;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.common.service.leo.ICommonRoleResourceService;
import com.landleaf.ibsaas.common.service.leo.ICommonRoleService;
import com.landleaf.ibsaas.common.service.leo.ICommonSubSystemService;
import com.landleaf.ibsaas.common.service.leo.ICommonUserRoleService;
import com.landleaf.ibsaas.web.web.constant.IbsaasWebConstants;
import com.landleaf.ibsaas.web.web.constant.MessageConstants;
import com.landleaf.ibsaas.web.web.context.user.UserContext;
import com.landleaf.ibsaas.web.web.dataprovider.IdGenerator;
import com.landleaf.ibsaas.web.web.dto.request.SelectorParamsDto;
import com.landleaf.ibsaas.web.web.dto.response.SelectorResultDto;
import com.landleaf.ibsaas.web.web.exception.RoleException;
import com.landleaf.ibsaas.web.web.service.leo.IRoleService;
import com.landleaf.ibsaas.web.web.vo.RoleQueryInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private ICommonUserRoleService commonUserRoleService;
    @Autowired
    private ICommonRoleService<Role> commonRoleService;
    @Autowired
    private ICommonRoleResourceService<RoleResource> commonRoleResourceService;
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleResourceDao roleResourceDao;
    @Autowired
    private ICommonSubSystemService<SubSystem> commonSubSystemService;

    @Autowired
    private UserRoleDao userRoleDao;

    public List<Role> listUserRoles(UserRole userRole) {
        return commonUserRoleService.listUserRoles(userRole);
    }

    public List<Role> listSystemRoles(String systemCode) {
        return userRoleDao.listSystemRoles(systemCode);
    }

    @Override
    public PageInfo queryRole(SelectorParamsDto selectorParamsDto) {
        PageHelper.startPage(selectorParamsDto.getPage(), selectorParamsDto.getLimit(), true);
        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition("is_active=", IbsaasWebConstants.ACTIVE);
        if (StringUtils.isNotBlank(selectorParamsDto.getQuery())) {
            criteria.andLike("roleName", "%" + selectorParamsDto.getQuery() + "%"); //模糊查询
        }
        //渲染选择器使用
        List<Role> roles = commonRoleService.selectByExample(example);
        List<SelectorResultDto> selectorResultDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roles)) {
            roles.forEach((Role item) -> {
                SelectorResultDto vo = new SelectorResultDto();
                vo.setValue(item.getRoleName());
                vo.setDisplayName(item.getRoleName());
                selectorResultDtos.add(vo);
            });
        }
        return new PageInfo(selectorResultDtos);
    }

    @Override
    public PageInfo listRoleQueryInfo(RoleQueryInfoVO roleQueryInfo) {
        List<RoleQueryInfoVO> roleQueryInfos = Lists.newArrayList();
        PageHelper.startPage(roleQueryInfo.getPage(), roleQueryInfo.getLimit(), true);
        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();
        //所属系统编码
        if (!StringUtils.isEmpty(roleQueryInfo.getBelongSystem())) {
            criteria.andCondition("belong_system=", roleQueryInfo.getBelongSystem());
        }
        //角色编码
        if (!StringUtils.isEmpty(roleQueryInfo.getRoleCode())) {
            criteria.andCondition("role_code=", roleQueryInfo.getRoleCode());
        }
        //角色名称
        if (!StringUtils.isEmpty(roleQueryInfo.getRoleName())) {
            criteria.andCondition("role_name=", roleQueryInfo.getRoleName());
        }
        criteria.andCondition("is_active=", IbsaasWebConstants.ACTIVE);
        example.setOrderByClause("create_time desc");
        List<Role> roles = commonRoleService.selectByExample(example);
        //根据对应所属系统编码封装所属系统名称到返回对象
        achieveSystemName(roles, roleQueryInfos);
        return new PageInfo(roleQueryInfos);
    }

    private void achieveSystemName(List<Role> roles, List<RoleQueryInfoVO> roleQueryInfos) {
        SubSystem querySystemParam = new SubSystem();
        querySystemParam.setActive(IbsaasWebConstants.ACTIVE);
        List<SubSystem> subSystems = commonSubSystemService.select(querySystemParam);
        Map<String, String> systemMap = Maps.newHashMap();
        if (!CollectionUtils.isEmpty(subSystems)) {
            subSystems.forEach(item -> systemMap.put(item.getSystemCode(), item.getSystemName()));
        }
        if (!CollectionUtils.isEmpty(roles)) {
            roles.forEach(item -> {
                RoleQueryInfoVO target = new RoleQueryInfoVO();
                BeanUtils.copyProperties(item, target);
                target.setSystemName(systemMap.get(item.getBelongSystem()) == null ? "" : systemMap.get(item.getBelongSystem()));
                roleQueryInfos.add(target);
            });
        }
    }

    @Override
    @Transactional
    public Role addRole(Role role) {
        //校验必需参数
        if (role == null || StringUtils.isBlank(role.getRoleCode()) || StringUtils.isBlank(role.getRoleName()))
            throw new BusinessException(MessageConstants.COMMON_ADD_ROLE_BADREQUEST);
        Role existRole = findRoleByCode(role.getRoleCode(), role.getBelongSystem());
        //校验同一系统下角色编码唯一性
        if (existRole != null) throw new BusinessException(MessageConstants.COMMON_ADD_ROLE_CONFLICT);
        Date oprTime = new Date();
        String id = idGenerator.nextId();
        role.setId(id);
        role.setCreateTime(oprTime);
        role.setCreateUserCode(((User) UserContext.getCurrentUser()).getUserCode());
        role.setActive(IbsaasWebConstants.ACTIVE);
        commonRoleService.save(role);
        return role;
    }

    @Override
    public Role queryRoleById(String id) {
        Role queryParam = new Role();
        queryParam.setId(id);
        return commonRoleService.selectByPrimaryKey(queryParam);
    }

    @Override
    @Transactional
    public Role updateRole(String id, Role role) {
        //更新先删除
        Role updateRole = new Role();
        initUpdateParams(updateRole);
        updateRole.setId(role.getId());
        updateRole.setActive(IbsaasWebConstants.INACTIVE);

        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition("is_active=", IbsaasWebConstants.ACTIVE).andCondition("id=", id);
        int res = commonRoleService.updateByExampleSelective(updateRole, example);
        if (res == 0) {
            throw new RoleException(RoleException.BUSINESS_ROLE_UPDATE_ROLE_NOT_EXISTS);
        }
        //重新插入
        Role targetRole = new Role();
        BeanUtils.copyProperties(role, targetRole);
        Date oprtime = new Date();
        targetRole.setActive(IbsaasWebConstants.ACTIVE);
        targetRole.setId(idGenerator.nextId());
        targetRole.setModifyUserCode(((User) UserContext.getCurrentUser()).getUserCode());
        targetRole.setModifyTime(oprtime);
        commonRoleService.save(targetRole);
        //返回新插入数据
        Role queryRole = new Role();
        queryRole.setId(targetRole.getId());
        return commonRoleService.selectOne(queryRole);
    }

    @Override
    @Transactional
    public void deleteRoleByIds(String[] roleIds) {
        roleDao.deleteRoleByIds(roleIds);
    }

    @Override
    public List<Resource> listRoleAllResources(RoleResource roleResource) {
        return roleResourceDao.listRoleResources(roleResource);
    }

    @Override
    @Transactional
    public void updateRoleResource(String roleCode, String systemCode, String[] resourceCodes) {
        Set<String> deleteRoleRes = new HashSet<String>();//待删除对象主键集合
        List<RoleResource> addRoleResource = Lists.newArrayList();//待新增关联实体对象
        //查询已存在关联实体对象，即为待删除
        RoleResource queryParam = new RoleResource();
        queryParam.setBelongSystem(systemCode);
        queryParam.setRoleCode(roleCode);
        queryParam.setActive(IbsaasWebConstants.ACTIVE);
        List<RoleResource> queryRoleResources = commonRoleResourceService.select(queryParam);
        if (!CollectionUtils.isEmpty(queryRoleResources)) {
            queryRoleResources.forEach(item -> deleteRoleRes.add(item.getId()));
        }
        //生成新增实体对象，封装到addRoleResource中
        convertDealRoleResource(addRoleResource, resourceCodes, roleCode, systemCode);
        if (!CollectionUtils.isEmpty(addRoleResource)) roleResourceDao.saveRoleResources(addRoleResource);//插入
        if (!CollectionUtils.isEmpty(deleteRoleRes)) {
            Date oprtime = new Date();
            int res = roleResourceDao.updateRoleResources(IbsaasWebConstants.INACTIVE, oprtime, (((User)UserContext.getCurrentUser()).getUserCode()), roleCode, systemCode, oprtime.getTime(), deleteRoleRes);//删除
            if (res == 0) throw new RoleException(RoleException.BUSINESS_ROLE_RELEVANCE_RESOURCE_UPDATE_NOT_EXISTS);
        }
        clearCachKey(roleCode, systemCode);//清除缓存
    }

    private void clearCachKey(String roleCode, String systemCode) {

    }

    @Override
    @Transactional
    public void deleteRoleById(String id) {
        Date oprTime = new Date();
        Role updateParam = new Role();
        updateParam.setId(id);
        updateParam.setActive(IbsaasWebConstants.INACTIVE);
        updateParam.setModifyTime(oprTime);
        updateParam.setModifyUserCode((((User)UserContext.getCurrentUser()).getUserCode()));
        commonRoleService.updateByPrimaryKeySelective(updateParam);
    }

    private void convertDealRoleResource(List<RoleResource> addRoleResource, String[] resourceCodes, String roleCode, String systemCode) {
        if (resourceCodes == null || resourceCodes.length == 0) return;
        Set<String> addRoleRes = Sets.newHashSet();
        addRoleRes.addAll(Arrays.asList(resourceCodes));
        if (!CollectionUtils.isEmpty(addRoleRes)) {
            //新增操作
            addRoleRes.forEach(item -> {
                RoleResource roleResource = new RoleResource();
                Date oprTime = new Date();
                String id = idGenerator.nextId();
                roleResource.setId(id);
                roleResource.setCreateTime(oprTime);
                roleResource.setCreateUserCode((((User)UserContext.getCurrentUser()).getUserCode()));
                roleResource.setActive(IbsaasWebConstants.ACTIVE);
                roleResource.setVersionNo(oprTime.getTime());
                roleResource.setRoleCode(roleCode);
                roleResource.setResourceCode(item);
                roleResource.setBelongSystem(systemCode);
                addRoleResource.add(roleResource);
            });
        }
    }


    public Role findRoleByCode(String roleCode, String belongSystem) {
        Role role = new Role();
        role.setRoleCode(roleCode);
        role.setBelongSystem(belongSystem);
        role.setActive(IbsaasWebConstants.ACTIVE);
        return commonRoleService.selectOne(role);
    }

    private void initAddParams(Role role) {
        String oprUser = ((User) UserContext.getCurrentUser()).getUserCode();
        Date oprTime = new Date();
        role.setId(idGenerator.nextId());
        role.setCreateUserCode(oprUser);
        role.setCreateTime(oprTime);
        role.setModifyUserCode(oprUser);
        role.setModifyTime(oprTime);
        role.setActive(IbsaasWebConstants.ACTIVE);
        role.setModifyUserCode(null);
        role.setModifyTime(null);
    }

    private void initUpdateParams(Role role) {
        role.setModifyTime(new Date());
        role.setModifyUserCode(((User) UserContext.getCurrentUser()).getUserCode());
    }

}
