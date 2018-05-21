package com.mytv.rtzhdj.app.data.entity;

/**
 * PartyNewsEntity   党建新闻实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-20
 * @update
 */
public class PartyNewsEntity {
    private int Nodeid;         // 父节点id
    private int NodeId;         // 父节点Id

    public int getNodeid() {
        return Nodeid;
    }

    public void setNodeid(int nodeid) {
        Nodeid = nodeid;
    }

    public int getNodeId() {
        return NodeId;
    }

    public void setNodeId(int nodeId) {
        NodeId = nodeId;
    }

    private int ArticleId;      // 文章id
    private String Title;       // 标题
    private int Digs;           // 点赞数
    private int Comments;       // 评论数
    private String ImageUrl;    // 缩略图地址
    private String AddDate;     // 时间
    private String AllImgUrl;

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

    public String getAllImgUrl() {
        return AllImgUrl;
    }

    public void setAllImgUrl(String allImgUrl) {
        AllImgUrl = allImgUrl;
    }
}
