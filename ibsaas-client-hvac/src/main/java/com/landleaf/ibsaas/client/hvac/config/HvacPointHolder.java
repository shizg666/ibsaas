package com.landleaf.ibsaas.client.hvac.config;

import com.landleaf.ibsaas.common.dao.hvac.HvacPointDao;
import com.landleaf.ibsaas.common.domain.hvac.assist.HvacPointDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lokiy
 * @date 2019/7/2 17:49
 * @description: 设备点位容器,避免刷取数据的时候,频繁查询数据库
 */
@Component
@Slf4j
public class HvacPointHolder {

    public static final Map<Integer, Map<String, List<HvacPointDetail>>> DEVICE_POINT_MAP = new ConcurrentHashMap<>();

    public static final Map<Integer, List<HvacPointDetail>> DEVICE_POINT_LIST_MAP = new ConcurrentHashMap<>();

    @Autowired
    private HvacPointDao hvacPointDao;

    @PostConstruct
    public void init(){
        List<HvacPointDetail> hvacPointDetails = hvacPointDao.allHvacPointDetails();
        hvacPointDetails.forEach(hpd -> {
            DEVICE_POINT_LIST_MAP.computeIfAbsent(hpd.getDeviceType(), k -> new ArrayList<>());
            DEVICE_POINT_LIST_MAP.get(hpd.getDeviceType()).add(hpd);

            DEVICE_POINT_MAP.computeIfAbsent(hpd.getDeviceType(), k -> new ConcurrentHashMap<>(32));
            DEVICE_POINT_MAP.get(hpd.getDeviceType()).computeIfAbsent(hpd.getNodeId(), k -> new ArrayList<>());
            DEVICE_POINT_MAP.get(hpd.getDeviceType()).get(hpd.getNodeId()).add(hpd);

        });
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>HvacPointHolder.init初始化加载成功<<<<<<<<<<<<<<<<<<<<<<<<<");

    }


    /**
     * 重新加载
     */
    public void reload(){
        init();
    }
}
