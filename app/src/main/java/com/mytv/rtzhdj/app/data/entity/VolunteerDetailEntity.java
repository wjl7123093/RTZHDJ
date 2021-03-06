package com.mytv.rtzhdj.app.data.entity;

/**
 * VolunteerDetailEntity   志愿服务详情实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update 2018-4-25 update 结构
 *         2018-5-14 update 结构
 */
public class VolunteerDetailEntity {

    private String Title;                   // 活动标题
    private String EnrollEndDate;           // 报名截止日期
    private String StartDate;               // 活动起始日期
    private String EndDate;                 // 活动结束日期
    private String Address;                 // 活动地点
    private int EnrollCount;                // 报名限额
    private int Signedup;                   // 已报名人数
    private float Score;                      // 活动评分
    private int Id;                         // 通过ID关联url显示内容到下面，活动回顾暂时没有设计
    private String review;                  // ?
//    private List<Comment> commentObjs;      // 评论
//    private List<SubColumn> subObjs;        // 子栏目

    private int Digs;                       // 点赞数
    private int IfJoin;                     // 是否结束 0 正在报名 1 已报名
    private int JoinScore;                  // 参与可获得分数


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getEnrollEndDate() {
        return EnrollEndDate;
    }

    public void setEnrollEndDate(String enrollEndDate) {
        EnrollEndDate = enrollEndDate;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getEnrollCount() {
        return EnrollCount;
    }

    public void setEnrollCount(int enrollCount) {
        EnrollCount = enrollCount;
    }

    public int getSignedup() {
        return Signedup;
    }

    public void setSignedup(int signedup) {
        Signedup = signedup;
    }

    public float getScore() {
        return Score;
    }

    public void setScore(float score) {
        Score = score;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getDigs() {
        return Digs;
    }

    public void setDigs(int digs) {
        this.Digs = digs;
    }

    public int getIfJoin() {
        return IfJoin;
    }

    public void setIfJoin(int ifJoin) {
        IfJoin = ifJoin;
    }

    public int getJoinScore() {
        return JoinScore;
    }

    public void setJoinScore(int joinScore) {
        JoinScore = joinScore;
    }
}
