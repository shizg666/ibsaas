package com.landleaf.ibsaas.web.web.service.leo;


import com.github.pagehelper.PageInfo;
import com.landleaf.ibsaas.common.domain.leo.User;
import com.landleaf.ibsaas.web.web.dto.request.SelectorParamsDto;
import com.landleaf.ibsaas.web.web.vo.UserQueryInfoVO;

/**
 * @description 用户信息操作接口
 * @author wyl
 * @date 2019/3/20 0020 17:03
 * @version 1.0
*/
public interface IUserService {

    /**
     * @description 根据用户名获取用户
     * @param userCode
     * @author wyl
     * @date 2019/3/20 0020 17:03
     * @return User
     * @version 1.0
    */
    User getUser(String userCode);

    /**
     * @description 新增用户
     * @param user
     * @author wyl
     * @date 2019/3/20 0020 17:03
     * @return com.landleaf.leo.common.domain.User
     * @version 1.0
    */
    User addUser(User user);

    /**
     * @description 更新用户
     * @param user
     * @author wyl
     * @date 2019/3/20 0020 17:03
     * @return com.landleaf.leo.common.domain.User
     * @version 1.0
    */
    User updateUser(User user);

    /**
     * @description 删除用户
     * @param id
     * @author wyl
     * @date 2019/3/20 0020 17:03
     * @return void
     * @version 1.0
    */
    void deleteUser(String id);

    /**
     * @description 根据名称模糊查询用户中文姓名列表 ,供前端用户管理页面搜索姓名下拉框
     * @param selectorParamsDto
     * @author wyl
     * @date 2019/3/20 0020 17:04
     * @return PageInfo
     * @version 1.0
    */
    PageInfo queryDisplayUser(SelectorParamsDto selectorParamsDto);

    /**
     * @description 根据条件分页获取用户列表
     * @param userQueryInfo
     * @author wyl
     * @date 2019/3/20 0020 17:14
     * @return com.github.pagehelper.PageInfo
     * @version 1.0
    */
    PageInfo listUserQueryInfos(UserQueryInfoVO userQueryInfo);

    /**
     * @description 校验用户名是否可用
     * @param userCode
     * @author wyl
     * @date 2019/3/20 0020 17:15
     * @return java.lang.Boolean
     * @version 1.0
    */
    Boolean checkUserCode(String userCode);

    /**
     * @description 根据主键id查询用户信息
     * @param id
     * @author wyl
     * @date 2019/3/20 0020 17:15
     * @return com.landleaf.leo.common.domain.User
     * @version 1.0
    */
     User queryUserInfo(String id);


   /**
    * @description 关联角色信息
    * @param userCode
    * @param systemCode
    * @param roleCodes
    * @author wyl
    * @date 2019/3/20 0020 17:15
    * @return void
    * @version 1.0
   */
    void updateUserRole(String userCode, String systemCode, String[] roleCodes);
}
