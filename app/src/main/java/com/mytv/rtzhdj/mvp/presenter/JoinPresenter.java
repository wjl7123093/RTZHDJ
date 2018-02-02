package com.mytv.rtzhdj.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.jess.arms.utils.ArmsUtils;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.Constant;
import com.mytv.rtzhdj.mvp.contract.JoinContract;
import com.mytv.rtzhdj.mvp.ui.activity.MainActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.BaseDelegateAdapter;

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
            }
        };
    }

    @Override
    public BaseDelegateAdapter initListVolunteer(String url, int status, int joinNum, int starNum,
                                                 int commentNum, String title, String deadtime) {
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
                                .url(url)
                                .imageView(holder.getView(R.id.iv_event))
                                .build());

                holder.setText(R.id.tv_status, status == 0 ? "进行中" : "已结束");
                holder.setText(R.id.tv_join_num, "参与人数: " + joinNum);
                holder.setText(R.id.tv_star_num, "" + starNum);
                holder.setText(R.id.tv_comment_num, "" + commentNum);
                holder.setText(R.id.tv_title, title);
                holder.setText(R.id.tv_deadtime, "报名截止: " + deadtime);

            }
        };
    }

    @Override
    public BaseDelegateAdapter initListCommunity(String url, int starNum, int commentNum,
                                                 String title, String deadtime) {
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
                                .url(url)
                                .imageView(holder.getView(R.id.iv_image))
                                .build());

                holder.setText(R.id.tv_star_num, "" + starNum);
                holder.setText(R.id.tv_comment_num, "" + commentNum);
                holder.setText(R.id.tv_title, title);
                holder.setText(R.id.tv_datetime, deadtime);

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
                holder.getView(R.id.btn_my_report).setOnClickListener(view -> {

                });
                holder.getView(R.id.btn_my_report).setOnClickListener(view -> {

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

                includeWish1.setOnClickListener(view -> {

                });
                includeWish2.setOnClickListener(view -> {

                });
                includeWish3.setOnClickListener(view -> {

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

                });
                holder.getView(R.id.rl_online_vote).setOnClickListener(view -> {

                });

            }
        };
    }
}
