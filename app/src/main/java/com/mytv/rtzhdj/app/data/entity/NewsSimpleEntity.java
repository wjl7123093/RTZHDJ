package com.mytv.rtzhdj.app.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * NewsSimpleEntity   带"推荐"通用 tab 新闻界面实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-7
 * @update
 */
public class NewsSimpleEntity implements Parcelable {

    private List<NewsDetailEntity> List_recommendBlock;
    private List<NewsDetailEntity> List_listBlock;
    private List<PartyColumnsEntity> List_columnList;

    protected NewsSimpleEntity(Parcel in) {
        List_recommendBlock = in.createTypedArrayList(NewsDetailEntity.CREATOR);
        List_listBlock = in.createTypedArrayList(NewsDetailEntity.CREATOR);
        List_columnList = in.createTypedArrayList(PartyColumnsEntity.CREATOR);
    }

    public static final Creator<NewsSimpleEntity> CREATOR = new Creator<NewsSimpleEntity>() {
        @Override
        public NewsSimpleEntity createFromParcel(Parcel in) {
            return new NewsSimpleEntity(in);
        }

        @Override
        public NewsSimpleEntity[] newArray(int size) {
            return new NewsSimpleEntity[size];
        }
    };

    public List<NewsDetailEntity> getList_recommendBlock() {
        return List_recommendBlock;
    }

    public void setList_recommendBlock(List<NewsDetailEntity> list_recommendBlock) {
        List_recommendBlock = list_recommendBlock;
    }

    public List<NewsDetailEntity> getList_listBlock() {
        return List_listBlock;
    }

    public void setList_listBlock(List<NewsDetailEntity> list_listBlock) {
        List_listBlock = list_listBlock;
    }

    public List<PartyColumnsEntity> getList_columnList() {
        return List_columnList;
    }

    public void setList_columnList(List<PartyColumnsEntity> list_columnList) {
        List_columnList = list_columnList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(List_recommendBlock);
        parcel.writeTypedList(List_listBlock);
        parcel.writeTypedList(List_columnList);
    }
}
