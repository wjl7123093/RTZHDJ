package com.mytv.rtzhdj.mvp.presenter;

import android.app.Application;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.StudyRecordEntity;
import com.mytv.rtzhdj.mvp.contract.StudyRecordContract;
import com.mytv.rtzhdj.mvp.ui.activity.StudyRecordActivity;
import com.mytv.rtzhdj.mvp.ui.decoration.DividerItemDecoration;


@ActivityScope
public class StudyRecordPresenter extends BasePresenter<StudyRecordContract.Model, StudyRecordContract.View>
    implements StudyRecordContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private StudyRecordActivity mActivity;

    @Inject
    public StudyRecordPresenter(StudyRecordContract.Model model, StudyRecordContract.View rootView
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
    public void setActivity(StudyRecordActivity activity) {
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

//            recyclerView.addItemDecoration(new DividerItemDecoration(mActivity,
//                    LinearLayoutManager.VERTICAL, ArmsUtils.dip2px(mActivity, 1)));

        return recyclerView;
    }

    @Override
    public void callMethodOfGetLearningRecords(int userId, boolean update) {
        mModel.getLearningRecords(userId, update)
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
                .subscribe(new ErrorHandleSubscriber<StudyRecordEntity>(mErrorHandler) {
                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull StudyRecordEntity liveMultiItems) {


                    }
                });
    }
}
