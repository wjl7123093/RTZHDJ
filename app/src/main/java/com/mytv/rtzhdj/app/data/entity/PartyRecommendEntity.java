package com.mytv.rtzhdj.app.data.entity;

import java.util.List;

/**
 * PartyRecommendEntity   党建新闻推荐实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update 2018-4-9 更新类名
 */
public class PartyRecommendEntity {

    private List<SpecialBlock> SpecialBlock;        // 专题
    private List<ImportandBlock> ImportandBlock;    // 要闻

    public List<SpecialBlock> getSpecialBlock() {
        return SpecialBlock;
    }

    public void setSpecialBlock(List<SpecialBlock> specialBlock) {
        this.SpecialBlock = specialBlock;
    }

    public List<ImportandBlock> getImportandBlock() {
        return ImportandBlock;
    }

    public void setImportandBlock(List<ImportandBlock> importandBlock) {
        this.ImportandBlock = importandBlock;
    }

    public class SpecialBlock {
        private int ArticleId;
        private String Title;
        private String ImageUrl;

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

        public String getImageUrl() {
            return ImageUrl;
        }

        public void setImageUrl(String imageUrl) {
            ImageUrl = imageUrl;
        }
    }

    public class ImportandBlock {
        private int ArticleId;
        private String Title;
        private int Digs;
        private int Comments;
        private String ImageUrl;
        private String AddDate;

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

        public String getAddDate() {
            return AddDate;
        }

        public void setAddDate(String addDate) {
            AddDate = addDate;
        }
    }

}
