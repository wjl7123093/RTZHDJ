package com.mytv.rtzhdj.app.data.entity;

/**
 * VoteDetailEntity   投票作品详情实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update 2018-5-2 修改结构
 *         2018-5-5 修改结构
 */
public class VoteDetailEntity {

    private int Id;
    private String Title;       // 标题
    private int Ranking;        // 排名
//    private String author;      // 作者
    private int VoteNum;        // 投票数
    private String ImgUrl;      // 作品图片地址
    private String AllImgUrl;

    // 投票作品列表 实体
    private int ContentId;          // 活动ID
    private int poll;               // 得票数
    private int IsVote;             // 是否可以投票

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

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public int getContentId() {
        return ContentId;
    }

    public void setContentId(int contentId) {
        ContentId = contentId;
    }

    public int getPoll() {
        return poll;
    }

    public void setPoll(int poll) {
        this.poll = poll;
    }

    public int getIsVote() {
        return IsVote;
    }

    public void setIsVote(int isVote) {
        IsVote = isVote;
    }

    public String getAllImgUrl() {
        return AllImgUrl;
    }

    public void setAllImgUrl(String allImgUrl) {
        AllImgUrl = allImgUrl;
    }
}
