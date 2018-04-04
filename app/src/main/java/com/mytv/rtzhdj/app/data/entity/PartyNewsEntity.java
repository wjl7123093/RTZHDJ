package com.mytv.rtzhdj.app.data.entity;

import java.util.List;

/**
 * PartyNewsEntity   党建新闻实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update
 */
public class PartyNewsEntity {

    private List<SpecialBlock> specialBlock;
    private List<ImportandBlock> importandBlock;

    public List<SpecialBlock> getSpecialBlock() {
        return specialBlock;
    }

    public void setSpecialBlock(List<SpecialBlock> specialBlock) {
        this.specialBlock = specialBlock;
    }

    public List<ImportandBlock> getImportandBlock() {
        return importandBlock;
    }

    public void setImportandBlock(List<ImportandBlock> importandBlock) {
        this.importandBlock = importandBlock;
    }

    class SpecialBlock {
        private int Article;
        private String ImageUrl;

        public int getArticle() {
            return Article;
        }

        public void setArticle(int article) {
            Article = article;
        }

        public String getImageUrl() {
            return ImageUrl;
        }

        public void setImageUrl(String imageUrl) {
            ImageUrl = imageUrl;
        }
    }

    class ImportandBlock {
        private int ArticleId;
        private String Title;
        private int Digs;
        private int Comments;
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

}
