package com.mytv.rtzhdj.app.data.entity;

import java.util.List;

/**
 * VolunteerDetailEntity   志愿服务详情实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update 2018-4-5 update 结构
 */
public class VolunteerDetailEntity {

    private String title;                   // 活动标题
    private String enrollEndDate;           // 报名截止日期
    private String startDate;               // 活动起始日期
    private String endDate;                 // 活动结束日期
    private String address;                 // 活动地点
    private int enrollCount;                // 报名限额
    private int signedup;                   // 已报名人数
    private int score;                      // 活动评分
    private int id;                         // 通过ID关联url显示内容到下面，活动回顾暂时没有设计
//    private List<Comment> commentObjs;      // 评论
//    private List<SubColumn> subObjs;        // 子栏目

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEnrollEndDate() {
        return enrollEndDate;
    }

    public void setEnrollEndDate(String enrollEndDate) {
        this.enrollEndDate = enrollEndDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getEnrollCount() {
        return enrollCount;
    }

    public void setEnrollCount(int enrollCount) {
        this.enrollCount = enrollCount;
    }

    public int getSignedup() {
        return signedup;
    }

    public void setSignedup(int signedup) {
        this.signedup = signedup;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
