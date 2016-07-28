package org.seckill.entity;

import java.util.Date;

/**
 * Created by tanshuangxi on 2016/5/29.
 */
public class SuccessKilled {

    private Long seckillId;

    private short state;
    private Date createTime;
    private long userPhone;
    private Seckill seckill;

    public Long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Long seckillId) {
        this.seckillId = seckillId;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", createTime=" + createTime +
                ", userPhone=" + userPhone +
                ", seckill=" + seckill +
                '}';
    }

    public static void main(String[] args) {

    }

}
