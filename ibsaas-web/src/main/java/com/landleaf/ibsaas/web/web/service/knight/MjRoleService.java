package com.landleaf.ibsaas.web.web.service.knight;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.ibsaas.common.dao.knight.MjRoleDao;
import com.landleaf.ibsaas.common.dao.knight.MjRoleResourceDao;
import com.landleaf.ibsaas.common.domain.knight.role.MjRole;
import com.landleaf.ibsaas.common.domain.knight.role.MjRoleResource;
import com.landleaf.ibsaas.common.domain.leo.Role;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.dataprovider.IdGenerator;
import com.landleaf.ibsaas.web.web.vo.MjRoleRequestVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class MjRoleService extends AbstractBaseService<MjRoleDao, MjRole> implements IMjRoleService<MjRole> {
    @Autowired
    private MjRoleResourceService mjRoleResourceService;
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private MjRoleResourceDao mjRoleResourceDao;

    @Override
    public PageInfo<MjRole> getPageInfo(String name, Integer departId, int page, int limit) {

        PageHelper.startPage(page, limit, true);
        Example example = new Example(MjRole.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(name)) {
            criteria.andLike("name", "%" + name + "%"); //模糊查询
        }
        if (departId != null && departId.intValue() > 0) {
            criteria.andCondition("depart_id=", departId);
        }
        //渲染选择器使用
        List<MjRole> roles = selectByExample(example);
        if (CollectionUtils.isEmpty(roles)) {
            roles = Lists.newArrayList();
        }
        return new PageInfo(roles);
    }

    @Override
    @Transactional
    public MjRole updateMjRoleDooorInfo(MjRoleRequestVO mjRoleRequestVO) {
        MjRole mjRole = new MjRole();
        BeanUtils.copyProperties(mjRoleRequestVO,mjRole);
        Integer result = updateByPrimaryKeySelective(mjRole);
        if (result < 0 ){
            throw new BusinessException("角色修改失败");
        }
        if (mjRoleRequestVO.getList() != null && mjRoleRequestVO.getList().size() > 0){
           mjRoleResourceService.updateOrAddRoleResourceByRoleId(mjRoleRequestVO.getId(),mjRoleRequestVO.getList());
        }
        return mjRole;
    }

    @Override
    @Transactional
    public MjRole addMjRoleDooorInfo(MjRoleRequestVO mjRoleRequestVO) {
        MjRole mjRole = new MjRole();
        BeanUtils.copyProperties(mjRoleRequestVO,mjRole);
        String id = idGenerator.nextId();
        mjRole.setId(id);
        Integer result = saveSelective(mjRole);
        if (result < 0 ){
            throw new BusinessException("角色添加失败");
        }
        if (mjRoleRequestVO.getList() != null && mjRoleRequestVO.getList().size() > 0){
            mjRoleResourceService.addBatchRoleResourceByRoleId(mjRole.getId(),mjRoleRequestVO.getList());
        }
        return mjRole;
    }

    @Override
    @Transactional
    public Integer deleteMjRoleDooorInfo(String roleId) {
        Integer result = deleteByPrimaryKey(roleId);
        if (result < 0 ) {
            throw new BusinessException("角色删除失败");
        }
        mjRoleResourceDao.deleteByRoleId(roleId);
        return result;
    }


    @Override
    public List<MjRole> getMjRolesByIds(List<String> roleids) {

        Example example = new Example(MjRole.class);
        Example.Criteria criteria = example.createCriteria();
        if(!CollectionUtils.isEmpty(roleids)){
            criteria.andIn("id",roleids);
        }

        return selectByExample(example);
    }

    @Override
    public MjRole addOrUpdateMjRole(MjRoleRequestVO mjRoleRequestVO) {
        MjRole mjRole;
        if (mjRoleRequestVO.getId() == null || mjRoleRequestVO.getId() ==""){
            mjRole = addMjRoleDooorInfo(mjRoleRequestVO);
        }else {
            mjRole = updateMjRoleDooorInfo(mjRoleRequestVO);
        }
        return mjRole;
    }
}
