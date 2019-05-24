package com.landleaf.ibsaas.web.web.service.knight;

import com.google.common.collect.Lists;
import com.landleaf.ibsaas.common.dao.knight.MjUserRoleDao;
import com.landleaf.ibsaas.common.domain.knight.emply.MjUserRole;
import com.landleaf.ibsaas.common.domain.knight.role.MjRole;
import com.landleaf.ibsaas.common.domain.leo.Role;
import com.landleaf.ibsaas.common.domain.leo.User;
import com.landleaf.ibsaas.common.domain.leo.UserRole;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.constant.IbsaasWebConstants;
import com.landleaf.ibsaas.web.web.context.user.UserContext;
import com.landleaf.ibsaas.web.web.dataprovider.IdGenerator;
import com.landleaf.ibsaas.web.web.exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MjUserRoleService extends AbstractBaseService<MjUserRoleDao, MjUserRole> implements IMjUserRoleService<MjUserRole> {
    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private MjUserRoleDao mjUserRoleDao;
    @Autowired
    private IMjRoleService mjRoleService;


    @Override
    public int userBindRole(Integer mjUserId, List<String> mjRoleIds) {

        Set<String> deleteUserRoleIds = new HashSet<String>();//待删除对象主键集合
        List<MjUserRole> addUserRoles = Lists.newArrayList();//待新增关联实体对象
        //查询已存在关联实体对象，即为待删除
        MjUserRole queryParam = new MjUserRole();
        queryParam.setMjUserId(mjUserId);
        List<MjUserRole> queryUserRoles = select(queryParam);
        if (!CollectionUtils.isEmpty(queryUserRoles)) {
            queryUserRoles.forEach(item -> deleteUserRoleIds.add(item.getId()));
        }
        //生成新增实体对象，addUserRoles
        convertDealUserRole(addUserRoles, mjRoleIds, mjUserId);
        if (!CollectionUtils.isEmpty(addUserRoles)) mjUserRoleDao.saveUserRoles(addUserRoles);//插入
        if (!CollectionUtils.isEmpty(deleteUserRoleIds)) {
            int res = mjUserRoleDao.deleteUserRoles(mjUserId, deleteUserRoleIds);//删除
        }
        return 0;
    }

    /**
     * 获取用户角色
     * @param sysNo
     * @return
     */
    @Override
    public List<MjRole> getUserRoleBySysNo(Integer sysNo) {
        List<MjRole> result = Lists.newArrayList();
        MjUserRole queryParam = new MjUserRole();
        queryParam.setMjUserId(sysNo);
        List<MjUserRole> queryResult = select(queryParam);

        if(!CollectionUtils.isEmpty(queryResult)){
            List<String> roleids = queryResult.stream().map(i -> {
                return i.getMjRoleId();
            }).collect(Collectors.toList());
            result=mjRoleService.getMjRolesByIds(roleids);

        }
        return result;
    }

    private MjUserRole findUserRole(Integer mjUserId, String mjRoleId) {
        MjUserRole queryParam = new MjUserRole();
        queryParam.setMjUserId(mjUserId);
        queryParam.setMjRoleId(mjRoleId);
        MjUserRole mjUserRole = mjUserRoleDao.selectOne(queryParam);
        return mjUserRole;
    }

    /**
     * @description:组装待新成用户角色实体对象集合
     */
    private void convertDealUserRole(List<MjUserRole> addUserRoles, List<String> roleCodes, Integer mjUserId) {
        if (roleCodes == null || roleCodes.size() == 0) return;
        Set<String> addUserRoleIds = new HashSet<String>();
        addUserRoleIds.addAll(roleCodes);
        if (!CollectionUtils.isEmpty(addUserRoleIds)) {
            //新增操作
            addUserRoleIds.forEach(item -> {
                MjUserRole userRole = new MjUserRole();
                Date oprTime = new Date();
                String id = idGenerator.nextId();
                userRole.setId(id);
                userRole.setCreateTime(oprTime);
                userRole.setMjRoleId(item);
                userRole.setMjUserId(mjUserId);
                addUserRoles.add(userRole);
            });
        }
    }

    private void filterUserRoles(String[] roleCodes, Set<String> existUserRoleIds, Set<String> updateUserRoleIds, Set<String> deleteUserRoleIds, Set<String> addUserRoleIds) {
        if (roleCodes == null || roleCodes.length == 0) {
            deleteUserRoleIds.addAll(existUserRoleIds);
        } else {
            List<String> requestRoleCodes = Arrays.asList(roleCodes);
            if (CollectionUtils.isEmpty(existUserRoleIds)) {
                addUserRoleIds.addAll(requestRoleCodes);
            } else {
                updateUserRoleIds.addAll(existUserRoleIds.stream().filter(item -> requestRoleCodes.contains(item)).collect(Collectors.toList()));
                deleteUserRoleIds.addAll(existUserRoleIds.stream().filter(item -> !requestRoleCodes.contains(item)).collect(Collectors.toList()));
                addUserRoleIds.addAll(requestRoleCodes.stream().filter(item -> !existUserRoleIds.contains(item)).collect(Collectors.toList()));
            }
        }
    }
    private void initAddParams(MjUserRole mjUserRole) {
        Date oprTime = new Date();
        mjUserRole.setId(idGenerator.nextId());
        mjUserRole.setCreateTime(oprTime);
        mjUserRole.setModifyTime(oprTime);
        mjUserRole.setModifyUserCode(null);
        mjUserRole.setModifyTime(null);
    }


}
