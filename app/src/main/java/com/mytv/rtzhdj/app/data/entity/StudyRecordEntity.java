package com.mytv.rtzhdj.app.data.entity;

/**
 * StudyRecordEntity   学习记录实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update
 */
public class StudyRecordEntity {

    private String Title;               // 标题
    private int Score;                  // 分数
    private String LastStudyTime;       // 上次学习时间
    private String typeString;          // 课件类型

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public String getLastStudyTime() {
        return LastStudyTime;
    }

    public void setLastStudyTime(String lastStudyTime) {
        LastStudyTime = lastStudyTime;
    }

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }
}
