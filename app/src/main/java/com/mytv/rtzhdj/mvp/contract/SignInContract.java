package com.mytv.rtzhdj.mvp.contract;

import android.support.annotation.NonNull;

import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.VerifyCodeEntity;
import com.mytv.rtzhdj.mvp.ui.activity.RegisterActivity;
import com.mytv.rtzhdj.mvp.ui.activity.SignInActivity;

import io.reactivex.Observable;


public interface SignInContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void changeStatus();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 签到
        Observable<BaseJson> postSignForScore(int userId);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(SignInActivity activity);

        // 调用 签到接口
        void callMethodOfPostSignForScore(@NonNull int userId);
    }
}
