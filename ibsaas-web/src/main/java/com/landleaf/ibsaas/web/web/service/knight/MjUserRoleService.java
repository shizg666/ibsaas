package com.landleaf.ibsaas.web.web.service.knight;

import com.landleaf.ibsaas.common.dao.knight.MjUserRoleDao;
import com.landleaf.ibsaas.common.domain.knight.emply.MjUserRole;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.dataprovider.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MjUserRoleService extends AbstractBaseService<MjUserRoleDao, MjUserRole> implements IMjUserRoleService<MjUserRole> {
    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private MjUserRoleDao mjUserRoleDao;

    /**
     * 用户绑定角色
     *
     * @param mjUserId 用户sysNO
     * @param mjRoleId 角色ID
     */
    @Override
    public int userBindRole(Integer mjUserId, String mjRoleId) {

        MjUserRole mjUserRole = new MjUserRole();
        initAddParams(mjUserRole);
        mjUserRole.setMjRoleId(mjRoleId);
        mjUserRole.setMjUserId(mjUserId);
        //删除原来的
        MjUserRole deletParam = new MjUserRole();
        deletParam.setMjRoleId(mjRoleId);
        deletParam.setMjUserId(mjUserId);
        int deleteCount = delete(deletParam);
        return save(mjUserRole);
    }

    private MjUserRole findUserRole(Integer mjUserId, String mjRoleId) {
        MjUserRole queryParam = new MjUserRole();
        queryParam.setMjUserId(mjUserId);
        queryParam.setMjRoleId(mjRoleId);
        MjUserRole mjUserRole = mjUserRoleDao.selectOne(queryParam);
        return mjUserRole;
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
