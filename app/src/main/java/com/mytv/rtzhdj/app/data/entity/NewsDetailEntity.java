package com.mytv.rtzhdj.app.data.entity;

/**
 * NewsDetailEntity   文章详情实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update 2018-4-20 更新
 */
public class NewsDetailEntity {

    private int ContentId;      // 内容id（跟在url末尾）
    private String Title;       // 文章标题
    private String Content;
    private int Digs;           // 点赞数
    private int Comments;       // 评论数

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

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
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
}
