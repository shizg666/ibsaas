package com.landleaf.ibsaas.client.hvac;

import com.landleaf.ibsaas.client.hvac.service.*;
import com.landleaf.ibsaas.client.hvac.util.DaoAdapter;
import com.landleaf.ibsaas.client.hvac.util.IdGeneratorEx;
import com.landleaf.ibsaas.common.constant.IbsaasConstant;
import com.landleaf.ibsaas.common.dao.energy.ConfigSettingDao;
import com.landleaf.ibsaas.common.dao.hvac.HvacDeviceDao;
import com.landleaf.ibsaas.common.dao.hvac.HvacFieldDao;
import com.landleaf.ibsaas.common.dao.hvac.HvacNodeDao;
import com.landleaf.ibsaas.common.dao.hvac.HvacPointDao;
import com.landleaf.ibsaas.common.domain.energy.ConfigSetting;
import com.landleaf.ibsaas.common.domain.energy.EnergyData;
import com.landleaf.ibsaas.common.domain.hvac.HvacDevice;
import com.landleaf.ibsaas.common.domain.hvac.HvacField;
import com.landleaf.ibsaas.common.domain.hvac.HvacNode;
import com.landleaf.ibsaas.common.domain.hvac.HvacPoint;
import com.landleaf.ibsaas.common.enums.hvac.BacnetPremissionEnum;
import com.landleaf.ibsaas.common.enums.hvac.HvacFloorEnum;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.common.utils.date.CalendarUtil;
import com.serotonin.bacnet4j.npdu.ip.IpNetwork;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
    private ConfigSettingDao configSettingDao;

    @Autowired
    private DaoAdapter daoAdapter;

    @Autowired
    private IEnergyDataService iEnergyDataService;

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
                    6 ,
                    "emComState",
                    "电表通信状态",
                    BacnetPremissionEnum.READ.getPermission()));
            add(getHvacField(
                    6 ,
                    "emReading",
                    "电表读数",
                    BacnetPremissionEnum.READ.getPermission()));



        }};
        list.forEach(hf -> hvacFieldDao.insertSelective(hf));
    }

    private HvacField getHvacField(Integer deviceType, String fieldName, String fieldDescription, Integer permission){
        HvacField hf = new HvacField();
        hf.setDeviceType(deviceType);
        hf.setFieldName(fieldName);
        hf.setFieldDescription(fieldDescription);
        hf.setPermission(permission);
        daoAdapter.consummateAddOperation(hf);
        return hf;
    }


    @Test
    public void insertNode(){
//
//        for (int i = 1; i <= 27; i++) {
//            HvacNode hd = getHvacNode(6,
//                    i+"##",
//                    HvacFloorEnum.A_1_F.getFloor());
//            hvacNodeDao.insertSelective(hd);
//        }

        HvacNode hd = getHvacNode(8,
                                  "AHU",
                                   HvacFloorEnum.A_1_F.getFloor());
        hvacNodeDao.insertSelective(hd);
    }

    private HvacNode getHvacNode( Integer deviceType, String nodeName, Integer floor){
        HvacNode hd = new HvacNode();
        hd.setDeviceType(deviceType);
        hd.setNodeName(nodeName);
        hd.setFloor(floor);
        daoAdapter.consummateAddOperation(hd);
        return hd;
    }



    @Test
    public void updateBatch(){
//        List<HvacPoint> hvacPoints = hvacPointDao.getHvacPointDaoByNodeIdOrFieldId( "240563253330186240", null, null);
        List<HvacPoint> hvacPoints = hvacPointDao.getHvacPointLmt( 300);
        hvacPoints.forEach(hp -> {
            HvacPoint temp = new HvacPoint();
            BeanUtils.copyProperties(hp, temp);
            daoAdapter.consummateAddOperation(temp);
            hvacPointDao.delete(hp);
            hvacPointDao.insertSelective(temp);
        });
//        List<ConfigSetting> configSettings = configSettingDao.getCongfigSettingLmt( 300);
//        configSettings.forEach(hp -> {
//            ConfigSetting temp = new ConfigSetting();
//            BeanUtils.copyProperties(hp, temp);
//            daoAdapter.consummateAddOperation(temp);
//            configSettingDao.insertSelective(temp);
//            configSettingDao.delete(hp);
//        });


//        List<HvacDevice> hvacDevices = hvacDeviceDao.getHvacDeviceLmt(100);
//        hvacDevices.forEach(hd -> {
//            HvacDevice temp = new HvacDevice();
//            BeanUtils.copyProperties(hd, temp);
//            daoAdapter.consummateAddOperation(temp);
//            hvacDeviceDao.insertSelective(temp);
//            hvacDeviceDao.delete(hd);
//        });

//        List<HvacField> hvacFields = hvacFieldDao.getHvacFieldLmt(100);
//        hvacFields.forEach(hf -> {
//            HvacField temp = new HvacField();
//            BeanUtils.copyProperties(hf, temp);
//            daoAdapter.consummateAddOperation(temp);
//            hvacFieldDao.delete(hf);
//            hvacFieldDao.insertSelective(temp);
//        });
//
//        List<HvacNode> list = hvacNodeDao.getHvacNodeLmt(100);
//        list.forEach(o -> {
//            HvacNode temp = new HvacNode();
//            BeanUtils.copyProperties(o, temp);
//            daoAdapter.consummateAddOperation(temp);
//            hvacNodeDao.delete(o);
//            hvacNodeDao.insertSelective(temp);
//        });



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


    @Test
    public void aa(){

    }


    @Test
    public void dateRecord(){
        Date date = CalendarUtil.str2Date("2019-06-27 16:00:00");
        iEnergyDataService.dataRecord(date);
    }

    @Test
    public void insertRecord(){
        Date date = CalendarUtil.str2Date("2019-06-28 18:00:00");
        EnergyData record = new EnergyData();
        record.setNodeId("245880983650439168");
        record.setEnergyDataTime(date);
        record.setEnergyDataDate(date);
        record.setEnergyDataMonth("2019-06");
        record.setEnergyDataYear(2019);
        record.setEnergyDataValue(new BigDecimal(15488));
        record.setEnergyDataIncreaseValue(new BigDecimal(0));
        record.setEnergyDataType(2);
        record.setEnergyDataSource(1);
        daoAdapter.consummateAddOperation(record);
        try {
            iEnergyDataService.saveSelective(record);
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
        }
        System.out.println(11111);
    }


    @Test
    public void dataOffset(){
        //补能耗差
        Date date = CalendarUtil.str2Date("2019-06-28 12:00:00");


        Date date2 = CalendarUtil.str2Date("2019-06-28 17:00:00");
        while (date.compareTo(date2) <= 0){
            iEnergyDataService.dataRecord(date);
            date = CalendarUtil.nextHour(date);
        }

    }

}
