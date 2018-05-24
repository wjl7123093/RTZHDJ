package com.mytv.rtzhdj.app.data.entity;

/**
 * HeaderIntegralEntity   头部积分实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-24
 * @update
 */
public class HeaderIntegralEntity {

    private int Integral;
    private int PlanValue;
    private int NextValue;

    public int getIntegral() {
        return Integral;
    }

    public void setIntegral(int integral) {
        Integral = integral;
    }

    public int getPlanValue() {
        return PlanValue;
    }

    public void setPlanValue(int planValue) {
        PlanValue = planValue;
    }

    public int getNextValue() {
        return NextValue;
    }

    public void setNextValue(int nextValue) {
        NextValue = nextValue;
    }
}
