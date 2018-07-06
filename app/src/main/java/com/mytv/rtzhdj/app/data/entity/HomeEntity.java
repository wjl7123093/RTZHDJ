package com.mytv.rtzhdj.app.data.entity;

import java.io.Serializable;
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
public class HomeEntity implements Serializable {

    private List<SpecialBlock> SpecialBlock;                            // 专题
    private List<NoticeBlock> NoticeBlock_ChildContent;                 // 通知
    private List<FocusNewsBlock> FocusNewsBlock_ChildContent;           // 要闻
    private List<AdBlock> AdBlock;                                      // 广告
    private List<List<PublicSpiritedBlock>> PublicSpiritedBlock_ChildContent; // 推荐活动
    private int myPositiveValue;    // 正能量值

    public List<HomeEntity.SpecialBlock> getSpecialBlock() {
        return SpecialBlock;
    }

    public void setSpecialBlock(List<HomeEntity.SpecialBlock> specialBlock) {
        SpecialBlock = specialBlock;
    }

    public List<NoticeBlock> getNoticeBlock_ChildContent() {
        return NoticeBlock_ChildContent;
    }

    public void setNoticeBlock_ChildContent(List<NoticeBlock> noticeBlock_ChildContent) {
        NoticeBlock_ChildContent = noticeBlock_ChildContent;
    }

    public List<FocusNewsBlock> getFocusNewsBlock_ChildContent() {
        return FocusNewsBlock_ChildContent;
    }

    public void setFocusNewsBlock_ChildContent(List<FocusNewsBlock> focusNewsBlock_ChildContent) {
        FocusNewsBlock_ChildContent = focusNewsBlock_ChildContent;
    }

    public List<HomeEntity.AdBlock> getAdBlock() {
        return AdBlock;
    }

    public void setAdBlock(List<HomeEntity.AdBlock> adBlock) {
        AdBlock = adBlock;
    }

    public List<List<PublicSpiritedBlock>> getPublicSpiritedBlock_ChildContent() {
        return PublicSpiritedBlock_ChildContent;
    }

    public void setPublicSpiritedBlock_ChildContent(List<List<PublicSpiritedBlock>> publicSpiritedBlock_ChildContent) {
        PublicSpiritedBlock_ChildContent = publicSpiritedBlock_ChildContent;
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
    public class SpecialBlock {
        private int NodeId;
        private String ImageUrl;
        private String AllImgUrl;

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

        public String getAllImgUrl() {
            return AllImgUrl;
        }

        public void setAllImgUrl(String allImgUrl) {
            AllImgUrl = allImgUrl;
        }
    }

    /**
     * 通知
     */
    public class NoticeBlock {
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
    public class FocusNewsBlock {
        private int NodeId;
        private int ID;
        private String Title;
        private String AddDate;
        private int Digs;
        private int Comments;
        private String ImageUrl;
        private String AllImgUrl;

        private int IsVideo;            // 是否视频

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

        public String getAllImgUrl() {
            return AllImgUrl;
        }

        public void setAllImgUrl(String allImgUrl) {
            AllImgUrl = allImgUrl;
        }

        public int getIsVideo() {
            return IsVideo;
        }

        public void setIsVideo(int isVideo) {
            IsVideo = isVideo;
        }
    }

    /**
     * 广告
     */
    public class AdBlock {
        private int NodeId;
        private String ImageUrl;
        private String AllImgUrl;

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

        public String getAllImgUrl() {
            return AllImgUrl;
        }

        public void setAllImgUrl(String allImgUrl) {
            AllImgUrl = allImgUrl;
        }
    }

    /**
     * 推荐活动
     */
    public class PublicSpiritedBlock {
        private int NodeId;
        private int ArticleId;
        private String Title;
        private int Digs;
        private int EnrollCount;
        private String TitleImageUrl;
        private String AllImgUrl;

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

        public String getAllImgUrl() {
            return AllImgUrl;
        }

        public void setAllImgUrl(String allImgUrl) {
            AllImgUrl = allImgUrl;
        }
    }

}
