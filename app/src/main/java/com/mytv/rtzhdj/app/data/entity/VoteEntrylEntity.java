package com.mytv.rtzhdj.app.data.entity;

/**
 * VoteEntryEntity   投票作品实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-3-26
 * @update
 */
public class VoteEntrylEntity {

    private int id;
    private String name;        // 作品名称
    private String author;      // 作者
    private String datetime;    // 时间
    private String img_url;     // 作品图片地址
    private String content;     // 作品描述
    private int rank;           // 排名
    private int votes;          // 得票数

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}
