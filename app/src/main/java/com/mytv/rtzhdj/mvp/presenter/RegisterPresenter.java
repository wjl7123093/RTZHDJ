package com.mytv.rtzhdj.mvp.presenter;

import android.app.Application;
import android.support.annotation.NonNull;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.mytv.rtzhdj.mvp.contract.RegisterContract;
import com.mytv.rtzhdj.mvp.ui.activity.LoginActivity;
import com.mytv.rtzhdj.mvp.ui.activity.RegisterActivity;


@ActivityScope
public class RegisterPresenter extends BasePresenter<RegisterContract.Model, RegisterContract.View>
    implements RegisterContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private RegisterActivity mActivity;

    @Inject
    public RegisterPresenter(RegisterContract.Model model, RegisterContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    @Override
    public void setActivity(RegisterActivity activity) {
        mActivity = activity;
    }

    /**
     * 调用 获取验证码接口
     */
    @Override
    public void callMethodOfGetCode() {

    }

    /**
     * 调用 注册接口
     * @param mobile    手机号
     * @param community 社区
     * @param pwd       密码
     * @param pwd2      密码2
     * @param code      验证码
     */
    @Override
    public void callMethodOfDoRegister(@NonNull String mobile,
                                       @NonNull String community,
                                       @NonNull String pwd,
                                       @NonNull String pwd2,
                                       @NonNull String code) {
        if (!pwd.equals(pwd2)) {
            mRootView.showMessage("密码不一致");
            return;
        }

    }
}
