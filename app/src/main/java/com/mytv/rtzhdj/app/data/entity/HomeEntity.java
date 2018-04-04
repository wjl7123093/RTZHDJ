package com.mytv.rtzhdj.app.data.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * HomeEntity   首页实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update
 */
public class HomeEntity {

    private List<SpecialBlock> specialBlock;
    private List<NoticeBlock> noticeBlock;
    private List<FocusNewsBlock> focusNewsBlock;
    private List<AdBlock> adBlock;
    private List<PublicSpiritedBlock> publicSpiritedBlock;
    private int myPositiveValue;    // 正能量值

    public List<SpecialBlock> getSpecialBlock() {
        return specialBlock;
    }

    public void setSpecialBlock(List<SpecialBlock> specialBlock) {
        this.specialBlock = specialBlock;
    }

    public List<NoticeBlock> getNoticeBlock() {
        return noticeBlock;
    }

    public void setNoticeBlock(List<NoticeBlock> noticeBlock) {
        this.noticeBlock = noticeBlock;
    }

    public List<FocusNewsBlock> getFocusNewsBlock() {
        return focusNewsBlock;
    }

    public void setFocusNewsBlock(List<FocusNewsBlock> focusNewsBlock) {
        this.focusNewsBlock = focusNewsBlock;
    }

    public List<AdBlock> getAdBlock() {
        return adBlock;
    }

    public void setAdBlock(List<AdBlock> adBlock) {
        this.adBlock = adBlock;
    }

    public List<PublicSpiritedBlock> getPublicSpiritedBlock() {
        return publicSpiritedBlock;
    }

    public void setPublicSpiritedBlock(List<PublicSpiritedBlock> publicSpiritedBlock) {
        this.publicSpiritedBlock = publicSpiritedBlock;
    }

    public int getMyPositiveValue() {
        return myPositiveValue;
    }

    public void setMyPositiveValue(int myPositiveValue) {
        this.myPositiveValue = myPositiveValue;
    }

    /**
     * 首页专题
     */
    class SpecialBlock {
        private int NodeId;
        private String ImageUrl;

        public int getNodeId() {
            return NodeId;
        }

        public void setNodeId(int nodeId) {
            NodeId = nodeId;
        }

        public String getImageUrl() {
            return ImageUrl;
        }

        public void setImageUrl(String imageUrl) {
            ImageUrl = imageUrl;
        }
    }

    /**
     * 通知
     */
    class NoticeBlock {
        private int NodeId;
        private int ID;
        private String Title;
        private String AddDate;

        public int getNodeId() {
            return NodeId;
        }

        public void setNodeId(int nodeId) {
            NodeId = nodeId;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getAddDate() {
            return AddDate;
        }

        public void setAddDate(String addDate) {
            AddDate = addDate;
        }
    }

    /**
     * 要闻
     */
    class FocusNewsBlock {
        private int NodeId;
        private int ID;
        private String Title;
        private String AddDate;
        private int Digs;
        private int Comments;
        private String ImageUrl;

        public int getNodeId() {
            return NodeId;
        }

        public void setNodeId(int nodeId) {
            NodeId = nodeId;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getAddDate() {
            return AddDate;
        }

        public void setAddDate(String addDate) {
            AddDate = addDate;
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

        public String getImageUrl() {
            return ImageUrl;
        }

        public void setImageUrl(String imageUrl) {
            ImageUrl = imageUrl;
        }
    }

    /**
     * 广告
     */
    class AdBlock {
        private int NodeId;
        private String ImageUrl;

        public int getNodeId() {
            return NodeId;
        }

        public void setNodeId(int nodeId) {
            NodeId = nodeId;
        }

        public String getImageUrl() {
            return ImageUrl;
        }

        public void setImageUrl(String imageUrl) {
            ImageUrl = imageUrl;
        }
    }

    /**
     * 推荐活动
     */
    class PublicSpiritedBlock {
        private int NodeId;
        private int ArticleId;
        private String Title;
        private int Digs;
        private int EnrollCount;
        private String TitleImageUrl;

        public int getNodeId() {
            return NodeId;
        }

        public void setNodeId(int nodeId) {
            NodeId = nodeId;
        }

        public int getArticleId() {
            return ArticleId;
        }

        public void setArticleId(int articleId) {
            ArticleId = articleId;
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

        public int getEnrollCount() {
            return EnrollCount;
        }

        public void setEnrollCount(int enrollCount) {
            EnrollCount = enrollCount;
        }

        public String getTitleImageUrl() {
            return TitleImageUrl;
        }

        public void setTitleImageUrl(String titleImageUrl) {
            TitleImageUrl = titleImageUrl;
        }
    }

}
