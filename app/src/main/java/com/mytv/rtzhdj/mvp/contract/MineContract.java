package com.mytv.rtzhdj.mvp.contract;

import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.MineEntity;
import com.mytv.rtzhdj.mvp.ui.activity.MainActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.BaseDelegateAdapter;

import io.reactivex.Observable;


public interface MineContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void setOnSettingsClick();
        void setOnSignClick();
        void setOnGridClick(int arrayPos, int position);
        void setOnSingleClick();
        void setOnTitleClick(int arrayPos);
        void setOnColumnClick(int arrayPos, int position);

        void loadData(MineEntity mineEntity);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取 我的积分等数据
        Observable<BaseJson<MineEntity>> getUserPartMessage(int userId, boolean update);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(MainActivity activity);
        //初始化
        DelegateAdapter initRecyclerView(RecyclerView recyclerView);
        BaseDelegateAdapter initGvMenu1();
        BaseDelegateAdapter initGvMenu2();
        BaseDelegateAdapter initGvMenu3();
        BaseDelegateAdapter initTitle(int arrayPos, String title, String btnDesc, int scores);
        BaseDelegateAdapter initHeader();
        BaseDelegateAdapter initSingle(float payment, int isPay);
        BaseDelegateAdapter initColumn2(int meetingTimes, int lessonTimes, int activeTimes);

        // 通栏[itemPos = 1 我的党支部，= 2 组织生活]
        BaseDelegateAdapter initColumn(int itemPos, String title);
        // 积分栏
        BaseDelegateAdapter initScores();
        // 缴费栏
        BaseDelegateAdapter initCash();
        // 生活助手栏
        BaseDelegateAdapter initHelper();

        // 调用 我的积分等数据
        void callMethodOfGetUserPartMessage(int userId, boolean update);
    }
}
