package com.landleaf.ibsaas.web.web.controller.energy;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.common.domain.CascadeVO;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.light.SelectedVo;
import com.landleaf.ibsaas.common.enums.hvac.BacnetDeviceTypeEnum;
import com.landleaf.ibsaas.common.enums.hvac.ModbusDeviceTypeEnum;
import com.landleaf.ibsaas.common.utils.date.LocalAndDateUtil;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.common.domain.excel.DeviceExcelData;
import com.landleaf.ibsaas.common.domain.excel.EnergyExcelData;
import com.landleaf.ibsaas.common.domain.excel.ExcelDataDTO;
import com.landleaf.ibsaas.web.web.service.excel.IExcelDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author Lokiy
 * @date 2020/2/11 0011
 * @description:
 */
@RestController
@RequestMapping("/excel-data")
@AllArgsConstructor
@Slf4j
@Api("lgc数据导入导出")
public class ExcelDataController extends BasicController {

    private final IExcelDataService excelDataService;



    @GetMapping("/data-select")
    @ApiOperation("导出数据选择")
    public Response dataSelect(){
        List<CascadeVO> result = excelDataService.dataSelect();
        return returnSuccess(result);
    }


    @PostMapping("download")
    @ApiOperation("导出功能")
    public void download(@RequestBody ExcelDataDTO dataDTO,  HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String oriName;
            // 这里需要设置不关闭流
            if(BacnetDeviceTypeEnum.WATER_METER.getDeviceType().equals(dataDTO.getDeviceType())
                    || ModbusDeviceTypeEnum.ELECTRIC_METER.getDeviceType().equals(dataDTO.getDeviceType())){
                oriName = "能耗数据";
                String fileName = URLEncoder.encode( oriName + ".xlsx", "UTF-8");
                response.setHeader("Content-disposition", "attachment;filename=" + fileName);

                EasyExcel.write(response.getOutputStream(), EnergyExcelData.class)
                        .sheet(oriName)
                        .doWrite(excelDataService.energyExcelData(dataDTO));
            }else {
                oriName = "设备数据";
                String fileName = URLEncoder.encode(oriName + ".xlsx", "UTF-8");
                response.setHeader("Content-disposition", "attachment;filename=" + fileName );
                EasyExcel.write(response.getOutputStream(), DeviceExcelData.class)
                        .sheet(oriName)
                        .doWrite(excelDataService.deviceExcelData(dataDTO));
            }
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Response resp = handlerException(new Exception("下载文件失败"));
            response.getWriter().println(JSON.toJSONString(resp));
        }
    }
}
