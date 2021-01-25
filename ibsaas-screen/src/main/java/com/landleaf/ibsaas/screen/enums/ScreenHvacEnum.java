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
    SENSOR("SENSOR", "多参数传感器数据", "com.landleaf.ibsaas.screen.service.LargeScreenService", "sensorStatus", new Class[]{}),
    NEW_FAN("NEW_FAN", "新风机状态数据", "com.landleaf.ibsaas.screen.service.LargeScreenService","newFanStatus", new Class[]{}),
    FAN_COIL("FAN_COIL","风盘状态数据", "com.landleaf.ibsaas.screen.service.LargeScreenService", "fanCoilStatus", new Class[]{}),
    ACHP_DETAIL("ACHP_DETAIL","风冷热泵状态数据", "com.landleaf.ibsaas.screen.service.LargeScreenService", "achpDetailStatus", new Class[]{}),
    WEATHER("WEATHER","天气信息", "com.landleaf.ibsaas.screen.service.LargeScreenService", "weatherStatus", new Class[]{}),
    ENERGY("ENERGY","能耗信息", "com.landleaf.ibsaas.screen.service.LargeScreenService", "energyStatus2", new Class[]{}),
    MEETING("MEETING","会议信息", "com.landleaf.ibsaas.screen.service.LargeScreenService", "meetingStatus", new Class[]{}),
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
