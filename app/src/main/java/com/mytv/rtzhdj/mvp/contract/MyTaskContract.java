package com.mytv.rtzhdj.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.HeaderIntegralEntity;

import io.reactivex.Observable;


public interface MyTaskContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void loadHeaderData(HeaderIntegralEntity headerIntegralEntity);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 获取 头部积分数据
        Observable<BaseJson<HeaderIntegralEntity>> getMyScore(int userId);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(Activity activity);

        // 调用 获取头部积分数据
        void callMethodOfGetMyScore(int userId, boolean update);
    }
}
