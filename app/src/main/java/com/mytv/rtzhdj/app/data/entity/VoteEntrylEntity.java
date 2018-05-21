package com.mytv.rtzhdj.app.data.entity;

/**
 * VoteEntryEntity   投票作品实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-3-26
 * @update 2018-5-3     更新结构
 */
public class VoteEntrylEntity {

    private int Id;
    private String Title;        // 作品名称
//    private String author;      // 作者
//    private String datetime;    // 时间
    private String ImgUrl;     // 作品图片地址
    private String AllImgUrl;
//    private String content;     // 作品描述
    private int Ranking;           // 排名
    private int VoteNum;          // 得票数

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public int getRanking() {
        return Ranking;
    }

    public void setRanking(int ranking) {
        Ranking = ranking;
    }

    public int getVoteNum() {
        return VoteNum;
    }

    public void setVoteNum(int voteNum) {
        VoteNum = voteNum;
    }

    public String getAllImgUrl() {
        return AllImgUrl;
    }

    public void setAllImgUrl(String allImgUrl) {
        AllImgUrl = allImgUrl;
    }
}
