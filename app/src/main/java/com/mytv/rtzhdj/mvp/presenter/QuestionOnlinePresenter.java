package com.mytv.rtzhdj.mvp.presenter;

import android.app.Application;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.jess.arms.utils.ArmsUtils;
import com.mytv.rtzhdj.mvp.contract.QuestionOnlineContract;
import com.mytv.rtzhdj.mvp.ui.activity.QuestionOnlineActivity;
import com.mytv.rtzhdj.mvp.ui.decoration.DividerItemDecoration;


@ActivityScope
public class QuestionOnlinePresenter extends BasePresenter<QuestionOnlineContract.Model, QuestionOnlineContract.View>
    implements QuestionOnlineContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private QuestionOnlineActivity mActivity;

    @Inject
    public QuestionOnlinePresenter(QuestionOnlineContract.Model model, QuestionOnlineContract.View rootView
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
    public void setActivity(QuestionOnlineActivity activity) {
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
}
