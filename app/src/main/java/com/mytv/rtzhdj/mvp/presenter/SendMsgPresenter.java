package com.mytv.rtzhdj.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.mytv.rtzhdj.mvp.contract.SendMsgContract;
import com.mytv.rtzhdj.mvp.ui.activity.SendMsgActivity;


@ActivityScope
public class SendMsgPresenter extends BasePresenter<SendMsgContract.Model, SendMsgContract.View>
    implements SendMsgContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private SendMsgActivity mActivity;

    @Inject
    public SendMsgPresenter(SendMsgContract.Model model, SendMsgContract.View rootView
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
    public void setActivity(SendMsgActivity activity) {
        mActivity = activity;
    }

    @Override
    public void callMethodOfSendMsg(@NonNull String name, @NonNull String theme, @NonNull String msg) {

    }
}