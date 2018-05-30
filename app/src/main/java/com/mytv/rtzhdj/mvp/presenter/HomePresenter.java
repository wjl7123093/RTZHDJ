package com.mytv.rtzhdj.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.utils.TextUtils;
import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
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
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.utils.BannerImageLoader;
import com.mytv.rtzhdj.mvp.contract.HomeContract;
import com.mytv.rtzhdj.mvp.ui.activity.MainActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.BaseDelegateAdapter;
import com.sunfusheng.marqueeview.MarqueeView;
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

/**
 * 首页 P层
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-19
 * @update 2018-1-25    解决 “RecyclerView自动滚动” 的BUG（详见 https://www.cnblogs.com/xgjblog/p/8260061.html）
 *         2018-1-26    彻底解决 “RecyclerView自动滚动” 的BUG（在 1-25 基础上，新增顶层聚焦控件）
 *         2018-1-29    删除了无关引用
 *         2018-2-2     修改了 initHeader() 里的 recyclerview 的itemType，
 *                      保证了不同itemView 使用不同的 itemType，解决了 [滑动卡顿] BUG。
 */
@ActivityScope
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View>
        implements HomeContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private MainActivity activity;

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView
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
    public BaseDelegateAdapter initTopHeader() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(activity, linearLayoutHelper , R.layout.item_vlayout_home_header,
                1, Constant.viewType.typeTopHeader) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

            }
        };
    }

    @Override
    public BaseDelegateAdapter initBannerAdapter(List<HomeEntity.SpecialBlock> SpecialBlock,
                                                 List<HomeEntity.NoticeBlock> NoticeBlock_ChildContent) {
//        final List<Object> arrayList = new ArrayList<>();
//        arrayList.add("http://bpic.wotucdn.com/11/66/23/55bOOOPIC3c_1024.jpg!/fw/780/quality/90/unsharp/true/compress/true/watermark/url/L2xvZ28ud2F0ZXIudjIucG5n/repeat/true");
//        arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505470629546&di=194a9a92bfcb7754c5e4d19ff1515355&imgtype=0&src=http%3A%2F%2Fpics.jiancai.com%2Fimgextra%2Fimg01%2F656928666%2Fi1%2FT2_IffXdxaXXXXXXXX_%2521%2521656928666.jpg");

        final List<Object> arrayList1 = new ArrayList<>();
        for (int i = 0; i < SpecialBlock.size(); i++) {
//            arrayList1.add(Api.APP_IMAGE_DOMAIN + SpecialBlock.get(i).getImageUrl().replace("@", ""));
            arrayList1.add(SpecialBlock.get(i).getAllImgUrl());
        }
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        //banner
        return new BaseDelegateAdapter(activity, linearLayoutHelper, R.layout.item_vlayout_home_banner,
                1, Constant.viewType.typeBanner) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

                // Banner。。。
                // 绑定数据
                Banner mBanner = holder.getView(R.id.banner);
                //设置banner样式
                mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                //设置图片加载器
                mBanner.setImageLoader(new BannerImageLoader());
                //设置图片集合
                mBanner.setImages(arrayList1);
                //设置banner动画效果
                mBanner.setBannerAnimation(Transformer.DepthPage);
                //设置标题集合（当banner样式有显示title时）
                //        mBanner.setBannerTitles(titles);
                //设置自动轮播，默认为true
                mBanner.isAutoPlay(true);
                //设置轮播时间
                mBanner.setDelayTime(3000);
                //设置指示器位置（当banner模式中有指示器时）
                mBanner.setIndicatorGravity(BannerConfig.CENTER);

                //banner设置方法全部调用完毕时最后调用
                mBanner.start();
                mBanner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        ARouter.getInstance().build(ARoutePath.PATH_TOPIC_DETAIL)
                                .withInt("nodeId", SpecialBlock.get(position).getNodeId())
                                .navigation();
                    }
                });

//                mRootView.setBanner(mBanner);
                holder.getView(R.id.iv_topic).setOnClickListener(view -> {
                    mRootView.setOnTopicClick();
                });

                // 跑马灯。。。
                MarqueeView marqueeView1 = holder.getView(R.id.marqueeView1);
                MarqueeView marqueeView2 = holder.getView(R.id.marqueeView2);

                List<String> info1 = new ArrayList<>();
                List<String> info2 = new ArrayList<>();
                for (int i = 0; i < NoticeBlock_ChildContent.size(); i++) {
                    info1.add(NoticeBlock_ChildContent.get(i).getTitle());
                }
                for (int i = 0; i < NoticeBlock_ChildContent.size(); i++) {
                    info2.add(NoticeBlock_ChildContent.get(i).getAddDate());
                }
                if (info1.size() == 1) {    // size == 1时，marquee 组件会失效报null，所以再加一个。
                    info1.add(info1.get(0));
                    info2.add(info2.get(0));
                }
                if (info1.size() > 0) {
                    marqueeView1.startWithList(info1);
                    marqueeView2.startWithList(info2);
                    // 在代码里设置自己的动画
                    marqueeView1.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position, TextView textView) {
//                        mRootView.setMarqueeClick(position);

                            if (NoticeBlock_ChildContent.size() == 1) {
                                ARouter.getInstance().build(ARoutePath.PATH_NEWS_DETAIL)
                                        .withInt("nodeId", NoticeBlock_ChildContent.get(0).getNodeId())
                                        .withInt("articleId", NoticeBlock_ChildContent.get(0).getID())
                                        .withInt("digs", -100)
                                        .withInt("comments", -100)
                                        .navigation();
                            } else {
                                ARouter.getInstance().build(ARoutePath.PATH_NEWS_DETAIL)
                                        .withInt("nodeId", NoticeBlock_ChildContent.get(position).getNodeId())
                                        .withInt("articleId", NoticeBlock_ChildContent.get(position).getID())
                                        .withInt("digs", -100)
                                        .withInt("comments", -100)
                                        .navigation();
                            }
                        }
                    });
                }
            }
        };
    }

    @Override
    public BaseDelegateAdapter initGvMenu() {
        // 在构造函数设置每行的网格个数
        final String[] proPic = activity.getResources().getStringArray(R.array.find_gv_image);
        final String[] proName = activity.getResources().getStringArray(R.array.find_gv_title);

        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
//        gridLayoutHelper.setPadding(0, 16, 0, 16);
        gridLayoutHelper.setVGap(2);   // 控制子元素之间的垂直间距
        gridLayoutHelper.setHGap(2);    // 控制子元素之间的水平间距
//        gridLayoutHelper.setBgColor(Color.WHITE);
//        gridLayoutHelper.setMarginBottom(ArmsUtils.dip2px(activity, 12));
        return new BaseDelegateAdapter(activity, gridLayoutHelper, R.layout.item_vlayout_home_grid, 8, Constant.viewType.typeGv) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, @SuppressLint("RecyclerView") final int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_name, proName[position]);
                holder.setText(R.id.ic_grid, proPic[position]);
                holder.getView(R.id.ll_container).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mRootView.setGridClick(position);
                    }
                });
            }
        };
    }

    @Override
    public BaseDelegateAdapter initMarqueeView(List<HomeEntity.NoticeBlock> NoticeBlock_ChildContent) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(activity, linearLayoutHelper , R.layout.item_vlayout_marqueeview,
                1, Constant.viewType.typeMarquee) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                MarqueeView marqueeView1 = holder.getView(R.id.marqueeView1);
                MarqueeView marqueeView2 = holder.getView(R.id.marqueeView2);

                List<String> info1 = new ArrayList<>();
                List<String> info2 = new ArrayList<>();
                for (int i = 0; i < NoticeBlock_ChildContent.size(); i++) {
                    info1.add(NoticeBlock_ChildContent.get(i).getTitle());
                }
                for (int i = 0; i < NoticeBlock_ChildContent.size(); i++) {
                    info2.add(NoticeBlock_ChildContent.get(i).getAddDate());
                }
                if (info1.size() == 1) {    // size == 1时，marquee 组件会失效报null，所以再加一个。
                    info1.add(info1.get(0));
                    info2.add(info2.get(0));
                }
                if (info1.size() > 0) {
                    marqueeView1.startWithList(info1);
                    marqueeView2.startWithList(info2);
                    // 在代码里设置自己的动画
                    marqueeView1.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position, TextView textView) {
//                        mRootView.setMarqueeClick(position);

                            if (NoticeBlock_ChildContent.size() == 1) {
                                ARouter.getInstance().build(ARoutePath.PATH_NEWS_DETAIL)
                                        .withInt("nodeId", NoticeBlock_ChildContent.get(0).getNodeId())
                                        .navigation();
                            } else {
                                ARouter.getInstance().build(ARoutePath.PATH_NEWS_DETAIL)
                                        .withInt("nodeId", NoticeBlock_ChildContent.get(position).getNodeId())
                                        .navigation();
                            }
                        }
                    });
                }
            }
        };
    }

    @Override
    public BaseDelegateAdapter initTitle(String title) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(activity, linearLayoutHelper , R.layout.item_vlayout_title,
                1, Constant.viewType.typeTitle) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_title, title);

                holder.getView(R.id.tv_moredata).setOnClickListener(view -> {
                    mRootView.setOnclick();
                });

            }
        };
    }

    @Override
    public BaseDelegateAdapter initList(List<HomeEntity.FocusNewsBlock> FocusNewsBlock_ChildContent) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setDividerHeight(ArmsUtils.dip2px(activity, 1));
        return new BaseDelegateAdapter(activity, linearLayoutHelper , R.layout.item_vlayout_list_image,
                FocusNewsBlock_ChildContent.size(), Constant.viewType.typeList) {

            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position, List<Object> payloads) {
//                super.onBindViewHolder(holder, position, payloads);

                if (payloads.isEmpty()) {
                    onBindViewHolder(holder, position);
                } else {

                    // 局部刷新（至刷新列表项数据，不刷新图片）
//                    mRootView.showMessage("SSSSSSS");
                    holder.setText(R.id.tv_comment_num, FocusNewsBlock_ChildContent.get(position).getComments() + "");
                    holder.setText(R.id.tv_star_num, FocusNewsBlock_ChildContent.get(position).getDigs() + "");

                    holder.getView(R.id.rl_container).setOnClickListener(view -> {
                        // 新闻详情页
                        mRootView.setNewsListClick(FocusNewsBlock_ChildContent.get(position), position);
                    });
                }
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_title, FocusNewsBlock_ChildContent.get(position).getTitle());
                holder.setText(R.id.tv_datetime, FocusNewsBlock_ChildContent.get(position).getAddDate());
                holder.setText(R.id.tv_comment_num, FocusNewsBlock_ChildContent.get(position).getComments() + "");
                holder.setText(R.id.tv_star_num, FocusNewsBlock_ChildContent.get(position).getDigs() + "");

                if (!TextUtils.isEmpty(FocusNewsBlock_ChildContent.get(position).getImageUrl())) {
                    mImageLoader.loadImage(getContext(),
                            ImageConfigImpl
                                    .builder()
                                    .errorPic(R.mipmap.ic_error)
                                    .placeholder(R.mipmap.ic_placeholder)
                                    .url(FocusNewsBlock_ChildContent.get(position).getAllImgUrl())
                                    .imageView(holder.getView(R.id.iv_image))
                                    .build());
                    holder.getView(R.id.iv_image).setVisibility(View.VISIBLE);
                } else {
                    holder.getView(R.id.iv_image).setVisibility(View.GONE);
                }

                holder.getView(R.id.rl_container).setOnClickListener(view -> {
                    // 新闻详情页
                    /*ARouter.getInstance().build(ARoutePath.PATH_NEWS_DETAIL)
                            .withInt("articleId", FocusNewsBlock_ChildContent.get(position).getID())
                            .withInt("nodeId", FocusNewsBlock_ChildContent.get(position).getNodeId())
                            .withInt("digs", FocusNewsBlock_ChildContent.get(position).getDigs())
                            .withInt("comments", FocusNewsBlock_ChildContent.get(position).getComments())
                            .navigation();*/

                    mRootView.setNewsListClick(FocusNewsBlock_ChildContent.get(position), position);
                });

            }
        };
    }

    @Override
    public BaseDelegateAdapter initMoreData(String moreStr) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(activity, linearLayoutHelper , R.layout.item_vlayout_moredata,
                1, Constant.viewType.typeFooter) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.btn_moredata, moreStr);

                holder.getView(R.id.btn_moredata).setOnClickListener(view -> {
                    if (moreStr.equals("更多要闻")) {
                        mRootView.setOnclick();
                    } else if (moreStr.equals("更多公益活动")) {
                        ARouter.getInstance().build(ARoutePath.PATH_VOLUNTEER_SERVICE).navigation();
                    }
                });

            }
        };
    }

    @Override
    public BaseDelegateAdapter initImage(List<HomeEntity.AdBlock> AdBlock) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(activity, linearLayoutHelper , R.layout.item_vlayout_image2,
                1, Constant.viewType.typeImage) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                mImageLoader.loadImage(getContext(),
                        ImageConfigImpl
                                .builder()
                                .errorPic(R.mipmap.ic_error)
                                .placeholder(R.mipmap.ic_placeholder)
                                .url(AdBlock.get(0).getAllImgUrl())
                                .imageView(holder.getView(R.id.iv_image))
                                .build());

                holder.getView(R.id.iv_image).setOnClickListener(view -> {
                    /*ARouter.getInstance().build(ARoutePath.PATH_NEWS_DETAIL)
                            .withInt("nodeId", AdBlock.get(position).getNodeId()).navigation();*/

                    mRootView.setImageClick(AdBlock.get(position));
                });

            }
        };
    }

    @Override
    public BaseDelegateAdapter initHeader(String title, String desc) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(activity, linearLayoutHelper , R.layout.item_vlayout_header,
                1, Constant.viewType.typeHeader) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_title, title);
                holder.setText(R.id.tv_desc, desc);
            }
        };
    }

    @Override
    public BaseDelegateAdapter initOnePlusN(List<List<HomeEntity.PublicSpiritedBlock>> PublicSpiritedBlock_ChildContent,
                                            int myPositiveValue) {
//        final List<Object> arrayList = new ArrayList<>();
//        arrayList.add("http://bpic.wotucdn.com/11/66/23/55bOOOPIC3c_1024.jpg!/fw/780/quality/90/unsharp/true/compress/true/watermark/url/L2xvZ28ud2F0ZXIudjIucG5n/repeat/true");
//        arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505470629546&di=194a9a92bfcb7754c5e4d19ff1515355&imgtype=0&src=http%3A%2F%2Fpics.jiancai.com%2Fimgextra%2Fimg01%2F656928666%2Fi1%2FT2_IffXdxaXXXXXXXX_%2521%2521656928666.jpg");
        final List<Object> arrayList1 = new ArrayList<>();
        final List<Object> arrayList2 = new ArrayList<>();
        if (PublicSpiritedBlock_ChildContent.size()>1) {
            if (PublicSpiritedBlock_ChildContent.get(0).size() > 0) {
                for (int i = 0; i < PublicSpiritedBlock_ChildContent.get(0).size(); i++) {
                    arrayList1.add(PublicSpiritedBlock_ChildContent.get(0).get(i).getAllImgUrl());
                }
            }

            if (PublicSpiritedBlock_ChildContent.get(1).size() > 0) {
                for (int i = 0; i < PublicSpiritedBlock_ChildContent.get(1).size(); i++) {
                    arrayList2.add(PublicSpiritedBlock_ChildContent.get(1).get(i).getAllImgUrl());
                }
            }
        }
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
//        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return new BaseDelegateAdapter(activity, linearLayoutHelper , R.layout.item_vlayout_oneplusn1,
                1, Constant.viewType.typePlus) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                View viewFocus = holder.getView(R.id.view_focus);   // 顶层聚焦控件，防止 recyclerview 下滚
                TextView tvPowerNum = holder.getView(R.id.tv_power_num);
                View includeBanner1 = holder.getView(R.id.include_banner1);
                View includeBanner2 = holder.getView(R.id.include_banner2);

                TextView tvJoinNum1 = includeBanner1.findViewById(R.id.tv_join_num);
                TextView tvTitle1 = includeBanner1.findViewById(R.id.tv_title);
                TextView tvStartNum1 = includeBanner1.findViewById(R.id.tv_star_num);
                Banner banner1 = includeBanner1.findViewById(R.id.banner);

                TextView tvJoinNum2 = includeBanner2.findViewById(R.id.tv_join_num);
                TextView tvTitle2 = includeBanner2.findViewById(R.id.tv_title);
                TextView tvStartNum2 = includeBanner2.findViewById(R.id.tv_star_num);
                Banner banner2 = includeBanner2.findViewById(R.id.banner);

                tvPowerNum.setText(myPositiveValue+"");

                /**
                 * 解决 “RecyclerView自动滚动” 的BUG
                 * Step 1:
                 *
                 * 如果没有 以下四句（让 banner1 和 banner2 所在视图获取焦点），
                 * 则在 recyclerview 滑动到 banner1，banner2 的视图时，recyclerview 会自动不停的往下滚动
                 */
                includeBanner1.setFocusableInTouchMode(true);
                includeBanner1.requestFocus();
                includeBanner2.setFocusableInTouchMode(true);
                includeBanner2.requestFocus();

//                initBannerParams(banner1, arrayList);
//                initBannerParams(banner2, arrayList);

                if (arrayList1.size() > 0) {
                    // 初始化默认显示第一页数据
                    tvJoinNum1.setText(PublicSpiritedBlock_ChildContent.get(0).get(0).getEnrollCount() + "");
                    tvTitle1.setText(PublicSpiritedBlock_ChildContent.get(0).get(0).getTitle());
                    tvStartNum1.setText(PublicSpiritedBlock_ChildContent.get(0).get(0).getDigs() + "");

                    initBannerParams(banner1, arrayList1);
                    banner1.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            /**
                             * 解决 “RecyclerView自动滚动” 的BUG
                             * Step 2:
                             *
                             * 在 banner 滚动时，不停的聚焦该控件顶层控件焦点，以防止 recyclerview 自动下滚
                             */
                            viewFocus.setFocusableInTouchMode(true);
                            viewFocus.requestFocus();

//                        if (0 == position)
//                            tvTitle1.setText("Banner111   哈哈哈");
//                        else
//                            tvTitle1.setText("Banner111   嘿嘿嘿");

                            tvJoinNum1.setText(PublicSpiritedBlock_ChildContent.get(0).get(position).getEnrollCount() + "");
                            tvTitle1.setText(PublicSpiritedBlock_ChildContent.get(0).get(position).getTitle());
                            tvStartNum1.setText(PublicSpiritedBlock_ChildContent.get(0).get(position).getDigs() + "");
//                        mImageLoader.loadImage(getContext(),
//                                ImageConfigImpl
//                                        .builder()
//                                        .errorPic(R.mipmap.ic_error)
//                                        .placeholder(R.mipmap.ic_placeholder)
//                                        .url(Api.APP_IMAGE_DOMAIN +
//                                                PublicSpiritedBlock_ChildContent.get(position).getTitleImageUrl().replace("@", ""))
//                                        .imageView(holder.getView(R.id.iv_image))
//                                        .build());
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                    banner1.setOnBannerListener(new OnBannerListener() {
                        @Override
                        public void OnBannerClick(int position) {
                            ARouter.getInstance().build(ARoutePath.PATH_VOLUNTEER_SERVICE_DETAIL)
                                    .withInt("nodeId", PublicSpiritedBlock_ChildContent.get(0).get(position).getNodeId())
                                    .withInt("id", PublicSpiritedBlock_ChildContent.get(0).get(position).getArticleId())
                                    .withString("imageUrl", PublicSpiritedBlock_ChildContent.get(0).get(position).getAllImgUrl())
                                    .navigation();
                        }
                    });
                }

                if (arrayList2.size() > 0) {
                    // 初始化默认显示第一页数据
                    tvJoinNum2.setText(PublicSpiritedBlock_ChildContent.get(1).get(0).getEnrollCount() + "");
                    tvTitle2.setText(PublicSpiritedBlock_ChildContent.get(1).get(0).getTitle());
                    tvStartNum2.setText(PublicSpiritedBlock_ChildContent.get(1).get(0).getDigs() + "");

                    initBannerParams(banner2, arrayList2);
                    banner2.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            /**
                             * 解决 “RecyclerView自动滚动” 的BUG
                             * Step 2:
                             *
                             * 在 banner 滚动时，不停的聚焦该控件顶层控件焦点，以防止 recyclerview 自动下滚
                             */
                            viewFocus.setFocusableInTouchMode(true);
                            viewFocus.requestFocus();

//                        if (0 == position)
//                            tvTitle2.setText("Banner222   哈哈哈");
//                        else
//                            tvTitle2.setText("Banner222   嘿嘿嘿");

                            tvJoinNum2.setText(PublicSpiritedBlock_ChildContent.get(1).get(position).getEnrollCount() + "");
                            tvTitle2.setText(PublicSpiritedBlock_ChildContent.get(1).get(position).getTitle());
                            tvStartNum2.setText(PublicSpiritedBlock_ChildContent.get(1).get(position).getDigs() + "");
//                        mImageLoader.loadImage(getContext(),
//                                ImageConfigImpl
//                                        .builder()
//                                        .errorPic(R.mipmap.ic_error)
//                                        .placeholder(R.mipmap.ic_placeholder)
//                                        .url(Api.APP_IMAGE_DOMAIN +
//                                                PublicSpiritedBlock_ChildContent.get(position).getTitleImageUrl().replace("@", ""))
//                                        .imageView(holder.getView(R.id.iv_image))
//                                        .build());
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                    banner2.setOnBannerListener(new OnBannerListener() {
                        @Override
                        public void OnBannerClick(int position) {
                            ARouter.getInstance().build(ARoutePath.PATH_VOLUNTEER_SERVICE_DETAIL)
                                    .withInt("nodeId", PublicSpiritedBlock_ChildContent.get(1).get(position).getNodeId())
                                    .withInt("id", PublicSpiritedBlock_ChildContent.get(1).get(position).getArticleId())
                                    .withString("imageUrl", PublicSpiritedBlock_ChildContent.get(1).get(position).getAllImgUrl())
                                    .navigation();
                        }
                    });
                }
            }
        };
    }

    @Override
    public void callMethodOfGetHomeData(boolean update) {
        mModel.getHomeData(update)
                .compose(RTZHDJApplication.rxCache.<BaseJson<HomeEntity>>transformObservable("getHomeData",
                        new TypeToken<BaseJson<HomeEntity>>() { }.getType(),
                        CacheStrategy.firstRemote()))
                .map(new CacheResult.MapFunc<BaseJson<HomeEntity>>())
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
                .subscribe(new ErrorHandleSubscriber<BaseJson<HomeEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<HomeEntity> homeData) {
                        Log.e("TAG", homeData.toString());

                        if (homeData.isSuccess() && homeData.getData() != null)
                            mRootView.showData(homeData.getData(), update);
                    }
                });
    }

    /**
     * 初始化 banner 参数
     * @param mBanner
     * @param arrayList
     */
    private void initBannerParams(Banner mBanner, List<Object> arrayList) {
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader(new BannerImageLoader());
        //设置图片集合
        mBanner.setImages(arrayList);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
        //        mBanner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(3000);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();

    }

    private Context getContext() {
        return mAppManager.getTopActivity() == null ?
                mApplication : mAppManager.getTopActivity();
    }
}
