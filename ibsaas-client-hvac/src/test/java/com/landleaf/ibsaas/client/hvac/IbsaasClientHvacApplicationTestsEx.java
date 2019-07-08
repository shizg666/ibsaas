package com.landleaf.ibsaas.client.hvac;


import com.landleaf.ibsaas.client.hvac.util.DaoAdapter;
import com.landleaf.ibsaas.common.dao.hvac.modbus.MbFieldDao;
import com.landleaf.ibsaas.common.dao.hvac.modbus.MbMasterDao;
import com.landleaf.ibsaas.common.dao.hvac.modbus.MbNodeDao;
import com.landleaf.ibsaas.common.dao.hvac.modbus.MbRegisterDao;
import com.landleaf.ibsaas.common.domain.hvac.modbus.MbField;
import com.landleaf.ibsaas.common.domain.hvac.modbus.MbMaster;
import com.landleaf.ibsaas.common.domain.hvac.modbus.MbNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IbsaasClientHvacApplicationTestsEx {


    @Autowired
    private MbMasterDao mbMasterDao;

    @Autowired
    private MbNodeDao mbNodeDao;

    @Autowired
    private MbFieldDao mbFieldDao;

    @Autowired
    private MbRegisterDao mbRegisterDao;

    @Autowired
    private DaoAdapter daoAdapter;


    @Test
    public void updateBatch(){
//        List<MbMaster> list = mbMasterDao.getMbMasterLmt( 100);
//        list.forEach(o -> {
//            MbMaster temp = new MbMaster();
//            BeanUtils.copyProperties(o, temp);
//            daoAdapter.consummateAddOperation(temp);
//            mbMasterDao.insertSelective(temp);
//            mbMasterDao.delete(o);
//        });


//        List<MbNode> list = mbNodeDao.getMbNodeLmt( 100);
//        list.forEach(o -> {
//            MbNode temp = new MbNode();
//            BeanUtils.copyProperties(o, temp);
//            daoAdapter.consummateAddOperation(temp);
//            mbNodeDao.insertSelective(temp);
//            mbNodeDao.delete(o);
//        });

//        List<MbField> list = mbFieldDao.getMbFieldLmt( 100);
//        list.forEach(o -> {
//            MbField temp = new MbField();
//            BeanUtils.copyProperties(o, temp);
//            daoAdapter.consummateAddOperation(temp);
//            mbFieldDao.insertSelective(temp);
//            mbFieldDao.delete(o);
//        });

    }
}
