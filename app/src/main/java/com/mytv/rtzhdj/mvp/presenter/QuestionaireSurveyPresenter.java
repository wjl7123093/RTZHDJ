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
import com.mytv.rtzhdj.app.data.entity.QuestionEntity;
import com.mytv.rtzhdj.mvp.contract.QuestionaireSurveyContract;
import com.mytv.rtzhdj.mvp.ui.activity.QuestionaireSurveyActivity;
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
public class QuestionaireSurveyPresenter extends BasePresenter<QuestionaireSurveyContract.Model, QuestionaireSurveyContract.View>
    implements QuestionaireSurveyContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private QuestionaireSurveyActivity mActivity;

    @Inject
    public QuestionaireSurveyPresenter(QuestionaireSurveyContract.Model model, QuestionaireSurveyContract.View rootView
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
    public void setActivity(QuestionaireSurveyActivity activity) {
        mActivity = activity;
    }

    @Override
    public void callMethodOfGetSurveyDetailList(int examinationId, boolean refresh) {
        mModel.getSurveyDetailList(examinationId, refresh)
                .compose(RTZHDJApplication.rxCache.<BaseJson<List<QuestionEntity>>>transformObservable("getSurveyDetailList" + examinationId,
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
                        if (questionList.isSuccess() && questionList.getData() != null)
                            mRootView.loadData(questionList.getData());
                    }
                });
    }

    @Override
    public void callMethodOfPostSurveyInfo(int userID, String answerString, boolean refresh) {
        mModel.postSurveyInfo(userID, answerString, refresh)
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
                .subscribe(new ErrorHandleSubscriber<BaseJson>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson postResult) {
                        Log.e("TAG", postResult.toString());

//                        mRootView.showPickerView(stationList.getData());
                        if (postResult.isSuccess()) {
                            mRootView.showMessage("问卷提交成功");
//                            mRootView.killMyself();
                        }
                    }
                });
    }
}
