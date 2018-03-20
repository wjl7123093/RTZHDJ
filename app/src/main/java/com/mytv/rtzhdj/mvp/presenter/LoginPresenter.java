package com.mytv.rtzhdj.mvp.presenter;

import android.app.Application;
import android.support.annotation.NonNull;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.mytv.rtzhdj.mvp.contract.LoginContract;
import com.mytv.rtzhdj.mvp.ui.activity.LoginActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;


@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View>
    implements LoginContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private LoginActivity mActivity;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView
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
    public void setActivity(LoginActivity activity) {
        mActivity = activity;
    }

    @Override
    public void callMethodOfDoLogin(@NonNull String acc, @NonNull String pwd) {
        checkNotNull(acc);
        checkNotNull(pwd);



    }
}
