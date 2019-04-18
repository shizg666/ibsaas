package com.landleaf.ibsaas.common.dao.leo;

import com.landleaf.ibsaas.common.domain.leo.Role;
import com.landleaf.ibsaas.common.domain.leo.UserRole;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/*
 * @Author wyl
 * @Description 用户角色信息数据库操作
 * @Date 16:04 2019/3/20 0020
 **/
@Repository
public interface UserRoleDao extends BaseDao<UserRole> {

    /**
     * 获取用户角色列表
     * @param userRole
     * @return
     */
    List<Role> listUserRoles(UserRole userRole);

    /**
     * 获取系统角色列表
     * @param systemCode
     * @return
     */
    List<Role> listSystemRoles(String systemCode);

    /**
     *批量保存用户角色
     *@params:
     *@return:
     *@author:wyl
     */
    void saveUserRoles(@Param("list") List<UserRole> addUserRoles);
    /**
     *批量修改状态
     *@params:
     *@return:
     *@author:wyl
     */
    int updateUserRoles(@Param("active") int active, @Param("modifyTime") Date modifyTime, @Param("modifyUserCode") String modifyUserCode, @Param("userCode") String userCode, @Param("belongSystem") String belongSystem, @Param("ids") Set<String> ids);
}
