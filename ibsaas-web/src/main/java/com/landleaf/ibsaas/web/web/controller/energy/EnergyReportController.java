package com.landleaf.ibsaas.web.web.controller.energy;

import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.energy.HlVl;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyReportDTO;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyReportExDTO;
import com.landleaf.ibsaas.common.domain.energy.report.EnergySavingEffectVO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyOverviewTotalVO;
import com.landleaf.ibsaas.common.enums.energy.EnergyGraphicsEnum;
import com.landleaf.ibsaas.common.enums.energy.QueryTypeEnum;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyReportService;
import com.landleaf.ibsaas.web.web.service.energy.impl.EnergyAsynService;
import com.landleaf.ibsaas.web.web.service.energyflow.IEnergyConsumeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Lokiy
 * @date 2019/6/18 12:38
 * @description:
 */
@RestController
@RequestMapping("energy-report")
@AllArgsConstructor
@Slf4j
@Api("能耗报表控制类")
public class EnergyReportController extends BasicController {

    private final IEnergyReportService iEnergyReportService;

    @Autowired
    private IEnergyConsumeService energyConsumeService;

    @Autowired
    private EnergyAsynService energyAsynService;


    @PostMapping("/overview/all")
    @ApiOperation("能耗总览-全部")
    public Response all(@RequestBody EnergyReportExDTO energyReportDTO) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.all 入参为:{}", energyReportDTO);
        Map<String, Object> result = energyAsynService.asynExecuteService(energyReportDTO);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.all 出参为:{}", result);
        return returnSuccess(result);
    }

    /**
     * 能源总览栏
     */
    @PostMapping("/overview/line-chart")
    @ApiOperation("能耗总览-折线图")
    public Response overviewLineChart(@RequestBody EnergyReportExDTO energyReportDTO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.overviewLineChart 入参为:{}", energyReportDTO);
        HlVl result = iEnergyReportService.overviewLineChart(energyReportDTO);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.overviewLineChart 出参为:{}", result);
        return returnSuccess(result);
    }

    @PostMapping("/overview/histogram")
    @ApiOperation("能耗总览-柱状图")
    public Response overviewHistogram(@RequestBody EnergyReportExDTO energyReportDTO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.overviewHistogram 入参为:{}", energyReportDTO);
        HlVl result = iEnergyReportService.overviewHistogram(energyReportDTO);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.overviewHistogram 出参为:{}", result);
        return returnSuccess(result);
    }


    @PostMapping("/overview/saving-effect")
    @ApiOperation("能耗总览-节能效果")
    public Response overviewSavingEffect(@RequestBody EnergyReportExDTO energyReportDTO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.overviewSavingEffect 入参为:{}", energyReportDTO);
        EnergySavingEffectVO result = iEnergyReportService.overviewSavingEffect(energyReportDTO);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.overviewSavingEffect 出参为:{}", result);
        return returnSuccess(result);
    }


    @PostMapping("/overview/saving-effect-line-chart")
    @ApiOperation("能耗总览-节能效果折线图")
    public Response overviewSavingEffectLineChart(@RequestBody EnergyReportExDTO energyReportDTO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.overviewSavingEffectLineChart 入参为:{}", energyReportDTO);
        HlVl result = iEnergyReportService.overviewSavingEffectLineChart(energyReportDTO);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.overviewSavingEffectLineChart 出参为:{}", result);
        return returnSuccess(result);
    }



    @PostMapping("/overview/ranking/classification-ranking")
    @ApiOperation("能耗总览-能耗排行TOP5项")
    public Response overviewRankingClassification(@RequestBody EnergyReportExDTO energyReportDTO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.overviewRankingClassification 入参为:{}", energyReportDTO);
        HlVl result = iEnergyReportService.overviewRankingClassification(energyReportDTO);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.overviewRankingClassification 出参为:{}", result);
        return returnSuccess(result);
    }

    @PostMapping("/overview/ranking/area-ranking")
    @ApiOperation("能耗总览-能耗排行TOP3区")
    public Response overviewRankingArea(@RequestBody EnergyReportExDTO energyReportDTO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.overviewRankingArea 入参为:{}", energyReportDTO);
        HlVl result = iEnergyReportService.overviewRankingArea(energyReportDTO);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.overviewRankingArea 出参为:{}", result);
        return returnSuccess(result);
    }


    @PostMapping("/overview/yoy")
    @ApiOperation("能耗总览-同比")
    public Response overviewYoy(@RequestBody EnergyReportExDTO energyReportDTO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.overviewYoy 入参为:{}", energyReportDTO);
        String result = iEnergyReportService.overviewYoy(energyReportDTO);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.overviewYoy 出参为:{}", result);
        return returnSuccess(result);
    }


    @PostMapping("/overview/qoq")
    @ApiOperation("能耗总览-环比")
    public Response overviewQoq(@RequestBody EnergyReportExDTO energyReportDTO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.overviewQoq 入参为:{}", energyReportDTO);
        String result = iEnergyReportService.overviewQoq(energyReportDTO);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.overviewQoq 出参为:{}", result);
        return returnSuccess(result);
    }


    @PostMapping("/overview/total")
    @ApiOperation("能耗总览-累计能耗")
    public Response overviewTotal(@RequestBody EnergyReportExDTO energyReportDTO){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.overviewTotal 入参为:空");
        EnergyOverviewTotalVO result = iEnergyReportService.overviewTotal(energyReportDTO);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.overviewTotal 出参为:{}", result);
        return returnSuccess(result);
    }


    /**
     * 区域能耗
     */
    @PostMapping("/area/line-chart")
    @ApiOperation("区域能耗-折线图")
    public Response areaLineChart(@RequestBody EnergyReportDTO energyReportDTO) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.areaLineChart 入参为:{}", energyReportDTO);


        Object result = energyConsumeService.energyFlowForOne(QueryTypeEnum.AREA.type, energyReportDTO.getEquipArea(),
                energyReportDTO.getDateType(), energyReportDTO.getEquipType(), energyReportDTO.getStartTime(),
                energyReportDTO.getEndTime(), EnergyGraphicsEnum.TIME_LINE_CHART);

        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.areaLineChart 出参为:{}", result);
        return returnSuccess(result);
    }

    @PostMapping("/area/histogram")
    @ApiOperation("区域能耗-柱状图")
    public Response areaHistogram(@RequestBody EnergyReportDTO energyReportDTO) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.areaHistogram 入参为:{}", energyReportDTO);

        Object result = energyConsumeService.energyFlowForOne(QueryTypeEnum.AREA.type, energyReportDTO.getEquipArea(),
                energyReportDTO.getDateType(), energyReportDTO.getEquipType(), energyReportDTO.getStartTime(),
                energyReportDTO.getEndTime(), EnergyGraphicsEnum.HISTOGRAM_CHART);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.areaHistogram 出参为:{}", result);
        return returnSuccess(result);
    }

    @PostMapping("/area/pie-chart")
    @ApiOperation("区域能耗-饼图")
    public Response areaPieChart(@RequestBody EnergyReportDTO energyReportDTO) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.areaPieChart 入参为:{}", energyReportDTO);
        Object result = energyConsumeService.energyFlowForOne(QueryTypeEnum.AREA.type, energyReportDTO.getEquipArea(),
                energyReportDTO.getDateType(), energyReportDTO.getEquipType(), energyReportDTO.getStartTime(),
                energyReportDTO.getEndTime(), EnergyGraphicsEnum.SHARE_PIE_CHART);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.areaPieChart 出参为:{}", result);
        return returnSuccess(result);
    }

    @PostMapping("/area/yoy")
    @ApiOperation("区域能耗-同比")
    public Response areaYoy(@RequestBody EnergyReportDTO energyReportDTO) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.areaYoy 入参为:{}", energyReportDTO);

        Object result = energyConsumeService.energyFlowForOne(QueryTypeEnum.AREA.type, energyReportDTO.getEquipArea(),
                energyReportDTO.getDateType(), energyReportDTO.getEquipType(), energyReportDTO.getStartTime(),
                energyReportDTO.getEndTime(), EnergyGraphicsEnum.YEAR_ON_YEAR_CHART);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.areaYoy 出参为:{}", result);
        return returnSuccess(result);
    }


    @PostMapping("/area/qoq")
    @ApiOperation("区域能耗-环比")
    public Response areaQoq(@RequestBody EnergyReportDTO energyReportDTO) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.areaQoq 入参为:{}", energyReportDTO);

        Object result = energyConsumeService.energyFlowForOne(QueryTypeEnum.AREA.type, energyReportDTO.getEquipArea(),
                energyReportDTO.getDateType(), energyReportDTO.getEquipType(), energyReportDTO.getStartTime(),
                energyReportDTO.getEndTime(), EnergyGraphicsEnum.RING_RATIO_CHART);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.areaQoq 出参为:{}", result);
        return returnSuccess(result);
    }


    /**
     * 分项能耗
     */

    @PostMapping("/classification/line-chart")
    @ApiOperation("分项能耗-折线图")
    public Response classificationLineChart(@RequestBody EnergyReportDTO energyReportDTO) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.classificationLineChart 入参为:{}", energyReportDTO);

        Object result = energyConsumeService.energyFlowForOne(QueryTypeEnum.TYPE.type, energyReportDTO.getEquipClassification(),
                energyReportDTO.getDateType(), energyReportDTO.getEquipType(), energyReportDTO.getStartTime(),
                energyReportDTO.getEndTime(), EnergyGraphicsEnum.TIME_LINE_CHART);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.classificationLineChart 出参为:{}", result);
        return returnSuccess(result);
    }

    @PostMapping("/classification/histogram")
    @ApiOperation("分项能耗-柱状图")
    public Response classificationHistogram(@RequestBody EnergyReportDTO energyReportDTO) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.classificationHistogram 入参为:{}", energyReportDTO);

        Object result = energyConsumeService.energyFlowForOne(QueryTypeEnum.TYPE.type, energyReportDTO.getEquipClassification(),
                energyReportDTO.getDateType(), energyReportDTO.getEquipType(), energyReportDTO.getStartTime(),
                energyReportDTO.getEndTime(), EnergyGraphicsEnum.HISTOGRAM_CHART);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.classificationHistogram 出参为:{}", result);
        return returnSuccess(result);
    }

    @PostMapping("/classification/pie-chart")
    @ApiOperation("分项能耗-饼图")
    public Response classificationPieChart(@RequestBody EnergyReportDTO energyReportDTO) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.classificationPieChart 入参为:{}", energyReportDTO);

        Object result = energyConsumeService.energyFlowForOne(QueryTypeEnum.TYPE.type, energyReportDTO.getEquipClassification(),
                energyReportDTO.getDateType(), energyReportDTO.getEquipType(), energyReportDTO.getStartTime(),
                energyReportDTO.getEndTime(), EnergyGraphicsEnum.SHARE_PIE_CHART);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.classificationPieChart 出参为:{}", result);
        return returnSuccess(result);
    }

    @PostMapping("/classification/yoy")
    @ApiOperation("分项能耗-同比")
    public Response classificationYoy(@RequestBody EnergyReportDTO energyReportDTO) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.classificationYoy 入参为:{}", energyReportDTO);

        Object result = energyConsumeService.energyFlowForOne(QueryTypeEnum.TYPE.type, energyReportDTO.getEquipClassification(),
                energyReportDTO.getDateType(), energyReportDTO.getEquipType(), energyReportDTO.getStartTime(),
                energyReportDTO.getEndTime(), EnergyGraphicsEnum.YEAR_ON_YEAR_CHART);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.classificationYoy 出参为:{}", result);
        return returnSuccess(result);
    }


    @PostMapping("/classification/qoq")
    @ApiOperation("分项能耗-环比")
    public Response classificationQoq(@RequestBody EnergyReportDTO energyReportDTO) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.classificationQoq 入参为:{}", energyReportDTO);
        Object result = energyConsumeService.energyFlowForOne(QueryTypeEnum.TYPE.type, energyReportDTO.getEquipClassification(),
                energyReportDTO.getDateType(), energyReportDTO.getEquipType(), energyReportDTO.getStartTime(),
                energyReportDTO.getEndTime(), EnergyGraphicsEnum.RING_RATIO_CHART);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.classificationQoq 出参为:{}", result);
        return returnSuccess(result);
    }


}
