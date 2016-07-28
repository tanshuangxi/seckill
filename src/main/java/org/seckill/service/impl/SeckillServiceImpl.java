package org.seckill.service.impl;

import org.seckill.constants.SeckillStateEnum;
import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillClosedException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by tanshuangxi on 2016/5/30.
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    /**
     * md5盐值
     */
    public final String slat = "sdf234342425#$@*(&^@#)+_)@#$fdfaw33789";

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 10);
    }

    @Override
    public Seckill getSeckillById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {

        Seckill seckill = seckillDao.queryById(seckillId);
        if(seckill == null){
            return new Exposer(false, seckillId);
        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date now = new Date();

        if(now.getTime() < startTime.getTime() || now.getTime() > endTime.getTime()){
            return new Exposer(false, seckillId, now.getTime(), startTime.getTime(), endTime.getTime());
        }

        return new Exposer(true, getMD5(seckillId), seckillId);
    }

    @Transactional
    @Override
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillClosedException {

        if(md5 == null || !md5.equals((getMD5(seckillId)))){
            throw new SeckillException("seckill data rewrite");
        }

        Date nowTime = new Date();
        int updateCount = seckillDao.reduceNumber(seckillId, nowTime);

        try{


            if(updateCount <= 0){
                throw new SeckillClosedException("seckill is closed");
            }else{
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                if(insertCount <= 0){
                    throw new RepeatKillException("seckill repeated");
                }else{
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                }
            }

        }catch(RepeatKillException re){
            throw re;
        }catch(SeckillClosedException se){
            throw se;
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }
    }

    /**
     * 获取秒杀id的md5值
     * @param seckillId
     * @return
     */
    private String getMD5(long seckillId){
        String base = seckillId + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
