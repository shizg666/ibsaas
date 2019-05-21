package com.landleaf.ibsaas.web.web.filter.sso;

import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.web.web.context.UserContext;
import com.landleaf.ibsaas.web.web.dataprovider.IUser;
import com.landleaf.ibsaas.web.web.dataprovider.IUserProvider;
import com.xxl.sso.core.conf.Conf;
import com.xxl.sso.core.login.SsoWebLoginHelper;
import com.xxl.sso.core.path.impl.AntPathMatcher;
import com.xxl.sso.core.user.XxlSsoUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录认证过滤器
 * web sso filter
 *
 * @author xuxueli 2018-04-03
 */
public class MyXxlSsoWebFilter extends HttpServlet implements Filter, ApplicationContextAware {
    private static Logger logger = LoggerFactory.getLogger(MyXxlSsoWebFilter.class);
    private ApplicationContext applicationContext;
    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private String ssoServer;
    private String ssoDefaultRedirectUrl;
    private String logoutPath;
    private String excludedPaths;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        ssoServer = filterConfig.getInitParameter(Conf.SSO_SERVER);
        ssoDefaultRedirectUrl= filterConfig.getInitParameter(Conf.SSO_DEFAULT_REDIRECTURL);
        logoutPath = filterConfig.getInitParameter(Conf.SSO_LOGOUT_PATH);
        excludedPaths = filterConfig.getInitParameter(Conf.SSO_EXCLUDED_PATHS);

        logger.info("MyXxlSsoWebFilter init.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // make url
        String servletPath = req.getServletPath();
        logger.info("请求url==》{}",servletPath);
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


        // logout path check
        if (logoutPath != null
                && logoutPath.trim().length() > 0
                && logoutPath.equals(servletPath)) {

            // remove cookie
            SsoWebLoginHelper.removeSessionIdByCookie(req, res);

            // redirect logout
            String logoutPageUrl = ssoServer.concat(Conf.SSO_LOGOUT)  + "?" + Conf.REDIRECT_URL + "=" + ssoDefaultRedirectUrl;
//            res.sendRedirect(logoutPageUrl);
            Response redirectResponse = new Response();

            redirectResponse.setErrorCode(Response.ERROR_REDIRECT);
            redirectResponse.setResult(logoutPageUrl);
            PrintWriter writer = res.getWriter();
            writer.write(JSON.toJSONString(redirectResponse));
            return;
        }

        // valid login user, cookie + redirect
        XxlSsoUser xxlUser = SsoWebLoginHelper.loginCheck(req, res);

        // valid login fail
        if (xxlUser == null) {

            String header = req.getHeader("content-type");
            boolean isJson = header != null && header.contains("json");
//            if (isJson) {
//
//                // json msg
//                res.setContentType("application/json;charset=utf-8");
//                res.getWriter().println("{\"code\":" + Conf.SSO_LOGIN_FAIL_RESULT.getCode() + ", \"msg\":\"" + Conf.SSO_LOGIN_FAIL_RESULT.getMsg() + "\"}");
//                return;
//            } else {

                // total link
//                String link = req.getRequestURL().toString();
//                link = "http://134.175.92.62:9999/leo/redirect";
                // redirect logout
                String loginPageUrl = ssoServer.concat(Conf.SSO_LOGIN)
                        + "?" + Conf.REDIRECT_URL + "=" +  ssoDefaultRedirectUrl;

                Response redirectResponse = new Response();

                redirectResponse.setErrorCode(Response.ERROR_REDIRECT);
                redirectResponse.setResult(loginPageUrl);
                PrintWriter writer = res.getWriter();
                writer.write(JSON.toJSONString(redirectResponse));
                return;
//            }

        }
        String userId = xxlUser.getUserid();
        IUserProvider userProvider = applicationContext.getBean(IUserProvider.class);
        IUser user = userProvider.getUserById(userId);
        UserContext.setCurrentUser(user);
        // ser sso user
        request.setAttribute(Conf.SSO_USER, xxlUser);


        // already login, allow
        chain.doFilter(request, response);
        return;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public boolean isContain(String url, String list){
        list = list.replaceAll("\r|\n|\t", "");
        String[] listArray = list.split(",");
        for(String str : listArray){
            if(url.indexOf(str.trim())>=0){
                return true;
            }
        }
        return false;
    }
}
