package com.landleaf.ibsaas.client.hvac.util;

import com.landleaf.ibsaas.client.hvac.constant.BacnetConstant;
import com.landleaf.ibsaas.common.enums.hvac.BacnetActiveEnum;
import com.landleaf.ibsaas.common.enums.hvac.BacnetObjectEnum;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.serotonin.bacnet4j.LocalDevice;
import com.serotonin.bacnet4j.RemoteDevice;
import com.serotonin.bacnet4j.exception.BACnetException;
import com.serotonin.bacnet4j.service.acknowledgement.AcknowledgementService;
import com.serotonin.bacnet4j.service.confirmed.ConfirmedRequestService;
import com.serotonin.bacnet4j.type.constructed.SequenceOf;
import com.serotonin.bacnet4j.type.enumerated.PropertyIdentifier;
import com.serotonin.bacnet4j.type.primitive.*;
import com.serotonin.bacnet4j.util.PropertyReferences;
import com.serotonin.bacnet4j.util.PropertyValues;
import com.serotonin.bacnet4j.util.RequestUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/5/20 16:03
 * @description: bacnet帮助类
 */
@Slf4j
public class BacnetUtil {


    /**
     * 获取远程设备请求
     * @param localDevice
     * @param remoteDevice
     * @param confirmedRequestService
     * @return
     * @throws Exception
     */
    public static AcknowledgementService send(LocalDevice localDevice, RemoteDevice remoteDevice, ConfirmedRequestService confirmedRequestService)
            throws Exception {
        return localDevice.send(remoteDevice, confirmedRequestService).get();
    }


    /**
     * 获取远程设备的所有对象
     * @param localDevice
     * @param remoteDevice
     * @return
     * @throws BACnetException
     */
    public static List<ObjectIdentifier> getAllRemoteObjectIdentifier(LocalDevice localDevice, RemoteDevice remoteDevice) throws BACnetException {
        return ((SequenceOf<ObjectIdentifier>) RequestUtils
                .sendReadPropertyAllowNull(localDevice, remoteDevice, remoteDevice.getObjectIdentifier(), PropertyIdentifier.objectList))
                .getValues();
    }


    /**
     * 获取远程数据
     * @param localDevice
     * @param remoteDevice
     * @param oids
     * @return
     */
    public static PropertyValues readProperties(LocalDevice localDevice, RemoteDevice remoteDevice, List<ObjectIdentifier> oids){
        PropertyReferences references = new PropertyReferences();
        oids.forEach( oid -> references.add(oid, PropertyIdentifier.presentValue));
        try {
            return RequestUtils.readProperties(localDevice, remoteDevice, references, null);
        } catch (BACnetException e) {
            e.printStackTrace();
            throw new BusinessException("读取远程设备数据失败");
        }
    }

    /**
     * 根据对象列表获取所有值
     * @param localDevice
     * @param remoteDevice
     * @param oids
     * @throws BACnetException
     */
    public static PropertyValues readPresentValues( LocalDevice localDevice, RemoteDevice remoteDevice, List<ObjectIdentifier> oids) throws BACnetException {
        PropertyReferences references = new PropertyReferences();
        for (ObjectIdentifier oid : oids) {
            references.add(oid, PropertyIdentifier.presentValue);
        }
        PropertyValues values = RequestUtils.readProperties(localDevice, remoteDevice, references, null);
//        for (ObjectIdentifier oid : oids) {
//            System.err.println(oid.getObjectType().toString()+"|"+oid.getInstanceNumber() + "|" +values.getString(oid, PropertyIdentifier.presentValue));
//        }

        return values;
    }

    /**
     * 写入远程设备
     * @param localDevice
     * @param remoteDevice
     * @param objectIdentifier
     * @param value
     */
    public static void writePresentValue(LocalDevice localDevice, RemoteDevice remoteDevice, ObjectIdentifier objectIdentifier, String value){
        try {
            RequestUtils.writePresentValue(localDevice, remoteDevice, objectIdentifier, getPrimitiveType(objectIdentifier, value));
        } catch (BACnetException e) {
            //不管bacnet
            if(!BacnetConstant.TIMEOUT_WAITING_FOR_RESPONSE.equals(e.getMessage())) {
                e.printStackTrace();
                throw new BusinessException("写入远程设备数据失败");
            }
        }
    }

    /**
     * 获取传入数值对象
     * @param objectIdentifier
     * @param value
     * @return
     */
    private static Primitive getPrimitiveType(ObjectIdentifier objectIdentifier, String value){
        String type = objectIdentifier.getObjectType().toString();
        if(type.equals(BacnetObjectEnum.ANALOG_INPUT.getObjectTypeStr())
                || type.equals(BacnetObjectEnum.ANALOG_OUTPUT.getObjectTypeStr())
                || type.equals(BacnetObjectEnum.ANALOG_VALUE.getObjectTypeStr())){
            return new Real(Float.valueOf(value));
        }else if(type.equals(BacnetObjectEnum.BINARY_INPUT.getObjectTypeStr())
                    || type.equals(BacnetObjectEnum.BINARY_OUTPUT.getObjectTypeStr())
                    || type.equals(BacnetObjectEnum.BINARY_VALUE.getObjectTypeStr())){
            return new Enumerated(Integer.valueOf(value));
        }else if(type.equals(BacnetObjectEnum.MULTI_STATE_INPUT.getObjectTypeStr())
                    || type.equals(BacnetObjectEnum.MULTI_STATE_OUTPUT.getObjectTypeStr())
                    || type.equals(BacnetObjectEnum.MULTI_STATE_VALUE.getObjectTypeStr())){
            return new UnsignedInteger(Integer.valueOf(value));
        }else {
            return new Null();
        }
    }



    public static String getState(String state){
        if(BacnetActiveEnum.ACTIVE.getBacnetState().equals(state)){
            return BacnetActiveEnum.ACTIVE.getStateStr();
        } else if(BacnetActiveEnum.INACTIVE.getBacnetState().equals(state)){
            return BacnetActiveEnum.INACTIVE.getStateStr();
        } else {
            return state;
        }


    }
}
