package com.landleaf.ibsaas.client.hvac.config.modbus;

import com.landleaf.ibsaas.common.dao.hvac.modbus.MbRegisterDao;
import com.landleaf.ibsaas.common.domain.hvac.assist.MbRegisterDetail;
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
 * @date 2019/7/5 17:35
 * @description:
 */
@Component
@Slf4j
public class MbRegisterHolder {
    public static final Map<Integer, Map<String, List<MbRegisterDetail>>> MASTER_POINT_MAP = new ConcurrentHashMap<>();

    public static final Map<Integer, List<MbRegisterDetail>> MASTER_POINT_LIST_MAP = new ConcurrentHashMap<>();


    @Autowired
    private MbRegisterDao mbRegisterDao;


    @PostConstruct
    public void init(){
        List<MbRegisterDetail> mbRegisterDetails = mbRegisterDao.allMbRegisterDetails();
        mbRegisterDetails.forEach(mrd -> {
            MASTER_POINT_LIST_MAP.computeIfAbsent(mrd.getMbType(), k -> new ArrayList<>());
            MASTER_POINT_LIST_MAP.get(mrd.getMbType()).add(mrd);

            MASTER_POINT_MAP.computeIfAbsent(mrd.getMbType(), k -> new ConcurrentHashMap<>(32));
            MASTER_POINT_MAP.get(mrd.getMbType()).computeIfAbsent(mrd.getNodeId(), k -> new ArrayList<>());
            MASTER_POINT_MAP.get(mrd.getMbType()).get(mrd.getNodeId()).add(mrd);
        });
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>MbRegisterHolder.init初始化完成<<<<<<<<<<<<<<<<<<<<<<<<<");
    }


    public void reload(){
        init();
    }
}
