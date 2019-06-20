//package com.landleaf.ibsaas.web.web.controller.energy;
//
//import com.landleaf.ibsaas.common.domain.Response;
//import com.landleaf.ibsaas.common.domain.energy.HlVl;
//import com.landleaf.ibsaas.common.domain.energy.dto.EnergyReportDTO;
//import com.landleaf.ibsaas.common.domain.energy.vo.EnergyOverviewTotalVO;
//import com.landleaf.ibsaas.web.web.controller.BasicController;
//import com.landleaf.ibsaas.web.web.service.energy.IEnergyReportService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//
///**
// * @author Lokiy
// * @date 2019/6/18 12:38
// * @description:
// */
//@RestController
//@RequestMapping("energy-report")
//@AllArgsConstructor
//@Slf4j
//@Api("能耗报表控制类")
//public class EnergyReportController extends BasicController {
//
//    private final IEnergyReportService iEnergyReportService;
//
//
//    /**
//     * 能源总览栏
//     */
//    @PostMapping("/overview/line-chart")
//    @ApiOperation("能耗总览-折线图")
//    public Response overviewLineChart(@RequestBody EnergyReportDTO energyReportDTO){
//        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.overviewLineChart 入参为:{}", energyReportDTO);
//        HlVl result = iEnergyReportService.overviewLineChart(energyReportDTO);
//        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.overviewLineChart 出参为:{}", result);
//        return returnSuccess(result);
//    }
//
//    @PostMapping("/overview/histogram")
//    @ApiOperation("能耗总览-柱状图")
//    public Response overviewHistogram(@RequestBody EnergyReportDTO energyReportDTO){
//        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.overviewHistogram 入参为:{}", energyReportDTO);
//        HlVl result = iEnergyReportService.overviewHistogram(energyReportDTO);
//        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.overviewHistogram 出参为:{}", result);
//        return returnSuccess(result);
//    }
//
//
//    @PostMapping("/overview/saving-effect")
//    @ApiOperation("能耗总览-节能效果")
//    public Response overviewSavingEffect(@RequestBody EnergyReportDTO energyReportDTO){
//        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.overviewSavingEffect 入参为:{}", energyReportDTO);
//        HlVl result = iEnergyReportService.overviewSavingEffect(energyReportDTO);
//        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.overviewSavingEffect 出参为:{}", result);
//        return returnSuccess(result);
//    }
//
//
//    @PostMapping("/overview/ranking/classification")
//    @ApiOperation("能耗总览-能耗排行TOP5项")
//    public Response overviewRankingClassification(@RequestBody EnergyReportDTO energyReportDTO){
//        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.overviewRankingClassification 入参为:{}", energyReportDTO);
//        HlVl result = iEnergyReportService.overviewRankingClassification(energyReportDTO);
//        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.overviewRankingClassification 出参为:{}", result);
//        return returnSuccess(result);
//    }
//
//    @PostMapping("/overview/ranking/area")
//    @ApiOperation("能耗总览-能耗排行TOP3区")
//    public Response overviewRankingArea(@RequestBody EnergyReportDTO energyReportDTO){
//        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.overviewRankingArea 入参为:{}", energyReportDTO);
//        HlVl result = iEnergyReportService.overviewRankingArea(energyReportDTO);
//        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.overviewRankingArea 出参为:{}", result);
//        return returnSuccess(result);
//    }
//
//
//    @PostMapping("/overview/yoy")
//    @ApiOperation("能耗总览-同比")
//    public Response overviewYoy(@RequestBody EnergyReportDTO energyReportDTO){
//        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.overviewYoy 入参为:{}", energyReportDTO);
//        HlVl result = iEnergyReportService.overviewYoy(energyReportDTO);
//        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.overviewYoy 出参为:{}", result);
//        return returnSuccess(result);
//    }
//
//
//    @PostMapping("/overview/qoq")
//    @ApiOperation("能耗总览-环比")
//    public Response overviewQoq(@RequestBody EnergyReportDTO energyReportDTO){
//        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.overviewQoq 入参为:{}", energyReportDTO);
//        HlVl result = iEnergyReportService.overviewQoq(energyReportDTO);
//        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.overviewQoq 出参为:{}", result);
//        return returnSuccess(result);
//    }
//
//
//    @GetMapping("/overview/total")
//    @ApiOperation("能耗总览-累计能耗")
//    public Response overviewTotal(){
//        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.overviewTotal 入参为:空");
//        EnergyOverviewTotalVO result = iEnergyReportService.overviewTotal();
//        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.overviewTotal 出参为:{}", result);
//        return returnSuccess(result);
//    }
//
//
//
//
//
//    /**
//     * 区域能耗
//     */
//    @PostMapping("/area/line-chart")
//    @ApiOperation("区域能耗-折线图")
//    public Response areaLineChart(@RequestBody EnergyReportDTO energyReportDTO){
//        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.areaLineChart 入参为:{}", energyReportDTO);
//        HlVl result = iEnergyReportService.areaLineChart(energyReportDTO);
//        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.areaLineChart 出参为:{}", result);
//        return returnSuccess(result);
//    }
//
//    @PostMapping("/area/histogram")
//    @ApiOperation("区域能耗-柱状图")
//    public Response areaHistogram(@RequestBody EnergyReportDTO energyReportDTO){
//        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.areaHistogram 入参为:{}", energyReportDTO);
//        HlVl result = iEnergyReportService.areaHistogram(energyReportDTO);
//        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.areaHistogram 出参为:{}", result);
//        return returnSuccess(result);
//    }
//
//    @PostMapping("/area/pie-chart")
//    @ApiOperation("区域能耗-柱状图")
//    public Response areaPieChart(@RequestBody EnergyReportDTO energyReportDTO){
//        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.areaPieChart 入参为:{}", energyReportDTO);
//        HlVl result = iEnergyReportService.areaPieChart(energyReportDTO);
//        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.areaPieChart 出参为:{}", result);
//        return returnSuccess(result);
//    }
//
//    @PostMapping("/area/yoy")
//    @ApiOperation("区域能耗-同比")
//    public Response areaYoy(@RequestBody EnergyReportDTO energyReportDTO){
//        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.areaYoy 入参为:{}", energyReportDTO);
//        HlVl result = iEnergyReportService.areaYoy(energyReportDTO);
//        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.areaYoy 出参为:{}", result);
//        return returnSuccess(result);
//    }
//
//
//    @PostMapping("/area/qoq")
//    @ApiOperation("区域能耗-环比")
//    public Response areaQoq(@RequestBody EnergyReportDTO energyReportDTO){
//        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.areaQoq 入参为:{}", energyReportDTO);
//        HlVl result = iEnergyReportService.areaQoq(energyReportDTO);
//        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.areaQoq 出参为:{}", result);
//        return returnSuccess(result);
//    }
//
//
//
//
//    /**
//     * 分项能耗
//     */
//
//    @PostMapping("/classification/line-chart")
//    @ApiOperation("分项能耗-折线图")
//    public Response classificationLineChart(@RequestBody EnergyReportDTO energyReportDTO){
//        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.classificationLineChart 入参为:{}", energyReportDTO);
//        HlVl result = iEnergyReportService.classificationLineChart(energyReportDTO);
//        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.classificationLineChart 出参为:{}", result);
//        return returnSuccess(result);
//    }
//
//    @PostMapping("/classification/histogram")
//    @ApiOperation("分项能耗-柱状图")
//    public Response classificationHistogram(@RequestBody EnergyReportDTO energyReportDTO){
//        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.classificationHistogram 入参为:{}", energyReportDTO);
//        HlVl result = iEnergyReportService.classificationHistogram(energyReportDTO);
//        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.classificationHistogram 出参为:{}", result);
//        return returnSuccess(result);
//    }
//
//    @PostMapping("/classification/pie-chart")
//    @ApiOperation("分项能耗-柱状图")
//    public Response classificationPieChart(@RequestBody EnergyReportDTO energyReportDTO){
//        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.classificationPieChart 入参为:{}", energyReportDTO);
//        HlVl result = iEnergyReportService.classificationPieChart(energyReportDTO);
//        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.classificationPieChart 出参为:{}", result);
//        return returnSuccess(result);
//    }
//
//    @PostMapping("/classification/yoy")
//    @ApiOperation("分项能耗-同比")
//    public Response classificationYoy(@RequestBody EnergyReportDTO energyReportDTO){
//        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.classificationYoy 入参为:{}", energyReportDTO);
//        HlVl result = iEnergyReportService.classificationYoy(energyReportDTO);
//        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.classificationYoy 出参为:{}", result);
//        return returnSuccess(result);
//    }
//
//
//    @PostMapping("/classification/qoq")
//    @ApiOperation("分项能耗-环比")
//    public Response classificationQoq(@RequestBody EnergyReportDTO energyReportDTO){
//        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>EnergyReportController.classificationQoq 入参为:{}", energyReportDTO);
//        HlVl result = iEnergyReportService.classificationQoq(energyReportDTO);
//        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<EnergyReportController.classificationQoq 出参为:{}", result);
//        return returnSuccess(result);
//    }
//
//
//}
