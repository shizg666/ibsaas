package com.landleaf.ibsaas.web;


import com.landleaf.ibsaas.common.dao.energy.EnergyEquipDao;
import com.landleaf.ibsaas.common.domain.energy.EnergyEquipData;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyEquipVO;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyEquipDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
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
    private IEnergyEquipDataService iEnergyEquipDataService;

    @Test
    public void contextLoads() {
    }


    @Test
    public void aa(){
        Date now = new Date();
        EnergyEquipData insert = new EnergyEquipData();
        insert.setEquipId("231231");
        insert.setId("213123");
        insert.setDataType(1);
        insert.setDataTime(now);
        insert.setDataIncreaseValue(BigDecimal.ZERO);
        insert.setDataValue(BigDecimal.ZERO);
        insert.setActive(1);
        insert.setCreateTime(now);
        insert.setModifyTime(now);

        iEnergyEquipDataService.saveSelective(insert);
    }
}
