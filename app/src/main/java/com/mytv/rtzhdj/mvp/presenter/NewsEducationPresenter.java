package com.mytv.rtzhdj.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.Constant;
import com.mytv.rtzhdj.app.base.RTZHDJApplication;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.NewsDetailEntity;
import com.mytv.rtzhdj.app.data.entity.NewsSimpleEntity;
import com.mytv.rtzhdj.mvp.contract.NewsEducationContract;
import com.mytv.rtzhdj.mvp.ui.activity.NewsEducationActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.BaseDelegateAdapter;
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
public class NewsEducationPresenter extends BasePresenter<NewsEducationContract.Model, NewsEducationContract.View>
    implements NewsEducationContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private NewsEducationActivity mActivity;

    @Inject
    public NewsEducationPresenter(NewsEducationContract.Model model, NewsEducationContract.View rootView
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
    public void setActivity(NewsEducationActivity activity) {
        mActivity = activity;
    }

    @Override
    public DelegateAdapter initRecyclerView(RecyclerView recyclerView) {
        //初始化
        //创建VirtualLayoutManager对象
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mActivity);
//        layoutManager.setRecycleChildrenOnDetach(true);
        recyclerView.setLayoutManager(layoutManager);

        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);

        //设置适配器
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager, true);
        recyclerView.setAdapter(delegateAdapter);
        return delegateAdapter;
    }

    @Override
    public BaseDelegateAdapter initGvMenu() {
        // 在构造函数设置每行的网格个数
        final TypedArray proPic = mActivity.getResources().obtainTypedArray(R.array.education_gv_images);
        final String[] proName = mActivity.getResources().getStringArray(R.array.education_gv_title);
        final List<Integer> images = new ArrayList<>();
        for(int a=0 ; a<proName.length ; a++){
            images.add(proPic.getResourceId(a,R.mipmap.ic_launcher));
        }
        proPic.recycle();
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
//        gridLayoutHelper.setPadding(0, 16, 0, 16);
        gridLayoutHelper.setVGap(2);   // 控制子元素之间的垂直间距
        gridLayoutHelper.setHGap(2);    // 控制子元素之间的水平间距
//        gridLayoutHelper.setBgColor(Color.WHITE);
//        gridLayoutHelper.setMarginBottom(ArmsUtils.dip2px(activity, 12));
        return new BaseDelegateAdapter(mActivity, gridLayoutHelper, R.layout.item_vlayout_grid, 4, Constant.viewType.typeGv) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, @SuppressLint("RecyclerView") final int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_name, proName[position]);
                holder.setImageResource(R.id.iv_icon, images.get(position));
                holder.getView(R.id.ll_container).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mRootView.setOnGridClick(position, proName[position]);
                    }
                });
            }
        };
    }

    @Override
    public BaseDelegateAdapter initTitle(String title) {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return new BaseDelegateAdapter(mActivity, singleLayoutHelper , R.layout.item_vlayout_title,
                1, Constant.viewType.typeTitle) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setGone(R.id.tv_moredata, false);
                holder.setText(R.id.tv_title, title);
            }
        };
    }

    @Override
    public BaseDelegateAdapter initList(List<NewsDetailEntity> data) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setDividerHeight(ArmsUtils.dip2px(mActivity, 1));
        return new BaseDelegateAdapter(mActivity, linearLayoutHelper , R.layout.item_vlayout_list_image,
                data.size(), Constant.viewType.typeList) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_title, data.get(position).getTitle());
                holder.setText(R.id.tv_datetime, data.get(position).getAddDate());
                holder.setText(R.id.tv_star_num, data.get(position).getDigs() + "");
                holder.setText(R.id.tv_comment_num, data.get(position).getComments() + "");

                if (!TextUtils.isEmpty(data.get(position).getAllImgUrl())) {
                    holder.setGone(R.id.iv_image, true);
                    com.mytv.rtzhdj.app.utils.ImageLoader.getInstance().showImage(mActivity,
                            holder.getView(R.id.iv_image), data.get(position).getAllImgUrl());
                } else {
                    holder.setGone(R.id.iv_image, false);
                }

                holder.getView(R.id.rl_container).setOnClickListener(view -> {
                    // 新闻详情页
                    ARouter.getInstance().build(ARoutePath.PATH_NEWS_DETAIL)
                            .withInt("articleId", data.get(position).getId())
                            .withInt("nodeId", data.get(position).getNodeId())
                            .withInt("digs", data.get(position).getDigs())
                            .withInt("comments", data.get(position).getComments())
                            .navigation();
                });

            }
        };
    }

    @Override
    public void callMethodOfGetTwoLevelList(int nodeId, int pageIndex, int pageSize, boolean update) {
        mModel.getTwoLevelList(nodeId, pageIndex, pageSize, update)
                .compose(RTZHDJApplication.rxCache.<BaseJson<NewsSimpleEntity>>transformObservable("getTwoLevelList" + nodeId,
                        new TypeToken<BaseJson<NewsSimpleEntity>>() { }.getType(),
                        CacheStrategy.firstRemote()))
                .map(new CacheResult.MapFunc<BaseJson<NewsSimpleEntity>>())
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
                .subscribe(new ErrorHandleSubscriber<BaseJson<NewsSimpleEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<NewsSimpleEntity> newsSimpleEntity) {
                        Log.e("TAG", newsSimpleEntity.toString());

                        if (newsSimpleEntity.isSuccess() && newsSimpleEntity.getData() != null)
                            mRootView.loadData(newsSimpleEntity.getData());

                    }
                });
    }
}
