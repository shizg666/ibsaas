package com.landleaf.ibsaas.web.web.vo;

import java.io.Serializable;

/**
 * @description 登出返回参数
 * @author wyl
 * @date 2019/3/20 0020 16:58
 * @version 1.0
*/
public class LogoutVO implements Serializable {

    private static final long serialVersionUID = 209589032903042233L;

    /**
     * CAS登出地址
     */
    private String casServerLogoutUrl;

    /**
     * 登出重定向地址
     */
    private String redirectServerUrl;

    public String getCasServerLogoutUrl() {
        return casServerLogoutUrl;
    }

    public void setCasServerLogoutUrl(String casServerLogoutUrl) {
        this.casServerLogoutUrl = casServerLogoutUrl;
    }

    public String getRedirectServerUrl() {
        return redirectServerUrl;
    }

    public void setRedirectServerUrl(String redirectServerUrl) {
        this.redirectServerUrl = redirectServerUrl;
    }
}
