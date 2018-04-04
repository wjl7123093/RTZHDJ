package com.mytv.rtzhdj.app.data.entity;

/**
 * NewsDetailEntity   文章详情实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update
 */
public class NewsDetailEntity {

    private int contentId;      // 内容id（跟在url末尾）
    private String title;       // 文章标题
    private int digs;           // 点赞数
    private int comments;       // 评论数

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
}
