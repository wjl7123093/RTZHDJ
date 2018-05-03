package com.mytv.rtzhdj.app.data.entity;

/**
 * CommentEntity   评论实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-27
 * @update 2018-5-3     更新结构
 */
public class CommentEntity {

    private int CommentId;
    private String UserName;
    private String CommentTime;
    private String AvatarUrl;
    private String commentInfo;
    private int likeNum;

    public int getCommentId() {
        return CommentId;
    }

    public void setCommentId(int commentId) {
        CommentId = commentId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getCommentTime() {
        return CommentTime;
    }

    public void setCommentTime(String commentTime) {
        CommentTime = commentTime;
    }

    public String getAvatarUrl() {
        return AvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        AvatarUrl = avatarUrl;
    }

    public String getCommentInfo() {
        return commentInfo;
    }

    public void setCommentInfo(String commentInfo) {
        this.commentInfo = commentInfo;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }
}
