package com.mytv.rtzhdj.app.data.entity;

import java.util.List;

/**
 * PartyLiveEntity   党建直播 实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-15
 * @update 2018-5-16    update 结构
 */
public class PartyLiveEntity {

    private String Title;
    private String VideoUrl;
    private int Digs;
    private int Comments;
    private List<Comment> SubObjs;

    private int ContentID;
    private int NodeId;
    private String AllVideoUrl;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

    public int getDigs() {
        return Digs;
    }

    public void setDigs(int digs) {
        Digs = digs;
    }

    public int getComments() {
        return Comments;
    }

    public void setComments(int comments) {
        Comments = comments;
    }

    public List<Comment> getSubObjects() {
        return SubObjs;
    }

    public void setSubObjects(List<Comment> subObjects) {
        SubObjs = subObjects;
    }

    public int getContentID() {
        return ContentID;
    }

    public void setContentID(int contentID) {
        ContentID = contentID;
    }

    public int getNodeId() {
        return NodeId;
    }

    public void setNodeId(int nodeId) {
        NodeId = nodeId;
    }

    public String getAllVideoUrl() {
        return AllVideoUrl;
    }

    public void setAllVideoUrl(String allVideoUrl) {
        AllVideoUrl = allVideoUrl;
    }

    public class Comment {
        private String UserName;
        private String AddDate;
        private String Content;

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getAddDate() {
            return AddDate;
        }

        public void setAddDate(String addDate) {
            AddDate = addDate;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String content) {
            Content = content;
        }
    }

}
