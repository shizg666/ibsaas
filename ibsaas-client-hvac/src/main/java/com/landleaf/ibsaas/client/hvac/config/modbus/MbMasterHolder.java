package com.landleaf.ibsaas.client.hvac.config.modbus;

import com.landleaf.ibsaas.common.dao.hvac.modbus.MbMasterDao;
import com.landleaf.ibsaas.common.domain.hvac.modbus.MbMaster;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.ip.IpParameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lokiy
 * @date 2019/7/5 16:48
 * @description:
 */
@Component
@Slf4j
public class MbMasterHolder {

    private static ModbusFactory modbusFactory;

    private static final Map<String, ModbusMaster> MODBUS_MASTER_MAP = new ConcurrentHashMap<>();

    @Autowired
    private MbMasterDao mbMasterDao;

    static {
        modbusFactory = new ModbusFactory();
    }


    @PostConstruct
    public void init(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>modbus连接开始初始化<<<<<<<<<<<<<<<<<<<<<<<<<");
        List<MbMaster> mbMasters = mbMasterDao.all();
        mbMasters.forEach( mm -> {
            IpParameters params = new IpParameters();
            params.setHost(mm.getHost());
            params.setPort(mm.getPort());
            params.setEncapsulated(mm.getEncapsulated() == 1);
            ModbusMaster master = modbusFactory.createTcpMaster(params, false);
            try {
                //设置超时时间
                master.setTimeout(1000);
                //设置重连次数
                master.setRetries(3);
                //初始化
                master.init();
            } catch (ModbusInitException e) {
                e.printStackTrace();
                log.error("------------------------------>modbus连接初始化发生异常:{}",e.getMessage(),e);
            }
            MODBUS_MASTER_MAP.put(mm.getId(), master);
        });

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>modbus连接初始化成功<<<<<<<<<<<<<<<<<<<<<<<<<");
    }


    /**
     * 重写加载连接
     */
    public void reload(){
        init();
    }


    /**
     * 销毁连接
     */
    @PreDestroy
    public void destroy(){
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>modbus连接开始销毁<<<<<<<<<<<<<<<<<<<<<<<<<");
        MODBUS_MASTER_MAP.values().forEach(ModbusMaster::destroy);
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>modbus连接销毁结束<<<<<<<<<<<<<<<<<<<<<<<<<");
    }
}
