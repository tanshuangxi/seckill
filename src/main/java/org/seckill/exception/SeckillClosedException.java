package org.seckill.exception;

/**
 * 秒杀关闭异常
 * Created by tanshuangxi on 2016/5/30.
 */
public class SeckillClosedException extends SeckillException{
    public SeckillClosedException() {
        super();
    }

    public SeckillClosedException(String message) {
        super(message);
    }

    public SeckillClosedException(String message, Throwable cause) {
        super(message, cause);
    }
}
