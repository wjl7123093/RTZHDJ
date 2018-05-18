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

    private List<VolunteerBlock> VolunteerBlock;    // 志愿服务
    private List<CommunityBlock> CommunityBlock;    // 社区动态
    private PartyInfoModel PartyInfoModel;          // 报道信息

    public List<VolunteerBlock> getVolunteerBlock() {
        return VolunteerBlock;
    }

    public void setVolunteerBlock(List<VolunteerBlock> volunteerBlock) {
        this.VolunteerBlock = volunteerBlock;
    }

    public List<CommunityBlock> getCommunityBlock() {
        return CommunityBlock;
    }

    public void setCommunityBlock(List<CommunityBlock> communityBlock) {
        this.CommunityBlock = communityBlock;
    }

    public MyJoinEntity.PartyInfoModel getPartyInfoModel() {
        return PartyInfoModel;
    }

    public void setPartyInfoModel(MyJoinEntity.PartyInfoModel partyInfoModel) {
        PartyInfoModel = partyInfoModel;
    }

    public class VolunteerBlock {
        private int ContentId;
        private String Title;
        private int Digs;
        private int Comments;
        private String Picture;
        private String EnrollEndDate;

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

        public String getPicture() {
            return Picture;
        }

        public void setPicture(String picture) {
            Picture = picture;
        }

        public String getEnrollEndDate() {
            return EnrollEndDate;
        }

        public void setEnrollEndDate(String enrollEndDate) {
            EnrollEndDate = enrollEndDate;
        }
    }

    public class CommunityBlock {
        private int ContentId;
        private String Title;
        private int Digs;
        private int Comments;
        private String Picture;
        private String AddDate;

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

        public String getPicture() {
            return Picture;
        }

        public void setPicture(String picture) {
            Picture = picture;
        }

        public String getAddDate() {
            return AddDate;
        }

        public void setAddDate(String addDate) {
            AddDate = addDate;
        }
    }

    public class PartyInfoModel {
        private String Name;
        private String Phone;
        private String Address;
        private String OptDate;

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String phone) {
            Phone = phone;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getOptDate() {
            return OptDate;
        }

        public void setOptDate(String optDate) {
            OptDate = optDate;
        }
    }

}
