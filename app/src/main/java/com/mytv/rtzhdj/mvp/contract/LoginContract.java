package com.mytv.rtzhdj.mvp.contract;

import android.support.annotation.NonNull;

import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mytv.rtzhdj.mvp.ui.activity.LoginActivity;


public interface LoginContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void setError();
        void goMainActivity();
        void goRegisterActivity();
        void goForgetPwdActivity();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(LoginActivity activity);
        // 调用 登陆接口
        void callMethodOfDoLogin(@NonNull String acc, @NonNull String pwd);
    }
}
