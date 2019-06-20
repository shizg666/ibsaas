package com.landleaf.ibsaas.web.web.service.energyflow.handler.data;

import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.ibsaas.common.domain.energy.vo.ConfigSettingVO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportQueryVO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportResponseVO;
import com.landleaf.ibsaas.common.domain.energyflow.dto.TimeLineChartResponseDTO;
import com.landleaf.ibsaas.common.enums.energy.EnergyGraphicsEnum;
import com.landleaf.ibsaas.web.web.service.energy.IConfigSettingService;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyReportService;
import com.landleaf.ibsaas.web.web.service.energyflow.handler.data.asyn.IEnergyFutureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class EnergyGraphicsDataProvider extends AbstractEnergyGraphicsDataProvider {

    @Autowired
    private IEnergyReportService energyReportService;
    @Autowired
    private IConfigSettingService configSettingService;
    @Autowired
    private IEnergyFutureService energyFutureService;

    /**
     * 能耗时间拆线图
     *
     * @param requestBody
     * @return
     */
    public Map<String, Map<String, List<String>>> timeLineChart(EnergyReportQueryVO requestBody) {
        Map<String, Map<String, List<String>>> result = Maps.newHashMap();

        List<ConfigSettingVO> classificationList = configSettingService.typeList("equip_classification");
        List<ConfigSettingVO> areaList = configSettingService.typeList("equip_area");

        Map<String, List<ConfigSettingVO>> classificationGroup = classificationList.stream().collect(Collectors.groupingBy(ConfigSettingVO::getSettingCode));
        Map<String, List<ConfigSettingVO>> areaGroup = areaList.stream().collect(Collectors.groupingBy(ConfigSettingVO::getSettingCode));
        Map<String, List<ConfigSettingVO>> queryTypeGroup = null;

        Integer queryType = requestBody.getQueryType();
        switch (queryType) {
            case 1:
                //分区
                queryTypeGroup = areaGroup;
                break;
            case 2:
                //分项
                queryTypeGroup = classificationGroup;
                break;
        }
        List<EnergyReportResponseVO> energyReporyInfolist = energyReportService.getEnergyReporyInfolist(requestBody);
        Map<Integer, List<EnergyReportResponseVO>> group = Maps.newHashMap();
        List<String> dateList = getDateList(requestBody);

        Map<String, List<ConfigSettingVO>> finalQueryTypeGroup = queryTypeGroup;
        group.forEach((i, v) -> {
            Map<String, List<String>> dataMap = Maps.newHashMap();
            List<TimeLineChartResponseDTO> tmpList = Lists.newArrayList();
            String settingCodeName = finalQueryTypeGroup.get(i).get(0).getSettingCodeName();
            for (String date : dateList) {
                TimeLineChartResponseDTO temp = new TimeLineChartResponseDTO();
                String x = date;
                String y = "0";
                List<EnergyReportResponseVO> filterList = v.stream().filter(i2 -> {
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
            result.put(settingCodeName, dataMap);

        });

        return result;

    }


    public void getOrginData(){
        Map<String ,List<EnergyReportResponseVO>> orginData = Maps.newHashMap();
        LOGGER.info("*************************获取原始数据*****************************");
        long getDateStartTime = System.currentTimeMillis();
        Map<String, Future> futureMap = Maps.newHashMap();
        for (EnergyGraphicsEnum enumObj : EnergyGraphicsEnum.values()) {
            Future<List<EnergyReportResponseVO>> future = energyFutureService.handlerMsg(enumObj, this.reportQueryVO);
            futureMap.put(enumObj.getCode(),future);
        }
        futureMap.forEach((i,v)->{
            try {
                List<EnergyReportResponseVO> o = (List<EnergyReportResponseVO>) v.get();
                orginData.put(i,o);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        LOGGER.info("*************************耗时:{}毫秒*****************************",(System.currentTimeMillis()-getDateStartTime));
        this.orginData= orginData;
    }


}
