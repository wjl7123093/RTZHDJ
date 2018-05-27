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
import com.mytv.rtzhdj.app.data.entity.PartyMemberEntity;
import com.mytv.rtzhdj.app.data.entity.PartyMienEntity;
import com.mytv.rtzhdj.mvp.contract.PartyMemberDetailContract;
import com.mytv.rtzhdj.mvp.ui.activity.PartyMemberDetailActivity;
import com.zchu.rxcache.data.CacheResult;
import com.zchu.rxcache.stategy.CacheStrategy;

import java.util.List;


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
    public void callMethodOfGetPartyMemberDetails(@NonNull int memberId, boolean update) {
        mModel.getPartyMemberDetail(memberId, update)
                .compose(RTZHDJApplication.rxCache.<BaseJson<PartyMienEntity>>transformObservable("getPartyMemberDetail" + memberId,
                        new TypeToken<BaseJson<PartyMienEntity>>() { }.getType(),
                        CacheStrategy.firstRemote()))
                .map(new CacheResult.MapFunc<BaseJson<PartyMienEntity>>())
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
                .subscribe(new ErrorHandleSubscriber<BaseJson<PartyMienEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<PartyMienEntity> memberInfo) {
                        Log.e(TAG, memberInfo.toString());

                        if (memberInfo.isSuccess() && memberInfo.getData() != null)
                            mRootView.loadData(memberInfo.getData());
                    }
                });
    }
}
