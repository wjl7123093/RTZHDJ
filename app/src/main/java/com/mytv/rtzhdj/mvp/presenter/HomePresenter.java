package com.mytv.rtzhdj.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.media.Image;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.mytv.rtzhdj.app.utils.BannerImageLoader;
import com.mytv.rtzhdj.mvp.contract.HomeContract;
import com.mytv.rtzhdj.mvp.ui.activity.MainActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.BaseDelegateAdapter;
import com.squareup.picasso.Picasso;
import com.sunfusheng.marqueeview.MarqueeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import net.qiujuer.genius.ui.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页 P层
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-19
 * @update 2018-1-25    解决 “RecyclerView自动滚动” 的BUG（详见 https://www.cnblogs.com/xgjblog/p/8260061.html）
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
    public BaseDelegateAdapter initBannerAdapter() {
        final List<Object> arrayList = new ArrayList<>();
        arrayList.add("http://bpic.wotucdn.com/11/66/23/55bOOOPIC3c_1024.jpg!/fw/780/quality/90/unsharp/true/compress/true/watermark/url/L2xvZ28ud2F0ZXIudjIucG5n/repeat/true");
        arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505470629546&di=194a9a92bfcb7754c5e4d19ff1515355&imgtype=0&src=http%3A%2F%2Fpics.jiancai.com%2Fimgextra%2Fimg01%2F656928666%2Fi1%2FT2_IffXdxaXXXXXXXX_%2521%2521656928666.jpg");
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
//        linearLayoutHelper.setMarginBottom(ArmsUtils.dip2px(activity, 12));
        //banner
        return new BaseDelegateAdapter(activity, linearLayoutHelper, R.layout.item_vlayout_banner,
                1, Constant.viewType.typeBanner) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                // 绑定数据
                Banner mBanner = holder.getView(R.id.banner);
                //设置banner样式
                mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                //设置图片加载器
                mBanner.setImageLoader(new BannerImageLoader());
                //设置图片集合
                mBanner.setImages(arrayList);
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

//                mBanner.setOnBannerListener(new OnBannerListener() {
//                    @Override
//                    public void OnBannerClick(int position) {
//                        Toast.makeText(get, "banner点击了" + position, Toast.LENGTH_SHORT).show();
//                    }
//                });


                mRootView.setBanner(mBanner);
            }
        };
    }

    @Override
    public BaseDelegateAdapter initGvMenu() {
        // 在构造函数设置每行的网格个数
        final TypedArray proPic = activity.getResources().obtainTypedArray(R.array.find_gv_image);
        final String[] proName = activity.getResources().getStringArray(R.array.find_gv_title);
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
        return new BaseDelegateAdapter(activity, gridLayoutHelper, R.layout.item_vlayout_grid, 8, Constant.viewType.typeGv) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, @SuppressLint("RecyclerView") final int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_name, proName[position]);
                holder.setImageResource(R.id.iv_icon, images.get(position));
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
    public BaseDelegateAdapter initMarqueeView() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(activity, linearLayoutHelper , R.layout.item_vlayout_marqueeview,
                1, Constant.viewType.typeMarquee) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                MarqueeView marqueeView = holder.getView(R.id.marqueeView1);

                List<String> info1 = new ArrayList<>();
                info1.add("1.坚持读书，写作，源于内心的动力！");
                info1.add("2.欢迎订阅喜马拉雅听书！");
                marqueeView.startWithList(info1);
                // 在代码里设置自己的动画
                marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, TextView textView) {
                        mRootView.setMarqueeClick(position);
                    }
                });
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
                TextView tvTitle = holder.getView(R.id.tv_title);
                TextView tvMoredata = holder.getView(R.id.tv_moredata);
                tvTitle.setText(title);

                tvMoredata.setOnClickListener(view -> {
                    mRootView.setOnclick();
                });

            }
        };
    }

    @Override
    public BaseDelegateAdapter initList() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setDividerHeight(ArmsUtils.dip2px(activity, 1));
        return new BaseDelegateAdapter(activity, linearLayoutHelper , R.layout.item_vlayout_list_image,
                10, Constant.viewType.typeList) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);


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
                Button btnMore = holder.getView(R.id.btn_moredata);
                btnMore.setText(moreStr);

                btnMore.setOnClickListener(view -> {
                    mRootView.setOnclick();
                });

            }
        };
    }

    @Override
    public BaseDelegateAdapter initImage(String url) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(activity, linearLayoutHelper , R.layout.item_vlayout_image2,
                1, Constant.viewType.typeImage) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ImageView imageView = holder.getView(R.id.iv_image);
//                com.mytv.rtzhdj.app.utils.ImageLoader.getInstance().showImage(imageView, url);
//                mRootView.showImage(imageView, url);

//                mImageLoader.loadImage(getContext(),
//                        ImageConfigImpl
//                                .builder()
//                                .url(url)
//                                .imageView(imageView)
//                                .build());

//                Picasso.with(activity).load(url).into(imageView);
                com.mytv.rtzhdj.app.utils.ImageLoader.getInstance().showImage(activity, imageView, url);

                imageView.setOnClickListener(view -> {
                    mRootView.setOnclick();
                });

            }
        };
    }

    @Override
    public BaseDelegateAdapter initHeader(String title, String desc) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return new BaseDelegateAdapter(activity, linearLayoutHelper , R.layout.item_vlayout_header,
                1, Constant.viewType.typeTitle) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                TextView tvTitle = holder.getView(R.id.tv_title);
                TextView tvDesc = holder.getView(R.id.tv_desc);
                tvTitle.setText(title);
                tvDesc.setText(desc);

            }
        };
    }

    @Override
    public BaseDelegateAdapter initOnePlusN() {
        final List<Object> arrayList = new ArrayList<>();
        arrayList.add("http://bpic.wotucdn.com/11/66/23/55bOOOPIC3c_1024.jpg!/fw/780/quality/90/unsharp/true/compress/true/watermark/url/L2xvZ28ud2F0ZXIudjIucG5n/repeat/true");
        arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505470629546&di=194a9a92bfcb7754c5e4d19ff1515355&imgtype=0&src=http%3A%2F%2Fpics.jiancai.com%2Fimgextra%2Fimg01%2F656928666%2Fi1%2FT2_IffXdxaXXXXXXXX_%2521%2521656928666.jpg");
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
//        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return new BaseDelegateAdapter(activity, linearLayoutHelper , R.layout.item_vlayout_oneplusn1,
                1, Constant.viewType.typePlus) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
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

                /**
                 * 以下四句代码是为了解决 “RecyclerView自动滚动” 的BUG
                 *
                 * 如果没有 以下四句（让 banner1 和 banner2 所在视图获取焦点），
                 * 则在 recyclerview 滑动到 banner1，banner2 的视图时，recyclerview 会自动不停的往下滚动
                 */
                includeBanner1.setFocusableInTouchMode(true);
                includeBanner1.requestFocus();
                includeBanner2.setFocusableInTouchMode(true);
                includeBanner2.requestFocus();

                initBannerParams(banner1, arrayList);
                initBannerParams(banner2, arrayList);

                banner1.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        if (0 == position)
                            tvTitle1.setText("Banner111   哈哈哈");
                        else
                            tvTitle1.setText("Banner111   嘿嘿嘿");
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                banner2.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        if (0 == position)
                            tvTitle2.setText("Banner222   哈哈哈");
                        else
                            tvTitle2.setText("Banner222   嘿嘿嘿");
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

            }
        };
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
