package com.mytv.rtzhdj.mvp.presenter;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;
import com.mytv.rtzhdj.app.SharepreferenceKey;
import com.mytv.rtzhdj.app.base.RTZHDJApplication;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.LoginEntity;
import com.mytv.rtzhdj.mvp.contract.LoginContract;
import com.mytv.rtzhdj.mvp.ui.activity.LoginActivity;
import com.zchu.rxcache.data.CacheResult;
import com.zchu.rxcache.stategy.CacheStrategy;

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
                .compose(RTZHDJApplication.rxCache.<BaseJson<LoginEntity>>transformObservable("postUserLogin",
                        new TypeToken<BaseJson<LoginEntity>>() { }.getType(),
                        CacheStrategy.firstRemote()))
                .map(new CacheResult.MapFunc<BaseJson<LoginEntity>>())
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

//                        mRootView.showData(homeData);
                        DataHelper.setIntergerSF(mActivity, SharepreferenceKey.KEY_IS_LOGIN, 1); // 1 表示登录
                        DataHelper.saveDeviceData(mActivity, SharepreferenceKey.KEY_LOGIN_USER, loginData.getData());
                        mRootView.goMainActivity();
                    }
                });

    }
}
