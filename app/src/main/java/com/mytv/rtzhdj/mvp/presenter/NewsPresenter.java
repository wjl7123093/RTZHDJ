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
import com.mytv.rtzhdj.app.data.entity.PartyColumnsEntity;
import com.mytv.rtzhdj.mvp.contract.NewsContract;
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
public class NewsPresenter extends BasePresenter<NewsContract.Model, NewsContract.View>
    implements NewsContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private Activity mActivity;

    @Inject
    public NewsPresenter(NewsContract.Model model, NewsContract.View rootView
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
    public void callMethodOfGetPartyColumns(int typeId, boolean update) {
        mModel.getPartyColumns(typeId, update)
                .compose(RTZHDJApplication.rxCache.<BaseJson<List<PartyColumnsEntity>>>transformObservable("getPartyColumns",
                        new TypeToken<BaseJson<List<PartyColumnsEntity>>>() { }.getType(),
                        CacheStrategy.firstRemote()))
                .map(new CacheResult.MapFunc<BaseJson<List<PartyColumnsEntity>>>())
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
                .subscribe(new ErrorHandleSubscriber<BaseJson<List<PartyColumnsEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<List<PartyColumnsEntity>> partyColumnsData) {
                        Log.e(TAG, partyColumnsData.getData().toString());

                        if (partyColumnsData.isSuccess() && partyColumnsData.getData() != null)
                            mRootView.initTab(partyColumnsData.getData());
                    }
                });
    }
}
