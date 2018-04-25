package com.mytv.rtzhdj.app.data.entity;

/**
 * CoursewareEntity   学习课件实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-6
 * @update 2018-2-7     新增 scores 字段
 *         2018-2-27    新增 status 字段
 *         2018-4-25    修改实体类字段
 */
public class CoursewareEntity {

    private int ContentId;
    private String Title;           // 标题
    private int CourseType;         // 学习状态（0 未学习，1 已学习未完成，2 已完成）
    private String CourseTypeString;
    private String LastStudyTime;   // 上次学习时间
//    private int scores;         // 得分
//    private int status;         // 学习状态（0 未学习，1 已学习未完成，2 已完成）

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

    public int getCourseType() {
        return CourseType;
    }

    public void setCourseType(int courseType) {
        CourseType = courseType;
    }

    public String getCourseTypeString() {
        return CourseTypeString;
    }

    public void setCourseTypeString(String courseTypeString) {
        CourseTypeString = courseTypeString;
    }

    public String getLastStudyTime() {
        return LastStudyTime;
    }

    public void setLastStudyTime(String lastStudyTime) {
        LastStudyTime = lastStudyTime;
    }
}
