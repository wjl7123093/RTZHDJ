package com.mytv.rtzhdj.app.data.entity;

/**
 * EffectEvaluationEntity   效果评测实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update 2018-4-26    更新实体类（小写 -> 大写）
 *         2018-5-15    update 结构
 */
public class EffectEvaluationEntity {

    private int Id;
    private String Title;               // 标题
    private int Score;                  // 分数
    private int TestAlready;            // 已完成
    private int ExaminationCount;       // 评测总数
    private String StartTime;           // 评测起始时间
    private String EndTime;             // 评测结束时间
    private String LastStudyTime;       // 最近测试时间

    private int examinationTime;        // 测评时间

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

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

    public int getTestAlready() {
        return TestAlready;
    }

    public void setTestAlready(int testAlready) {
        TestAlready = testAlready;
    }

    public int getExaminationCount() {
        return ExaminationCount;
    }

    public void setExaminationCount(int examinationCount) {
        ExaminationCount = examinationCount;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getLastStudyTime() {
        return LastStudyTime;
    }

    public void setLastStudyTime(String lastStudyTime) {
        LastStudyTime = lastStudyTime;
    }

    public int getExaminationTime() {
        return examinationTime;
    }

    public void setExaminationTime(int examinationTime) {
        this.examinationTime = examinationTime;
    }
}
