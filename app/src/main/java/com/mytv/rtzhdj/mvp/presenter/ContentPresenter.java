package com.mytv.rtzhdj.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.base.RTZHDJApplication;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.PartyNewsEntity;
import com.mytv.rtzhdj.app.data.entity.PartyRecommendEntity;
import com.mytv.rtzhdj.app.data.entity.PartySubNewsEntity;
import com.mytv.rtzhdj.app.utils.BannerImageLoader;
import com.mytv.rtzhdj.mvp.contract.ContentContract;
import com.mytv.rtzhdj.mvp.ui.decoration.DividerItemDecoration;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zchu.rxcache.data.CacheResult;
import com.zchu.rxcache.stategy.CacheStrategy;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class ContentPresenter extends BasePresenter<ContentContract.Model, ContentContract.View>
    implements ContentContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private Activity mActivity;

    @Inject
    public ContentPresenter(ContentContract.Model model, ContentContract.View rootView
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
    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    @Override
    public RecyclerView initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setHasFixedSize(true);
        //设置item间距，1dp
        recyclerView.addItemDecoration(new DividerItemDecoration(mActivity,
                LinearLayoutManager.VERTICAL, ArmsUtils.dip2px(mActivity, 1)));
        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);

        return recyclerView;
    }

    @Override
    public View initHeaderView(List<PartyNewsEntity> specialBlockList, ViewGroup parent) {
        List<Object> imageUrls = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < specialBlockList.size(); i++) {
            imageUrls.add(specialBlockList.get(i).getAllImgUrl());
            titles.add(specialBlockList.get(i).getTitle());
        }

        View view = mActivity.getLayoutInflater().inflate(R.layout.item_vlayout_banner, parent, false);

        view.findViewById(R.id.iv_topic).setVisibility(View.GONE);
        // 绑定数据
        Banner mBanner = view.findViewById(R.id.banner);
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        mBanner.setImageLoader(new BannerImageLoader());
        //设置图片集合
        mBanner.setImages(imageUrls);
        //设置标题集合
        mBanner.setBannerTitles(titles);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        //        mBanner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);

        //banner设置方法全部调用完毕时最后调用
        mBanner.start();

        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                mRootView.onBannerClick(specialBlockList.get(position));

                /*ARouter.getInstance().build(ARoutePath.PATH_NEWS_DETAIL)
                        .withInt("articleId", specialBlockList.get(position).getArticleId())
                        .withInt("nodeId", specialBlockList.get(position).getNodeId())
                        .withInt("digs", 0)
                        .withInt("comments", 0).navigation();*/
            }
        });

        return view;
    }

    @Override
    public void callMethodOfGetPartyRecommend(int currentSystemId, int pageIndex, int pageSize, boolean update) {
        mModel.getPartyRecommend(currentSystemId, pageIndex, pageSize, update)
                .compose(RTZHDJApplication.rxCache.<BaseJson<PartyRecommendEntity>>transformObservable("getPartyRecommend",
                        new TypeToken<BaseJson<PartyRecommendEntity>>() { }.getType(),
                        CacheStrategy.firstRemote()))    // 60s以内用缓存
                .map(new CacheResult.MapFunc<BaseJson<PartyRecommendEntity>>())
                .retryWhen(new RetryWithDelay(3, 2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    if (!update)
                        mRootView.showLoading();
                })
                .doFinally(() -> {
                    if (!update)
                        mRootView.hideLoading();
                })
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<PartyRecommendEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<PartyRecommendEntity> partyRecommendData) {
                        Log.e(TAG, partyRecommendData.getData().toString());

                        if (partyRecommendData.isSuccess() && partyRecommendData.getData() != null)
                            mRootView.showRecommendData(partyRecommendData.getData(), update);
                    }
                });
    }

    @Override
    public void callMethodOfGetPartySubList(int currentSystemId, int nodeId, int pageIndex, int pageSize, boolean update) {
        mModel.getPartySubList(currentSystemId, nodeId, pageIndex, pageSize, update)
                .compose(RTZHDJApplication.rxCache.<BaseJson<PartySubNewsEntity>>transformObservable("getPartySubList" + nodeId + pageIndex,
                        new TypeToken<BaseJson<PartySubNewsEntity>>() { }.getType(),
                        CacheStrategy.firstRemote()))    // 60s以内用缓存
                .map(new CacheResult.MapFunc<BaseJson<PartySubNewsEntity>>())
                .retryWhen(new RetryWithDelay(3, 2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    if (!update)
                        mRootView.showLoading();
                })
                .doFinally(() -> {
                    if (!update)
                        mRootView.hideLoading();
                })
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<PartySubNewsEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<PartySubNewsEntity> partySubListData) {
                        Log.e(TAG, partySubListData.getData().toString());

                        if (partySubListData.isSuccess() && partySubListData.getData() != null)
                            mRootView.showSubListData(partySubListData.getData(), update);
                    }
                });
    }
}
