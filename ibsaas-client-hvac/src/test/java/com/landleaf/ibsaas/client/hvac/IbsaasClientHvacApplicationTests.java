package com.landleaf.ibsaas.client.hvac;

import com.landleaf.ibsaas.client.hvac.service.ICommonDeviceService;
import com.landleaf.ibsaas.client.hvac.service.IHvacFieldService;
import com.landleaf.ibsaas.client.hvac.service.IHvacNodeService;
import com.landleaf.ibsaas.client.hvac.service.IHvacPointService;
import com.landleaf.ibsaas.client.hvac.util.DaoAdapter;
import com.landleaf.ibsaas.client.hvac.util.IdGeneratorEx;
import com.landleaf.ibsaas.common.constant.IbsaasConstant;
import com.landleaf.ibsaas.common.dao.hvac.HvacDeviceDao;
import com.landleaf.ibsaas.common.dao.hvac.HvacFieldDao;
import com.landleaf.ibsaas.common.dao.hvac.HvacNodeDao;
import com.landleaf.ibsaas.common.dao.hvac.HvacPointDao;
import com.landleaf.ibsaas.common.domain.hvac.HvacDevice;
import com.landleaf.ibsaas.common.domain.hvac.HvacField;
import com.landleaf.ibsaas.common.domain.hvac.HvacNode;
import com.landleaf.ibsaas.common.domain.hvac.HvacPoint;
import com.landleaf.ibsaas.common.enums.hvac.BacnetPremissionEnum;
import com.landleaf.ibsaas.common.enums.hvac.HvacFloorEnum;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.serotonin.bacnet4j.LocalDevice;
import com.serotonin.bacnet4j.npdu.ip.IpNetwork;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IbsaasClientHvacApplicationTests {


    @Autowired
    private HvacDeviceDao hvacDeviceDao;

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

    @Autowired
    private RedisHandle redisHandle;

    @Autowired
    private ICommonDeviceService iCommonDeviceService;

    @Autowired
    private DaoAdapter daoAdapter;

    @Test
    public void contextLoads() {

    }


    /**
     * 新增设备
     */
    @Test
    public void insertDevice(){
        HvacDevice hd = new HvacDevice();
        hd.setIp("192.168.10.212");
        hd.setPort(IpNetwork.DEFAULT_PORT);
        hd.setDeviceInstanceNumber(30032);
        hd.setDescription("风机盘管(3F-4F)");
        daoAdapter.consummateAddOperation(hd);
        hvacDeviceDao.insertSelective(hd);
    }

    /**
     * 插入设备字段
     */
    @Test
    public void insertField(){
        List<HvacField> list = new ArrayList<HvacField>(){{
            add(getHvacField(
                    "240563083381182464" ,
                    "emComState",
                    "电表通信状态",
                    BacnetPremissionEnum.READ.getPermission()));
            add(getHvacField(
                    "240563083381182464" ,
                    "emReading",
                    "电表读数",
                    BacnetPremissionEnum.READ.getPermission()));



        }};
        list.forEach(hf -> hvacFieldDao.insertSelective(hf));
    }

    private HvacField getHvacField(String deviceId, String fieldName, String fieldDescription, Integer permission){
        HvacField hf = new HvacField();
        hf.setDeviceId(deviceId);
        hf.setFieldName(fieldName);
        hf.setFieldDescription(fieldDescription);
        hf.setPermission(permission);
        daoAdapter.consummateAddOperation(hf);
        return hf;
    }


    @Test
    public void insertNode(){

        for (int i = 1; i <= 27; i++) {
            HvacNode hd = getHvacNode("240563083381182464",
                    i+"##",
                    HvacFloorEnum.A_1_F.getFloor());
            hvacNodeDao.insertSelective(hd);
        }

//        HvacNode hd = getHvacNode("240563007812407296",
//                                  "2#",
//                                   HvacFloorEnum.A_1_F.getFloor());
//        hvacNodeDao.insertSelective(hd);
    }

    private HvacNode getHvacNode( String deviceId, String nodeName, Integer floor){
        HvacNode hd = new HvacNode();
        hd.setDeviceId(deviceId);
        hd.setNodeName(nodeName);
        hd.setFloor(floor);
        daoAdapter.consummateAddOperation(hd);
        return hd;
    }



    @Test
    public void updateBatch(){
//        List<HvacPoint> hvacPoints = hvacPointDao.getHvacPointDaoByNodeIdOrFieldId( "240563253330186240", null, null);
        List<HvacPoint> hvacPoints = hvacPointDao.getHvacPointLmt( 100);
        hvacPoints.forEach(hp -> {
            HvacPoint temp = new HvacPoint();
            BeanUtils.copyProperties(hp, temp);
            daoAdapter.consummateAddOperation(temp);
            hvacPointDao.insertSelective(temp);
            hvacPointDao.delete(hp);
        });
//        System.out.println(hvacPoints);
    }




    @Test
    public void currentDataToRedis(){
        iCommonDeviceService.currentDataToRedis();
    }

    @Test
    public void redisOp(){
        //这里进去是int
//        redisHandle.addMap("lcc", "123", "bbb");


        Object lcc = redisHandle.getMapField("lcc", "123");
        System.err.println(lcc);
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
