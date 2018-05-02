package com.mytv.rtzhdj.mvp.presenter;

import android.app.Application;
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
import com.mytv.rtzhdj.app.data.entity.UserRegisterEntity;
import com.mytv.rtzhdj.mvp.contract.SendMsgContract;
import com.mytv.rtzhdj.mvp.ui.activity.SendMsgActivity;


@ActivityScope
public class SendMsgPresenter extends BasePresenter<SendMsgContract.Model, SendMsgContract.View>
    implements SendMsgContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private SendMsgActivity mActivity;

    @Inject
    public SendMsgPresenter(SendMsgContract.Model model, SendMsgContract.View rootView
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
    public void setActivity(SendMsgActivity activity) {
        mActivity = activity;
    }

    @Override
    public void callMethodOfPostSendMessage(@NonNull int userId, @NonNull int receiveId, @NonNull String topic, @NonNull String msg) {
        mModel.postSendMessage(userId, receiveId, topic, msg)
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
                            mRootView.showMessage("发送成功");
//                            mRootView.killMyself();
                        }
                    }
                });
    }
}
