package com.landleaf.ibsaas.web.web.service.energyflow;

import com.google.common.collect.Maps;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyReportDTO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportResponseVO;
import com.landleaf.ibsaas.common.enums.energy.EnergyGraphicsEnum;
import com.landleaf.ibsaas.common.utils.date.DateUtils;
import com.landleaf.ibsaas.web.web.service.energyflow.handler.data.asyn.IEnergyFutureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public abstract class AbstractEnergyEnergyFlowHandler implements EnergyFlowHandler {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractEnergyEnergyFlowHandler.class);


    @Autowired
    private IEnergyFutureService energyFutureService;

    public EnergyReportDTO energyReportDTO;

    public Map<String ,List<EnergyReportResponseVO>> orginData = Maps.newHashMap();
    @Override
    public Map<String, List<String>> handle() {
        return this.handle();
    }




   void getOrginData(){
       Map<String ,List<EnergyReportResponseVO>> orginData = Maps.newHashMap();
       LOGGER.info("*************************获取原始数据*****************************");
       long getDateStartTime = System.currentTimeMillis();
       Map<String, Future> futureMap = Maps.newHashMap();
       for (EnergyGraphicsEnum enumObj : EnergyGraphicsEnum.values()) {
           Future<List<EnergyReportResponseVO>> future = energyFutureService.handlerMsg(enumObj, this.energyReportDTO);
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

    @Override
    public void buildParam(Integer equipArea, Integer equipClassification, Integer dateType, Integer equipType, String startTime, String endTime){
        energyReportDTO = new EnergyReportDTO( dateType,  startTime,  endTime,  equipType,  equipArea,  equipClassification);
        this.convertimeByDateType(energyReportDTO);
        getOrginData();
    }


    /**
     * 根据维度类型转换起止时间
     * @param requestBody
     */
    public void convertimeByDateType(EnergyReportDTO requestBody) {
        String startTime = requestBody.getStartTime();
        String endTime = requestBody.getEndTime();
        Integer dateType = requestBody.getDateType();
        //根据维度生成
        switch (dateType) {
            case 1:
                //时
                startTime = DateUtils.convert(DateUtils.getStartDateForHour(DateUtils.convert(startTime)));
                endTime = DateUtils.convert(DateUtils.getEndDateForHour(DateUtils.convert(endTime)));
                break;
            case 2:
                //日
                startTime = DateUtils.convert(DateUtils.getStartDateForDay(DateUtils.convert(startTime)));
                endTime = DateUtils.convert(DateUtils.getEndDateForDay(DateUtils.convert(endTime)));
                break;
            case 3:
                //月
                startTime = DateUtils.convert(DateUtils.getStartDateForMonth(DateUtils.convert(startTime)));
                endTime = DateUtils.convert(DateUtils.getEndDateForMonth(DateUtils.convert(endTime)));
                break;
            case 4:
                //年
                startTime = DateUtils.convert(DateUtils.getStartDateForYear(DateUtils.convert(startTime)));
                endTime = DateUtils.convert(DateUtils.getEndDateForYear(DateUtils.convert(endTime)));
                break;
        }
        requestBody.setStartTime(startTime);
        requestBody.setEndTime(endTime);
    }

}
