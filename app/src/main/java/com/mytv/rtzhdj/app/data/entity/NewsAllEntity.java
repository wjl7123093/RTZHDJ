package com.mytv.rtzhdj.app.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * NewsAllEntity   带"全部"通用 tab 新闻界面实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-8
 * @update
 */
public class NewsAllEntity implements Parcelable {

    private List<NewsDetailEntity> List_allinfoBlock;
    private List<PartyColumnsEntity> List_data;

    protected NewsAllEntity(Parcel in) {
        List_allinfoBlock = in.createTypedArrayList(NewsDetailEntity.CREATOR);
        List_data = in.createTypedArrayList(PartyColumnsEntity.CREATOR);
    }

    public static final Creator<NewsAllEntity> CREATOR = new Creator<NewsAllEntity>() {
        @Override
        public NewsAllEntity createFromParcel(Parcel in) {
            return new NewsAllEntity(in);
        }

        @Override
        public NewsAllEntity[] newArray(int size) {
            return new NewsAllEntity[size];
        }
    };

    public List<NewsDetailEntity> getList_allinfoBlock() {
        return List_allinfoBlock;
    }

    public void setList_allinfoBlock(List<NewsDetailEntity> list_allinfoBlock) {
        List_allinfoBlock = list_allinfoBlock;
    }

    public List<PartyColumnsEntity> getList_data() {
        return List_data;
    }

    public void setList_data(List<PartyColumnsEntity> list_data) {
        List_data = list_data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(List_allinfoBlock);
        parcel.writeTypedList(List_data);
    }
}
