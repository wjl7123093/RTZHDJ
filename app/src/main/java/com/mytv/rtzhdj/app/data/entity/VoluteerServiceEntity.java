package com.mytv.rtzhdj.app.data.entity;

/**
 * VoluteerServiceEntity   志愿服务实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-7
 * @update
 */
public class VoluteerServiceEntity {

    private int id;
    private String url;             // 活动图片
    private String deadtime;        // 截止日期
    private String title;           // 标题
    private int star_num;           // 点赞数
    private int comment_num;        // 评论数
    private int join_num;           // 参与人数
    private int total_num;          // 总人数
    private int status;             // 状态（0 进行，1 结束）

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDeadtime() {
        return deadtime;
    }

    public void setDeadtime(String deadtime) {
        this.deadtime = deadtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStar_num() {
        return star_num;
    }

    public void setStar_num(int star_num) {
        this.star_num = star_num;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public int getJoin_num() {
        return join_num;
    }

    public void setJoin_num(int join_num) {
        this.join_num = join_num;
    }

    public int getTotal_num() {
        return total_num;
    }

    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
