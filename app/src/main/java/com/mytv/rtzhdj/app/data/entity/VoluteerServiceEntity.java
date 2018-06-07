package com.mytv.rtzhdj.app.data.entity;

/**
 * VoluteerServiceEntity   志愿服务实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-7
 * @update 2018-5-5     更新结构
 */
public class VoluteerServiceEntity {

    private int NodeId;
    private int Id;                         //
    private String Title;                   // 活动标题
    private String ImgUrl;
    private boolean IsEnd;
    private String EnrollEndDate;           // 报名截止日期
    private int EnrollCount;                // 报名限额
    private int SignedUp;                   // 已报名人数
    private String AllImgUrl;

    public int getNodeId() {
        return NodeId;
    }

    public void setNodeId(int nodeId) {
        NodeId = nodeId;
    }

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

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public boolean isEnd() {
        return IsEnd;
    }

    public void setEnd(boolean end) {
        IsEnd = end;
    }

    public String getEnrollEndDate() {
        return EnrollEndDate;
    }

    public void setEnrollEndDate(String enrollEndDate) {
        EnrollEndDate = enrollEndDate;
    }

    public int getEnrollCount() {
        return EnrollCount;
    }

    public void setEnrollCount(int enrollCount) {
        EnrollCount = enrollCount;
    }

    public int getSignedup() {
        return SignedUp;
    }

    public void setSignedup(int signedup) {
        SignedUp = signedup;
    }

    public String getAllImgUrl() {
        return AllImgUrl;
    }

    public void setAllImgUrl(String allImgUrl) {
        AllImgUrl = allImgUrl;
    }
}
