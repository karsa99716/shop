package com.gjc.domain;

import java.util.Date;

public class Activate {
    private int activateid;//id
    private String code;//验证码
    private Date expiredate;//过期时间
    private int vipid;//用户id

    public int getActivateid() {
        return activateid;
    }

    public void setActivateid(int activateid) {
        this.activateid = activateid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getExpiredate() {
        return expiredate;
    }

    public void setExpiredate(Date expiredate) {
        this.expiredate = expiredate;
    }

    public int getVipid() {
        return vipid;
    }

    public void setVipid(int vipid) {
        this.vipid = vipid;
    }

    @Override
    public String toString() {
        return "Activate{" +
                "activateid=" + activateid +
                ", code='" + code + '\'' +
                ", expiredate=" + expiredate +
                ", vipid=" + vipid +
                '}';
    }

}
