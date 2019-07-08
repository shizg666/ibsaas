package com.landleaf.ibsaas.client.hvac.util;

import com.serotonin.modbus4j.BatchRead;
import com.serotonin.modbus4j.BatchResults;
import com.serotonin.modbus4j.ModbusMaster;

import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.locator.BaseLocator;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Lokiy
 * @date 2019/7/8 14:10
 * @description: modbus帮助类
 */
@Slf4j
public class ModbusUtil {


    public static <T> BatchResults<T> batchRead(ModbusMaster master, BatchRead<T> read) {
        try {
            return master.send(read);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("------------------------------>ModbusUtil批量读取寄存器地址失败:{}", e.getMessage(), e);
        }
        return null;
    }


    public static Number readHoldingRegister(ModbusMaster master, int slaveId, int offset, int dataType) {
        // 03 Holding Register类型数据读取
        BaseLocator<Number> loc = BaseLocator.holdingRegister(slaveId, offset, dataType);
        try {
            return master.getValue(loc);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("------------------------------>ModbusUtil读取寄存器地址失败:{}", e.getMessage(), e);
        }
        return null;
    }


    public static boolean writeHoldingRegister(ModbusMaster master, int slaveId, int offset, Number value, int dataType){

        BaseLocator<Number> locator = BaseLocator.holdingRegister(slaveId, offset, dataType);
        try {
            master.setValue(locator, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("------------------------------>ModbusUtil写寄存器地址数据失败:{}", e.getMessage(), e);
        }
        return false;
    }
}
