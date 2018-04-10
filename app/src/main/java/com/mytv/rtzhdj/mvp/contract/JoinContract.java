package com.mytv.rtzhdj.mvp.contract;

import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mytv.rtzhdj.app.data.entity.MyJoinEntity;
import com.mytv.rtzhdj.mvp.ui.activity.MainActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.BaseDelegateAdapter;

import io.reactivex.Observable;


public interface JoinContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void setOnGridClick(int position);
        void setOnListClick(int arrayPos, int position);
        void setOnFooterClick();
        void setOnColumnClick(int arrayPos, int position);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取 我要参与数据
        Observable<MyJoinEntity> getMyPartIn(int userId, int count, boolean update);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(MainActivity activity);
        //初始化
        DelegateAdapter initRecyclerView(RecyclerView recyclerView);
        BaseDelegateAdapter initGvMenu();
        BaseDelegateAdapter initTitle(String title);
        BaseDelegateAdapter initListVolunteer(String url, int status, int joinNum, int starNum,
                                              int commentNum, String title, String deadtime);
        BaseDelegateAdapter initListCommunity(String url, int starNum, int commentNum,
                                              String title, String deadtime);
        BaseDelegateAdapter initHeader(String report);
        BaseDelegateAdapter initFooter(String footer);
        BaseDelegateAdapter initOnePlusN2();
        BaseDelegateAdapter initColumnWish();
        BaseDelegateAdapter initColumnOnline();

        // 调用 我要参与数据
        void callMethodOfGetMyPartIn(int userId, int count, boolean update);
    }
}
