package com.mytv.rtzhdj.app.data.entity;

/**
 * StudyCoursewareEntity   课件详情实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update 2018-5-23    update 结构
 */
public class CoursewareDetailEntity {

    private int StudyTime;      // 学习时长(s)
    private int Integral;       // 积分

    public int getStudyTime() {
        return StudyTime;
    }

    public void setStudyTime(int studyTime) {
        StudyTime = studyTime;
    }

    public int getIntegral() {
        return Integral;
    }

    public void setIntegral(int integral) {
        Integral = integral;
    }
}
