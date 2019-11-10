package com.landleaf.ibsaas.web.web.filter.sso;

public class Conf {

    public static final String SSO_SESSIONID = "xxl_sso_sessionid";
    public static final String REDIRECT_URL = "redirect_url";
    public static final String SSO_USER = "xxl_sso_user";
    public static final String SSO_SERVER = "sso_server";
    public static final String SSO_LOGIN = "/login";
    public static final String SSO_LOGOUT = "/logout";
    public static final String SSO_LOGOUT_PATH = "SSO_LOGOUT_PATH";
    public static final String SSO_EXCLUDED_PATHS = "SSO_EXCLUDED_PATHS";
    public static final String SSO_DEFAULT_REDIRECTURL = "SSO_DEFAULT_REDIRECTURL";
    public static final ReturnT<String> SSO_LOGIN_FAIL_RESULT = new ReturnT(501, "sso not login.");

    public Conf() {
    }
}
