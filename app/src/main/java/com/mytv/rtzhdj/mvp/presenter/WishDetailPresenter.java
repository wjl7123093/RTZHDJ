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
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.jess.arms.utils.RxLifecycleUtils;
import com.mytv.rtzhdj.app.base.RTZHDJApplication;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.PartyRecommendEntity;
import com.mytv.rtzhdj.app.data.entity.WishDetailEntity;
import com.mytv.rtzhdj.mvp.contract.WishDetailContract;
import com.mytv.rtzhdj.mvp.ui.activity.WishDetailActivity;
import com.zchu.rxcache.data.CacheResult;
import com.zchu.rxcache.stategy.CacheStrategy;

import java.util.List;
import java.util.Map;


@ActivityScope
public class WishDetailPresenter extends BasePresenter<WishDetailContract.Model, WishDetailContract.View>
    implements WishDetailContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private WishDetailActivity mActivity;

    @Inject
    public WishDetailPresenter(WishDetailContract.Model model, WishDetailContract.View rootView
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
    public void setActivity(WishDetailActivity activity) {
        mActivity = activity;
    }

    @Override
    public void callMethodOfPostWishDetail(int wishId, boolean update) {
        mModel.postWishDetail(wishId, update)
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
                .subscribe(new ErrorHandleSubscriber<BaseJson<WishDetailEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<WishDetailEntity> wishDetailEntity) {
                        Log.e(TAG, wishDetailEntity.getData().toString());

                        mRootView.loadData(wishDetailEntity.getData());
                    }
                });
    }

    @Override
    public void callMethodOfPostEditMyWish(Map<String, RequestBody> params, List<MultipartBody.Part> parts) {
        mModel.postEditMyWish(params, parts)
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

                        if (postResult.isSuccess()) {
                            mRootView.showMessage("提交成功");
                            mRootView.dismissDialog();
                            mRootView.killMyself();
                        }

                    }
                });
    }

    @Override
    public void callMethodOfPostDeleteMyWish(int wishId) {
        mModel.postDeleteMyWish(wishId)
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

                        if (postResult.isSuccess()) {
                            mRootView.showMessage("删除成功");
                            mRootView.killMyself();
                        }
                    }
                });
    }
}
