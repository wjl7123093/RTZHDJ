package com.mytv.rtzhdj.mvp.contract;

import android.support.annotation.NonNull;

import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mytv.rtzhdj.mvp.ui.activity.RegisterActivity;


public interface RegisterContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void setBtnRegisterBg(boolean isChecked);
        void goWebviewActivity();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(RegisterActivity activity);
        // 调用 获取验证码接口
        void callMethodOfGetCode();
        // 调用 注册接口
        void callMethodOfDoRegister(@NonNull String mobile,
                                    @NonNull String community,
                                    @NonNull String pwd,
                                    @NonNull String pwd2,
                                    @NonNull String code);
    }
}
