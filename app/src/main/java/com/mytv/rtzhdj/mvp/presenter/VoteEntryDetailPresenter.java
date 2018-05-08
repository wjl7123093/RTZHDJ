package com.mytv.rtzhdj.mvp.presenter;

import android.app.Application;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.MyDonateEntity;
import com.mytv.rtzhdj.app.data.entity.VoteEntrylEntity;
import com.mytv.rtzhdj.mvp.contract.VoteEntryDetailContract;
import com.mytv.rtzhdj.mvp.ui.activity.MyDonationActivity;
import com.mytv.rtzhdj.mvp.ui.activity.VoteEntryDetailActivity;

import java.util.List;


@ActivityScope
public class VoteEntryDetailPresenter extends BasePresenter<VoteEntryDetailContract.Model, VoteEntryDetailContract.View>
    implements VoteEntryDetailContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private VoteEntryDetailActivity mActivity;

    @Inject
    public VoteEntryDetailPresenter(VoteEntryDetailContract.Model model, VoteEntryDetailContract.View rootView
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
    public void setActivity(VoteEntryDetailActivity activity) {
        mActivity = activity;
    }

    @Override
    public void callMethodOfPostOnlineVoteDetails(int id, boolean update) {
        mModel.postOnlineVoteDetails(id, update)
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
                .subscribe(new ErrorHandleSubscriber<BaseJson<VoteEntrylEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<VoteEntrylEntity> voteEntryEntity) {
                        Log.e(TAG, voteEntryEntity.toString());

                        if (voteEntryEntity.isSuccess())
                            mRootView.loadData(voteEntryEntity.getData());

                    }
                });
    }

    @Override
    public void callMethodOfPostVoteSubmit(int contentId, int id, int userId) {
        mModel.postVoteSubmit(contentId, id, userId)
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
                            mRootView.showMessage(postResult.getInfo());

                    }
                });
    }
}
