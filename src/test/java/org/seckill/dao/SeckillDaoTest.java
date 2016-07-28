package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by tanshuangxi  on 2016/5/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void testReduceNumber() throws Exception{
         Date killTime = new Date();
         int updateCount = seckillDao.reduceNumber(1000L, killTime);
         System.out.println("updateCount: " + updateCount);
    }

    @Test
    public void testQueryById() throws Exception{
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void testQeuryAll() throws Exception{
        List<Seckill> seckillList = seckillDao.queryAll(1,10);
        for(Seckill seckill : seckillList){
            System.out.println(seckill);
        }

    }
}
