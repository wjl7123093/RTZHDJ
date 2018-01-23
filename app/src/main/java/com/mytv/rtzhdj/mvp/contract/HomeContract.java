package com.mytv.rtzhdj.mvp.contract;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mytv.rtzhdj.mvp.ui.activity.MainActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.BaseDelegateAdapter;
import com.youth.banner.Banner;

import java.util.List;


public interface HomeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        //设置轮播图
        void setBanner(Banner banner);
        void setOnclick();
        void setMarqueeClick(int position);
        void setGridClick(int position);    // GridView Click
        void setNewsListClick(int position, String url);
        void setImageClick();
        void setOnePlusNClick();

        void showImage(ImageView iv, String url);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(MainActivity activity);
        //初始化
        DelegateAdapter initRecyclerView(RecyclerView recyclerView);
        BaseDelegateAdapter initBannerAdapter();
        BaseDelegateAdapter initGvMenu();
        BaseDelegateAdapter initMarqueeView();
        BaseDelegateAdapter initTitle(String title);
        BaseDelegateAdapter initList();
        BaseDelegateAdapter initMoreData(String moreStr);
        BaseDelegateAdapter initImage(String url);
        BaseDelegateAdapter initHeader(String title, String desc);
        BaseDelegateAdapter initOnePlusN();
    }
}
