package com.mytv.rtzhdj.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
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
import com.mytv.rtzhdj.app.data.entity.StudyCoursewareEntity;
import com.mytv.rtzhdj.mvp.contract.StudyCoursewareContract;
import com.mytv.rtzhdj.mvp.ui.activity.StudyCoursewareActivity;
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
public class StudyCoursewarePresenter extends BasePresenter<StudyCoursewareContract.Model, StudyCoursewareContract.View>
    implements StudyCoursewareContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private StudyCoursewareActivity mActivity;

    @Inject
    public StudyCoursewarePresenter(StudyCoursewareContract.Model model, StudyCoursewareContract.View rootView
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
    public void setActivity(StudyCoursewareActivity activity) {
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
    public BaseDelegateAdapter initBest() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return new BaseDelegateAdapter(mActivity, singleLayoutHelper , R.layout.item_vlayout_study_course_best,
                1, Constant.viewType.typeTitle) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.getView(R.id.ll_container).setOnClickListener(view -> {
                    mRootView.setOnGridClick(3, "精品课件展播");
                });
            }
        };
    }

    @Override
    public BaseDelegateAdapter initGvMenu() {
        // 在构造函数设置每行的网格个数
        final TypedArray proPic = mActivity.getResources().obtainTypedArray(R.array.courseware_gv_images);
        final String[] proName = mActivity.getResources().getStringArray(R.array.courseware_gv_title);
        final List<Integer> images = new ArrayList<>();
        for(int a=0 ; a<proName.length ; a++){
            images.add(proPic.getResourceId(a,R.mipmap.ic_launcher));
        }
        proPic.recycle();
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(3);
//        gridLayoutHelper.setPadding(0, 16, 0, 16);
        gridLayoutHelper.setVGap(2);   // 控制子元素之间的垂直间距
        gridLayoutHelper.setHGap(2);    // 控制子元素之间的水平间距
//        gridLayoutHelper.setBgColor(Color.WHITE);
//        gridLayoutHelper.setMarginBottom(ArmsUtils.dip2px(activity, 12));
        return new BaseDelegateAdapter(mActivity, gridLayoutHelper, R.layout.item_vlayout_grid, 3, Constant.viewType.typeGv) {
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
    public BaseDelegateAdapter initList(List<StudyCoursewareEntity> data) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setDividerHeight(ArmsUtils.dip2px(mActivity, 10));
        return new BaseDelegateAdapter(mActivity, linearLayoutHelper , R.layout.item_rv_study_courseware,
                data.size(), Constant.viewType.typeList) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_title, data.get(position).getTitle());
                holder.setText(R.id.tv_datetime, data.get(position).getLastStudyTime());
                holder.setText(R.id.tv_type, data.get(position).getCourseTypeName());

//                holder.setBackgroundRes(R.id.tv_type,
//                        data.get(position).getType() == 1 ? R.drawable.sp_bg_courseware_type_red
//                        : data.get(position).getType() == 2 ? R.drawable.sp_bg_courseware_type_yellow
//                                : R.drawable.sp_bg_courseware_type_green);
//                holder.setTextColor(R.id.tv_type,
//                        data.get(position).getType() == 1 ? mActivity.getResources().getColor(R.color.primary_dark)
//                        : data.get(position).getType() == 2 ? mActivity.getResources().getColor(R.color.accent)
//                                : mActivity.getResources().getColor(R.color.green_400));

                holder.getView(R.id.ll_container).setOnClickListener(view -> {
                    ARouter.getInstance().build(ARoutePath.PATH_COURSE_DETAIL)
                            .withString("title", data.get(position).getCourseTypeName())
                            .withInt("nodeId", 0)
                            .withInt("articleId", data.get(position).getContentId())
                            .withInt("courseType", data.get(position).getCourseType()).navigation();
                });

            }
        };
    }

    @Override
    public void callMethodOfGetNewCoursewareList(int userId, int pageIndex, int pageSize, boolean update) {
        mModel.getNewCoursewareList(userId, pageIndex, pageSize, update)
                .compose(RTZHDJApplication.rxCache.<BaseJson<List<StudyCoursewareEntity>>>transformObservable("getNewCoursewareList" + userId,
                        new TypeToken<BaseJson<List<StudyCoursewareEntity>>>() { }.getType(),
                        CacheStrategy.firstRemote()))
                .map(new CacheResult.MapFunc<BaseJson<List<StudyCoursewareEntity>>>())
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
                .subscribe(new ErrorHandleSubscriber<BaseJson<List<StudyCoursewareEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<List<StudyCoursewareEntity>> courseList) {
                        Log.e(TAG, courseList.toString());

                        if (courseList.isSuccess() && courseList.getData() != null)
                            mRootView.loadData(courseList.getData(), update);
                    }
                });
    }
}
