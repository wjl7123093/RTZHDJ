package com.mytv.rtzhdj.app.data.entity;

/**
 * SignScoresEntity   签到积分实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-27
 * @update
 */
public class SignScoresEntity {

    private int Integral;           // 当前获取积分
    private int CurrentIntegral;    // 总积分

    public int getIntegral() {
        return Integral;
    }

    public void setIntegral(int integral) {
        Integral = integral;
    }

    public int getCurrentIntegral() {
        return CurrentIntegral;
    }

    public void setCurrentIntegral(int currentIntegral) {
        CurrentIntegral = currentIntegral;
    }
}
