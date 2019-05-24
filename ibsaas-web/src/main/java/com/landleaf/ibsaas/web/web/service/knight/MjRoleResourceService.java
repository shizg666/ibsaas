package com.landleaf.ibsaas.web.web.service.knight;

import com.google.common.collect.Lists;
import com.landleaf.ibsaas.common.dao.knight.MjRoleResourceDao;
import com.landleaf.ibsaas.common.domain.knight.role.MjRoleResource;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.dataprovider.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MjRoleResourceService extends AbstractBaseService<MjRoleResourceDao, MjRoleResource> implements IMjRoleResourceService<MjRoleResource> {
    @Autowired
    private IdGenerator idGenerator;

    @Override
    public List<MjRoleResource> findRoleResourceByRoleId(String mjRoleId) {
        MjRoleResource mjRoleResource = new MjRoleResource();
        mjRoleResource.setMjRoleId(mjRoleId);
        return select(mjRoleResource);
    }

    @Override
    public List<MjRoleResource> updateOrAddRoleResourceByRoleId(String mjRoleId, List<Integer> mjDoorids) {
        Integer result = dao.deleteByRoleId(mjRoleId);
        List<MjRoleResource> mjRoleResourceList = Lists.newArrayList();
        mjDoorids.forEach(obj->{
            MjRoleResource mjRoleResource = new MjRoleResource();
            String id = idGenerator.nextId();
            mjRoleResource.setId(id);
            mjRoleResource.setMjRoleId(mjRoleId);
            mjRoleResource.setMjDoorId(obj);
            mjRoleResourceList.add(mjRoleResource);
        });
        dao.insertBatch(mjRoleResourceList);
        return mjRoleResourceList;
    }

    @Override
    public List<MjRoleResource> addBatchRoleResourceByRoleId(String mjRoleId, List<Integer> mjDoorids) {
//        Integer result = dao.deleteByRoleId(mjRoleId);
        List<MjRoleResource> mjRoleResourceList = Lists.newArrayList();
        mjDoorids.forEach(obj->{
            MjRoleResource mjRoleResource = new MjRoleResource();
            String id = idGenerator.nextId();
            mjRoleResource.setId(id);
            mjRoleResource.setMjRoleId(mjRoleId);
            mjRoleResource.setMjDoorId(obj);
            mjRoleResourceList.add(mjRoleResource);
        });
        dao.insertBatch(mjRoleResourceList);
        return mjRoleResourceList;
    }


}
