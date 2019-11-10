package com.landleaf.ibsaas.web.web.service.parking.impl;

import com.github.pagehelper.PageHelper;
import com.landleaf.ibsaas.common.dao.parking.HeartBeatRecordDao;
import com.landleaf.ibsaas.common.domain.parking.HeartBeatRecord;
import com.landleaf.ibsaas.common.domain.parking.request.HeartBeatDTO;
import com.landleaf.ibsaas.common.enums.parking.HeartBeatStatusEnum;
import com.landleaf.ibsaas.common.utils.id.SnowflakeIdWorker;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.service.parking.ICommonParkingHeartBeatRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class CommonParkingHeartBeatRecordService extends AbstractBaseService<HeartBeatRecordDao, HeartBeatRecord> implements ICommonParkingHeartBeatRecordService<HeartBeatRecord> {
    @Autowired
    private HeartBeatRecordDao heartBeatRecordDao;
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Override
    public void updateLatestedRecord(HeartBeatDTO requestBody) {
        PageHelper.startPage(1, 1, true);
        Example example = new Example(HeartBeatRecord.class);
        example.setOrderByClause("end_time desc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition("client_id=", requestBody.getClientId());
        List<HeartBeatRecord> queryResult = selectByExample(example);

        HeartBeatRecord save = null;
        HeartBeatRecord update = null;
        /**
         *1、如果无记录，此记录为第一条，则新增插入
         */
        if (CollectionUtils.isEmpty(queryResult)) {
            Date date = new Date();
            save = buildSaveData(requestBody.getClientId(), requestBody.getClientInfo(), requestBody.getClientVersion(),
                    date, date, HeartBeatStatusEnum.KEEP.type);
        } else {
            /**
             * 有记录
             *
             *    若上次为保持 ==》小于则修改上次时间的截止时间；大于则插入一条丢失记录
             *    若上次为丢失==》小于则新增一条保持记录，起始时间为上次截止时间，截止时间为本次时间；大于则修改上次丢失的截止时间。
             */
            update = queryResult.get(0);
            Date startTime = update.getStartTime();
            Date endTime = update.getEndTime();
            //比较时间
            Date currentDate = new Date();

            Date targetDate = new Date(endTime.getTime() + 1000L * requestBody.getInterval());
            if (update.getStatus().intValue() == HeartBeatStatusEnum.KEEP.type) {
                //保持
                if (targetDate.before(currentDate)) {
                    //大于===>插入丢失记录
                    save = buildSaveData(requestBody.getClientId(), requestBody.getClientInfo(), requestBody.getClientVersion(),
                            endTime, currentDate, HeartBeatStatusEnum.LOSE.type);
                } else {
                    //小于===>修改先前截止时间
                    update.setEndTime(currentDate);
                    update.setModifyTime(currentDate);
                }
            } else if (update.getStatus().intValue() == HeartBeatStatusEnum.LOSE.type) {
                //丢失
                if (targetDate.before(currentDate)) {
                    //大于===>修改丢失截止时间
                    update.setEndTime(currentDate);
                    update.setModifyTime(currentDate);
                } else {
                    //小于===>插入新记录
                    save = buildSaveData(requestBody.getClientId(), requestBody.getClientInfo(), requestBody.getClientVersion(),
                            endTime, currentDate, HeartBeatStatusEnum.KEEP.type);
                }
            }
        }
        if (save != null) {
            save(save);
        } else {
            if (update != null) {
                updateByPrimaryKey(update);
            }
        }
    }

    private HeartBeatRecord buildSaveData(String clientId, String clientInfo, String clientVersion, Date startTime, Date endTime, int status) {
        HeartBeatRecord save = new HeartBeatRecord();
        save.setClientId(clientId);
        save.setClientInfo(clientInfo);
        save.setClientVersion(clientVersion);
        save.setStartTime(startTime);
        save.setEndTime(endTime);
        save.setStatus(status);
        save.setCreateTime(new Date());
        save.setId(String.valueOf(snowflakeIdWorker.nextId()));
        save.setActive(1);
        save.setVersionNo(new Date().getTime());
        return save;

    }
}
