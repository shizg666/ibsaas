package com.landleaf.ibsaas.web;

import com.landleaf.ibsaas.common.dao.hvac.HvacFieldDao;
import com.landleaf.ibsaas.common.dao.hvac.HvacNodeDao;
import com.landleaf.ibsaas.common.dao.hvac.HvacPointDao;
import com.landleaf.ibsaas.common.domain.hvac.HvacField;
import com.landleaf.ibsaas.common.domain.hvac.HvacNode;
import com.landleaf.ibsaas.common.domain.hvac.HvacPoint;
import com.landleaf.ibsaas.web.web.constant.IbsaasWebConstants;
import com.landleaf.ibsaas.web.web.dataprovider.IdGenerator;
import com.landleaf.ibsaas.web.web.service.hvac.IHvacFieldService;
import com.landleaf.ibsaas.web.web.service.hvac.IHvacNodeService;
import com.landleaf.ibsaas.web.web.service.hvac.IHvacPointService;
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
    private IdGenerator idGenerator;

    @Test
    public void contextLoads() {
    }


    @Test
    public void fillIds(){

    }



    private void fillHvacNode(){
        List<HvacNode> hvacNodes = iHvacNodeService.selectAll();
        for (HvacNode hn:hvacNodes) {
            hn.setActive(IbsaasWebConstants.INACTIVE);
            hvacNodeDao.updateByPrimaryKeySelective(hn);

            hn.setId(idGenerator.nextId());
            hn.setActive(IbsaasWebConstants.ACTIVE);
            hvacNodeDao.insertSelective(hn);
        }
    }

    private void fillHvacField(){
        List<HvacField> hvacFields = iHvacFieldService.selectAll();
        for (HvacField hf: hvacFields){
            hf.setActive(IbsaasWebConstants.INACTIVE);
            hvacFieldDao.updateByPrimaryKeySelective(hf);

            hf.setId(idGenerator.nextId());
            hf.setActive(IbsaasWebConstants.ACTIVE);
            hvacFieldDao.insertSelective(hf);
        }
    }


    private void fillHvacPoint(){
        List<HvacPoint> hvacPoints = iHvacPointService.selectAll();
        for (HvacPoint hp: hvacPoints){
            hp.setActive(IbsaasWebConstants.INACTIVE);
            hvacPointDao.updateByPrimaryKeySelective(hp);


            hp.setId(idGenerator.nextId());
            hp.setActive(IbsaasWebConstants.ACTIVE);
            hvacPointDao.insertSelective(hp);
        }
    }
}
