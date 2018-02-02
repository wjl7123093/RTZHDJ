package com.mytv.rtzhdj.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.ColumnLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
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
import com.mytv.rtzhdj.mvp.contract.MineContract;
import com.mytv.rtzhdj.mvp.ui.activity.MainActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.BaseDelegateAdapter;

import java.util.ArrayList;
import java.util.List;


@ActivityScope
public class MinePresenter extends BasePresenter<MineContract.Model, MineContract.View>
    implements MineContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private MainActivity activity;

    @Inject
    public MinePresenter(MineContract.Model model, MineContract.View rootView
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
    public BaseDelegateAdapter initGvMenu1() {
        // 在构造函数设置每行的网格个数
        final TypedArray proPic = activity.getResources().obtainTypedArray(R.array.mine_gv_party_branch_images);
        final String[] proName = activity.getResources().getStringArray(R.array.mine_gv_party_branch_title);
        final List<Integer> images = new ArrayList<>();
        for(int a=0 ; a<proName.length ; a++){
            images.add(proPic.getResourceId(a,R.mipmap.ic_launcher));
        }
        proPic.recycle();
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
//        gridLayoutHelper.setVGap(2);   // 控制子元素之间的垂直间距
//        gridLayoutHelper.setHGap(2);    // 控制子元素之间的水平间距
        gridLayoutHelper.setPadding(ArmsUtils.dip2px(activity, 16), 0,
                ArmsUtils.dip2px(activity, 16), 0);
        gridLayoutHelper.setBgColor(Color.WHITE);
        return new BaseDelegateAdapter(activity, gridLayoutHelper, R.layout.item_vlayout_grid3, 5, Constant.viewType.typeGv) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, @SuppressLint("RecyclerView") final int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_name, proName[position]);
                holder.setImageResource(R.id.iv_icon, images.get(position));
                holder.getView(R.id.ll_container).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mRootView.setOnGridClick(1, position);
                    }
                });
            }
        };
    }

    @Override
    public BaseDelegateAdapter initGvMenu2() {
        // 在构造函数设置每行的网格个数
        final TypedArray proPic = activity.getResources().obtainTypedArray(R.array.mine_gv_scores_images);
        final String[] proName = activity.getResources().getStringArray(R.array.mine_gv_scores_title);
        final List<Integer> images = new ArrayList<>();
        for(int a=0 ; a<proName.length ; a++){
            images.add(proPic.getResourceId(a,R.mipmap.ic_launcher));
        }
        proPic.recycle();
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
//        gridLayoutHelper.setVGap(2);   // 控制子元素之间的垂直间距
//        gridLayoutHelper.setHGap(2);    // 控制子元素之间的水平间距
        gridLayoutHelper.setPadding(ArmsUtils.dip2px(activity, 16), 0,
                ArmsUtils.dip2px(activity, 16), 0);
        gridLayoutHelper.setBgColor(Color.WHITE);
        return new BaseDelegateAdapter(activity, gridLayoutHelper, R.layout.item_vlayout_grid3, 3, Constant.viewType.typeGv) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, @SuppressLint("RecyclerView") final int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_name, proName[position]);
                holder.setImageResource(R.id.iv_icon, images.get(position));
                holder.getView(R.id.ll_container).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mRootView.setOnGridClick(2, position);
                    }
                });
            }
        };
    }

    @Override
    public BaseDelegateAdapter initGvMenu3() {
        // 在构造函数设置每行的网格个数
        final TypedArray proPic = activity.getResources().obtainTypedArray(R.array.mine_gv_assistant_images);
        final String[] proName = activity.getResources().getStringArray(R.array.mine_gv_assistant_title);
        final List<Integer> images = new ArrayList<>();
        for(int a=0 ; a<proName.length ; a++){
            images.add(proPic.getResourceId(a,R.mipmap.ic_launcher));
        }
        proPic.recycle();
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
//        gridLayoutHelper.setVGap(2);   // 控制子元素之间的垂直间距
//        gridLayoutHelper.setHGap(2);    // 控制子元素之间的水平间距
        gridLayoutHelper.setPadding(ArmsUtils.dip2px(activity, 16), 0,
                ArmsUtils.dip2px(activity, 16), 0);
        gridLayoutHelper.setBgColor(Color.WHITE);
        return new BaseDelegateAdapter(activity, gridLayoutHelper, R.layout.item_vlayout_grid3, 2, Constant.viewType.typeGv) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, @SuppressLint("RecyclerView") final int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_name, proName[position]);
                holder.setImageResource(R.id.iv_icon, images.get(position));
                holder.getView(R.id.ll_container).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mRootView.setOnGridClick(3, position);
                    }
                });
            }
        };
    }

    @Override
    public BaseDelegateAdapter initTitle(int arrayPos, String title, String btnDesc, int scores) {
        BaseDelegateAdapter adapter = null;
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        switch (arrayPos) {
            case 1:
            case 2:
            case 5:
                adapter = new BaseDelegateAdapter(activity, singleLayoutHelper , R.layout.item_vlayout_title2,
                        1, Constant.viewType.typeTitle) {
                    @Override
                    public void onBindViewHolder(BaseViewHolder holder, int position) {
                        super.onBindViewHolder(holder, position);
                        holder.setText(R.id.tv_title, title);
                        holder.setGone(R.id.tv_study_scores, false);
                        holder.setGone(R.id.iv_icon, false);
                        holder.setGone(R.id.tv_desc, false);
                    }
                };
                break;
            case 3:
                adapter = new BaseDelegateAdapter(activity, singleLayoutHelper , R.layout.item_vlayout_title2,
                        1, Constant.viewType.typeTitle) {
                    @Override
                    public void onBindViewHolder(BaseViewHolder holder, int position) {
                        super.onBindViewHolder(holder, position);
                        holder.setText(R.id.tv_title, title);
                        holder.setGone(R.id.tv_study_scores, false);
                        holder.setGone(R.id.iv_icon, false);
                        holder.setText(R.id.tv_desc, "银行卡管理 >>");
                        holder.setBackgroundColor(R.id.tv_desc, Color.WHITE);

                        holder.getView(R.id.tv_desc).setOnClickListener(view -> {

                        });
                    }
                };
                break;
            case 4:
                adapter = new BaseDelegateAdapter(activity, singleLayoutHelper , R.layout.item_vlayout_title2,
                        1, Constant.viewType.typeTitle) {
                    @Override
                    public void onBindViewHolder(BaseViewHolder holder, int position) {
                        super.onBindViewHolder(holder, position);
                        holder.setText(R.id.tv_title, title);
                        holder.setText(R.id.tv_study_scores, "" + scores);
                        holder.setGone(R.id.iv_icon, true);
                        holder.setText(R.id.tv_desc, "怎样获取积分?");
                        holder.setBackgroundRes(R.id.tv_desc, R.drawable.sp_bg_mine_btn);

                        holder.getView(R.id.tv_desc).setOnClickListener(view -> {

                        });
                    }
                };
                break;
        }

        return adapter;
    }

    @Override
    public BaseDelegateAdapter initHeader(String url, String name, String partyBranch) {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return new BaseDelegateAdapter(activity, singleLayoutHelper , R.layout.item_vlayout_header4,
                1, Constant.viewType.typeHeader) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_name, name);
                holder.setText(R.id.tv_party_branch, partyBranch);

                mImageLoader.loadImage(activity,
                        ImageConfigImpl
                                .builder()
                                .errorPic(R.mipmap.ic_error)
                                .placeholder(R.mipmap.ic_placeholder)
                                .url(url)
                                .imageView(holder.getView(R.id.iv_header))
                                .build());

                holder.getView(R.id.tv_settings).setOnClickListener(view -> {
                    // 设置

                });
                holder.getView(R.id.tv_sign).setOnClickListener(view -> {
                    // 天天签到

                });
            }
        };
    }

    @Override
    public BaseDelegateAdapter initSingle(float payment, int isPay) {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return new BaseDelegateAdapter(activity, singleLayoutHelper , R.layout.item_vlayout_mine,
                1, Constant.viewType.typeSingle) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_study_scores, isPay == 0 ? "--.--" : "" + payment);
                holder.setText(R.id.tv_is_pay, isPay == 0 ? "未交" : "已交");

                holder.getView(R.id.tv_query).setOnClickListener(view -> {
                    // 查看

                });
            }
        };
    }

    @Override
    public BaseDelegateAdapter initColumn1(int scores, int power, int rank) {
        ColumnLayoutHelper columnLayoutHelper = new ColumnLayoutHelper();
        return new BaseDelegateAdapter(activity, columnLayoutHelper , R.layout.item_vlayout_grid5,
                3, Constant.viewType.typeColumn) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

                switch (position) {
                    case 0:
                        holder.setText(R.id.tv_scores, scores + "");
                        holder.setText(R.id.tv_title, "累计积分");
                        holder.setGone(R.id.tv_desc, false);
                        holder.setGone(R.id.iv_desc, true);
                        holder.setImageResource(R.id.iv_desc, R.mipmap.ic_launcher);
                        break;
                    case 1:
                        holder.setText(R.id.tv_scores, power + "");
                        holder.setText(R.id.tv_title, "正能量值");
                        holder.setText(R.id.tv_desc, "完成任务，获取正能量值");
                        holder.setGone(R.id.tv_desc, true);
                        holder.setGone(R.id.iv_desc, false);
                        break;
                    case 2:
                        holder.setText(R.id.tv_scores, rank + "");
                        holder.setText(R.id.tv_title, "全市排名");
                        holder.setGone(R.id.tv_desc, false);
                        holder.setGone(R.id.iv_desc, true);
                        holder.setImageResource(R.id.iv_desc, R.mipmap.ic_launcher);
                        break;
                }
            }
        };
    }

    @Override
    public BaseDelegateAdapter initColumn2(int meetingTimes, int lessonTimes, int activeTimes) {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(3);
        gridLayoutHelper.setVGap(2);   // 控制子元素之间的垂直间距
        gridLayoutHelper.setHGap(ArmsUtils.dip2px(activity, 10));    // 控制子元素之间的水平间距
        gridLayoutHelper.setPadding(ArmsUtils.dip2px(activity, 16), ArmsUtils.dip2px(activity, 16),
                ArmsUtils.dip2px(activity, 16), ArmsUtils.dip2px(activity, 16));
        gridLayoutHelper.setBgColor(Color.WHITE);
        return new BaseDelegateAdapter(activity, gridLayoutHelper , R.layout.item_vlayout_grid4,
                3, Constant.viewType.typePlus) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

                switch (position) {
                    case 0:
                        holder.setText(R.id.tv_times, meetingTimes + "次 >>");
                        holder.setText(R.id.tv_title, "参会记录");
                        break;
                    case 1:
                        holder.setText(R.id.tv_times, lessonTimes + "次 >>");
                        holder.setText(R.id.tv_title, "听课记录");
                        break;
                    case 2:
                        holder.setText(R.id.tv_times, activeTimes + "次 >>");
                        holder.setText(R.id.tv_title, "活动记录");
                        break;
                }
            }
        };
    }
}
