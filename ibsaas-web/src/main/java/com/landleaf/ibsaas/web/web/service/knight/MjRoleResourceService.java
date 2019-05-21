package com.landleaf.ibsaas.web.web.service.knight;

import com.landleaf.ibsaas.common.dao.knight.MjRoleResourceDao;
import com.landleaf.ibsaas.common.domain.knight.role.MjRoleResource;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MjRoleResourceService extends AbstractBaseService<MjRoleResourceDao, MjRoleResource> implements IMjRoleResourceService<MjRoleResource> {
    @Override
    public List<MjRoleResource> findRoleResourceByRoleId(String mjRoleId) {
        MjRoleResource mjRoleResource = new MjRoleResource();
        mjRoleResource.setMjRoleId(mjRoleId);
        return select(mjRoleResource);
    }
}
