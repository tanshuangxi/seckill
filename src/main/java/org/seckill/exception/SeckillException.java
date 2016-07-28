package org.seckill.exception;

/**
 * 秒杀异常
 * Created by tanshuangxi on 2016/5/30.
 */
public class SeckillException extends RuntimeException {
    public SeckillException() {
        super();
    }

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
