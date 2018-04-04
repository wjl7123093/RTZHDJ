package com.mytv.rtzhdj.app.data.entity;

import java.util.List;

/**
 * VolunteerDetailEntity   志愿服务详情实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update
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
    private int id;
    private List<Comment> commentObjs;      // 评论
    private List<SubColumn> subObjs;        // 子栏目

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

    public List<Comment> getCommentObjs() {
        return commentObjs;
    }

    public void setCommentObjs(List<Comment> commentObjs) {
        this.commentObjs = commentObjs;
    }

    public List<SubColumn> getSubObjs() {
        return subObjs;
    }

    public void setSubObjs(List<SubColumn> subObjs) {
        this.subObjs = subObjs;
    }

    class Comment {
        private String username;
        private String adddate;
        private String content;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAdddate() {
            return adddate;
        }

        public void setAdddate(String adddate) {
            this.adddate = adddate;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    class SubColumn {
        private int nodeId;
        private String title;

        public int getNodeId() {
            return nodeId;
        }

        public void setNodeId(int nodeId) {
            this.nodeId = nodeId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
