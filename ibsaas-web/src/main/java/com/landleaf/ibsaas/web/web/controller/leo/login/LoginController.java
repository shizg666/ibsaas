package com.landleaf.ibsaas.web.web.controller.leo.login;

import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.leo.User;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.common.utils.encode.EncryptMD5Util;
import com.landleaf.ibsaas.web.web.context.user.SsoWebLoginHelper;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.leo.impl.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
@Api(value = "/login", description = "登录相关操作")
public class LoginController extends BasicController {

    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private Environment environment;


    @Autowired
    private SsoWebLoginHelper ssoWebLoginHelper;

    @ApiOperation(value = "用户登录", notes = "用户登录")
    @PostMapping(value = "/username-login")
    @ResponseBody
    public Response login(HttpServletRequest request, @RequestBody User user) {
        HttpSession session = request.getSession();
        String sessionid = session.getId();
        String userCode = user.getUserCode();
        String password = user.getPassword();
        User existUser = userService.getUser(userCode);
        if (existUser == null) {
            throw new BusinessException("用户不存在！");
        }
        //传过来已经加密一次了
//        password = EncryptMD5Util.encryptMD5(password);
        if (!StringUtils.equals(user.getPassword(), password)) {
            throw new BusinessException("密码错误！");
        }
        ssoWebLoginHelper.set(sessionid, existUser, 3600 * 24L);
        Map<String, Object> data = new HashMap<>(16);
        data.put("sid", sessionid);
        User saveUser = new User();
        BeanUtils.copyProperties(existUser, saveUser);
        saveUser.setPassword(null);
        data.put("user", saveUser);
        return returnSuccess(data);
    }


    @GetMapping(value = "/logout")
    @ResponseBody
    public Response logout(HttpServletRequest request, @RequestParam("sid") String sid) {
        ssoWebLoginHelper.remove(sid);
        return returnSuccess();
    }

}
