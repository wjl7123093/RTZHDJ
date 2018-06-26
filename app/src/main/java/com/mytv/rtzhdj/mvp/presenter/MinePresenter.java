package com.mytv.rtzhdj.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.Constant;
import com.mytv.rtzhdj.app.SharepreferenceKey;
import com.mytv.rtzhdj.app.base.RTZHDJApplication;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.MineEntity;
import com.mytv.rtzhdj.mvp.contract.MineContract;
import com.mytv.rtzhdj.mvp.ui.activity.MainActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.BaseDelegateAdapter;
import com.mytv.rtzhdj.mvp.ui.adapter.MineGridAdapter;
import com.mytv.rtzhdj.mvp.ui.widget.MyGridView;
import com.zchu.rxcache.data.CacheResult;
import com.zchu.rxcache.stategy.CacheStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class MinePresenter extends BasePresenter<MineContract.Model, MineContract.View>
    implements MineContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private MainActivity activity;

    private String[] from = { "image", "title" };
    private int[] to = { R.id.iv_icon, R.id.tv_name };

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
        final String[] proPic = activity.getResources().getStringArray(R.array.mine_gv_party_branch_images);
        final String[] proName = activity.getResources().getStringArray(R.array.mine_gv_party_branch_title);

        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
        gridLayoutHelper.setVGap(2);   // 控制子元素之间的垂直间距
        gridLayoutHelper.setHGap(2);    // 控制子元素之间的水平间距
//        gridLayoutHelper.setPadding(ArmsUtils.dip2px(activity, 16), 0,
//                ArmsUtils.dip2px(activity, 16), 0);
//        gridLayoutHelper.setBgColor(Color.WHITE);
        return new BaseDelegateAdapter(activity, gridLayoutHelper, R.layout.item_vlayout_mine_grid, 4, Constant.viewType.typeGv) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, @SuppressLint("RecyclerView") final int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_name, proName[position]);
                holder.setText(R.id.ic_grid, proPic[position]);
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
    public BaseDelegateAdapter initHeader() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return new BaseDelegateAdapter(activity, singleLayoutHelper , R.layout.item_vlayout_mine_header,
                1, Constant.viewType.typeHeader) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_name,
                        DataHelper.getStringSF(activity, SharepreferenceKey.KEY_LOGIN_USER_NAME));
                holder.setText(R.id.tv_party_branch,
                        DataHelper.getStringSF(activity, SharepreferenceKey.KEY_PUBLISHMENT_SYSTEM_NAME));
                holder.setText(R.id.tv_study_scores,
                        DataHelper.getIntergerSF(activity, SharepreferenceKey.KEY_LOGIN_INTEGRAL) + "");
//                holder.setText(R.id.tv_power_num,
//                        DataHelper.getIntergerSF(activity, SharepreferenceKey.KEY_POSITIVE_ENERGY_VALUE) + "");
                holder.setText(R.id.tv_power_num,
                        DataHelper.getIntergerSF(activity, SharepreferenceKey.KEY_LOGIN_INTEGRAL) + "");
                holder.setText(R.id.tv_grade_rank,
                        DataHelper.getIntergerSF(activity, SharepreferenceKey.KEY_LOGIN_USER_RANK) + "");

                if (!TextUtils.isEmpty(DataHelper.getStringSF(activity, SharepreferenceKey.KEY_LOGIN_HEADER_URL)))
                    mImageLoader.loadImage(activity,
                            ImageConfigImpl
                                    .builder()
                                    .errorPic(R.mipmap.ic_error)
                                    .placeholder(R.mipmap.ic_placeholder)
                                    .url(DataHelper.getStringSF(activity, SharepreferenceKey.KEY_LOGIN_HEADER_URL))
                                    .imageView(holder.getView(R.id.iv_header))
                                    .build());

                /*holder.getView(R.id.tv_settings).setOnClickListener(view -> {
                    // 设置
                    mRootView.setOnSettingsClick();
                });*/
                holder.getView(R.id.ll_btn_sign).setOnClickListener(view -> {
                    // 天天签到
                    mRootView.setOnSignClick();
                });
                holder.getView(R.id.iv_header).setOnClickListener(view -> {
                    ARouter.getInstance().build(ARoutePath.PATH_SETTINGS).navigation();
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
    public BaseDelegateAdapter initColumn2(int meetingTimes, int lessonTimes, int activeTimes) {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(3);
        gridLayoutHelper.setVGap(2);   // 控制子元素之间的垂直间距
        gridLayoutHelper.setHGap(2);    // 控制子元素之间的水平间距
        return new BaseDelegateAdapter(activity, gridLayoutHelper , R.layout.item_vlayout_grid4,
                3, Constant.viewType.typePlus) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

                switch (position) {
                    case 0:
                        holder.setText(R.id.tv_times, meetingTimes + "");
                        holder.setText(R.id.tv_title, "参会记录");
                        break;
                    case 1:
                        holder.setText(R.id.tv_times, lessonTimes + "");
                        holder.setText(R.id.tv_title, "听课记录");
                        break;
                    case 2:
                        holder.setText(R.id.tv_times, activeTimes + "");
                        holder.setText(R.id.tv_title, "活动记录");
                        break;
                }
            }
        };
    }

    @Override
    public BaseDelegateAdapter initColumn(int itemPos, String title) {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return new BaseDelegateAdapter(activity, singleLayoutHelper , R.layout.item_vlayout_mine_column,
                1, Constant.viewType.typeTopHeader) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_title, title);

                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                MyGridView gridView = (MyGridView) holder.getView(R.id.gv_mine_column);
                switch (itemPos) {
                    case 1: // 我的党支部

                        gridView.setNumColumns(4);
                        // GridView。。。
                        // 在构造函数设置每行的网格个数
                        final String[] proPic1 = activity.getResources().getStringArray(R.array.mine_gv_party_branch_images);
                        final String[] proName1 = activity.getResources().getStringArray(R.array.mine_gv_party_branch_title);


                        Map<String, Object> map1 = null;
                        for (int i = 0; i < proPic1.length; i++) {
                            map1 = new HashMap<String, Object>();
                            map1.put("image", proPic1[i]);
                            map1.put("title", proName1[i]);
                            list.add(map1);
                        }

                        break;
                    case 2: // 组织生活

                        gridView.setNumColumns(3);
                        // GridView。。。
                        // 在构造函数设置每行的网格个数
//                        final String[] proPic2 = activity.getResources().getStringArray(R.array.mine_gv_organization_times);
                        final String[] proName2 = activity.getResources().getStringArray(R.array.mine_gv_organization_title);
                        List<String> proPic2 = new ArrayList<>();
                        proPic2.add(0, (DataHelper.getIntergerSF(activity, SharepreferenceKey.KEY_LOGIN_USER_PARTIN_TIMES) < 0
                                ? 0 : DataHelper.getIntergerSF(activity, SharepreferenceKey.KEY_LOGIN_USER_PARTIN_TIMES)) + "");
                        proPic2.add(1, (DataHelper.getIntergerSF(activity, SharepreferenceKey.KEY_LOGIN_USER_STUDY_TIMES) < 0
                                ? 0 : DataHelper.getIntergerSF(activity, SharepreferenceKey.KEY_LOGIN_USER_STUDY_TIMES)) + "");
                        proPic2.add(2, (DataHelper.getIntergerSF(activity, SharepreferenceKey.KEY_LOGIN_USER_ACTIVITY_TIMES) < 0
                                ? 0 : DataHelper.getIntergerSF(activity, SharepreferenceKey.KEY_LOGIN_USER_ACTIVITY_TIMES)) + "");

                        Map<String, Object> map2 = null;
                        for (int i = 0; i < proPic2.size(); i++) {
                            map2 = new HashMap<String, Object>();
                            map2.put("image", proPic2.get(i));
                            map2.put("title", proName2[i]);
                            list.add(map2);
                        }

                        break;

                }

                MineGridAdapter adapter = new MineGridAdapter(activity, list);
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        mRootView.setOnGridClick(itemPos, position);
                    }
                });
            }
        };
    }

    @Override
    public BaseDelegateAdapter initScores() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return new BaseDelegateAdapter(activity, singleLayoutHelper , R.layout.item_vlayout_mine_scores,
                1, Constant.viewType.typeScores) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_scores, DataHelper.getIntergerSF(activity, SharepreferenceKey.KEY_LOGIN_INTEGRAL) + "");

                holder.getView(R.id.ll_scores).setOnClickListener(view -> {
                    // 积分说明
                    ARouter.getInstance().build(ARoutePath.PATH_MY_TASK)
                            .withInt("Integeral", -100)
                            .withInt("PlanValue", -100)
                            .withInt("NextValue", -100).navigation();
                });

                holder.getView(R.id.ll_scores_detail).setOnClickListener(view -> {
                    // 积分明细
                    ARouter.getInstance().build(ARoutePath.PATH_SCORES_DETAILS).navigation();
                });
            }
        };
    }

    @Override
    public BaseDelegateAdapter initCash() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return new BaseDelegateAdapter(activity, singleLayoutHelper , R.layout.item_vlayout_mine_cash,
                1, Constant.viewType.typeCash) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

            }
        };
    }

    @Override
    public BaseDelegateAdapter initHelper() {

        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return new BaseDelegateAdapter(activity, singleLayoutHelper , R.layout.item_vlayout_mine_column,
                1, Constant.viewType.typeTopHeader) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_title, "生活助手");

                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                MyGridView gridView = (MyGridView) holder.getView(R.id.gv_mine_column);

                gridView.setNumColumns(3);
                // GridView。。。
                // 在构造函数设置每行的网格个数
                final TypedArray proPic3 = activity.getResources().obtainTypedArray(R.array.mine_gv_assistant_images);
                final String[] proName3 = activity.getResources().getStringArray(R.array.mine_gv_assistant_title);
                final List<Integer> images3 = new ArrayList<>();
                for(int a=0 ; a<proName3.length ; a++){
                    images3.add(proPic3.getResourceId(a,R.mipmap.ic_launcher));
                }
                proPic3.recycle();

                Map<String, Object> map3 = null;
                for (int i = 0; i < images3.size(); i++) {
                    map3 = new HashMap<String, Object>();
                    map3.put("image", images3.get(i));
                    map3.put("title", proName3[i]);
                    list.add(map3);
                }

                SimpleAdapter pictureAdapter = new SimpleAdapter(activity, list,
                        R.layout.item_vlayout_grid3, from, to);
                gridView.setAdapter(pictureAdapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        mRootView.setOnGridClick(3, position);
                    }
                });
            }
        };
    }

    @Override
    public void callMethodOfGetUserPartMessage(int userId, boolean update) {
        mModel.getUserPartMessage(userId, update)
                .compose(RTZHDJApplication.rxCache.<BaseJson<MineEntity>>transformObservable("getUserPartMessage" + userId,
                        new TypeToken<BaseJson<MineEntity>>() { }.getType(),
                        CacheStrategy.firstRemote()))
                .map(new CacheResult.MapFunc<BaseJson<MineEntity>>())
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
                .subscribe(new ErrorHandleSubscriber<BaseJson<MineEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<MineEntity> mineEntity) {
                        Log.e(TAG, mineEntity.toString());

                        if (mineEntity.isSuccess() && mineEntity.getData() != null)
                            mRootView.loadData(mineEntity.getData());

                    }
                });
    }
}
