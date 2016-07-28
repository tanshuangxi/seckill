package org.seckill.web;

import org.seckill.constants.SeckillStateEnum;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillClosedException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by tanshuangxi on 2016/5/30.
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    /**
     * 秒杀商品列表
     * @param model
     * @return
     */
    @RequestMapping(name = "/list", method = RequestMethod.GET)
    public String list(Model model){
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list", list);
        return "list";
    }

    /**
     * 秒杀详情页
     * @param seckillId
     * @param model
     * @return
     */
    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model){

        return "";
    }

    /**
     * 获取秒杀请求地址
     * @param seckillId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST,
            produces = {"application/json;charset=utf-8"})
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){

        SeckillResult<Exposer> result = null;
        try{
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }

        return result;
    }

    /**
     * 执行秒杀
     * @param seckillId
     * @param md5
     * @param phone
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{seckillId}/{md5}/execute", method = RequestMethod.POST,
            produces = {"application/json;charset=utf-8"})
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                          @PathVariable("md5") String md5,
                                          @CookieValue(value="killPhone", required = false) Long phone){


        if(phone == null){
            return new SeckillResult<SeckillExecution>(false, "未注册");
        }

        try{
            SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, phone, md5);
            return new SeckillResult<SeckillExecution>(true, seckillExecution);

        }catch(RepeatKillException e){
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.PEPEAT_KILL);
            return new SeckillResult<SeckillExecution>(false, seckillExecution);

        }catch(SeckillClosedException e){
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.END);
            return new SeckillResult<SeckillExecution>(false, seckillExecution);

        }catch(Exception e){
            logger.error(e.getMessage(), e);
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(true, seckillExecution);
        }
    }

    /**
     * 获取系统当前时间
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    public SeckillResult<Long> time(){
        return new SeckillResult<Long>(true, new Date().getTime());
    }


}
