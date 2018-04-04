package com.mytv.rtzhdj.app.data.entity;

/**
 * StudyCoursewareEntity   课件详情实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update
 */
public class CoursewareDetailEntity {

    private String StudyTime;       // 还剩多长时间

    public String getStudyTime() {
        return StudyTime;
    }

    public void setStudyTime(String studyTime) {
        StudyTime = studyTime;
    }
}
