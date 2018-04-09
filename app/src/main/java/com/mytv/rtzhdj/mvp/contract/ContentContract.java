package com.mytv.rtzhdj.mvp.contract;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mytv.rtzhdj.app.data.entity.PartyColumnsEntity;
import com.mytv.rtzhdj.app.data.entity.PartyRecommendEntity;

import java.util.List;

import io.reactivex.Observable;


public interface ContentContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void onBannerClick(int position);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        // 获取党建推荐新闻信息
        Observable<PartyRecommendEntity> getPartyRecommend(String typedId, int count, boolean update);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(Activity activity);
        //初始化 RecyclerView
        RecyclerView initRecyclerView(RecyclerView recyclerView);
        // 初始化 头部View
        android.view.View initHeaderView(List<?> imageUrls, ViewGroup parent);

        // 调用 获取党建新闻推荐数据
        void callMethodOfGetPartyRecommend(String typeId, int count, boolean update);
    }
}
