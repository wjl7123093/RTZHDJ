package com.mytv.rtzhdj.mvp.contract;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.PartyNewsEntity;
import com.mytv.rtzhdj.app.data.entity.PartyRecommendEntity;
import com.mytv.rtzhdj.app.data.entity.PartySubNewsEntity;

import java.util.List;

import io.reactivex.Observable;


public interface ContentContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void onBannerClick(int position);

        void showRecommendData(PartyRecommendEntity recommendEntity, boolean update);
        void showSubListData(PartySubNewsEntity subNewsEntity, boolean update);
        void initAdapter(List<PartyNewsEntity> importandBlockListm, boolean update);
//        void showOtherData();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取 党建推荐新闻数据
        Observable<BaseJson<PartyRecommendEntity>> getPartyRecommend(int pageIndex, int pageSize, boolean update);

        // 获取 党建新闻二级列表数据
        Observable<BaseJson<PartySubNewsEntity>> getPartySubList(int nodeId, int pageIndex, int pageSize, boolean update);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(Activity activity);
        //初始化 RecyclerView
        RecyclerView initRecyclerView(RecyclerView recyclerView);
        // 初始化 头部View
        android.view.View initHeaderView(List<PartyNewsEntity> specialBlockList, ViewGroup parent);

        // 调用 获取党建新闻推荐数据
        void callMethodOfGetPartyRecommend(int pageIndex, int pageSize, boolean update);

        // 调用 获取党建新闻二级列表(除推荐)数据
        void callMethodOfGetPartySubList(int nodeId, int pageIndex, int pageSize, boolean update);
    }
}
