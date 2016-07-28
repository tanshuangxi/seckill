package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by tanshuangxi on 2016/5/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Test
    public void testInsertSuccessKilled() throws Exception{
        long skillId = 1000L;
        long userPhone = 13051772094L;
        int count = successKilledDao.insertSuccessKilled(skillId, userPhone);
        System.out.println("count : " + count);
    }

    @Test
    public void testQeuryByIdWithSeckill() throws Exception{
        long skillId = 1000L;
        long userPhone = 13051772094L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(skillId, userPhone);
        System.out.println(successKilled);
    }
}
