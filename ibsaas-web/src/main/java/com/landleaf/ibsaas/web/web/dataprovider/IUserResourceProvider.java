package com.landleaf.ibsaas.web.web.dataprovider;



import java.util.List;

/**
* @Title: IUserResourceProvider
* @Description: 用户访问权限数据提供者
*/
public interface IUserResourceProvider {

    /**
     * 根据用户获取用户的访问权限
     * @param user
     * @param systemCode
     */
    List<IResource> getResources(IUser user, String systemCode);

}
