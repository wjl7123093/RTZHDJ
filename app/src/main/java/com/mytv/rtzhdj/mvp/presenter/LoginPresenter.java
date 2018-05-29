package com.mytv.rtzhdj.mvp.presenter;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;
import com.mytv.rtzhdj.app.SharepreferenceKey;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.LoginEntity;
import com.mytv.rtzhdj.mvp.contract.LoginContract;
import com.mytv.rtzhdj.mvp.ui.activity.LoginActivity;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import static com.jess.arms.utils.Preconditions.checkNotNull;


@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View>
    implements LoginContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private LoginActivity mActivity;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView
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
    public void setActivity(LoginActivity activity) {
        mActivity = activity;
    }

    @Override
    public void callMethodOfDoLogin(@NonNull String acc, @NonNull String pwd) {
        checkNotNull(acc);
        checkNotNull(pwd);

        mModel.postUserLogin(acc, pwd)
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
                .subscribe(new ErrorHandleSubscriber<BaseJson<LoginEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<LoginEntity> loginData) {
                        Log.e("TAG", loginData.toString());

                        if (loginData.isSuccess() && loginData.getData() != null) {
//                        mRootView.showData(homeData);
                            DataHelper.setIntergerSF(mActivity, SharepreferenceKey.KEY_IS_LOGIN, 1); // 1 表示登录
                            DataHelper.saveDeviceData(mActivity, SharepreferenceKey.KEY_LOGIN_USER, loginData.getData());
                            DataHelper.setIntergerSF(mActivity, SharepreferenceKey.KEY_USER_ID, loginData.getData().getUserId());
                            DataHelper.setIntergerSF(mActivity, SharepreferenceKey.KEY_PUBLISHMENT_SYSTEM_ID, loginData.getData().getPublishmentSystemId());
//                            DataHelper.setIntergerSF(mActivity, SharepreferenceKey.KEY_USER_ID, 39);
//                            DataHelper.setIntergerSF(mActivity, SharepreferenceKey.KEY_PUBLISHMENT_SYSTEM_ID, 1);
                            DataHelper.setStringSF(mActivity, SharepreferenceKey.KEY_PUBLISHMENT_SYSTEM_NAME, loginData.getData().getPublishmentSystemName());
                            DataHelper.setStringSF(mActivity, SharepreferenceKey.KEY_LOGIN_USER_NAME, loginData.getData().getUserName());
                            DataHelper.setIntergerSF(mActivity, SharepreferenceKey.KEY_LOGIN_USER_TYPE, loginData.getData().getUserTypeId());
                            DataHelper.setIntergerSF(mActivity, SharepreferenceKey.KEY_LOGIN_USER_RANK, loginData.getData().getRanking());
                            DataHelper.setStringSF(mActivity, SharepreferenceKey.KEY_LOGIN_HEADER_URL, loginData.getData().getPhotoUrl());
                            DataHelper.setIntergerSF(mActivity, SharepreferenceKey.KEY_LOGIN_INTEGRAL, loginData.getData().getIntegral());
                            DataHelper.setIntergerSF(mActivity, SharepreferenceKey.KEY_POSITIVE_ENERGY_VALUE, loginData.getData().getPositiveEnergyValue());


                            mRootView.goMainActivity();
                            mRootView.killMyself();
                        } else {
                            mRootView.showMessage(loginData.getInfo());
                        }
                    }
                });

    }
}
