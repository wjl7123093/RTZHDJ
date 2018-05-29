package com.mytv.rtzhdj.mvp.contract;

import android.support.annotation.NonNull;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.StationEntity;
import com.mytv.rtzhdj.app.data.entity.UserCategoryEntity;
import com.mytv.rtzhdj.app.data.entity.UserRegisterEntity;
import com.mytv.rtzhdj.app.data.entity.VerifyCodeEntity;
import com.mytv.rtzhdj.mvp.ui.activity.RegisterActivity;

import java.util.List;

import io.reactivex.Observable;


public interface RegisterContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void setBtnRegisterBg(boolean isChecked);
        void goWebviewActivity();
        void showDialog(List<UserCategoryEntity> categoryList);
        void showPickerView(List<StationEntity> stationList);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        // 获取用户类型
        Observable<BaseJson<List<UserCategoryEntity>>> getUserCategory(boolean update);

        // 获取站点集合
        Observable<BaseJson<List<StationEntity>>> postAllPublishmentSystem(boolean update);

        // 获取验证码
        Observable<BaseJson<VerifyCodeEntity>> getVerifyCode(String telNumber);

        // 注册
        Observable<BaseJson<UserRegisterEntity>> getUserRegister(String mobile, int publishmentSystemId,
                                                       String password);
    }

    //Presenter控制器
    interface Presenter extends IPresenter {
        void setActivity(RegisterActivity activity);
        // 调用 获取用户类型接口
        void callMethodOfGetUserCategory(boolean refresh);
        // 调用 获取站点集合
        void callMethodOfPostAllPublishmentSystem(boolean refresh);
        // 调用 获取验证码接口
        void callMethodOfGetCode(@NonNull String telNumber);
        // 调用 注册接口
        void callMethodOfDoRegister(@NonNull String mobile,
                                    @NonNull int publishmentSystemId,
                                    @NonNull String pwd,
                                    @NonNull String pwd2);
    }
}
