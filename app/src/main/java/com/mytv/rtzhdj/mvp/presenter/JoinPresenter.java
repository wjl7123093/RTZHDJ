package com.mytv.rtzhdj.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
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
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.Constant;
import com.mytv.rtzhdj.app.base.RTZHDJApplication;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.Api;
import com.mytv.rtzhdj.app.data.entity.MyJoinEntity;
import com.mytv.rtzhdj.app.data.entity.VolunteerDetailEntity;
import com.mytv.rtzhdj.mvp.contract.JoinContract;
import com.mytv.rtzhdj.mvp.ui.activity.MainActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.BaseDelegateAdapter;
import com.zchu.rxcache.data.CacheResult;
import com.zchu.rxcache.stategy.CacheStrategy;

import java.util.ArrayList;
import java.util.List;


@ActivityScope
public class JoinPresenter extends BasePresenter<JoinContract.Model, JoinContract.View>
    implements JoinContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private MainActivity activity;

    @Inject
    public JoinPresenter(JoinContract.Model model, JoinContract.View rootView
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
        final TypedArray proPic = activity.getResources().obtainTypedArray(R.array.join_gv_image);
        final String[] proName = activity.getResources().getStringArray(R.array.join_gv_title);
        final List<Integer> images = new ArrayList<>();
        for(int a=0 ; a<proName.length ; a++){
            images.add(proPic.getResourceId(a,R.mipmap.ic_launcher));
        }
        proPic.recycle();
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
//        gridLayoutHelper.setPadding(0, 16, 0, 16);
        gridLayoutHelper.setVGap(2);   // 控制子元素之间的垂直间距
        gridLayoutHelper.setHGap(2);    // 控制子元素之间的水平间距
//        gridLayoutHelper.setBgColor(Color.WHITE);
//        gridLayoutHelper.setMarginBottom(ArmsUtils.dip2px(activity, 12));
        return new BaseDelegateAdapter(activity, gridLayoutHelper, R.layout.item_vlayout_grid, 4, Constant.viewType.typeGv) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, @SuppressLint("RecyclerView") final int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_name, proName[position]);
                holder.setImageResource(R.id.iv_icon, images.get(position));
                holder.getView(R.id.ll_container).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mRootView.setOnGridClick(position);
                    }
                });
            }
        };
    }

    @Override
    public BaseDelegateAdapter initTitle(String title) {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return new BaseDelegateAdapter(activity, singleLayoutHelper , R.layout.item_vlayout_title,
                1, Constant.viewType.typeTitle) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_title, title);
                holder.setGone(R.id.tv_moredata, false);

                if (title.equals("志愿服务")) {
                    holder.setGone(R.id.view_divider, false);
                    holder.setGone(R.id.iv_volunteer, true);
                }
            }
        };
    }

    @Override
    public BaseDelegateAdapter initListVolunteer(List<MyJoinEntity.VolunteerBlock> volunteerBlocks) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setDividerHeight(ArmsUtils.dip2px(activity, 1));
        return new BaseDelegateAdapter(activity, linearLayoutHelper , R.layout.item_vlayout_list_event,
                2, Constant.viewType.typeList) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                mImageLoader.loadImage(activity,
                        ImageConfigImpl
                                .builder()
                                .errorPic(R.mipmap.ic_error)
                                .placeholder(R.mipmap.ic_placeholder)
                                .url(Api.APP_IMAGE_DOMAIN + volunteerBlocks.get(position).getPicture().replace("@", ""))
                                .imageView(holder.getView(R.id.iv_event))
                                .build());

//                holder.setText(R.id.tv_status, status == 0 ? "进行中" : "已结束");
//                holder.setText(R.id.tv_join_num, "参与人数: " + volunteerBlocks.get(position).);
                holder.setText(R.id.tv_star_num, "" + volunteerBlocks.get(position).getDigs());
                holder.setText(R.id.tv_comment_num, "" + volunteerBlocks.get(position).getComments());
                holder.setText(R.id.tv_title, volunteerBlocks.get(position).getTitle());
                holder.setText(R.id.tv_deadtime, "报名截止: " + volunteerBlocks.get(position).getEnrollEndDate());

                holder.getView(R.id.ll_container).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ARouter.getInstance().build(ARoutePath.PATH_VOLUNTEER_SERVICE_DETAIL)
                                .withInt("id", volunteerBlocks.get(position).getContentId()).navigation();
                    }
                });

            }
        };
    }

    @Override
    public BaseDelegateAdapter initListCommunity(List<MyJoinEntity.CommunityBlock> communityBlocks) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setDividerHeight(ArmsUtils.dip2px(activity, 1));
        return new BaseDelegateAdapter(activity, linearLayoutHelper , R.layout.item_vlayout_list_image,
                10, Constant.viewType.typeNews) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                mImageLoader.loadImage(activity,
                        ImageConfigImpl
                                .builder()
                                .errorPic(R.mipmap.ic_error)
                                .placeholder(R.mipmap.ic_placeholder)
                                .url(Api.APP_IMAGE_DOMAIN + communityBlocks.get(position).getPicture().replace("@", ""))
                                .imageView(holder.getView(R.id.iv_image))
                                .build());

                holder.setText(R.id.tv_star_num, "" + communityBlocks.get(position).getDigs());
                holder.setText(R.id.tv_comment_num, "" + communityBlocks.get(position).getComments());
                holder.setText(R.id.tv_title, communityBlocks.get(position).getTitle());
                holder.setText(R.id.tv_datetime, communityBlocks.get(position).getAddDate());

                holder.getView(R.id.rl_container).setOnClickListener(view -> {
                    // 新闻详情页
                    ARouter.getInstance().build(ARoutePath.PATH_NEWS_DETAIL)
                            .withInt("articleId", communityBlocks.get(position).getContentId())
                            .withInt("nodeId", 0)
                            .withInt("digs", communityBlocks.get(position).getDigs())
                            .withInt("comments", communityBlocks.get(position).getComments())
                            .navigation();
                });

            }
        };
    }

    @Override
    public BaseDelegateAdapter initHeader(String report) {
//        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(activity, linearLayoutHelper , R.layout.item_vlayout_header3,
                1, Constant.viewType.typeHeader) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.getView(R.id.tv_report).setOnClickListener(view -> {
                    ARouter.getInstance().build(ARoutePath.PATH_DOUBLE_REPORTING).navigation();
                });
                holder.getView(R.id.iv_my_report).setOnClickListener(view -> {
                    ARouter.getInstance().build(ARoutePath.PATH_MY_REPORTING).navigation();
                });
            }
        };
    }

    @Override
    public BaseDelegateAdapter initFooter(String footer) {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return new BaseDelegateAdapter(activity, singleLayoutHelper , R.layout.item_vlayout_moredata,
                1, Constant.viewType.typeFooter) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.btn_moredata, footer);

                holder.getView(R.id.btn_moredata).setOnClickListener(view -> {
                    mRootView.setOnFooterClick();
                });
            }
        };
    }

    @Override
    public BaseDelegateAdapter initOnePlusN2() {
        // 在构造函数设置每行的网格个数
        final TypedArray proPic = activity.getResources().obtainTypedArray(R.array.join_plus_image);
        final String[] proTitle = activity.getResources().getStringArray(R.array.join_plus_title);
        final String[] proDesc = activity.getResources().getStringArray(R.array.join_plus_desc);
        final List<Integer> images = new ArrayList<>();
        for(int a=0 ; a<proTitle.length ; a++){
            images.add(proPic.getResourceId(a,R.mipmap.ic_launcher));
        }
        proPic.recycle();

        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return new BaseDelegateAdapter(activity, singleLayoutHelper , R.layout.item_vlayout_oneplusn2,
                1, Constant.viewType.typePlus) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                View includeWish1 = holder.getView(R.id.include_wish1);
                View includeWish2 = holder.getView(R.id.include_wish2);
                View includeWish3 = holder.getView(R.id.include_wish3);
                TextView tvTitle1 = includeWish1.findViewById(R.id.tv_title);
                TextView tvTitle2 = includeWish2.findViewById(R.id.tv_title);
                TextView tvTitle3 = includeWish3.findViewById(R.id.tv_title);
                TextView tvDesc1 = includeWish1.findViewById(R.id.tv_desc);
                TextView tvDesc2 = includeWish2.findViewById(R.id.tv_desc);
                TextView tvDesc3 = includeWish3.findViewById(R.id.tv_desc);
                ImageView icon1 = includeWish1.findViewById(R.id.iv_icon);
                ImageView icon2 = includeWish2.findViewById(R.id.iv_icon);
                ImageView icon3 = includeWish3.findViewById(R.id.iv_icon);

                tvTitle1.setText(proTitle[0]);
                tvTitle2.setText(proTitle[1]);
                tvTitle3.setText(proTitle[2]);
                tvDesc1.setText(proDesc[0]);
                tvDesc2.setText(proDesc[1]);
                tvDesc3.setText(proDesc[2]);
                Glide.with(activity).load(images.get(0)).into(icon1);
                Glide.with(activity).load(images.get(1)).into(icon2);
                Glide.with(activity).load(images.get(2)).into(icon3);

                includeWish1.setOnClickListener(view -> {   // 心愿墙
                    mRootView.setOnColumnClick(0, 0);
                });
                includeWish2.setOnClickListener(view -> {   // 我要捐赠
                    mRootView.setOnColumnClick(0, 1);
                });
                includeWish3.setOnClickListener(view -> {   // 所有捐赠物品
                    mRootView.setOnColumnClick(0, 2);
                });
            }
        };
    }

    @Override
    public BaseDelegateAdapter initColumnWish() {
        // 在构造函数设置每行的网格个数
        final TypedArray proPic = activity.getResources().obtainTypedArray(R.array.join_column_wish_image);
        final String[] proName = activity.getResources().getStringArray(R.array.join_column_wish_title);
        final List<Integer> images = new ArrayList<>();
        for(int a=0 ; a<proName.length ; a++){
            images.add(proPic.getResourceId(a,R.mipmap.ic_launcher));
        }
        proPic.recycle();

//        ColumnLayoutHelper columnLayoutHelper = new ColumnLayoutHelper();
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
        gridLayoutHelper.setVGap(2);   // 控制子元素之间的垂直间距
        gridLayoutHelper.setHGap(2);    // 控制子元素之间的水平间距
        gridLayoutHelper.setWeights(new float[]{22, 28, 22, 28});
        return new BaseDelegateAdapter(activity, gridLayoutHelper , R.layout.item_vlayout_grid2,
                4, Constant.viewType.typeColumn) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_name, proName[position]);
                holder.setImageResource(R.id.iv_icon, images.get(position));
                holder.getView(R.id.ll_container).setOnClickListener(view -> {
                    mRootView.setOnColumnClick(1, position);
                });
            }
        };
    }

    @Override
    public BaseDelegateAdapter initColumnOnline() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return new BaseDelegateAdapter(activity, singleLayoutHelper , R.layout.item_vlayout_column_online,
                1, Constant.viewType.typeColumn) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.getView(R.id.rl_online_questionaire).setOnClickListener(view -> {
                    mRootView.setOnColumnClick(2, 0);
                });
                holder.getView(R.id.rl_online_vote).setOnClickListener(view -> {
                    mRootView.setOnColumnClick(2, 1);
                });

            }
        };
    }

    @Override
    public void callMethodOfGetMyPartIn(int userId, int pageIndex, int pageSize, boolean update) {
        mModel.getMyPartIn(userId, pageIndex, pageSize, update)
                .compose(RTZHDJApplication.rxCache.<BaseJson<MyJoinEntity>>transformObservable("getMyPartIn" + userId,
                        new TypeToken<BaseJson<MyJoinEntity>>() { }.getType(),
                        CacheStrategy.firstCache()))
                .map(new CacheResult.MapFunc<BaseJson<MyJoinEntity>>())
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
                .subscribe(new ErrorHandleSubscriber<BaseJson<MyJoinEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<MyJoinEntity> myJoinEntity) {
                        Log.e(TAG, myJoinEntity.toString());

                        mRootView.loadData(myJoinEntity.getData());

                    }
                });
    }
}
