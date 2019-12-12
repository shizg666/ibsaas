package com.landleaf.ibsaas.screen.enums;


/**
 * @author Lokiy
 * @date 2019/6/25 10:08
 * @description:
 */
public enum ScreenBaseEnum {
    /**
     * service
     */
    ;

    private String type;

    private String description;

    private String executeClassPath;

    private String methodName;

    private Class[] params;

    ScreenBaseEnum(String type, String description, String executeClassPath, String methodName, Class[] params) {
        this.type = type;
        this.description = description;
        this.executeClassPath = executeClassPath;
        this.params = params;
        this.methodName = methodName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExecuteClassPath() {
        return executeClassPath;
    }

    public void setExecuteClassPath(String executeClassPath) {
        this.executeClassPath = executeClassPath;
    }

    public Class[] getParams() {
        return params;
    }

    public void setParams(Class[] params) {
        this.params = params;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
