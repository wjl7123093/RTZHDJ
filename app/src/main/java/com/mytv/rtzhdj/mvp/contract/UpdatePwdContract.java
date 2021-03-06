package com.mytv.rtzhdj.mvp.contract;

import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.VerifyCodeEntity;
import com.mytv.rtzhdj.mvp.ui.activity.UpdatePwdActivity;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;


public interface UpdatePwdContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        // 修改密码
        Observable<BaseJson> postUpdatePassword(int userId, String oldPwd, String newPwd);

    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(UpdatePwdActivity activity);
        // 调用 更新密码接口
        void callMethodOfPostUpdatePassword(@NonNull int userId,
                                            @NonNull String oldPwd,
                                            @NonNull String newPwd,
                                            @NonNull String newPwd2);
    }
}
