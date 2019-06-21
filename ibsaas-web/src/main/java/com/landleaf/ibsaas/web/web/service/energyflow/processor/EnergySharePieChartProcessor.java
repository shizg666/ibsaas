package com.landleaf.ibsaas.web.web.service.energyflow.processor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.ibsaas.common.domain.energy.vo.ConfigSettingVO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportQueryVO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportResponseVO;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyReportService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 饼图
 */
@Component
public class EnergySharePieChartProcessor extends AbstractEnergyChartProcessor {

    @Autowired
    private IEnergyReportService energyReportService;
    @Autowired
    private EnergyGraphicsDataProcessor energyGraphicsDataProcessor;

    /**
     * 能耗饼图 （各分项占比）
     *
     * @param requestBody
     * @return
     */
    public Map<String, String> getData(EnergyReportQueryVO requestBody) {
        Map<String, String> result = Maps.newHashMap();
        //分区或者分项分组2
        Map<String, List<ConfigSettingVO>> queryTypeGroup = energyGraphicsDataProcessor.getQueryTypeGroup(requestBody.getQueryType());
        //获取原始数据
        long getDBStartTime = System.currentTimeMillis();
        //同比不区分时间
        requestBody.setQueryValue(null);
        //本期数据
        List<EnergyReportResponseVO> energyReporyInfolist = energyReportService.getEnergyReporyInfolist(requestBody);
        LOGGER.info("获取能耗饼图数据耗时{}毫秒", System.currentTimeMillis() - getDBStartTime);
        if(CollectionUtils.isEmpty(energyReporyInfolist)){
            energyReporyInfolist= Lists.newArrayList();
        }
        double sum = 0d;
        double remain = 100;
        sum = energyReporyInfolist.stream().mapToDouble(i1 -> {
            return Double.parseDouble(i1.getEnergyValue());
        }).sum();

        Map<String, List<EnergyReportResponseVO>> currentGroup = energyReporyInfolist.stream().collect(Collectors.groupingBy(EnergyReportResponseVO::getTypeValue));
        Map<String, List<ConfigSettingVO>> finalQueryTypeGroup = queryTypeGroup;
        Set<String> keys = queryTypeGroup.keySet();
        int i = 0;
        for (String key : keys) {
            String settingValue = null;
            try {
                settingValue = queryTypeGroup.get(key).get(0).getSettingValue();
            } catch (Exception e) {
                LOGGER.error("分项不存在",e);


            }
            if(!StringUtils.isEmpty(settingValue)){
                if (i == keys.size() - 1) {
                    result.put(settingValue, String.valueOf(remain));
                }
                List<EnergyReportResponseVO> currentResponseVOS = currentGroup.get(key);
                if(CollectionUtils.isEmpty(currentResponseVOS)){
                    currentResponseVOS=Lists.newArrayList();
                }
                double currestSum = 0d;
                currestSum = currentResponseVOS.stream().mapToDouble(i1 -> {
                    return Double.parseDouble(i1.getEnergyValue());
                }).sum();

                String value = new BigDecimal(currestSum / sum).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_DOWN).toPlainString();
                remain = remain - Double.parseDouble(value);
                result.put(settingValue, value);
            }
            i++;
        }
        return result;

    }


}
