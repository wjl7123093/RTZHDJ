package com.mytv.rtzhdj.mvp.contract;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.mvp.ui.activity.MainActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.BaseDelegateAdapter;
import com.youth.banner.Banner;

import java.util.List;

import io.reactivex.Observable;


public interface HomeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        //设置轮播图
        void setBanner(Banner banner);
        void setOnTopicClick();
        void setOnclick();
        void setMarqueeClick(int position);
        void setGridClick(int position);    // GridView Click
        void setNewsListClick(int position, String url);
        void setImageClick();
        void setOnePlusNClick();

        void showImage(ImageView iv, String url);

        void showData(HomeEntity homeData, boolean update);
//        void setOnBannerClick(int position);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        // 获取用户类型
        Observable<BaseJson<HomeEntity>> getHomeData(boolean update);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(MainActivity activity);
        //初始化
        DelegateAdapter initRecyclerView(RecyclerView recyclerView);
        BaseDelegateAdapter initTopHeader();
        BaseDelegateAdapter initBannerAdapter(List<HomeEntity.SpecialBlock> SpecialBlock,
                                              List<HomeEntity.NoticeBlock> NoticeBlock_ChildContent);
        BaseDelegateAdapter initGvMenu();
        BaseDelegateAdapter initMarqueeView(List<HomeEntity.NoticeBlock> NoticeBlock_ChildContent);
        BaseDelegateAdapter initTitle(String title);
        BaseDelegateAdapter initList(List<HomeEntity.FocusNewsBlock> FocusNewsBlock_ChildContent);
        BaseDelegateAdapter initMoreData(String moreStr);
        BaseDelegateAdapter initImage(List<HomeEntity.AdBlock> AdBlock);
        BaseDelegateAdapter initHeader(String title, String desc);
        BaseDelegateAdapter initOnePlusN(List<List<HomeEntity.PublicSpiritedBlock>> PublicSpiritedBlock_ChildContent,
                                         int myPositiveValue);

        // 调用 获取首页数据
        void callMethodOfGetHomeData(boolean update);
    }
}
