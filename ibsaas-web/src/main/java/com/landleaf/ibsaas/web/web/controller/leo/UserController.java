package com.landleaf.ibsaas.web.web.controller.leo;

import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.leo.User;
import com.landleaf.ibsaas.common.utils.CryptoUtil;
import com.landleaf.ibsaas.web.web.constant.MessageConstants;
import com.landleaf.ibsaas.web.web.context.SessionContext;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.dto.request.SelectorParamsDto;
import com.landleaf.ibsaas.web.web.exception.UserException;
import com.landleaf.ibsaas.web.web.service.leo.IUserService;
import com.landleaf.ibsaas.web.web.vo.LogoutVO;
import com.landleaf.ibsaas.web.web.vo.UserQueryInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户信息
 */
@RestController
@RequestMapping("/users")
@Api(value = "/users", description = "用户相关操作")
public class UserController extends BasicController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

//    @Autowired
//    private CasClientProperties casClientProperties;

    @Autowired
    private IUserService userService;

    /**
     * 不启动cas情况下的登陆
     *
     * @param user
     * @return
     * @author wyl
     * @date 2017年09月06日20:14:14
     */
    @RequestMapping(value = "/v1/session", method = RequestMethod.POST)
    public Response login(@RequestBody User user) {
        //校验用户名
        User serverUser = userService.getUser(user.getUserCode());
        if (serverUser == null) {
            throw new UserException(UserException.BUSINESS_USER_LOGIN_USER_CODE_NOT_EXISTS);
        }
        //校验密码
        String pwd = CryptoUtil.getInstance().getMD5ofStr(user.getPassword());
        if (!StringUtils.equals(pwd, serverUser.getPassword())) {
            throw new UserException(UserException.BUSINESS_USER_LOGIN_USER_PASSWORD_ERROR);
        }
        //登录成功
        SessionContext.setCurrentUserId(user.getUserCode());
        return returnSuccess(SessionContext.getSession().getId(), MessageConstants.COMMON_LOGIN_SUCCESS_MESSAGE);
    }


   /**
    * @description 获取当前登陆的用户信息，在拦截器中已经设置，此处直接从UserContext中获取
    * @author wyl
    * @date 2019/3/21 0021 11:43
    * @return com.landleaf.leo.web.dto.response.Response
    * @version 1.0
   */
    @ApiOperation(value = "获取当前登录用户", notes = "获取当前登录用户，应该在用户访问主界面后第一个调用此接口")
    @RequestMapping(value = "/v1/current", method = RequestMethod.GET)
    public Response getCurrentUser() {
//        return returnSuccess(UserContext.getCurrentUser());
        return returnSuccess(userService.getUser("test"));
    }

    /**
     * @return
     * @autor wyl
     * @date 2017年08月03日17:53:29
     * @description:登出
     */
    @RequestMapping(value = "/v1/session", method = RequestMethod.DELETE)
    public Response logout() {
        SessionContext.invalidateSession();
        LogoutVO logoutVO = new LogoutVO();
//        logoutVO.setCasServerLogoutUrl(casClientProperties.getCasServerLogoutUrl());
//        logoutVO.setRedirectServerUrl(casClientProperties.getRedirectServerUrl());
        return returnSuccess(logoutVO);
    }

    /**
     * @param user
     * @return
     * @author wyl
     * @date 2017年08月10日19:04:10
     * @description:新增用户
     */
    @ApiOperation(value = "新增用户", notes = "新增用户")
    @RequestMapping(value = "/v1/user", method = RequestMethod.POST)
    public Response addUser(@RequestBody @ApiParam User user) {
        String pwd = CryptoUtil.getInstance().getMD5ofStr(user.getPassword());
        user.setPassword(pwd);
        return returnSuccess(userService.addUser(user), MessageConstants.COMMON_ADD_SUCCESS_MESSAGE);
    }

    /**
     * @param user
     * @return
     * @author wyl
     * @date 2017年08月13日15:58:59
     * @description:修改用户
     */
    @ApiOperation(value = "修改用户", notes = "修改用户")
    @RequestMapping(value = "/v1/user", method = RequestMethod.PUT)
    public Response updateUser(@RequestBody User user) {
        return returnSuccess(userService.updateUser(user), MessageConstants.COMMON_UPDATE_SUCCESS_MESSAGE);
    }

    /**
     * @param id
     * @return
     * @author wyl
     * @date 2017年08月13日16:04:15
     * @description:删除用户
     */
    @ApiOperation(value = "根据主键删除用户", notes = "根据主键删除用户")
    @RequestMapping(value = "/v1/user/{id}", method = RequestMethod.DELETE)
    public Response deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return returnSuccess(MessageConstants.COMMON_DELETE_SUCCESS_MESSAGE);
    }


    /**
     * @param:selectorParamsDto
     * @return:
     * @author:温奕禄
     * @date 2017/9/22 17:11
     * @description:根据名称模糊查询用户中文姓名列表 , 供前端用户管理页面搜索姓名下拉框
     */
    @ApiOperation(value = "根据名称模糊查询用户中文姓名列表", notes = "根据名称模糊查询用户中文姓名列表，供前端用户管理页面搜索姓名下拉框使用")
    @RequestMapping(value = "/v1/user-list/name", method = RequestMethod.GET)
    public Response queryDisplayUser(@ApiParam SelectorParamsDto selectorParamsDto) {
        return returnSuccess(userService.queryDisplayUser(selectorParamsDto));
    }

    /**
     * @param:userQueryInfo
     * @author:温奕禄
     * @date 2017/9/22 17:13
     * @description:根据条件分页获取用户列表
     */
    @ApiOperation(value = "条件分页获取用户列表", notes = "条件分页获取用户列表")
    @RequestMapping(value = "/v1/user-list", method = RequestMethod.GET)
    public Response listUserQueryInfos(@ApiParam UserQueryInfoVO userQueryInfo) {
        return returnSuccess(userService.listUserQueryInfos(userQueryInfo));
    }

    /**
     * @param:userCode
     * @return:
     * @author:温奕禄
     * @date 2017/9/22 17:13
     * @description:校验用户名是否可用
     */
    @ApiOperation(value = "校验用户名是否可用", notes = "校验用户名是否可用，返回对象下字段result值为true则表示可用")
    @RequestMapping(value = "/v1/user-code/unique/{userCode}", method = RequestMethod.GET)
    public Response checkUserCode(@PathVariable String userCode) {
        return returnSuccess(userService.checkUserCode(userCode));
    }

    /**
     * @param:id
     * @return:
     * @author:温奕禄
     * @date 2017/9/22 17:14
     * @description:根据主键id查询用户信息
     */
    @ApiOperation(value = "根据主键查询用户信息", notes = "根据主键查询用户信息")
    @RequestMapping(value = "/v1/user", method = RequestMethod.GET)
    public Response queryUserInfo(@RequestParam String id) {
        return returnSuccess(userService.queryUserInfo(id));
    }


    /**
     * @param:roleCodes
     * @param:userCode
     * @param:systemCode
     * @return:
     * @author:温奕禄
     * @date 2017/9/22 17:14
     * @description:关联角色信息
     */
    @ApiOperation(value = "用户关联角色信息", notes = "用户关联角色信息")
    @RequestMapping(value = "/v1/user/{userCode}/relevance/system/{systemCode}/role", method = RequestMethod.PUT)
    public Response updateUserRole(@PathVariable String userCode, @PathVariable("systemCode") String systemCode, @RequestBody String[] roleCodes) {
        userService.updateUserRole(userCode, systemCode, roleCodes);
        return returnSuccess(null, MessageConstants.COMMON_UPDATE_SUCCESS_MESSAGE);
    }


}
