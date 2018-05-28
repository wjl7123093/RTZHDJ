package com.mytv.rtzhdj.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.mytv.rtzhdj.app.base.RTZHDJApplication;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.HeaderIntegralEntity;
import com.mytv.rtzhdj.mvp.contract.MyTaskContract;
import com.zchu.rxcache.data.CacheResult;
import com.zchu.rxcache.stategy.CacheStrategy;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class MyTaskPresenter extends BasePresenter<MyTaskContract.Model, MyTaskContract.View>
        implements MyTaskContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private Activity mActivity;

    @Inject
    public MyTaskPresenter(MyTaskContract.Model model, MyTaskContract.View rootView
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
    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void callMethodOfGetMyScore(int userId, boolean update) {
        mModel.getMyScore(userId)
                .compose(RTZHDJApplication.rxCache.<BaseJson<HeaderIntegralEntity>>transformObservable("getMyScore" + userId,
                        new TypeToken<BaseJson<HeaderIntegralEntity>>() { }.getType(),
                        CacheStrategy.firstRemote()))
                .map(new CacheResult.MapFunc<BaseJson<HeaderIntegralEntity>>())
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
                .subscribe(new ErrorHandleSubscriber<BaseJson<HeaderIntegralEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<HeaderIntegralEntity> headerIntegralEntity) {
                        Log.e(TAG, headerIntegralEntity.getData().toString());

                        if (headerIntegralEntity.isSuccess() && headerIntegralEntity.getData() != null)
                            mRootView.loadHeaderData(headerIntegralEntity.getData());
                    }
                });
    }

}
