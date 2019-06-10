package com.landleaf.ibsaas.web.web.filter.sso;

import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.leo.User;
import com.landleaf.ibsaas.web.web.context.user.SsoWebLoginHelper;
import com.landleaf.ibsaas.web.web.context.user.UserContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录认证过滤器
 * web sso filter
 */
public class SsoWebFilter extends HttpServlet implements Filter, ApplicationContextAware {
    private static Logger logger = LoggerFactory.getLogger(SsoWebFilter.class);
    private ApplicationContext applicationContext;
    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private String logoutPath;
    private String excludedPaths;

    private SsoWebLoginHelper ssoWebLoginHelper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        logoutPath = filterConfig.getInitParameter(Conf.SSO_LOGOUT_PATH);
        excludedPaths = filterConfig.getInitParameter(Conf.SSO_EXCLUDED_PATHS);

        logger.info("MySsoWebFilter init.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        // make url
        String servletPath = req.getServletPath();
        logger.info("请求url==》{}", servletPath);
        // excluded path check
        if (excludedPaths != null && excludedPaths.trim().length() > 0) {
            for (String excludedPath : excludedPaths.split(",")) {
                String uriPattern = excludedPath.trim();
                // 支持ANT表达式
                if (antPathMatcher.match(uriPattern, servletPath)) {
                    // excluded path, allow
                    chain.doFilter(request, response);
                    return;
                }
            }
        }
        String sid = req.getParameter("sid");
        User sysUser = null;
        // logout path check
        if (logoutPath != null
                && logoutPath.trim().length() > 0
                && logoutPath.equals(servletPath)) {
            // remove sid
            ssoWebLoginHelper.remove(sid);
            chain.doFilter(request, response);
            return;
        }
        //redis写死id
//        sid = "03a56e8e-5baf-435e-aa2e-c0e54c4ac66d";
        if (StringUtils.isNotEmpty(sid)) {
            try {
                sysUser = ssoWebLoginHelper.get(sid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // valid login fail
        if (sysUser == null) {
            Response returnResponse = new Response<>();
            returnResponse.setSuccess(false);
            returnResponse.setErrorCode(Conf.SSO_LOGIN_FAIL_RESULT.getCode()+"");
            returnResponse.setMessage(Conf.SSO_LOGIN_FAIL_RESULT.getMsg());
            returnResponse.setErrorMsg(Conf.SSO_LOGIN_FAIL_RESULT.getMsg());
            res.setContentType("application/json;charset=utf-8");
//            res.getWriter().println("{\"code\":" + Conf.SSO_LOGIN_FAIL_RESULT.getCode() + ", \"msg\":\"" + Conf.SSO_LOGIN_FAIL_RESULT.getMsg() + "\"}");
            res.getWriter().println(JSON.toJSONString(returnResponse));
            return;
        }
        //将用户信息设置到UserContext中
        UserContext.setCurrentUser(sysUser);
        // already login, allow
        try {
            chain.doFilter(request, response);
        } finally {
            //请求处理完成后将UserContext中的用户信息移除掉
            UserContext.remove();
        }
        return;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public SsoWebLoginHelper getSsoWebLoginHelper() {
        return ssoWebLoginHelper;
    }

    public void setSsoWebLoginHelper(SsoWebLoginHelper ssoWebLoginHelper) {
        this.ssoWebLoginHelper = ssoWebLoginHelper;
    }
}
