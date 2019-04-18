package com.landleaf.ibsaas.web.web.service.leo;

import com.github.pagehelper.PageInfo;
import com.landleaf.ibsaas.common.domain.leo.Resource;
import com.landleaf.ibsaas.web.web.dto.request.SelectorParamsDto;
import com.landleaf.ibsaas.web.web.vo.TreeNodeVO;

import java.util.List;
import java.util.Map;

/**
 * @author wyl
 * @version 1.0
 * @description 菜单资源操作
 * @date 2019/3/20 0020 17:20
 * @return
 */
public interface IResourceService {

    /**
     * @param systemCode
     * @return java.util.List<Resource>
     * @description 加载所有的子菜单
     * @author wyl
     * @date 2019/3/20 0020 17:20
     * @version 1.0
     */
    List<Resource> listSystemAllResources(String systemCode);

    /**
     * @param systemCode
     * @param userCode
     * @return java.util.List<TreeNodeVO>
     * @description 加载用户所有可用菜单
     * @author wyl
     * @date 2019/3/20 0020 17:20
     * @version 1.0
     */
    List<TreeNodeVO> listUserAllResources(String systemCode, String userCode);

    /**
     * @param resourceCode
     * @param systemCode
     * @return com.landleaf.leo.common.domain.Resource
     * @description 根据权限编码查询权限详细信息
     * @author wyl
     * @date 2019/3/20 0020 17:20
     * @version 1.0
     */
    Resource findResourceByCode(String resourceCode, String systemCode);

    /**
     * @param systemCode
     * @param resourceName
     * @return java.util.Map<java.lang.String,java.util.List<java.util.List<java.util.Map<java.lang.String,java.lang.String>>>>
     * @description 根据所属系统编码权限名称模糊查询权限列表
     * @author wyl
     * @date 2019/3/20 0020 17:20
     * @version 1.0
     */
    Map<String, List<List<Map<String, String>>>> findResourceByNameToDisplay(String systemCode, String resourceName);

    /**
     * @param resource
     * @return com.landleaf.leo.common.domain.Resource
     * @description 新增权限
     * @author wyl
     * @date 2019/3/20 0020 17:21
     * @version 1.0
     */
    Resource addResource(Resource resource);

    /**
     * @param id
     * @param resource
     * @return com.landleaf.leo.common.domain.Resource
     * @description 修改权限
     * @author wyl
     * @date 2019/3/20 0020 17:21
     * @version 1.0
     */
    Resource updateResource(String id, Resource resource);

    /**
     * @param id
     * @return void
     * @description 根据数据库id删除指定权限
     * @author wyl
     * @date 2019/3/20 0020 17:21
     * @version 1.0
     */
    void deleteResource(String id);

    /**
     * @param codes
     * @return void
     * @description 根据resourceCode 批量删除
     * @author wyl
     * @date 2019/3/20 0020 17:21
     * @version 1.0
     */
    void deleteResourceByCodes(String[] codes);

    /**
     * @param systemCode
     * @return java.util.List<com.landleaf.leo.web.vo.TreeNodeVO>
     * @description 根据系统编码查询所属权限列表　并封装成树状结构返回
     * @author wyl
     * @date 2019/3/20 0020 17:21
     * @version 1.0
     */
    List<TreeNodeVO> listAllResourcesBySystem(String systemCode);

    /**
     * @param paramsDto
     * @return com.github.pagehelper.PageInfo
     * @description 通用方法根据code 返回一个菜单集合
     * @author wyl
     * @date 2019/3/20 0020 17:21
     * @version 1.0
     */
    PageInfo queryResource(SelectorParamsDto paramsDto);
}
