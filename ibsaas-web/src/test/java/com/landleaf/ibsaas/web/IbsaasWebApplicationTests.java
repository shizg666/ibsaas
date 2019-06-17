package com.landleaf.ibsaas.web;


import com.landleaf.ibsaas.common.dao.energy.EnergyEquipDao;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyEquipVO;
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


    @Test
    public void contextLoads() {
    }


    @Test
    public void aa(){
        List<EnergyEquipVO> energyEquipVOS = energyEquipDao.allWithNodeIds();
        System.out.println(energyEquipVOS);
    }
}
