package com.mytv.rtzhdj.mvp.presenter;

import android.app.Application;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.mytv.rtzhdj.app.base.RTZHDJApplication;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.NewsDetailEntity;
import com.mytv.rtzhdj.app.data.entity.ScoresDetailsEntity;
import com.mytv.rtzhdj.mvp.contract.ScoresDetailsContract;
import com.mytv.rtzhdj.mvp.ui.activity.ScoresDetailsActivity;
import com.mytv.rtzhdj.mvp.ui.decoration.DividerItemDecoration;
import com.zchu.rxcache.data.CacheResult;
import com.zchu.rxcache.stategy.CacheStrategy;

import java.util.List;


@ActivityScope
public class ScoresDetailsPresenter extends BasePresenter<ScoresDetailsContract.Model, ScoresDetailsContract.View>
    implements ScoresDetailsContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private ScoresDetailsActivity mActivity;

    @Inject
    public ScoresDetailsPresenter(ScoresDetailsContract.Model model, ScoresDetailsContract.View rootView
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
    public void setActivity(ScoresDetailsActivity activity) {
        mActivity = activity;
    }

    @Override
    public RecyclerView initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setHasFixedSize(true);

        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);

        //设置item间距
        recyclerView.addItemDecoration(new DividerItemDecoration(mActivity,
                LinearLayoutManager.VERTICAL, ArmsUtils.dip2px(mActivity, 10)));

        return recyclerView;
    }

    @Override
    public void callMethodOfPostMyScore(int userId, boolean update) {
        mModel.postMyScore(userId, update)
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
                .subscribe(new ErrorHandleSubscriber<BaseJson<ScoresDetailsEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<ScoresDetailsEntity> scoreDetailsEntity) {
                        Log.e("TAG", scoreDetailsEntity.toString());

                        if (scoreDetailsEntity.isSuccess())
                            mRootView.loadData(scoreDetailsEntity.getData());

                    }
                });
    }
}
