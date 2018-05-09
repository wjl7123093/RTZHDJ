package com.mytv.rtzhdj.mvp.contract;

import android.support.v7.widget.RecyclerView;

import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.PartyMemberEntity;
import com.mytv.rtzhdj.app.data.entity.PartyMienEntity;
import com.mytv.rtzhdj.mvp.ui.activity.PartyMemberActivity;

import java.util.List;

import io.reactivex.Observable;


public interface PartyMemberContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void loadData(List<PartyMemberEntity> memberList);
        void loadDataMine(List<PartyMienEntity> memberList);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取 党员信息列表
        Observable<BaseJson<List<PartyMemberEntity>>> getPartyMember(int publishmentSystemId, boolean update);

        // 获取 党员风采 数据
        Observable<BaseJson<List<PartyMienEntity>>> getPartymembermien(int publishmentSystemId, int pageIndex, int pageSize, boolean update);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(PartyMemberActivity activity);
        //初始化 RecyclerView
        RecyclerView initRecyclerView(RecyclerView recyclerView);

        // 调用 获取党员信息列表
        void callMethodOfGetPartyMmeber(int publishmentSystemId, boolean update);

        // 调用 获取党员风采数据
        void callMethodOfGetPartymembermien(int publishmentSystemId, int pageIndex, int pageSize, boolean update);
    }
}
