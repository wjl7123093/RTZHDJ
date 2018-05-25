package com.mytv.rtzhdj.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.vlayout.DelegateAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;
import com.mytv.rtzhdj.di.component.DaggerHomeComponent;
import com.mytv.rtzhdj.di.module.HomeModule;
import com.mytv.rtzhdj.mvp.contract.HomeContract;
import com.mytv.rtzhdj.mvp.presenter.HomePresenter;
import com.mytv.rtzhdj.mvp.ui.activity.MainActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.BaseDelegateAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * HomeFragment 首页 Fragment
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-19
 * @update 2018-4-20    对接 getHomeInfo 接口
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {

    private final static int PAGE_SIZE = 10;    // 每页个数

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    /** 存放各个模块的适配器*/
    private List<DelegateAdapter.Adapter> mAdapters;


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mAdapters = new LinkedList<>();
        mPresenter.setActivity((MainActivity)getActivity());
//        initRecyclerView();
        initRefreshLayout();

        // 获取首页数据
        mPresenter.callMethodOfGetHomeData(false);
    }

    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传Message,通过what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事
     * <p>
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onCreate还没执行
     * setData里却调用了presenter的方法时,是会报空的,因为dagger注入是在onCreated方法中执行的,然后才创建的presenter
     * 如果要做一些初始化操作,可以不必让外部调setData,在initData中初始化就可以了
     *
     * @param data
     */

    @Override
    public void setData(Object data) {

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void setBanner(Banner banner) {
        banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    ARouter.getInstance().build(ARoutePath.PATH_TOPIC_DETAIL).navigation();
                }
            });
    }

    @Override
    public void setOnTopicClick() {
        ARouter.getInstance().build(ARoutePath.PATH_TOPIC).navigation();
    }

    @Override
    public void setOnclick() {
        // 更多要闻 点击
        ((MainActivity) getActivity()).doClick(1, null);
    }

    @Override
    public void setMarqueeClick(int position) {
        ArmsUtils.snackbarText("MarqueeClick点击了" + position);
    }

    @Override
    public void setGridClick(int position) {
        ArmsUtils.snackbarText("Grid点击了" + position);
        switch (position) {
            case 0: // 时政新闻
//                ARouter.getInstance().build(ARoutePath.PATH_NEWS).navigation();
                ARouter.getInstance().build(ARoutePath.PATH_NEWS_SIMPLE)
                        .withInt("nodeId", 1002)
                        .withString("title", getResources().getString(R.string.title_news)).navigation();
                break;
            case 1: // 党员教育
                ARouter.getInstance().build(ARoutePath.PATH_NEWS_EDUCATION).navigation();
                break;
            case 2: // 党风廉政
//                ARouter.getInstance().build(ARoutePath.PATH_NEWS_CLEAN).navigation();
                ARouter.getInstance().build(ARoutePath.PATH_NEWS_SIMPLE)
                        .withInt("nodeId", 3005)
                        .withString("title", getResources().getString(R.string.title_news_clean)).navigation();
                break;
            case 3: // 脱贫攻坚
//                ARouter.getInstance().build(ARoutePath.PATH_NEWS_POVERTY).navigation();
                ARouter.getInstance().build(ARoutePath.PATH_NEWS_SIMPLE)
                        .withInt("nodeId", 3006)
                        .withString("title", getResources().getString(R.string.title_news_poverty)).navigation();
                break;
            case 4: // 党建直播
                ARouter.getInstance().build(ARoutePath.PATH_NEWS_VIDEO_DETAIL).navigation();
                break;
            case 5: // 党建地图
                break;
            case 6:
                break;
            case 7:
                break;
        }
    }

    @Override
    public void setNewsListClick(int position, String url) {

    }

    @Override
    public void setImageClick() {

    }

    @Override
    public void setOnePlusNClick() {

    }

    @Override
    public void showImage(ImageView iv, String url) {
        ImageLoader.getInstance().showImage(getContext(), iv, url);
    }

    @Override
    public void showData(BaseJson<HomeEntity> homeData) {
        initRecyclerView(homeData);
    }

    private void initRecyclerView(BaseJson<HomeEntity> homeData) {
        if (homeData.getData() == null)
            return;

        List<HomeEntity.SpecialBlock> SpecialBlock = homeData.getData().getSpecialBlock();
        List<HomeEntity.NoticeBlock> NoticeBlock_ChildContent = homeData.getData().getNoticeBlock_ChildContent();
        List<HomeEntity.FocusNewsBlock> FocusNewsBlock_ChildContent = homeData.getData().getFocusNewsBlock_ChildContent();
        List<HomeEntity.AdBlock> AdBlock = homeData.getData().getAdBlock();
        List<List<HomeEntity.PublicSpiritedBlock>> PublicSpiritedBlock_ChildContent = homeData.getData().getPublicSpiritedBlock_ChildContent();
        int myPositiveValue = homeData.getData().getMyPositiveValue();


        DelegateAdapter delegateAdapter = mPresenter.initRecyclerView(mRecyclerView);
        //把轮播器添加到集合
        BaseDelegateAdapter bannerAdapter = mPresenter.initBannerAdapter(SpecialBlock, NoticeBlock_ChildContent);
        mAdapters.add(bannerAdapter);

        /*//初始化跑马灯
        BaseDelegateAdapter marqueeAdapter = mPresenter.initMarqueeView(NoticeBlock_ChildContent);
        mAdapters.add(marqueeAdapter);*/

        //初始化九宫格
        BaseDelegateAdapter menuAdapter = mPresenter.initGvMenu();
        mAdapters.add(menuAdapter);


        //初始化标题
        BaseDelegateAdapter titleAdapter = mPresenter.initTitle("要闻");
        mAdapters.add(titleAdapter);
        //初始化list3
        BaseDelegateAdapter listAdapter = mPresenter.initList(FocusNewsBlock_ChildContent);
        mAdapters.add(listAdapter);
        //初始化脚部
        BaseDelegateAdapter footerAdapter = mPresenter.initMoreData("更多要闻");
        mAdapters.add(footerAdapter);

        //初始化图片
        BaseDelegateAdapter imageAdapter = mPresenter.initImage(AdBlock);
        mAdapters.add(imageAdapter);

        //初始化头部
        BaseDelegateAdapter headerAdapter = mPresenter.initHeader("推荐活动", "公益，让爱和美丽在你我之间传递");
        mAdapters.add(headerAdapter);

        //初始化 1PlusN
        BaseDelegateAdapter oneplusnAdapter = mPresenter.initOnePlusN(PublicSpiritedBlock_ChildContent, myPositiveValue);
        mAdapters.add(oneplusnAdapter);

        footerAdapter = mPresenter.initMoreData("更多公益活动");
        mAdapters.add(footerAdapter);

        //设置适配器
        delegateAdapter.setAdapters(mAdapters);
    }

    private void initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        mRefreshLayout.setEnableLoadmore(false);
//        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
//            }
//        });
    }

}
