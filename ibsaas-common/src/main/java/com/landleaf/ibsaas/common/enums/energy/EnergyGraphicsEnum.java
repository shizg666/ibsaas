package com.landleaf.ibsaas.common.enums.energy;


import com.landleaf.ibsaas.common.domain.energy.dto.EnergyReportDTO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportQueryVO;
import com.landleaf.ibsaas.common.enums.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 能耗图形类型
 */
public enum EnergyGraphicsEnum implements BaseEnum {
    TIME_LINE_CHART(1, "time_line_chart","energyTimeLineChartProcessor","getData",EnergyReportQueryVO.class, "时间折线图"),
    HISTOGRAM_CHART(2, "histogram_chart", "energyHistogramChartProcessor","getData",EnergyReportQueryVO.class,"柱状图"),
    SHARE_PIE_CHART(3, "share_pie_chart", "EnergySharePieChartProcessor","getData",EnergyReportQueryVO.class,"占比饼图"),
    YEAR_ON_YEAR_CHART(4, "year_on_year_chart", "energyYearOnYearChartProcessor","getData",EnergyReportQueryVO.class,"同比图"),
    RING_RATIO_CHART(5, "ring_ratio_chart","energyRingRationChartProcessor", "getData",EnergyReportQueryVO.class,"环比图"),;
    public final int type;
    public String code;
    private String name;
    /**
     * 业务类
     */
    public String beanName;
    /**
     * 方法名
     */
    public String methodName;
    /**
     * 参数类型（1：object,2:list）
     */
    private int paramType;
    /**
     * 参数名
     */
    public Class paramName;

    EnergyGraphicsEnum(int type, String code, String beanName,String methodName,Class paramName,String name) {
        this.type = type;
        this.code = code;
        this.name = name;
        this.beanName=beanName;
        this.methodName=methodName;
        this.paramName=paramName;
    }

    @Override
    public int getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getParamType() {
        return paramType;
    }

    public void setParamType(int paramType) {
        this.paramType = paramType;
    }

    public Class getParamName() {
        return paramName;
    }

    public void setParamName(Class paramName) {
        this.paramName = paramName;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private static Map<Integer, EnergyGraphicsEnum> map = new HashMap<Integer, EnergyGraphicsEnum>();
    ;

    static {
        for (EnergyGraphicsEnum enumObj : EnergyGraphicsEnum.values()) {
            map.put(enumObj.getType(), enumObj);
        }
    }

    public static EnergyGraphicsEnum getInstByType(int type) {
        return map.get(type);
    }
}
