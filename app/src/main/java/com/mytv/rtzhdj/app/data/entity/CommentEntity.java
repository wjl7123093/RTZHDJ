package com.mytv.rtzhdj.app.data.entity;

/**
 * CommentEntity   评论实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-27
 * @update
 */
public class CommentEntity {

    private int id;
    private String name;
    private String datetime;
    private String img_url;
    private String content;
    private int star_num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getStar_num() {
        return star_num;
    }

    public void setStar_num(int star_num) {
        this.star_num = star_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
