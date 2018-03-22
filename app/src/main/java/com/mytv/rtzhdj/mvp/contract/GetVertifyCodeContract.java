package com.mytv.rtzhdj.mvp.contract;

import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mytv.rtzhdj.mvp.ui.activity.GetVertifyCodeActivity;

import io.reactivex.annotations.NonNull;


public interface GetVertifyCodeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void goSetPwdActivity();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(GetVertifyCodeActivity activity);
        // 调用 获取验证码 接口
        void callMethodOfGetCode(@NonNull String mobile);
    }
}
