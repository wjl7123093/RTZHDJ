package com.mytv.rtzhdj.app.data.entity;

/**
 * PartyKnowledgeEntity   党建知识列表实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update
 */
public class PartyKnowledgeEntity {

    private int contentId;      // 内容id（拼接在url末尾）
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
