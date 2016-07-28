package org.seckill.exception;

/**
 * 重复秒杀异常
 * Created by tanshuangxi on 2016/5/30.
 */
public class RepeatKillException extends SeckillException{
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException() {
        super();
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
