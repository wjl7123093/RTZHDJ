package com.mytv.rtzhdj.app.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * NewsDetailEntity   文章详情实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update 2018-4-20 更新
 */
public class NewsDetailEntity implements Parcelable {

    private int ContentId;      // 内容id（跟在url末尾）
    private String Title;       // 文章标题
    private String Content;
    private int Digs;           // 点赞数
    private int Comments;       // 评论数

    // 通用 tab 界面
    private int NodeId;         // 节点ID
    private int Id;             // 内容ID
    private String AddDate;     // 发布时间
    private String ImgUrl;      // 图片地址
    private String AllImgUrl;

    public NewsDetailEntity() {}

    protected NewsDetailEntity(Parcel in) {
        ContentId = in.readInt();
        Title = in.readString();
        Content = in.readString();
        Digs = in.readInt();
        Comments = in.readInt();
        NodeId = in.readInt();
        Id = in.readInt();
        AddDate = in.readString();
        ImgUrl = in.readString();
        AllImgUrl = in.readString();
    }

    public static final Creator<NewsDetailEntity> CREATOR = new Creator<NewsDetailEntity>() {
        @Override
        public NewsDetailEntity createFromParcel(Parcel in) {
            return new NewsDetailEntity(in);
        }

        @Override
        public NewsDetailEntity[] newArray(int size) {
            return new NewsDetailEntity[size];
        }
    };

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

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getAddDate() {
        return AddDate;
    }

    public void setAddDate(String addDate) {
        AddDate = addDate;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public int getNodeId() {
        return NodeId;
    }

    public void setNodeId(int nodeId) {
        NodeId = nodeId;
    }

    public String getAllImgUrl() {
        return AllImgUrl;
    }

    public void setAllImgUrl(String allImgUrl) {
        AllImgUrl = allImgUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(ContentId);
        parcel.writeString(Title);
        parcel.writeString(Content);
        parcel.writeInt(Digs);
        parcel.writeInt(Comments);
        parcel.writeInt(NodeId);
        parcel.writeInt(Id);
        parcel.writeString(AddDate);
        parcel.writeString(ImgUrl);
        parcel.writeString(AllImgUrl);
    }
}
