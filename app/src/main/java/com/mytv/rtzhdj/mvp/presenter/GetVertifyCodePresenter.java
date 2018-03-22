package com.mytv.rtzhdj.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.mytv.rtzhdj.mvp.contract.GetVertifyCodeContract;
import com.mytv.rtzhdj.mvp.ui.activity.GetVertifyCodeActivity;


@ActivityScope
public class GetVertifyCodePresenter extends BasePresenter<GetVertifyCodeContract.Model, GetVertifyCodeContract.View>
    implements GetVertifyCodeContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private GetVertifyCodeActivity mActivity;

    @Inject
    public GetVertifyCodePresenter(GetVertifyCodeContract.Model model, GetVertifyCodeContract.View rootView
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
    public void setActivity(GetVertifyCodeActivity activity) {
        mActivity = activity;
    }

    /**
     * 调用 获取验证码 接口
     * @param mobile 手机号
     */
    @Override
    public void callMethodOfGetCode(@NonNull String mobile) {

    }
}
