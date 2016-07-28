package org.seckill.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillClosedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by tanshuangxi on 2016/5/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void testGetSeckillList() throws Exception{
        List<Seckill> seckillList = seckillService.getSeckillList();
        logger.info("seckillList={}", seckillList);
    }

    @Test
    public void testGetSeckillById() throws Exception{
        long seckillId = 1000L;
        Seckill seckill = seckillService.getSeckillById(seckillId);
        logger.info("seckill={}", seckill);
    }

    @Test
    public void testSeckillLogic() throws Exception{
        long id = 1000L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if(exposer.isExposed()){
            logger.info("exposer={}", exposer);
            long phone = 13051770294L;
            String md5 = exposer.getMd5();
            try{
                SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
                logger.info("result={}", seckillExecution);
            }catch(RepeatKillException e){
                logger.error(e.getMessage(), e);
            }catch(SeckillClosedException e){
                logger.error(e.getMessage(), e);
            }
        }else{
            //秒杀未开启
            logger.warn("exporse={}", exposer);
        }
    }

    @Test
      public void testExportSeckillUrl() throws Exception{
        Exposer exposer = seckillService.exportSeckillUrl(1000L);
        logger.info("exposer={}", exposer);
    }

    /**
     * 此方法不能重复测试，秒杀完整测试逻辑见方法 testSeckillLogic
     * @See testSeckillLogic
     * @throws Exception
     */
    @Ignore
    public void testxecuteSeckill() throws Exception{
        long id = 1000L;
        long phone = 13051770294L;
        String md5 = "7d6366046754b0d5923b88b8ad907e70";
        try{
            SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
            logger.info("result={}", seckillExecution);
        }catch(RepeatKillException e){
            logger.error(e.getMessage(), e);
        }catch(SeckillClosedException e){
            logger.error(e.getMessage(), e);
        }
    }
}
