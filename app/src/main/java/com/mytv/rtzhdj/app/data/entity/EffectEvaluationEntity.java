package com.mytv.rtzhdj.app.data.entity;

/**
 * EffectEvaluationEntity   效果评测实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update
 */
public class EffectEvaluationEntity {

    private int id;
    private String title;               // 标题
    private int score;                  // 分数
    private int testAlready;            // 已完成
    private int examinationCount;       // 评测总数
    private String startTime;           // 评测起始时间
    private String endTime;             // 评测结束时间
    private String lastStudyTime;       // 最近测试时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTestAlready() {
        return testAlready;
    }

    public void setTestAlready(int testAlready) {
        this.testAlready = testAlready;
    }

    public int getExaminationCount() {
        return examinationCount;
    }

    public void setExaminationCount(int examinationCount) {
        this.examinationCount = examinationCount;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLastStudyTime() {
        return lastStudyTime;
    }

    public void setLastStudyTime(String lastStudyTime) {
        this.lastStudyTime = lastStudyTime;
    }
}
