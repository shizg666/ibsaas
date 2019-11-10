package com.landleaf.ibsaas.web.web.dataprovider;


/**
* @Title: IResourceProvider
* @Description: 权限数据提供接口，需要实现根据访问的uri构建权限对象
*/
public interface IResourceProvider {

    /**
     * 根据请求的uri构建resource对象
     * @param accessUri
     * @param systemCode
     */
    IResource getResource(String accessUri, String systemCode);

}
