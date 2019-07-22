package com.landleaf.ibsaas.client.light.handle.light.reponse;


import com.landleaf.ibsaas.client.light.annotation.HandlerType;
import com.landleaf.ibsaas.client.light.enums.HostAdressEnum;
import com.landleaf.ibsaas.common.dao.light.TLightDeviceStateDao;
import com.landleaf.ibsaas.common.domain.knight.KnightMessage;
import com.landleaf.ibsaas.common.domain.light.TLightDeviceState;
import com.landleaf.ibsaas.common.domain.light.message.LightMsgResponse;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.common.utils.date.DateUtil;
import com.landleaf.ibsaas.common.utils.date.DateUtils;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

/**
 * AUTO<a>! / Poll a scene automatically / C  page 28
 * 情景变化消息处理
 * Reaction: <Address><Type>S<s>W<w>E<e><f><g><h><i>
 */
@HandlerType(type = ".+(S\\dW.+E\\d{5}).+",business = "pattern",desc = ".+(S\\dW.E\\d{5}).+ 正则表达式")
@Slf4j
@Service
public class AutoScenesPeponse implements LightResponse{
    @Autowired
    private RedisHandle redisHandle;

    private TLightDeviceStateDao tLightDeviceStateDao;

    @Override
    public LightMsgResponse getReponse(String message) {
        //目前只有区域
        int rIndex = message.indexOf("R");
        int gIndex = message.indexOf("G");
        int bIndex = message.indexOf("B");
        int tIndex = message.indexOf("T");
        int sIndex = message.indexOf("S");
        int wIndex = message.indexOf("W");
        String region = "";
        String scenes = "";
        LightMsgResponse lightMsgResponse = new LightMsgResponse();
        lightMsgResponse.setCt(new Date());
        if (rIndex > 0){
            if(gIndex > 0){
                region = message.substring(rIndex+1,gIndex);
            }else if (bIndex > 0){
                region = message.substring(rIndex+1,gIndex);
            }else if (tIndex > 0){
                region = message.substring(rIndex+1,tIndex);
            }
        }
        if (sIndex > 0 && wIndex > 0){
            scenes = message.substring(sIndex+1,wIndex);
        }
        lightMsgResponse.setRegion(region);
        lightMsgResponse.setScenes(scenes);
        lightMsgResponse.setResult(message);
        return lightMsgResponse;
    }

    @Override
    public void handle(LightMsgResponse message) {
        log.info("AutoScenesPeponse -----------------> 接收到消息：{}",message.toString());
        HostAdressEnum hostAdressEnum = HostAdressEnum.getInstByAdress(message.getAddress().substring(1));
        if (hostAdressEnum == null){
            log.error("根据地址获取不到服务器adress:{}",message.getAddress());
        }
        String adress = "R_"+message.getRegion();
//        TLightDeviceState result = tLightDeviceStateDao.getByAdress(adress);
//        if (result == null){
//            TLightDeviceState tLightDeviceState = new TLightDeviceState();
//            tLightDeviceState.setScenes(message.getScenes());
//            tLightDeviceState.setAddress(adress);
//            tLightDeviceState.setCt(new Date());
//            tLightDeviceStateDao.insertSelective(tLightDeviceState);
//        }else {
//            Long ct = result.getCt().getTime();
//            Long mct = message.getCt().getTime();
//            String old = result.getState();
//            String now = message.getScenes();
//            if ((!old.equals(now)) && mct > ct){
//                result.setScenes(message.getScenes());
//                result.setCt(message.getCt());
//                tLightDeviceStateDao.updateByPrimaryKey(result);
//            }
//        }
        try {
            redisHandle.addMap(hostAdressEnum.getKey(),"R_"+message.getRegion(),message.getScenes());
        }catch (Exception e){
            retryRedis(hostAdressEnum.getKey(),message,2000L);
        }

    }

//    public void retryRedis2(String key,LightMsgResponse message, Long timeout) {
//        redisHandle.watch(key);
//        redisHandle.multi();
//        String scenes = redisHandle.getMapField(key,"R_"+message.getRegion());
//        if (StringUtil.isBlank(scenes)){
//            redisHandle.addMap(key,"R_"+message.getRegion(),message.getScenes()+"_"+message.getCt());
//            return;
//        }
//        String[] s = scenes.split("_");
//        if (Long.valueOf(s[1]) > message.getCt()){
//            return;
//        }
//        redisHandle.addMap(key,"R_"+message.getRegion(),message.getScenes()+"_"+message.getCt());
//        List exec = redisHandle.exec();
//        if (null==exec){
//            System.out.println("已经有其他线程更改了");
//        }else {
//            System.out.println("更改成功");
//        }
//        redisHandle.addMap(key,"R_"+message.getRegion(),message.getScenes());
//    }

    public void retryRedis(String key,LightMsgResponse message, Long timeout) {
        long currentTimeMillis = System.currentTimeMillis();
        long expireTimeMillis = currentTimeMillis + timeout;
        String field = "R_"+message.getRegion();
        String value = message.getScenes();
        try {
            while (System.currentTimeMillis() < expireTimeMillis) {
                Thread.sleep(100L);
                try {
                    redisHandle.addMap(key,field,value);
                }catch (Exception e){
                    log.error("*************************************返回消息入Redis失败：{}",e.getMessage());
                }
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            log.error("*************************************消息入Redis message:{],最终失败原因：{}",message.toString(),e.getMessage());
        }
        try {
            redisHandle.addMap(key,field,value);
        }catch (Exception e){
            log.error("*************************************返回消息入Redis最终失败：{}",e.getMessage());
        }
    }
}
