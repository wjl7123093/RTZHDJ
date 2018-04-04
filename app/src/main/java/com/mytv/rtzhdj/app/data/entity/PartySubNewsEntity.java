package com.mytv.rtzhdj.app.data.entity;

import java.util.List;

/**
 * PartySubNewsEntity   党建新闻二级列表实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update
 */
public class PartySubNewsEntity {

    private List<ChannelNewsBlock> channelNewsBlock;    // 二级新闻列表

    public List<ChannelNewsBlock> getChannelNewsBlock() {
        return channelNewsBlock;
    }

    public void setChannelNewsBlock(List<ChannelNewsBlock> channelNewsBlock) {
        this.channelNewsBlock = channelNewsBlock;
    }

    class ChannelNewsBlock {
        private int ArticleId;      // 文章id
        private String Title;       // 标题
        private int Digs;           // 点赞数
        private int Comments;       // 评论数
        private String ImageUrl;    // 缩略图地址

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
