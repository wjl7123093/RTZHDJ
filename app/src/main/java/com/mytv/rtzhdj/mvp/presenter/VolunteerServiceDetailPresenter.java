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
import com.mytv.rtzhdj.app.data.entity.VolunteerDetailEntity;
import com.mytv.rtzhdj.mvp.contract.VolunteerServiceDetailContract;
import com.mytv.rtzhdj.mvp.ui.activity.VolunteerServiceDetailActivity;
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
public class VolunteerServiceDetailPresenter extends BasePresenter<VolunteerServiceDetailContract.Model, VolunteerServiceDetailContract.View>
    implements VolunteerServiceDetailContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private VolunteerServiceDetailActivity mActivity;

    @Inject
    public VolunteerServiceDetailPresenter(VolunteerServiceDetailContract.Model model, VolunteerServiceDetailContract.View rootView
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
    public void setActivity(VolunteerServiceDetailActivity activity) {
        mActivity = activity;
    }

    @Override
    public void callMethodOfGetVolunteerServiceDetail(int id, boolean update) {
        mModel.getVolunteerServiceDetail(id, update)
                .compose(RTZHDJApplication.rxCache.<BaseJson<VolunteerDetailEntity>>transformObservable("getVolunteerServiceDetail" + id,
                        new TypeToken<BaseJson<VolunteerDetailEntity>>() { }.getType(),
                        CacheStrategy.firstCache()))
                .map(new CacheResult.MapFunc<BaseJson<VolunteerDetailEntity>>())
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
                .subscribe(new ErrorHandleSubscriber<BaseJson<VolunteerDetailEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<VolunteerDetailEntity> volunteerDetailEntity) {
                        Log.e(TAG, volunteerDetailEntity.toString());

                        if (volunteerDetailEntity.isSuccess() && volunteerDetailEntity.getData() != null)
                            mRootView.loadData(volunteerDetailEntity.getData());
                    }
                });
    }
}
