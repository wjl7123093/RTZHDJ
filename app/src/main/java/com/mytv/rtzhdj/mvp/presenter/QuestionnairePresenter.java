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
import com.mytv.rtzhdj.app.data.entity.CommentEntity;
import com.mytv.rtzhdj.app.data.entity.QuestionEntity;
import com.mytv.rtzhdj.mvp.contract.QuestionnaireContract;
import com.mytv.rtzhdj.mvp.ui.activity.QuestionnaireActivity;
import com.zchu.rxcache.data.CacheResult;
import com.zchu.rxcache.stategy.CacheStrategy;

import java.util.List;


@ActivityScope
public class QuestionnairePresenter extends BasePresenter<QuestionnaireContract.Model, QuestionnaireContract.View>
    implements QuestionnaireContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private QuestionnaireActivity mActivity;

    @Inject
    public QuestionnairePresenter(QuestionnaireContract.Model model, QuestionnaireContract.View rootView
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
    public void setActivity(QuestionnaireActivity activity) {
        mActivity = activity;
    }

    @Override
    public void callMethodOfGetTestInfo(int examinationId, boolean refresh) {
        mModel.getTestInfo(examinationId, refresh)
                .compose(RTZHDJApplication.rxCache.<BaseJson<List<QuestionEntity>>>transformObservable("getTestInfo" + examinationId,
                        new TypeToken<BaseJson<List<QuestionEntity>>>() { }.getType(),
                        CacheStrategy.firstRemote()))
                .map(new CacheResult.MapFunc<BaseJson<List<QuestionEntity>>>())
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
                .subscribe(new ErrorHandleSubscriber<BaseJson<List<QuestionEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<List<QuestionEntity>> questionList) {
                        Log.e("TAG", questionList.toString());

//                        mRootView.showPickerView(stationList.getData());
                        if (questionList.isSuccess())
                            mRootView.loadData(questionList.getData());
                    }
                });
    }
}
