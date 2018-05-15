package com.mytv.rtzhdj.mvp.presenter;

import android.app.Application;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.jess.arms.utils.RxLifecycleUtils;
import com.mytv.rtzhdj.app.base.RTZHDJApplication;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.NewsDetailEntity;
import com.mytv.rtzhdj.app.data.entity.PartyLiveEntity;
import com.mytv.rtzhdj.mvp.contract.NewsVideoDetailContract;
import com.mytv.rtzhdj.mvp.ui.activity.NewsVideoDetailActivity;
import com.zchu.rxcache.data.CacheResult;
import com.zchu.rxcache.stategy.CacheStrategy;


@ActivityScope
public class NewsVideoDetailPresenter extends BasePresenter<NewsVideoDetailContract.Model, NewsVideoDetailContract.View>
    implements NewsVideoDetailContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private NewsVideoDetailActivity mActivity;

    @Inject
    public NewsVideoDetailPresenter(NewsVideoDetailContract.Model model, NewsVideoDetailContract.View rootView
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
    public void setActivity(NewsVideoDetailActivity activity) {
        mActivity = activity;
    }

    @Override
    public void callMethodOfGetContent(boolean update) {
        mModel.getContent(update)
                .compose(RTZHDJApplication.rxCache.<BaseJson<PartyLiveEntity>>transformObservable("GetPartyLiveInfo",
                        new TypeToken<BaseJson<PartyLiveEntity>>() { }.getType(),
                        CacheStrategy.firstCache()))
                .map(new CacheResult.MapFunc<BaseJson<PartyLiveEntity>>())
                .retryWhen(new RetryWithDelay(3, 2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                })
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<PartyLiveEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<PartyLiveEntity> partyLiveEntity) {
                        Log.e(TAG, partyLiveEntity.getData().toString());

                        if (partyLiveEntity.isSuccess())
                            mRootView.loadData(partyLiveEntity.getData());

                    }
                });
    }
}
