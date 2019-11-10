package com.landleaf.ibsaas.web.web.service.energyflow.processor;

import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.ibsaas.common.domain.energy.HlVl;
import com.landleaf.ibsaas.common.domain.energy.vo.ConfigSettingVO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportQueryVO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportResponseVO;
import com.landleaf.ibsaas.common.domain.energyflow.dto.TimeLineChartResponseDTO;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EnergyTimeLineChartProcessor extends AbstractEnergyChartProcessor {

    @Autowired
    private IEnergyReportService energyReportService;
    @Autowired
    private EnergyGraphicsDataProcessor energyGraphicsDataProcessor;

    /**
     * 能耗时间拆线图
     *
     * @param requestBody
     * @return
     */
    public HlVl getData(EnergyReportQueryVO requestBody) {
        HlVl result =new HlVl();
        Map<String, Map<String, List<String>>> tmpResult = Maps.newHashMap();
        //分区或者分项分组
        Map<String, List<ConfigSettingVO>> queryTypeGroup = energyGraphicsDataProcessor.getQueryTypeGroup(requestBody.getQueryType(),requestBody.getEnergyType());
        //获取原始数据
        long getDBStartTime = System.currentTimeMillis();
        List<EnergyReportResponseVO> energyReporyInfolist = energyReportService.getEnergyReporyInfolist(requestBody);
        LOGGER.info("获取能耗时间拆线图耗时{}毫秒", System.currentTimeMillis() - getDBStartTime);
        if(CollectionUtils.isEmpty(energyReporyInfolist)){
            energyReporyInfolist= Lists.newArrayList();
        }
        Map<String, List<EnergyReportResponseVO>> group = energyReporyInfolist.stream().collect(Collectors.groupingBy(EnergyReportResponseVO::getTypeValue));
        List<String> dateList = getDateList(requestBody);
        Map<String, List<ConfigSettingVO>> finalQueryTypeGroup = queryTypeGroup;
        Integer queryValue = requestBody.getQueryValue();
        queryTypeGroup.forEach((i, v) -> {
            if (queryValue != null && !StringUtils.equals(i, String.valueOf(queryValue))) {
                return;
            }
            Map<String, List<String>> dataMap = Maps.newHashMap();
            List<TimeLineChartResponseDTO> tmpList = Lists.newArrayList();
            String settingValue = null;
            try {
                settingValue = v.get(0).getSettingValue();
            } catch (Exception e) {
                LOGGER.error("分项不存在",e);
            }
            List<EnergyReportResponseVO> currentResponseVOS = group.get(i);
            if(CollectionUtils.isEmpty(currentResponseVOS)){
                currentResponseVOS=Lists.newArrayList();
            }
            if(!StringUtils.isEmpty(settingValue)){
                for (String date : dateList) {
                    TimeLineChartResponseDTO temp = new TimeLineChartResponseDTO();
                    String x = date;
                    String y = "0.00";
                    List<EnergyReportResponseVO> filterList = currentResponseVOS.stream().filter(i2 -> {
                        if (StringUtils.equals(date, i2.getTimeValue())) {
                            return true;
                        }
                        return false;
                    }).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(filterList)) {
//                    filterList.sort((o1, o2) -> o1.getTypeValue() - o2.getTypeValue());
                        y = filterList.get(0).getEnergyValue();
                    }
                    temp.setX(x);
                    temp.setY(y);
                    tmpList.add(temp);
                }
                List<String> xList = Lists.newArrayList();
                List<String> yList = Lists.newArrayList();
                if (!CollectionUtils.isEmpty(tmpList)) {
                    xList = tmpList.stream().map(TimeLineChartResponseDTO::getX).collect(Collectors.toList());
                    yList = tmpList.stream().map(TimeLineChartResponseDTO::getY).collect(Collectors.toList());
                }
                dataMap.put("x", xList);
                dataMap.put("y", yList);
                tmpResult.put(settingValue, dataMap);
            }

        });

        if(tmpResult!=null&&tmpResult.size()>0){
            List<Object> xs = Lists.newArrayList();
            List<Map<String,Object>> ys = Lists.newArrayList();
            tmpResult.forEach((i,v)->{
                Map<String,Object> data = Maps.newHashMap();
                data.put(i,v.get("y"));
                xs.clear();
                xs.addAll(v.get("x"));
                ys.add(data);
            });
            result.setXs(xs);
            result.setYs(ys);
        }

        return result;

    }


}
