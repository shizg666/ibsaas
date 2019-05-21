package com.landleaf.ibsaas.web.web.service.knight;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.landleaf.ibsaas.common.dao.knight.MjRoleDao;
import com.landleaf.ibsaas.common.domain.knight.role.MjRole;
import com.landleaf.ibsaas.common.domain.leo.Role;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.constant.IbsaasWebConstants;
import com.landleaf.ibsaas.web.web.dto.knight.role.WebMjRoleDTO;
import com.landleaf.ibsaas.web.web.dto.response.SelectorResultDto;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class MjRoleService extends AbstractBaseService<MjRoleDao, MjRole> implements IMjRoleService<MjRole> {


    @Override
    public PageInfo<MjRole> getPageInfo(String name, Integer departId, int page, int limit) {

        PageHelper.startPage(page, limit, true);
        Example example = new Example(MjRole.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(name)) {
            criteria.andLike("name", "%" + name + "%"); //模糊查询
        }
        if(departId!=null&&departId.intValue()>0){
            criteria.andCondition("depart_id=",departId);
        }
        //渲染选择器使用
        List<MjRole> roles = selectByExample(example);
        if (CollectionUtils.isEmpty(roles)) {
            roles= Lists.newArrayList();
        }
        return new PageInfo(roles);
    }
}
