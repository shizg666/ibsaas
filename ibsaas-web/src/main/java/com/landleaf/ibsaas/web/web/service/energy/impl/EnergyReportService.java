package com.landleaf.ibsaas.web.web.service.energy.impl;

import com.google.common.collect.Lists;
import com.landleaf.ibsaas.common.dao.energy.ConfigSettingDao;
import com.landleaf.ibsaas.common.dao.energy.EnergyDataDao;
import com.landleaf.ibsaas.common.domain.energy.HlVl;
import com.landleaf.ibsaas.common.domain.energy.HlVlBO;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyReportDTO;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyReportExDTO;
import com.landleaf.ibsaas.common.domain.energy.report.EnergySavingEffectVO;
import com.landleaf.ibsaas.common.domain.energy.report.ProportionalData;
import com.landleaf.ibsaas.common.domain.energy.report.IntervalData;
import com.landleaf.ibsaas.common.domain.energy.report.ProportionalDataList;
import com.landleaf.ibsaas.common.domain.energy.vo.ConfigSettingVO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyOverviewTotalVO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportQueryVO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportResponseVO;
import com.landleaf.ibsaas.common.enums.energy.DimensionTypeEnum;
import com.landleaf.ibsaas.common.enums.energy.EnergyTypeEnum;
import com.landleaf.ibsaas.common.utils.date.CalendarUtil;
import com.landleaf.ibsaas.common.enums.energy.QueryTypeEnum;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Lokiy
 * @date 2019/6/18 16:03
 * @description:
 */
@Service
@Slf4j
public class EnergyReportService implements IEnergyReportService {

    private static final String EQUIP_CLASSIFICATION = "equip_classification";

    private static final String EQUIP_AREA = "equip_area";

    private static final String BUILDING_ACREAGE = "building_acreage";

    private static final String CHINESE_STANDARD_ENERGY_CONSUMPTION = "chinese_standard_energy_consumption";

    private static final String LGC = "lgc";

    private static final String PEOPLE_ATTENDANCE = "people_attendance";

    private static final BigDecimal BIGDECIMAL_100 = new BigDecimal(100);

    private static final BigDecimal DAY_OF_YEAR = new BigDecimal(365);

    @Autowired
    private EnergyDataDao energyDataDao;
    @Autowired
    private ConfigSettingDao configSettingDao;


    @Override
    public HlVl overviewLineChart(EnergyReportExDTO energyReportDTO) {
        List<HlVlBO> hlVlBOList = energyDataDao.overviewLineChart(energyReportDTO);
        return getHlVl(hlVlBOList);
    }

    @Override
    public HlVl overviewHistogram(EnergyReportExDTO energyReportDTO) {
        //现值
        List<HlVlBO> hlVlBOS = energyDataDao.overviewLineChart(energyReportDTO);
        //往前挪一个维度
        EnergyReportExDTO query = offsetEnergyReportDTO(energyReportDTO);
        Map<String, String> map = new HashMap<>(64);
        if(query!=energyReportDTO){
            //选择的不是年份  年份没有对比
            List<HlVlBO> tempList = energyDataDao.overviewLineChart(query);
            Map<String, String> tempMap = tempList.stream().collect(Collectors.toMap(
                    HlVlBO::getX,
                    HlVlBO::getY));
            map.putAll(tempMap);
        }

        List<String> xs = new ArrayList<>();
        List<String> comp = new ArrayList<>();
        List<String> current = new ArrayList<>();
//        List<ProportionalData> ys = new ArrayList<>();
        hlVlBOS.forEach( h -> {
            xs.add(h.getX());
            String upPrevX = getUpPrevX(h.getX(), energyReportDTO.getDateType());
            comp.add(map.get(upPrevX));
            current.add(h.getY());
//            ys.add(new ProportionalData(map.get(upPrevX), h.getY()));
        });
        ProportionalDataList ys = new ProportionalDataList(comp, current);
        return new HlVl(xs, ys);
    }



    @Override
    public EnergySavingEffectVO overviewSavingEffect(EnergyReportExDTO energyReportDTO) {
        LocalDateTime now = LocalDateTime.now();
        //当年能耗
        BigDecimal curConsumption = energyDataDao.getEnergyByYear(energyReportDTO.getEquipType(), now.getYear());
        BigDecimal dayOfYear = new BigDecimal(now.getDayOfYear());
        //根据类型获取能耗标准
        String standardConsumptionStr = configSettingDao.getStandardConsumption(CHINESE_STANDARD_ENERGY_CONSUMPTION, String.valueOf(energyReportDTO.getEquipType()), now.getYear());
        BigDecimal standardConsumption = new BigDecimal(standardConsumptionStr);
        BigDecimal staConsumption = null,savingConsumption = null;
        String savingPercent = null;
        if(EnergyTypeEnum.ENERGY_WATER.getEnergyType().equals(energyReportDTO.getEquipType())){
            //水能耗
            ConfigSettingVO lgcAcreageConfig = configSettingDao.getByTypeAndCode(LGC, PEOPLE_ATTENDANCE);
            BigDecimal peopleAttendance = new BigDecimal(lgcAcreageConfig.getSettingValue());
            //国标到现在的能耗
            staConsumption = dayOfYear.multiply(peopleAttendance).multiply(standardConsumption);
            //节能数
            savingConsumption = curConsumption.subtract(staConsumption);
            //节能率
            savingPercent = savingConsumption.divide(staConsumption, 4, BigDecimal.ROUND_HALF_UP).multiply(BIGDECIMAL_100).toString();

        }
        if(EnergyTypeEnum.ENERGY_ELECTRIC.getEnergyType().equals(energyReportDTO.getEquipType())){
            //电能耗
            //建筑面积
            ConfigSettingVO lgcAcreageConfig = configSettingDao.getByTypeAndCode(BUILDING_ACREAGE, LGC);
            BigDecimal lgcAcreage = new BigDecimal(lgcAcreageConfig.getSettingValue());
            //国标每天到现在的能耗
            staConsumption = lgcAcreage.multiply(standardConsumption).divide(DAY_OF_YEAR,2, BigDecimal.ROUND_HALF_UP).multiply(dayOfYear);
            //节能数
            savingConsumption = curConsumption.subtract(staConsumption);
            //节能率
            savingPercent = savingConsumption.divide(staConsumption,4, BigDecimal.ROUND_HALF_UP).multiply(BIGDECIMAL_100).toString();
        }
        return new EnergySavingEffectVO(staConsumption, savingPercent, savingConsumption);
    }

    @Override
    public HlVl overviewSavingEffectLineChart(EnergyReportExDTO energyReportDTO) {
        LocalDateTime now = LocalDateTime.now();
        List<IntervalData> energyData = energyDataDao.getEnergyDateByTime(energyReportDTO.getEquipType());
        List<IntervalData> standardData = configSettingDao.getIntervalStandardConsumption(CHINESE_STANDARD_ENERGY_CONSUMPTION, String.valueOf(energyReportDTO.getEquipType()));
        Map<String, BigDecimal> standardMap = standardData.stream().collect(Collectors.toMap(IntervalData::getTimeInterval, IntervalData::getIntervalValue));
        BigDecimal dayOfYear = new BigDecimal(now.getDayOfYear());
        List<String> xs = new ArrayList<>();
        List<String> comp = new ArrayList<>();
        List<String> current = new ArrayList<>();
//        List<ProportionalData> ys = new ArrayList<>();
        if(EnergyTypeEnum.ENERGY_WATER.getEnergyType().equals(energyReportDTO.getEquipType())){
            //水能耗
            ConfigSettingVO lgcAcreageConfig = configSettingDao.getByTypeAndCode(LGC, PEOPLE_ATTENDANCE);
            BigDecimal peopleAttendance = new BigDecimal(lgcAcreageConfig.getSettingValue());
            energyData.forEach( ed -> {
                xs.add(ed.getTimeInterval());

                if(now.getYear() == Integer.valueOf(ed.getTimeInterval())){
                    BigDecimal standardConsumption = standardMap.get(ed.getTimeInterval());
                    //国标到现在的能耗
                    BigDecimal staConsumption = dayOfYear.multiply(peopleAttendance).multiply(standardConsumption);
                    comp.add(standardMap.get(ed.getTimeInterval()).toString());
                    current.add(staConsumption.toString());

//                    ys.add(new ProportionalData(standardMap.get(ed.getTimeInterval()).toString(), staConsumption.toString()));

                }else {
                    BigDecimal consumption = ed.getIntervalValue().multiply(DAY_OF_YEAR).multiply(peopleAttendance);

                    comp.add(standardMap.get(ed.getTimeInterval()).toString());
                    current.add(consumption.toString());
//                    ys.add(new ProportionalData(standardMap.get(ed.getTimeInterval()).toString(), consumption.toString()));
                }
            });

        }
        if(EnergyTypeEnum.ENERGY_ELECTRIC.getEnergyType().equals(energyReportDTO.getEquipType())){
            //电能耗
            //建筑面积
            ConfigSettingVO lgcAcreageConfig = configSettingDao.getByTypeAndCode(BUILDING_ACREAGE, LGC);
            BigDecimal lgcAcreage = new BigDecimal(lgcAcreageConfig.getSettingValue());
            energyData.forEach( ed -> {
                xs.add(ed.getTimeInterval());
                if(now.getYear() == Integer.valueOf(ed.getTimeInterval())){
                    BigDecimal standardConsumption = standardMap.get(ed.getTimeInterval());
                    //国标每天到现在的能耗
                    BigDecimal staConsumption = lgcAcreage.multiply(standardConsumption).divide(DAY_OF_YEAR, 2, BigDecimal.ROUND_HALF_UP).multiply(dayOfYear);
                    comp.add(staConsumption.toString());
                    current.add(ed.getIntervalValue().toString());

//                    ys.add(new ProportionalData(staConsumption.toString(), ed.getIntervalValue().toString()));
                }else {
                    BigDecimal consumption = ed.getIntervalValue().divide(lgcAcreage, 2, BigDecimal.ROUND_HALF_UP);

                    comp.add(standardMap.get(ed.getTimeInterval()).toString());
                    current.add(consumption.toString());

//                    ys.add(new ProportionalData(standardMap.get(ed.getTimeInterval()).toString(), consumption.toString()));
                }
            });



        }
        ProportionalDataList ys = new ProportionalDataList(comp, current);
        return new HlVl(xs, ys);
    }

    @Override
    public HlVl overviewRankingClassification(EnergyReportExDTO energyReportDTO) {
        List<HlVlBO> hlVlBOList = energyDataDao.getEnergyRanking(
                energyReportDTO.getStartTime(),
                energyReportDTO.getEndTime(),
                energyReportDTO.getEquipType(),
                EQUIP_CLASSIFICATION,
                5);
        return getHlVl(hlVlBOList);
    }

    @Override
    public HlVl overviewRankingArea(EnergyReportExDTO energyReportDTO) {
        List<HlVlBO> hlVlBOList = energyDataDao.getEnergyRanking(
                energyReportDTO.getStartTime(),
                energyReportDTO.getEndTime(),
                energyReportDTO.getEquipType(),
                EQUIP_AREA,
                3);
        return getHlVl(hlVlBOList);
    }

    @Override
    public String overviewYoy(EnergyReportExDTO energyReportDTO) {
        BigDecimal curValue = energyDataDao.getEnergyByDate(energyReportDTO);
        EnergyReportExDTO query = offsetEnergyReportDTO(energyReportDTO);
        BigDecimal prevValue = BigDecimal.ZERO;
        if(query!=energyReportDTO){
            //维度不是年
            prevValue = energyDataDao.getEnergyByDate(query);
        }
        return getProportion(prevValue, curValue);
    }

    @Override
    public String overviewQoq(EnergyReportExDTO energyReportDTO) {
        BigDecimal curValue = energyDataDao.getEnergyByDate(energyReportDTO);
        EnergyReportExDTO query = offsetParallelEnergyReportDTO(energyReportDTO);
        BigDecimal prevValue = energyDataDao.getEnergyByDate(query);
        return getProportion(prevValue, curValue);
    }

    /**
     * 根据现值 和 上值 求得比例
     * @param prevValue
     * @param curValue
     * @return
     */
    private String getProportion(BigDecimal prevValue, BigDecimal curValue){
        BigDecimal subtract = curValue.subtract(prevValue);
        BigDecimal divide = BigDecimal.ZERO.compareTo(prevValue) == 0 ? BigDecimal.ONE : subtract.divide(prevValue, 4, BigDecimal.ROUND_HALF_UP);
        BigDecimal percent = divide.multiply(BIGDECIMAL_100);
        return percent.toString();
    }


    @Override
    public EnergyOverviewTotalVO overviewTotal(EnergyReportExDTO energyReportDTO) {
        LocalDate now = LocalDate.now();
        //构建当天的查询开始日期喝结束日期
        energyReportDTO.setStartTime(CalendarUtil.localDate2Date(now));
        energyReportDTO.setEndTime(CalendarUtil.localDate2Date(now.plusDays(1)));
        BigDecimal dayEnergy = energyDataDao.getEnergyByDate(energyReportDTO);
        //当周的开始和下周的开始
        LocalDate weekFirstDay = now.with(DayOfWeek.MONDAY);
        energyReportDTO.setStartTime(CalendarUtil.localDate2Date(weekFirstDay));
        energyReportDTO.setEndTime(CalendarUtil.localDate2Date(weekFirstDay.plusDays(7)));
        BigDecimal weekEnergy = energyDataDao.getEnergyByDate(energyReportDTO);
        //当月的第一天
        LocalDate monthFirstDay = LocalDate.of(now.getYear(), now.getMonth(), 1);
        energyReportDTO.setStartTime(CalendarUtil.localDate2Date(monthFirstDay));
        energyReportDTO.setEndTime(CalendarUtil.localDate2Date(monthFirstDay.plusMonths(1)));
        BigDecimal monthEnergy = energyDataDao.getEnergyByDate(energyReportDTO);
        //当年的第一天
        LocalDate yearFirstDay = LocalDate.of(now.getYear(), 1, 1);
        energyReportDTO.setStartTime(CalendarUtil.localDate2Date(yearFirstDay));
        energyReportDTO.setEndTime(CalendarUtil.localDate2Date(yearFirstDay.plusYears(1)));
        BigDecimal yearEnergy = energyDataDao.getEnergyByDate(energyReportDTO);
        //建筑面积
        ConfigSettingVO configSetting = configSettingDao.getByTypeAndCode(BUILDING_ACREAGE, LGC);
        BigDecimal buildingAcreage = new BigDecimal(configSetting.getSettingValue());

        return new EnergyOverviewTotalVO(
                buildingAcreage,
                dayEnergy,
                weekEnergy,
                monthEnergy,
                yearEnergy,
                yearEnergy.divide(buildingAcreage, 2, BigDecimal.ROUND_HALF_UP));
    }


    /**
     * 根据给定的时间x 上升维度后提前一个方位 获取的值
     * @param x
     * @param dateType
     * @return
     */
    private String getUpPrevX(String x, Integer dateType){
        if(DimensionTypeEnum.HOUR.getType() == dateType){
            return CalendarUtil.date2Str(
                    CalendarUtil.prevDay(
                            CalendarUtil.str2Date(x)));
        }
        if(DimensionTypeEnum.DAY.getType() == dateType){
            return CalendarUtil.date2StrPattern(
                    CalendarUtil.prevMonth(
                            CalendarUtil.str2DatePattern(x, CalendarUtil.YYYY_MM_DD)), CalendarUtil.YYYY_MM_DD);
        }
        if(DimensionTypeEnum.MONTH.getType() == dateType){
            return CalendarUtil.date2StrPattern(
                    CalendarUtil.prevYear(
                            CalendarUtil.str2DatePattern(x, CalendarUtil.YYYY_MM)), CalendarUtil.YYYY_MM);
        }
        if(DimensionTypeEnum.YEAR.getType() == dateType){

        }
        return null;
    }


    /**
     * 根据查询结果变更返回值
     * @param hlVlBOList
     * @return
     */
    private HlVl getHlVl(List<HlVlBO> hlVlBOList) {
        List<String> xs = new ArrayList<>(), ys = new ArrayList<>();
        hlVlBOList.forEach( h -> {
            xs.add(h.getX());
            ys.add(h.getY());
        });
        return new HlVl(xs, ys);
    }

    /**
     * 根据所选维度 变更更查询范围
     * @param energyReportDTO
     */
    private EnergyReportExDTO offsetEnergyReportDTO(EnergyReportExDTO energyReportDTO){
        EnergyReportExDTO query = new EnergyReportExDTO();
        BeanUtils.copyProperties(energyReportDTO, query);
        if(DimensionTypeEnum.HOUR.getType() == energyReportDTO.getDateType()){
            query.setStartTime(CalendarUtil.prevDay(query.getStartTime()));
            query.setEndTime(CalendarUtil.prevDay(query.getEndTime()));
        }
        if(DimensionTypeEnum.DAY.getType() == energyReportDTO.getDateType()){
            query.setStartTime(CalendarUtil.prevMonth(query.getStartTime()));
            query.setEndTime(CalendarUtil.prevMonth(query.getEndTime()));
        }
        if(DimensionTypeEnum.MONTH.getType() == energyReportDTO.getDateType()){
            query.setStartTime(CalendarUtil.prevYear(query.getStartTime()));
            query.setEndTime(CalendarUtil.prevYear(query.getEndTime()));
        }
        if(DimensionTypeEnum.YEAR.getType() == energyReportDTO.getDateType()){
            return energyReportDTO;
        }
        return query;
    }

    /**
     * 根据所选维度 往前推查询范围
     * @param energyReportDTO
     */
    private EnergyReportExDTO offsetParallelEnergyReportDTO(EnergyReportExDTO energyReportDTO){
        EnergyReportExDTO query = new EnergyReportExDTO();
        BeanUtils.copyProperties(energyReportDTO, query);
        if(DimensionTypeEnum.HOUR.getType() == energyReportDTO.getDateType()){
            long hours = CalendarUtil.diff(query.getStartTime(), query.getEndTime(), ChronoUnit.HOURS);
            query.setStartTime(CalendarUtil.offsetDate(query.getStartTime(), -hours, ChronoUnit.HOURS));
            query.setEndTime(CalendarUtil.offsetDate(query.getEndTime(), -hours, ChronoUnit.HOURS));
        }
        if(DimensionTypeEnum.DAY.getType() == energyReportDTO.getDateType()){
            long days = CalendarUtil.diff(query.getStartTime(), query.getEndTime(), ChronoUnit.DAYS);
            query.setStartTime(CalendarUtil.offsetDate(query.getStartTime(), -days, ChronoUnit.DAYS));
            query.setEndTime(CalendarUtil.offsetDate(query.getEndTime(), -days, ChronoUnit.DAYS));
        }
        if(DimensionTypeEnum.MONTH.getType() == energyReportDTO.getDateType()){
            long months = CalendarUtil.diff(query.getStartTime(), query.getEndTime(), ChronoUnit.MONTHS);
            query.setStartTime(CalendarUtil.offsetDate(query.getStartTime(), -months, ChronoUnit.MONTHS));
            query.setEndTime(CalendarUtil.offsetDate(query.getEndTime(), -months, ChronoUnit.MONTHS));
        }
        if(DimensionTypeEnum.YEAR.getType() == energyReportDTO.getDateType()){
            long years = CalendarUtil.diff(query.getStartTime(), query.getEndTime(), ChronoUnit.YEARS);
            query.setStartTime(CalendarUtil.offsetDate(query.getStartTime(), -years, ChronoUnit.YEARS));
            query.setEndTime(CalendarUtil.offsetDate(query.getEndTime(), -years, ChronoUnit.YEARS));
        }
        return query;
    }


    @Override
    public HlVl areaLineChart(EnergyReportDTO energyReportDTO) {
        return null;
    }

    @Override
    public HlVl areaHistogram(EnergyReportDTO energyReportDTO) {
        return null;
    }

    @Override
    public HlVl areaPieChart(EnergyReportDTO energyReportDTO) {
        return null;
    }

    @Override
    public HlVl areaYoy(EnergyReportDTO energyReportDTO) {
        return null;
    }

    @Override
    public HlVl areaQoq(EnergyReportDTO energyReportDTO) {
        return null;
    }

    @Override
    public HlVl classificationLineChart(EnergyReportDTO energyReportDTO) {
        return null;
    }

    @Override
    public HlVl classificationHistogram(EnergyReportDTO energyReportDTO) {
        return null;
    }

    @Override
    public HlVl classificationPieChart(EnergyReportDTO energyReportDTO) {
        return null;
    }

    @Override
    public HlVl classificationYoy(EnergyReportDTO energyReportDTO) {
        return null;
    }

    @Override
    public HlVl classificationQoq(EnergyReportDTO energyReportDTO) {
        return null;
    }

    @Override
    public List<EnergyReportResponseVO> getEnergyReporyInfolist(EnergyReportQueryVO queryVO) {
        List<EnergyReportResponseVO> responseVOS = Lists.newArrayList();
        DimensionTypeEnum dimensionTypeEnum = DimensionTypeEnum.getInstByType(queryVO.getDateType());
        String columValue = dimensionTypeEnum.code;
        queryVO.setDateCode(columValue);
        if (QueryTypeEnum.TYPE.getType() == queryVO.getQueryType()){
            //分项统计
            responseVOS = getEnergyReporyInfoByType(queryVO);
        }else {
            //分区统计
            responseVOS = getEnergyReporyInfoByArea(queryVO);
        }
        if (DimensionTypeEnum.HOUR.getType() == queryVO.getDateType()){
            responseVOS.forEach(o->{
                String str = o.getTimeValue();
                o.setTimeValue(str.substring(0,str.lastIndexOf(":")));
            });
        }
        return responseVOS;
    }

    private List<EnergyReportResponseVO> getEnergyReporyInfoByArea(EnergyReportQueryVO queryVO) {
        return energyDataDao.getEnergyReporyInfoByArea(queryVO);
    }
    private List<EnergyReportResponseVO> getEnergyReporyInfoByType(EnergyReportQueryVO queryVO) {
        return energyDataDao.getEnergyReporyInfoByType(queryVO);
    }

}
