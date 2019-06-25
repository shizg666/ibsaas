package com.landleaf.ibsaas.common.enums.energy;

import com.landleaf.ibsaas.common.domain.energy.dto.EnergyReportExDTO;

/**
 * @author Lokiy
 * @date 2019/6/25 10:08
 * @description:
 */
public enum  EnergyReportOverviewEnum {
    /**
     * service
     */
    LINE_CHART("line-chart", "能耗总览-折线图", "com.landleaf.ibsaas.web.web.service.energy.impl.EnergyReportService", "overviewLineChart", new Class[]{EnergyReportExDTO.class}),
    HISTOGRAM("histogram", "能耗总览-柱状图", "com.landleaf.ibsaas.web.web.service.energy.impl.EnergyReportService","overviewHistogram", new Class[]{EnergyReportExDTO.class}),
    SAVING_EFFECT("saving-effect","能耗总览-节能效果", "com.landleaf.ibsaas.web.web.service.energy.impl.EnergyReportService", "overviewSavingEffect", new Class[]{EnergyReportExDTO.class}),
    SAVING_EFFECT_LINE_CHART("saving-effect-line-chart", "能耗总览-节能效果折线图", "com.landleaf.ibsaas.web.web.service.energy.impl.EnergyReportService", "overviewSavingEffectLineChart", new Class[]{EnergyReportExDTO.class}),
    CLASSIFICATION_RANKING("classification-ranking","能耗总览-能耗排行TOP5项", "com.landleaf.ibsaas.web.web.service.energy.impl.EnergyReportService", "overviewRankingClassification", new Class[]{EnergyReportExDTO.class}),
    AREA_RANKING("area-ranking","能耗总览-能耗排行TOP3区", "com.landleaf.ibsaas.web.web.service.energy.impl.EnergyReportService", "overviewRankingArea", new Class[]{EnergyReportExDTO.class}),
    YOY("yoy","能耗总览-同比", "com.landleaf.ibsaas.web.web.service.energy.impl.EnergyReportService", "overviewYoy", new Class[]{EnergyReportExDTO.class}),
    QOQ("qoq","能耗总览-环比", "com.landleaf.ibsaas.web.web.service.energy.impl.EnergyReportService", "overviewQoq", new Class[]{EnergyReportExDTO.class}),
    TOTAL("total", "能耗总览-累计能耗", "com.landleaf.ibsaas.web.web.service.energy.impl.EnergyReportService", "overviewTotal", new Class[]{EnergyReportExDTO.class})
    ;

    private String type;

    private String description;

    private String executeClassPath;

    private String methodName;

    private Class[] params;

    EnergyReportOverviewEnum(String type, String description, String executeClassPath,  String methodName, Class[] params) {
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
