package com.mytv.rtzhdj.app.data.entity;

/**
 * StudyCoursewareEntity   学习课件实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update
 */
public class StudyCoursewareEntity {

    private int contentId;              // 内容id（拼接在url末尾）
    private String title;               // 课件标题
    private int courseType;             // 课件类型
    private String lastStudyTime;       // 上次学习时间
    private String courseTypeName;      // 课件类型描述

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCourseType() {
        return courseType;
    }

    public void setCourseType(int courseType) {
        this.courseType = courseType;
    }

    public String getLastStudyTime() {
        return lastStudyTime;
    }

    public void setLastStudyTime(String lastStudyTime) {
        this.lastStudyTime = lastStudyTime;
    }

    public String getCourseTypeName() {
        return courseTypeName;
    }

    public void setCourseTypeName(String courseTypeName) {
        this.courseTypeName = courseTypeName;
    }
}
