package com.mytv.rtzhdj.app.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * PartyColumnsEntity   党建新闻栏目实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update 2018-4-5 更新了 NodeID 字段
 *         2018-5-7 更新了 NodeId 字段
 */
public class PartyColumnsEntity implements Parcelable {

    private int NodeID;
    private String Title;   // 栏目标题

    // 通用 tab 新闻界面
    private int NodeId;

    public PartyColumnsEntity() {}

    protected PartyColumnsEntity(Parcel in) {
        NodeID = in.readInt();
        Title = in.readString();
        NodeId = in.readInt();
    }

    public static final Creator<PartyColumnsEntity> CREATOR = new Creator<PartyColumnsEntity>() {
        @Override
        public PartyColumnsEntity createFromParcel(Parcel in) {
            return new PartyColumnsEntity(in);
        }

        @Override
        public PartyColumnsEntity[] newArray(int size) {
            return new PartyColumnsEntity[size];
        }
    };

    public int getNodeId() {
        return NodeID;
    }

    public void setNodeId(int nodeId) {
        NodeID = nodeId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getNodeID() {
        return NodeID;
    }

    public void setNodeID(int nodeID) {
        NodeID = nodeID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(NodeID);
        parcel.writeString(Title);
        parcel.writeInt(NodeId);
    }
}
