package com.mytv.rtzhdj.app.data.entity;

import java.util.List;

/**
 * MyJoinEntity   我要参与面实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update
 */
public class MyJoinEntity {

    private List<VolunteerBlock> volunteerBlock;
    private List<CommunityBlock> communityBlock;

    public List<VolunteerBlock> getVolunteerBlock() {
        return volunteerBlock;
    }

    public void setVolunteerBlock(List<VolunteerBlock> volunteerBlock) {
        this.volunteerBlock = volunteerBlock;
    }

    public List<CommunityBlock> getCommunityBlock() {
        return communityBlock;
    }

    public void setCommunityBlock(List<CommunityBlock> communityBlock) {
        this.communityBlock = communityBlock;
    }

    class VolunteerBlock {
        private int contentId;
        private String title;
        private int digs;
        private int comments;
        private String picture;
        private String EnrollEndDate;

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

        public int getDigs() {
            return digs;
        }

        public void setDigs(int digs) {
            this.digs = digs;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getEnrollEndDate() {
            return EnrollEndDate;
        }

        public void setEnrollEndDate(String enrollEndDate) {
            EnrollEndDate = enrollEndDate;
        }
    }

    class CommunityBlock {
        private int contentId;
        private String title;
        private int digs;
        private int comments;
        private String picture;
        private String AddDate;

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

        public int getDigs() {
            return digs;
        }

        public void setDigs(int digs) {
            this.digs = digs;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getAddDate() {
            return AddDate;
        }

        public void setAddDate(String addDate) {
            AddDate = addDate;
        }
    }

}
