package com.landleaf.ibsaas.web.web.service.energyflow.processor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.ibsaas.common.domain.energy.vo.ConfigSettingVO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportQueryVO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportResponseVO;
import com.landleaf.ibsaas.common.utils.date.DateUtils;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyReportService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 同比图
 */
@Component
public class EnergyYearOnYearChartProcessor extends AbstractEnergyChartProcessor {

    @Autowired
    private IEnergyReportService energyReportService;
    @Autowired
    private EnergyGraphicsDataProcessor energyGraphicsDataProcessor;

    /**
     * 能耗同比
     *
     * @param requestBody
     * @return
     */
    public Map<String, String> getData(EnergyReportQueryVO requestBody) {
        Map<String, String> result = Maps.newHashMap();
        //分区或者分项分组
        Map<String, List<ConfigSettingVO>> queryTypeGroup = energyGraphicsDataProcessor.getQueryTypeGroup(requestBody.getQueryType());
        //获取原始数据
        long getDBStartTime = System.currentTimeMillis();
        //同比不区分时间
        requestBody.setQueryValue(null);
        //本期与去年同期比较各减去一年
        EnergyReportQueryVO targetBody = getCompareTarget(requestBody);
        //本期数据
        List<EnergyReportResponseVO> energyReporyInfolist = energyReportService.getEnergyReporyInfolist(requestBody);
        //比较数据
        List<EnergyReportResponseVO> compareTargetInfolist = energyReportService.getEnergyReporyInfolist(targetBody);
        LOGGER.info("获取能耗同比图数据耗时{}毫秒", System.currentTimeMillis() - getDBStartTime);
        if(CollectionUtils.isEmpty(energyReporyInfolist)){
            energyReporyInfolist= Lists.newArrayList();
        }
        if(CollectionUtils.isEmpty(compareTargetInfolist)){
            compareTargetInfolist= Lists.newArrayList();
        }
        //计算 ：该时间段相比去年同一时间段的和值比较
        /**
         * 同比增长速度=
         (本期数-去年同期数)/去年同期数
         */

        Map<String, List<EnergyReportResponseVO>> currentGroup = energyReporyInfolist.stream().collect(Collectors.groupingBy(EnergyReportResponseVO::getTypeValue));
        Map<String, List<EnergyReportResponseVO>> compareTargetGroup = compareTargetInfolist.stream().collect(Collectors.groupingBy(EnergyReportResponseVO::getTypeValue));
        List<String> dateList = getDateList(requestBody);
        Map<String, List<ConfigSettingVO>> finalQueryTypeGroup = queryTypeGroup;
        queryTypeGroup.forEach((String i, List<ConfigSettingVO> v) -> {
            List<EnergyReportResponseVO> currentResponseVOS = currentGroup.get(i);
            List<EnergyReportResponseVO> compareTargetResponseVOS = compareTargetGroup.get(i);
            String settingValue = null;
            try {
                settingValue = v.get(0).getSettingValue();
            } catch (Exception e) {
                LOGGER.error("分项不存在",e);
            }
            if(!StringUtils.isEmpty(settingValue)){
                double currestSum = 0d;
                double compareTargetSum = 0d;
                currestSum = currentResponseVOS.stream().mapToDouble(i1 -> {
                    return Double.parseDouble(i1.getEnergyValue());
                }).sum();
                compareTargetSum = compareTargetResponseVOS.stream().mapToDouble(i1 -> {
                    return Double.parseDouble(i1.getEnergyValue());
                }).sum();
                String value = new BigDecimal((currestSum - compareTargetSum) / compareTargetSum==0?1:compareTargetSum).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_DOWN).toPlainString();
                result.put(settingValue, value);
            }
        });
        return result;

    }

    //获取比较的去年同期数（即原始数据减一年）
    private EnergyReportQueryVO getCompareTarget(EnergyReportQueryVO requestBody) {
        EnergyReportQueryVO target = new EnergyReportQueryVO();
        BeanUtils.copyProperties(requestBody, target);
        String startTime = target.getStartTime();
        String endTime = target.getEndTime();
        target.setStartTime(DateUtils.convert(DateUtils.getDateBeforeYear(DateUtils.convert(startTime))));
        target.setEndTime(DateUtils.convert(DateUtils.getDateBeforeYear(DateUtils.convert(endTime))));
        return target;
    }


}
