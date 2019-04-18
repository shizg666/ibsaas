package com.landleaf.ibsaas.common.dao.leo;

import com.landleaf.ibsaas.common.domain.leo.Resource;
import com.landleaf.ibsaas.common.domain.leo.RoleResource;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/*
 * @Author wyl
 * @Description 角色信息数据库操作
 * @Date 16:01 2019/3/20 0020
 **/
@Repository
public interface RoleResourceDao extends BaseDao<RoleResource> {

    /**
     * 列出角色下的所有菜单
     * @param roleResource
     * @return
     * @author wyl
     */
    List<Resource> listRoleResources(RoleResource roleResource);
    /**
     *列出角色下的所有权限
     *@params:
     *@return:
     *@author:wyl
     */
    List<Resource> listRoleAllResources(RoleResource roleResource);
    /**
     *批量保存角色权限
     *@params:
     *@return:
     *@author:wyl
     */
    void saveRoleResources(@Param("list") List<RoleResource> addRoleResources);
    /**
     *批量修改状态
     *@params:
     *@return:
     *@author:wyl
     */
    int updateRoleResources(@Param("active") int active, @Param("modifyTime") Date modifyTime, @Param("modifyUserCode") String modifyUserCode, @Param("roleCode") String roleCode, @Param("belongSystem") String belongSystem, @Param("versionNo") Long versionNO, @Param("ids") Set<String> ids);

}
