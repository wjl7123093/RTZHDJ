package com.mytv.rtzhdj.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.mytv.rtzhdj.mvp.contract.PartyMemberDetailContract;
import com.mytv.rtzhdj.mvp.ui.activity.PartyMemberDetailActivity;


@ActivityScope
public class PartyMemberDetailPresenter extends BasePresenter<PartyMemberDetailContract.Model, PartyMemberDetailContract.View>
    implements PartyMemberDetailContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private PartyMemberDetailActivity mActivity;

    @Inject
    public PartyMemberDetailPresenter(PartyMemberDetailContract.Model model, PartyMemberDetailContract.View rootView
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
    public void setActivity(PartyMemberDetailActivity activity) {
        mActivity = activity;
    }

    @Override
    public void callMethodOfGetPartyMemberDetails(@NonNull String id) {

    }
}
