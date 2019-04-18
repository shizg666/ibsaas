package com.landleaf.ibsaas.web.web.controller.leo.login;

import com.landleaf.ibsaas.common.domain.leo.User;
import com.landleaf.ibsaas.common.utils.encode.EncryptMD5Util;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.leo.impl.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

;

@Controller
public class LoginController extends BasicController {

    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private Environment environment;


    @GetMapping(value = "/login")
    public String login(HttpServletRequest request) {
        //清除登录验证码
        return "/login";
    }

    @PostMapping(value = "/usernameLogin")
    @ResponseBody
    public Map<String, Object> checkUsernameLogin(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String sessionid = session.getId();
//      取消验证码验证
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String origUrl = request.getParameter("origUrl");

        if (StringUtils.isBlank(username)) {

        }
        if (StringUtils.isBlank(password)) {
        }
        User user = userService.getUser(username);
        if (null == user) {
        }
        //传过来已经加密一次了
        password = EncryptMD5Util.encryptMD5(password);
        if (!StringUtils.equals(user.getPassword(), password)) {

        }
        session.setAttribute("loginuserid", user.getId());
        origUrl = getOrigUrl(origUrl, user, sessionid);
        LOGGER.info("login success login origUrl=" + origUrl);
        Map<String, Object> data = new HashMap<>(16);
        data.put("origUrl", origUrl);
        return data;
    }

    public String getOrigUrl(String origUrl, User user, String sessionid) {
        origUrl += (origUrl.contains("?") ? "&" : "?") + "randm=" + System.currentTimeMillis() + "&ticket=" + sessionid;
        return origUrl;
    }


    @GetMapping(value = "/logout")
    @ResponseBody
    public String logout(HttpServletRequest request) {
        request.getSession(false).invalidate();
        return null;
    }

}
