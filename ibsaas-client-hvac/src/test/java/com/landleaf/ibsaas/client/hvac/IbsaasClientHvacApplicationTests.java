package com.landleaf.ibsaas.client.hvac;

import com.landleaf.ibsaas.client.hvac.service.IHvacFieldService;
import com.landleaf.ibsaas.client.hvac.service.IHvacNodeService;
import com.landleaf.ibsaas.client.hvac.service.IHvacPointService;
import com.landleaf.ibsaas.client.hvac.util.IdGeneratorEx;
import com.landleaf.ibsaas.common.constant.IbsaasConstant;
import com.landleaf.ibsaas.common.dao.hvac.HvacFieldDao;
import com.landleaf.ibsaas.common.dao.hvac.HvacNodeDao;
import com.landleaf.ibsaas.common.dao.hvac.HvacPointDao;
import com.landleaf.ibsaas.common.domain.hvac.HvacField;
import com.landleaf.ibsaas.common.domain.hvac.HvacNode;
import com.landleaf.ibsaas.common.domain.hvac.HvacPoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IbsaasClientHvacApplicationTests {

    @Autowired
    private HvacFieldDao hvacFieldDao;

    @Autowired
    private IHvacFieldService iHvacFieldService;

    @Autowired
    private HvacNodeDao hvacNodeDao;

    @Autowired
    private IHvacNodeService iHvacNodeService;

    @Autowired
    private HvacPointDao hvacPointDao;

    @Autowired
    private IHvacPointService iHvacPointService;

    @Autowired
    private IdGeneratorEx idGenerator;

    @Test
    public void contextLoads() {
    }


    @Test
    public void fillIds(){

    }



    private void fillHvacNode(){
        List<HvacNode> hvacNodes = iHvacNodeService.selectAll();
        for (HvacNode hn:hvacNodes) {
            hn.setActive(IbsaasConstant.IN_ACTIVE);
            hvacNodeDao.updateByPrimaryKeySelective(hn);

            hn.setId(idGenerator.nextId());
            hn.setActive(IbsaasConstant.ACTIVE);
            hvacNodeDao.insertSelective(hn);
        }
    }

    private void fillHvacField(){
        List<HvacField> hvacFields = iHvacFieldService.selectAll();
        for (HvacField hf: hvacFields){
            hf.setActive(IbsaasConstant.IN_ACTIVE);
            hvacFieldDao.updateByPrimaryKeySelective(hf);

            hf.setId(idGenerator.nextId());
            hf.setActive(IbsaasConstant.ACTIVE);
            hvacFieldDao.insertSelective(hf);
        }
    }


    private void fillHvacPoint(){
        List<HvacPoint> hvacPoints = iHvacPointService.selectAll();
        for (HvacPoint hp: hvacPoints){
            hp.setActive(IbsaasConstant.IN_ACTIVE);
            hvacPointDao.updateByPrimaryKeySelective(hp);


            hp.setId(idGenerator.nextId());
            hp.setActive(IbsaasConstant.ACTIVE);
            hvacPointDao.insertSelective(hp);
        }
    }

}
