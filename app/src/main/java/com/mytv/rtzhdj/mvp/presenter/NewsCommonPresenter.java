package com.mytv.rtzhdj.mvp.presenter;

import android.app.Application;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.mytv.rtzhdj.app.base.RTZHDJApplication;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.NewsDetailEntity;
import com.mytv.rtzhdj.mvp.contract.NewsCommonContract;
import com.mytv.rtzhdj.mvp.ui.activity.NewsCommonActivity;
import com.mytv.rtzhdj.mvp.ui.decoration.DividerItemDecoration;
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
public class NewsCommonPresenter extends BasePresenter<NewsCommonContract.Model, NewsCommonContract.View>
    implements NewsCommonContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private NewsCommonActivity mActivity;

    @Inject
    public NewsCommonPresenter(NewsCommonContract.Model model, NewsCommonContract.View rootView
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
    public void setActivity(NewsCommonActivity activity) {
        mActivity = activity;
    }

    @Override
    public RecyclerView initRecyclerView(RecyclerView recyclerView, String from) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setHasFixedSize(true);

        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);

        //设置item间距
        if (from.equals("StudyCoursewareActivity") || from.equals("StudyRecordActivity")) { // 10dp
            recyclerView.addItemDecoration(new DividerItemDecoration(mActivity,
                    LinearLayoutManager.VERTICAL, ArmsUtils.dip2px(mActivity, 10)));
        } else { // 1dp
            recyclerView.addItemDecoration(new DividerItemDecoration(mActivity,
                    LinearLayoutManager.VERTICAL, ArmsUtils.dip2px(mActivity, 1)));
        }

        return recyclerView;
    }

    @Override
    public void callMethodOfGetTwoLevelInfoList(int nodeId, int pageIndex, int pageSize, boolean update) {
        mModel.getTwoLevelInfoList(nodeId, pageIndex, pageSize, update)
                .compose(RTZHDJApplication.rxCache.<BaseJson<List<NewsDetailEntity>>>transformObservable("getTwoLevelInfoList" + nodeId,
                        new TypeToken<BaseJson<List<NewsDetailEntity>>>() { }.getType(),
                        CacheStrategy.firstRemote()))
                .map(new CacheResult.MapFunc<BaseJson<List<NewsDetailEntity>>>())
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                    if (!update)
                        mRootView.showLoading();
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    // Action onFinally
                    if (!update)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<List<NewsDetailEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<List<NewsDetailEntity>> newsList) {
                        Log.e("TAG", newsList.toString());

                        if (newsList.isSuccess() && newsList.getData() != null)
                            mRootView.loadListData(newsList.getData(), update);

                    }
                });
    }
}
