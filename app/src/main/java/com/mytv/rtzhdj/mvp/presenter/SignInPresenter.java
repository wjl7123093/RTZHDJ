package com.mytv.rtzhdj.mvp.presenter;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.mytv.rtzhdj.app.base.RTZHDJApplication;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.SignEntity;
import com.mytv.rtzhdj.app.data.entity.SignScoresEntity;
import com.mytv.rtzhdj.mvp.contract.SignInContract;
import com.mytv.rtzhdj.mvp.ui.activity.SignInActivity;
import com.zchu.rxcache.data.CacheResult;
import com.zchu.rxcache.stategy.CacheStrategy;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class SignInPresenter extends BasePresenter<SignInContract.Model, SignInContract.View>
    implements SignInContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private SignInActivity mActivity;

    @Inject
    public SignInPresenter(SignInContract.Model model, SignInContract.View rootView
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
    public void setActivity(SignInActivity activity) {
        mActivity = activity;
    }

    @Override
    public void callMethodOfPostSignForScore(@NonNull int userId) {
        mModel.postSignForScore(userId)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    // Action onFinally
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<SignScoresEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<SignScoresEntity> signScoreEntity) {
                        Log.e("TAG", signScoreEntity.toString());

                        if (signScoreEntity.isSuccess() && signScoreEntity.getData() != null)
                            mRootView.changeStatus(signScoreEntity.getData());
                    }
                });
    }

    @Override
    public void callMethodOfGetSignList(@NonNull int userId) {
        mModel.getSignList(userId)
                .compose(RTZHDJApplication.rxCache.<BaseJson<SignEntity>>transformObservable("getSignList" + userId,
                        new TypeToken<BaseJson<SignEntity>>() { }.getType(),
                        CacheStrategy.firstRemote()))
                .map(new CacheResult.MapFunc<BaseJson<SignEntity>>())
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    // Action onFinally
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<SignEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<SignEntity> signEntity) {
                        Log.e("TAG", signEntity.toString());

//                        mRootView.showPickerView(stationList.getData());
                        if (signEntity.isSuccess() && signEntity.getData() != null)
                            mRootView.loadData(signEntity.getData());
                    }
                });
    }
}
