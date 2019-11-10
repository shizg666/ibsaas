package com.landleaf.ibsaas.web.web.context.user;


import com.landleaf.ibsaas.common.domain.leo.User;

/**
 * 存取当前登录用户
 */
public class UserContext {

    /**
     * 用threadLocal存储当前登陆的用户
     */
    private static final ThreadLocal<User> USER_HOLDER = new ThreadLocal<User>();

    /**
     * 获取当前登录用户
     */
    public static User getCurrentUser() {
        return USER_HOLDER.get();
    }

    /**
     * 设置当前登陆用户
     */
    public static void setCurrentUser(User user) {
        USER_HOLDER.set(user);
    }

    /**
     * 清除用户
     * @author 陈宇霖
     * @date 2017年08月02日20:30:54
     */
    public static void remove() {
        USER_HOLDER.remove();
    }
}
