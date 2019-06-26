package com.landleaf.ibsaas.web.web.service.energyflow.processor;

import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.ibsaas.common.domain.energy.HlVl;
import com.landleaf.ibsaas.common.domain.energy.report.ProportionalDataList;
import com.landleaf.ibsaas.common.domain.energy.vo.ConfigSettingVO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportQueryVO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportResponseVO;
import com.landleaf.ibsaas.common.domain.energyflow.dto.TimeLineChartResponseDTO;
import com.landleaf.ibsaas.common.enums.energy.DimensionTypeEnum;
import com.landleaf.ibsaas.common.utils.date.CalendarUtil;
import com.landleaf.ibsaas.common.utils.date.DateUtils;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyReportService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EnergyHistogramChartProcessor extends AbstractEnergyChartProcessor {

    @Autowired
    private IEnergyReportService energyReportService;
    @Autowired
    private EnergyGraphicsDataProcessor energyGraphicsDataProcessor;

    /**
     * 能耗柱状图
     *
     * @param requestBody
     * @return
     */
    public HlVl getData(EnergyReportQueryVO requestBody) {
        HlVl result = new HlVl();
        Map<String, Map<String, List<String>>> tmpResult = Maps.newHashMap();
        //分区或者分项分组
        Map<String, List<ConfigSettingVO>> queryTypeGroup = energyGraphicsDataProcessor.getQueryTypeGroup(requestBody.getQueryType());
        //获取原始数据
        long getDBStartTime = System.currentTimeMillis();
        List<EnergyReportResponseVO> energyReporyInfolist = energyReportService.getEnergyReporyInfolist(requestBody);
        LOGGER.info("获取能耗柱状图数据耗时{}毫秒", System.currentTimeMillis() - getDBStartTime);
        //往前挪一个维度
        EnergyReportQueryVO targetBody = offsetEnergyReportDTO(requestBody);
        //前一阶段数据
        List<EnergyReportResponseVO> compareTargetInfolist = Lists.newArrayList();
        if(requestBody.getDateType().intValue()!=DimensionTypeEnum.YEAR.getType()){
            compareTargetInfolist = energyReportService.getEnergyReporyInfolist(targetBody);
        }
        if (CollectionUtils.isEmpty(energyReporyInfolist)) {
            energyReporyInfolist = Lists.newArrayList();
        }
        if (CollectionUtils.isEmpty(compareTargetInfolist)) {
            compareTargetInfolist = Lists.newArrayList();
        }

        Map<String, List<EnergyReportResponseVO>> sourceGroup = energyReporyInfolist.stream().collect(Collectors.groupingBy(EnergyReportResponseVO::getTimeValue));
        Map<String, List<EnergyReportResponseVO>> targetGroup = compareTargetInfolist.stream().collect(Collectors.groupingBy(EnergyReportResponseVO::getTimeValue));
        List<String> dateList = getDateList(requestBody);
        Integer queryValue = requestBody.getQueryValue();

        List<String> xs = new ArrayList<>();
        List<String> comp = new ArrayList<>();
        List<String> current = new ArrayList<>();
        for (String date : dateList) {
            List<EnergyReportResponseVO> sources = sourceGroup.get(date);
            String upPrevX = getPreDate(date, requestBody.getDateType());
            List<EnergyReportResponseVO> targets = targetGroup.get(upPrevX);
            if(CollectionUtils.isEmpty(sources)) {
                sources = Lists.newArrayList();
            }
            if(CollectionUtils.isEmpty(targets)) {
                targets = Lists.newArrayList();
            }
            double currestSum = 0d;
            double compareTargetSum = 0d;
            currestSum = sources.stream().mapToDouble(i1 -> {
                return Double.parseDouble(i1.getEnergyValue());
            }).sum();
            compareTargetSum = targets.stream().mapToDouble(i1 -> {
                return Double.parseDouble(i1.getEnergyValue());
            }).sum();

            xs.add(date);
            comp.add(String.valueOf(compareTargetSum));
            current.add(String.valueOf(currestSum));
        }
        ProportionalDataList ys = new ProportionalDataList(comp, current);
        return new HlVl(xs, ys);

}


    /**
     * 根据所选维度 变更更查询范围
     *
     * @param energyReportDTO
     */
    private EnergyReportQueryVO offsetEnergyReportDTO(EnergyReportQueryVO energyReportDTO) {
        EnergyReportQueryVO query = new EnergyReportQueryVO();
        BeanUtils.copyProperties(energyReportDTO, query);
        if (DimensionTypeEnum.HOUR.getType() == energyReportDTO.getDateType()) {
            query.setStartTime(DateUtils.convert(CalendarUtil.prevDay(DateUtils.convert(query.getStartTime()))));
            query.setEndTime(DateUtils.convert(CalendarUtil.prevDay(DateUtils.convert(query.getEndTime()))));
        }
        if (DimensionTypeEnum.DAY.getType() == energyReportDTO.getDateType()) {
            query.setStartTime(DateUtils.convert(CalendarUtil.prevMonth(DateUtils.convert(query.getStartTime()))));
            query.setEndTime(DateUtils.convert(CalendarUtil.prevMonth(DateUtils.convert(query.getEndTime()))));
        }
        if (DimensionTypeEnum.MONTH.getType() == energyReportDTO.getDateType()) {
            query.setStartTime(DateUtils.convert(CalendarUtil.prevYear(DateUtils.convert(query.getStartTime()))));
            query.setEndTime(DateUtils.convert(CalendarUtil.prevYear(DateUtils.convert(query.getEndTime()))));
        }
        if (DimensionTypeEnum.YEAR.getType() == energyReportDTO.getDateType()) {
            return energyReportDTO;
        }
        return query;
    }



}
