package com.landleaf.ibsaas.common.dao.leo;

import com.landleaf.ibsaas.common.domain.leo.Role;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/*
 * @Author wyl
 * @Description 角色信息数据库操作
 * @Date 15:59 2019/3/20 0020
 **/
@Repository
public interface RoleDao extends BaseDao<Role> {
    /**
     *根据角色主键批量删除
     *@params:　id 主键
     *@return:
     *@author:wyl
     */
    void deleteRoleByIds(@Param("roleIds") String[] roleIds);
}
