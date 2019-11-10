package com.landleaf.ibsaas.common.service.leo;


import com.landleaf.ibsaas.common.domain.leo.Resource;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

import java.util.Set;

/**
 * @description 资源菜单服务
 * @author wyl
 * @date 2019/3/20 0020 16:41
 * @version 1.0
*/
public interface ICommonResourceService<T> extends IBaseService<T> {

    /**
     * 加载用户在指定系统中所有可用菜单
     * @param systemCode
     * @param userCode
     * @return
     */
    Set<Resource> listUserAllResources(String systemCode, String userCode);

    /**
     * @description 据所属系统编码和entryUri获取唯一权限
     * @param systemCode
     * @param entryUri
     * @author wyl
     * @date 2019/3/20 0020 16:39
     * @return com.landleaf.leo.common.domain.Resource
     * @version 1.0
    */
    Resource findResourceBySystemAndUri(String systemCode, String entryUri);
}
