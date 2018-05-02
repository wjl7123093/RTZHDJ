package com.mytv.rtzhdj.app.data.entity;

/**
 * StudyCoursewareEntity   学习课件实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update 2018-5-2     修改了结构
 */
public class StudyCoursewareEntity {

    private int ContentId;              // 内容id（拼接在url末尾）
    private String Title;               // 课件标题
//    private int courseType;             // 课件类型
    private String LastStudyTime;       // 上次学习时间
    private String CourseTypeName;      // 课件类型描述

    public int getContentId() {
        return ContentId;
    }

    public void setContentId(int contentId) {
        ContentId = contentId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLastStudyTime() {
        return LastStudyTime;
    }

    public void setLastStudyTime(String lastStudyTime) {
        LastStudyTime = lastStudyTime;
    }

    public String getCourseTypeName() {
        return CourseTypeName;
    }

    public void setCourseTypeName(String courseTypeName) {
        CourseTypeName = courseTypeName;
    }
}
