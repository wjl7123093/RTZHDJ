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
import com.mytv.rtzhdj.app.data.entity.SpecialColumnsEntity;
import com.mytv.rtzhdj.app.data.entity.UserCategoryEntity;
import com.mytv.rtzhdj.mvp.contract.TopicDetailContract;
import com.mytv.rtzhdj.mvp.ui.activity.TopicDetailActivity;
import com.zchu.rxcache.data.CacheResult;
import com.zchu.rxcache.stategy.CacheStrategy;

import java.util.List;


@ActivityScope
public class TopicDetailPresenter extends BasePresenter<TopicDetailContract.Model, TopicDetailContract.View>
    implements TopicDetailContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private TopicDetailActivity mActivity;

    @Inject
    public TopicDetailPresenter(TopicDetailContract.Model model, TopicDetailContract.View rootView
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
    public void setActivity(TopicDetailActivity activity) {
        mActivity = activity;
    }

    @Override
    public void callMethodOfGetSpecialClass(int nodeId, boolean update) {
        mModel.getSpecialClass(nodeId, update)
                .compose(RTZHDJApplication.rxCache.<BaseJson<SpecialColumnsEntity>>transformObservable("getSpecailClass" + nodeId,
                        new TypeToken<BaseJson<SpecialColumnsEntity>>() { }.getType(),
                        CacheStrategy.firstRemote()))
                .map(new CacheResult.MapFunc<BaseJson<SpecialColumnsEntity>>())
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
                .subscribe(new ErrorHandleSubscriber<BaseJson<SpecialColumnsEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<SpecialColumnsEntity> specialColumnsEntity) {
                        Log.e("TAG", specialColumnsEntity.getData().toString());

//                        List<UserCategoryEntity> userCategorys = userCategoryList.getData();
//                        mRootView.showDialog(userCategorys);
                        mRootView.initBackground(specialColumnsEntity.getData());
                        mRootView.initTab(specialColumnsEntity.getData().getSubObjs());

                    }
                });
    }
}
