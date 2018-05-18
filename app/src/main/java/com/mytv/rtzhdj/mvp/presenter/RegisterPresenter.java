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
import com.mytv.rtzhdj.app.data.entity.UserCategoryEntity;
import com.mytv.rtzhdj.app.data.entity.UserRegisterEntity;
import com.mytv.rtzhdj.app.data.entity.VerifyCodeEntity;
import com.mytv.rtzhdj.mvp.contract.RegisterContract;
import com.mytv.rtzhdj.mvp.ui.activity.RegisterActivity;
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
public class RegisterPresenter extends BasePresenter<RegisterContract.Model, RegisterContract.View>
    implements RegisterContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private RegisterActivity mActivity;

    @Inject
    public RegisterPresenter(RegisterContract.Model model, RegisterContract.View rootView
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
    public void setActivity(RegisterActivity activity) {
        mActivity = activity;
    }

    @Override
    public void callMethodOfGetUserCategory(boolean refresh) {
        mModel.getUserCategory(refresh)
                .compose(RTZHDJApplication.rxCache.<BaseJson<List<UserCategoryEntity>>>transformObservable("getUserCategory",
                        new TypeToken<BaseJson<List<UserCategoryEntity>>>() { }.getType(),
                        CacheStrategy.firstRemote()))
                .map(new CacheResult.MapFunc<BaseJson<List<UserCategoryEntity>>>())
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
                .subscribe(new ErrorHandleSubscriber<BaseJson<List<UserCategoryEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<List<UserCategoryEntity>> userCategoryList) {
                        Log.e("TAG", userCategoryList.toString());

                        if (userCategoryList.isSuccess() && userCategoryList.getData() != null) {
                            List<UserCategoryEntity> userCategorys = userCategoryList.getData();
                            mRootView.showDialog(userCategorys);
                        }

                    }
                });
    }

    /**
     * 调用 获取站点集合 接口
     * @param refresh
     */
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

    /**
     * 调用 获取验证码接口
     */
    @Override
    public void callMethodOfGetCode(@NonNull String telNumber) {
        mModel.getVerifyCode(telNumber)
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
                .subscribe(new ErrorHandleSubscriber<BaseJson<VerifyCodeEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<VerifyCodeEntity> verifyCode) {
                        Log.e("TAG", verifyCode.toString());


                    }
                });
    }

    /**
     * 调用 注册接口
     * @param mobile    手机号
     * @param publishmentSystemId 社区
     * @param pwd       密码
     * @param pwd2      密码2
     */
    @Override
    public void callMethodOfDoRegister(@NonNull String mobile,
                                       @NonNull String publishmentSystemId,
                                       @NonNull String pwd,
                                       @NonNull String pwd2) {
        if (!pwd.equals(pwd2)) {
            mRootView.showMessage("密码不一致");
            return;
        }

        mModel.getUserRegister(mobile, publishmentSystemId, pwd)
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
                .subscribe(new ErrorHandleSubscriber<BaseJson<UserRegisterEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<UserRegisterEntity> userRegisterEntity) {
                        Log.e(TAG, userRegisterEntity.toString());

                        if (userRegisterEntity.isSuccess()) {
                            mRootView.showMessage("注册成功");
                            mRootView.killMyself();
                        }
                    }
                });

    }
}
