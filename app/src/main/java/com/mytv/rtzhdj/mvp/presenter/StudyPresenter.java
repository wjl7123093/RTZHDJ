package com.mytv.rtzhdj.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.utils.TextUtils;
import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.Constant;
import com.mytv.rtzhdj.app.base.RTZHDJApplication;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.MyStudyEntity;
import com.mytv.rtzhdj.mvp.contract.StudyContract;
import com.mytv.rtzhdj.mvp.ui.activity.MainActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.BaseDelegateAdapter;
import com.zchu.rxcache.data.CacheResult;
import com.zchu.rxcache.stategy.CacheStrategy;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class StudyPresenter extends BasePresenter<StudyContract.Model, StudyContract.View>
    implements StudyContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private MainActivity activity;

    @Inject
    public StudyPresenter(StudyContract.Model model, StudyContract.View rootView
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
    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public DelegateAdapter initRecyclerView(RecyclerView recyclerView) {
        //初始化
        //创建VirtualLayoutManager对象
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(activity);
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
        final TypedArray proPic = activity.getResources().obtainTypedArray(R.array.study_gv_image);
        final String[] proName = activity.getResources().getStringArray(R.array.study_gv_title);
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
        return new BaseDelegateAdapter(activity, gridLayoutHelper, R.layout.item_vlayout_grid, 6, Constant.viewType.typeGv) {
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
    public BaseDelegateAdapter initTitle(String title, int arrayPos) {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return new BaseDelegateAdapter(activity, singleLayoutHelper , R.layout.item_vlayout_title,
                1, Constant.viewType.typeTitle) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_title, title);

                holder.getView(R.id.tv_moredata).setOnClickListener(view -> {
                    mRootView.setOnMoreClick(arrayPos, title);
                });

            }
        };
    }

    @Override
    public BaseDelegateAdapter initList(List<MyStudyEntity.CoursewareBlock> coursewareList, int arrayPos) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setDividerHeight(ArmsUtils.dip2px(activity, 10));
        return new BaseDelegateAdapter(activity, linearLayoutHelper , R.layout.item_vlayout_list_text1,
                coursewareList.size(), Constant.viewType.typeList) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_title, coursewareList.get(position).getTitle());
                holder.setText(R.id.tv_datetime, coursewareList.get(position).getLastStudyTime());

                holder.getView(R.id.ll_container).setOnClickListener(view -> {
//                    mRootView.setOnListClick(arrayPos, position);

                    switch (arrayPos) {
                        case 0: // 必修课
                            ARouter.getInstance().build(ARoutePath.PATH_COURSE_DETAIL)
                                    .withString("title", "必修课")
                                    .withInt("nodeId", coursewareList.get(position).getNodeId())
                                    .withInt("articleId", coursewareList.get(position).getArticleId()).navigation();
                            break;
                        case 1: // 选修课
                            ARouter.getInstance().build(ARoutePath.PATH_COURSE_DETAIL)
                                    .withString("title", "选修课")
                                    .withInt("nodeId", coursewareList.get(position).getNodeId())
                                    .withInt("articleId", coursewareList.get(position).getArticleId()).navigation();
                            break;
                        case 2: // 微党课
                            ARouter.getInstance().build(ARoutePath.PATH_COURSE_DETAIL)
                                    .withString("title", "微党课")
                                    .withInt("nodeId", coursewareList.get(position).getNodeId())
                                    .withInt("articleId", coursewareList.get(position).getArticleId()).navigation();
                            break;
                    }
                });

            }
        };
    }

    @Override
    public BaseDelegateAdapter initHeader(MyStudyEntity.UserInfoBlock userInfo) {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return new BaseDelegateAdapter(activity, singleLayoutHelper , R.layout.item_vlayout_header2,
                1, Constant.viewType.typeHeader) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_name, userInfo.getUserName());
                holder.setText(R.id.tv_study_record, Html.fromHtml("未学: " + userInfo.getNoStudyNum()
                        + "<font color=\"#ffffff\">/已学: " + userInfo.getYesStudyNum() + "</font>"));
                holder.setText(R.id.tv_study_scores, "累积: " + userInfo.getIntegral());

                if (!TextUtils.isEmpty(userInfo.getPhotoUrl())) {
                    mImageLoader.loadImage(activity,
                            ImageConfigImpl
                                    .builder()
                                    .url(userInfo.getPhotoUrl())
                                    .imageView(holder.getView(R.id.iv_header))
                                    .build());
                }

                holder.getView(R.id.rl_study_record).setOnClickListener(view -> {
                    mRootView.setOnStudyRecordClick();
                });
            }
        };
    }

    @Override
    public void callMethodOfGetMyStudy(int userId, boolean update) {
        mModel.getMyStudy(userId, update)
                .compose(RTZHDJApplication.rxCache.<BaseJson<MyStudyEntity>>transformObservable("getMyStudy" + userId,
                        new TypeToken<BaseJson<MyStudyEntity>>() { }.getType(),
                        CacheStrategy.firstRemote()))
                .map(new CacheResult.MapFunc<BaseJson<MyStudyEntity>>())
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
                .subscribe(new ErrorHandleSubscriber<BaseJson<MyStudyEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<MyStudyEntity> myStudyData) {
                        Log.e(TAG, myStudyData.toString());

                        if (myStudyData.isSuccess() && myStudyData.getData() != null)
                            mRootView.showStudyData(myStudyData.getData());

                    }
                });
    }
}
