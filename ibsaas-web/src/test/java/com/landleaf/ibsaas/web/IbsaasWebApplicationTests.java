package com.landleaf.ibsaas.web;


import com.landleaf.ibsaas.common.dao.energy.EnergyEquipDao;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyReportDTO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportResponseVO;
import com.landleaf.ibsaas.common.enums.energy.DimensionTypeEnum;
import com.landleaf.ibsaas.web.web.service.energy.impl.EnergyReportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


/**
 * @author Lokiy
 * @date 2019/5/28 12:20
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IbsaasWebApplicationTests {

    @Autowired
    private EnergyEquipDao energyEquipDao;
    @Autowired
    private EnergyReportService energyReportService;


    @Test
    public void contextLoads() {
    }


    @Test
    public void aa(){

        EnergyReportDTO queryVO = new EnergyReportDTO();
        queryVO.setStartTime("2019-06-10 20:00:00");
        queryVO.setEndTime("2019-06-22 20:00:00");
        queryVO.setDateType(DimensionTypeEnum.DAY.type);
//        queryVO.setEquipClassification(0);
        queryVO.setEquipType(1);
        Long start = System.currentTimeMillis();
        List<EnergyReportResponseVO> energyReportResponseVOS =  energyReportService.getEnergyReporyInfolist(queryVO);
        Long end = System.currentTimeMillis();
        System.out.println(end-start);
    }
}
