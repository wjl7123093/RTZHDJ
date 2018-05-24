package com.mytv.rtzhdj.app.data.entity;

/**
 * SignEntity   签到实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-24
 * @update
 */
public class SignEntity {

    private int Days;       // 连续签到天数
    private int IfSign;     // 当天是否已签到  0 未签到，1 已签到

    public int getDays() {
        return Days;
    }

    public void setDays(int days) {
        Days = days;
    }

    public int getIfSign() {
        return IfSign;
    }

    public void setIfSign(int ifSign) {
        IfSign = ifSign;
    }
}
