package com.landleaf.ibsaas.web.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.web.web.context.UserContext;
import com.landleaf.ibsaas.web.web.message.LocaleMessageSource;
import com.landleaf.ibsaas.web.web.security.SecurityAccess;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @Title: AccessInterceptor 
* @Description: 访问控制拦截器
*/
public class AccessInterceptor extends HandlerInterceptorAdapter {

    /**
     * 是否忽略未配置的链接
     */
    private boolean ignoreNoneConfigUri = true;

    /**
     * 拦截的系统编码
     */
    private String systemCode;

    /**
     * 异常信息进行国际化
     */
    private LocaleMessageSource localeMessageSource;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            if (!method.hasMethodAnnotation(AccessNonCheck.class)) {
                boolean canAccess = SecurityAccess.checkCanAccess(UserContext.getCurrentUser(), systemCode, request.getRequestURI(), ignoreNoneConfigUri);
                if (!canAccess) {
                    String errorMessage = "User No Right To Access The Resource";
                    if (localeMessageSource != null) {
                        errorMessage = localeMessageSource.getMessage("error.user.no.right.to.access");
                    }
                    response.setStatus(HttpStatus.OK.value());
                    response.getWriter().write(JSON.toJSONString(Response.buildNoRightToAccessResponse(errorMessage)));
                    return false;
                }
            }
        }
        return super.preHandle(request, response, handler);
    }

    public boolean isIgnoreNoneConfigUri() {
        return ignoreNoneConfigUri;
    }

    public void setIgnoreNoneConfigUri(boolean ignoreNoneConfigUri) {
        this.ignoreNoneConfigUri = ignoreNoneConfigUri;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public LocaleMessageSource getLocaleMessageSource() {
        return localeMessageSource;
    }

    public void setLocaleMessageSource(LocaleMessageSource localeMessageSource) {
        this.localeMessageSource = localeMessageSource;
    }
}
