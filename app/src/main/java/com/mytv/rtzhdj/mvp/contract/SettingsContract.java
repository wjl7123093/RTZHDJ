package com.mytv.rtzhdj.mvp.contract;

import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.UserDetailEntity;
import com.mytv.rtzhdj.mvp.ui.activity.SettingsActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.BaseDelegateAdapter;

import io.reactivex.Observable;

public interface SettingsContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void loadData(UserDetailEntity userDetailEntity, int flag);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取 用户详情
        Observable<BaseJson<UserDetailEntity>> getUserDetail(int userId, boolean update);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(SettingsActivity activity);
        //初始化
        DelegateAdapter initRecyclerView(RecyclerView recyclerView);
        BaseDelegateAdapter initHeader1(String url);
        BaseDelegateAdapter initHeader2(String url);
        BaseDelegateAdapter initInfo1(String title, String content);
        BaseDelegateAdapter initInfo2(String type, String title, String content, String hint);
        BaseDelegateAdapter initInfo3(String title, String content);
        BaseDelegateAdapter initMobilePhone(String mobile, boolean isOpen);
        BaseDelegateAdapter initOtherContacts(String title, String content, String hint);
//        BaseDelegateAdapter initTitle(int arrayPos, String title, String btnDesc, int scores);
//        BaseDelegateAdapter initHeader(String url, String name, String partyBranch);
//        BaseDelegateAdapter initSingle(float payment, int isPay);
//        BaseDelegateAdapter initColumn1(int scores, int power, int rank);
//        BaseDelegateAdapter initColumn2(int meetingTimes, int lessonTimes, int activeTimes);

        // 调用 获取用户详情数据
        void callMethodOfGetUserDetail(int userId, boolean update);
    }
}
