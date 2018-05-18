package com.mytv.rtzhdj.mvp.presenter;

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
import com.mytv.rtzhdj.app.data.entity.StationEntity;
import com.mytv.rtzhdj.mvp.contract.DoubleReportingContract;
import com.mytv.rtzhdj.mvp.ui.activity.DoubleReportingActivity;
import com.zchu.rxcache.data.CacheResult;
import com.zchu.rxcache.stategy.CacheStrategy;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class DoubleReportingPresenter extends BasePresenter<DoubleReportingContract.Model, DoubleReportingContract.View>
    implements DoubleReportingContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private DoubleReportingActivity mActivity;

    @Inject
    public DoubleReportingPresenter(DoubleReportingContract.Model model, DoubleReportingContract.View rootView
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
    public void setActivity(DoubleReportingActivity activity) {
        mActivity = activity;
    }

    @Override
    public void callMethodOfPostPersonalReach(@NonNull int userId, @NonNull int publishmentSystemId) {
        mModel.postPersonalReach(userId, publishmentSystemId, false)
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
                .subscribe(new ErrorHandleSubscriber<BaseJson>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson postResult) {
                        Log.e(TAG, postResult.toString());

                        if (postResult.isSuccess())
                            mRootView.showMessage("双报到成功！");

                    }
                });
    }

    @Override
    public void callMethodOfPostAllPublishmentSystem(boolean refresh) {
        mModel.postAllPublishmentSystem(refresh)
                .compose(RTZHDJApplication.rxCache.<BaseJson<List<StationEntity>>>transformObservable("postAllPublishmentSystem",
                        new TypeToken<BaseJson<List<StationEntity>>>() { }.getType(),
                        CacheStrategy.firstRemote()))
                .map(new CacheResult.MapFunc<BaseJson<List<StationEntity>>>())
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
                .subscribe(new ErrorHandleSubscriber<BaseJson<List<StationEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<List<StationEntity>> stationList) {
                        Log.e("TAG", stationList.toString());

                        mRootView.showPickerView(stationList.getData());

                    }
                });
    }
}
