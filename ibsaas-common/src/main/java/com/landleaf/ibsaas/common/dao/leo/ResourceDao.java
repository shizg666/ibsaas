package com.landleaf.ibsaas.common.dao.leo;

import com.landleaf.ibsaas.common.domain.leo.Resource;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @Author wyl
 * @Description 菜单信息数据库操作
 * @Date 15:54 2019/3/20
 **/
@Repository
public interface ResourceDao extends BaseDao<Resource> {
    /*
     * @Author wyl
     * @Description 根据功能名称模糊查询
     * @Date 15:55 2019/3/20
     * @Param
     * @return
     **/
    List<Resource> findResourceByNameToDisplay(@Param("systemCode") String systemCode, @Param("resourceName") String resourceName);
    /*
     * @Author wyl
     * @Description 根据权限编码批量删除
     * @Date 15:57 2019/3/20 0020
     * @Param 
     * @return 
     **/
    void deleteResourceByCodes(@Param("resourceCodes") String[] resourceCodes);
}
