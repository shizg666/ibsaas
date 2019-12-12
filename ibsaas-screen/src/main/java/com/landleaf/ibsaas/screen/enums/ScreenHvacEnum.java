package com.landleaf.ibsaas.screen.enums;

/**
 * @author Lokiy
 * @date 2019/12/12 16:50
 * @description:
 */
public enum ScreenHvacEnum{

    /**
     * 请求
     */
    SENSOR("SENSO", "多参数传感器数据", "com.landleaf.ibsaas.screen.service.LargeScreenService", "sensorStatus", new Class[]{}),
    NEWFAN("NEWFAN", "新风机状态数据", "com.landleaf.ibsaas.screen.service.LargeScreenService","newFanStatus", new Class[]{}),
    FANCOIL("FANCOIL","风盘状态数据", "com.landleaf.ibsaas.screen.service.LargeScreenService", "fanCoilStatus", new Class[]{}),
    ;

    private String type;

    private String description;

    private String executeClassPath;

    private String methodName;

    private Class[] params;

    ScreenHvacEnum(String type, String description, String executeClassPath, String methodName, Class[] params) {
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
