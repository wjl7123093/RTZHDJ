package com.mytv.rtzhdj.app.data.entity;

/**
 * MineEntity   我的积分等数据实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-27
 * @update
 */
public class MineEntity {

    private int ParticipantsNum;    // 参会次数
    private int StudyNum;           // 学习次数
    private int ActivityNum;        // 活动次数
    private int UserScores;         // 积分
    private int UserEnergyValue;    // 正能量值

    public int getParticipantsNum() {
        return ParticipantsNum;
    }

    public void setParticipantsNum(int participantsNum) {
        ParticipantsNum = participantsNum;
    }

    public int getStudyNum() {
        return StudyNum;
    }

    public void setStudyNum(int studyNum) {
        StudyNum = studyNum;
    }

    public int getActivityNum() {
        return ActivityNum;
    }

    public void setActivityNum(int activityNum) {
        ActivityNum = activityNum;
    }

    public int getUserScores() {
        return UserScores;
    }

    public void setUserScores(int userScores) {
        UserScores = userScores;
    }

    public int getUserEnergyValue() {
        return UserEnergyValue;
    }

    public void setUserEnergyValue(int userEnergyValue) {
        UserEnergyValue = userEnergyValue;
    }
}
