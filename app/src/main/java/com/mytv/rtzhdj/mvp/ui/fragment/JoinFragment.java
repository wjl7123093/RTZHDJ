package com.mytv.rtzhdj.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.vlayout.DelegateAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.SharepreferenceKey;
import com.mytv.rtzhdj.app.data.entity.MyJoinEntity;
import com.mytv.rtzhdj.di.component.DaggerJoinComponent;
import com.mytv.rtzhdj.di.module.JoinModule;
import com.mytv.rtzhdj.mvp.contract.JoinContract;
import com.mytv.rtzhdj.mvp.presenter.JoinPresenter;
import com.mytv.rtzhdj.mvp.ui.activity.MainActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.BaseDelegateAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * JoinFragment 我要参与 Fragment
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-19
 * @update 2018-2-2     填充布局
 */
public class JoinFragment extends BaseFragment<JoinPresenter> implements JoinContract.View {

    private final static int PAGE_SIZE = 10;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    /** 存放各个模块的适配器*/
    private List<DelegateAdapter.Adapter> mAdapters;


    public static JoinFragment newInstance() {
        JoinFragment fragment = new JoinFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerJoinComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .joinModule(new JoinModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_join, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mAdapters = new LinkedList<>();
        mPresenter.setActivity((MainActivity)getActivity());
//        initRecyclerView();
        initRefreshLayout();

        // 获取 我要参与数据
        mPresenter.callMethodOfGetMyPartIn(DataHelper.getIntergerSF(getActivity(),
                SharepreferenceKey.KEY_USER_ID), 1, PAGE_SIZE, false);
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
    public void setOnGridClick(int position) {
        switch (position) {
            case 0: // 组织活动
                ARouter.getInstance().build(ARoutePath.PATH_ORGANIZATIONAL).navigation();
                break;
            case 1: // 志愿服务
                ARouter.getInstance().build(ARoutePath.PATH_VOLUNTEER_SERVICE).navigation();
                break;
            case 2: // 报道社区
                break;
            case 3: // 周边社区
                ARouter.getInstance().build(ARoutePath.PATH_SURROUNDING_COMMUNITY).navigation();
                break;
        }
    }

    @Override
    public void setOnListClick(int arrayPos, int position) {

    }

    @Override
    public void setOnFooterClick() {
        ARouter.getInstance().build(ARoutePath.PATH_VOLUNTEER_SERVICE).navigation();
    }

    @Override
    public void setOnColumnClick(int arrayPos, int position) {
        switch (arrayPos) {
            case 0: // 心愿墙区
                switch (position) {
                    case 0: // 心愿墙
                        ARouter.getInstance().build(ARoutePath.PATH_WISH_WALL)
                                .withString("type", "wall").navigation();
                        break;
                    case 1: // 我要捐赠
                        ARouter.getInstance().build(ARoutePath.PATH_DONATE).navigation();
                        break;
                    case 2: // 所有捐赠物品
                        ARouter.getInstance().build(ARoutePath.PATH_MY_DONATION)
                                .withString("type", "all").navigation();
                        break;
                }
                break;
            case 1: // 我的心愿区
                switch (position) {
                    case 0: // 我的心愿
                        ARouter.getInstance().build(ARoutePath.PATH_WISH_WALL)
                                .withString("type", "mine").navigation();
                        break;
                    case 1: // 我认领的心愿
                        ARouter.getInstance().build(ARoutePath.PATH_MY_RECEIVE_WISH).navigation();
                        break;
                    case 2: // 我的捐赠
                        ARouter.getInstance().build(ARoutePath.PATH_MY_DONATION)
                                .withString("type", "mine").navigation();
                        break;
                    case 3: // 我领取的物品
                        ARouter.getInstance().build(ARoutePath.PATH_MY_RECEIVE).navigation();
                        break;
                }
                break;
            case 2: // 在线问卷区
                switch (position) {
                    case 0: // 在线问卷
                        ARouter.getInstance().build(ARoutePath.PATH_QUESTION_ONLINE)
                                .withInt("publishmentSystemId", 1).navigation();
                        break;
                    case 1: // 在线投票
                        ARouter.getInstance().build(ARoutePath.PATH_VOTE_ONLINE).navigation();
                        break;
                }
                break;
        }
    }

    @Override
    public void loadData(MyJoinEntity myJoinEntity) {
        initRecyclerView(myJoinEntity);
    }

    private void initRecyclerView() {
        /*DelegateAdapter delegateAdapter = mPresenter.initRecyclerView(mRecyclerView);

        //初始化头部
        BaseDelegateAdapter headerAdapter = mPresenter.initHeader("点击立即报到");
        mAdapters.add(headerAdapter);

        //初始化九宫格
        BaseDelegateAdapter menuAdapter = mPresenter.initGvMenu();
        mAdapters.add(menuAdapter);

        //初始化 1PlusN
        BaseDelegateAdapter oneplusnAdapter = mPresenter.initOnePlusN2();
        mAdapters.add(oneplusnAdapter);

        //初始化 格栏布局 - 我的心愿
        BaseDelegateAdapter columnWishAdapter = mPresenter.initColumnWish();
        mAdapters.add(columnWishAdapter);

        //初始化 格栏布局 - 在线问卷
        BaseDelegateAdapter columnOnlineAdapter = mPresenter.initColumnOnline();
        mAdapters.add(columnOnlineAdapter);

        //初始化标题 - 志愿服务
        BaseDelegateAdapter titleAdapter = mPresenter.initTitle("志愿服务");
        mAdapters.add(titleAdapter);
        //初始化list
        BaseDelegateAdapter listAdapter = mPresenter.initListVolunteer(
                "http://pic117.nipic.com/file/20161213/24416158_165731241000_2.jpg",
                0, 40, 639, 687, "志愿服务，从心开始", "2018-2-27");
        mAdapters.add(listAdapter);

        //初始化脚部
        BaseDelegateAdapter footerAdapter = mPresenter.initFooter("更多志愿服务");
        mAdapters.add(footerAdapter);

        //初始化标题 - 社区动态
        titleAdapter = mPresenter.initTitle("社区动态");
        mAdapters.add(titleAdapter);
        //初始化list
        BaseDelegateAdapter listAdapter2 = mPresenter.initListCommunity(
                "http://pic22.photophoto.cn/20120105/0020033017931766_b.jpg",
                639, 687, "志愿服务，从心开始", "2018-02-02");
        mAdapters.add(listAdapter2);

        //设置适配器
        delegateAdapter.setAdapters(mAdapters);*/
    }

    private void initRecyclerView(MyJoinEntity myJoinEntity) {
        List<MyJoinEntity.VolunteerBlock> volunteerBlocks = myJoinEntity.getVolunteerBlock();
        List<MyJoinEntity.CommunityBlock> communityBlocks = myJoinEntity.getCommunityBlock();
        MyJoinEntity.PartyInfoModel partyInfoModel = myJoinEntity.getPartyInfoModel();

        DelegateAdapter delegateAdapter = mPresenter.initRecyclerView(mRecyclerView);

        //初始化头部
        BaseDelegateAdapter headerAdapter = mPresenter.initHeader(partyInfoModel);
        mAdapters.add(headerAdapter);

        //初始化九宫格
        BaseDelegateAdapter menuAdapter = mPresenter.initGvMenu();
        mAdapters.add(menuAdapter);

        //初始化 1PlusN
        BaseDelegateAdapter oneplusnAdapter = mPresenter.initOnePlusN2();
        mAdapters.add(oneplusnAdapter);

        //初始化 格栏布局 - 我的心愿
        BaseDelegateAdapter columnWishAdapter = mPresenter.initColumnWish();
        mAdapters.add(columnWishAdapter);

        //初始化 格栏布局 - 在线问卷
        BaseDelegateAdapter columnOnlineAdapter = mPresenter.initColumnOnline();
        mAdapters.add(columnOnlineAdapter);

        //初始化标题 - 志愿服务
        BaseDelegateAdapter titleAdapter = mPresenter.initTitle("志愿服务");
        mAdapters.add(titleAdapter);
        //初始化list
        BaseDelegateAdapter listAdapter = mPresenter.initListVolunteer(volunteerBlocks);
        mAdapters.add(listAdapter);

        //初始化脚部
        BaseDelegateAdapter footerAdapter = mPresenter.initFooter("更多志愿服务");
        mAdapters.add(footerAdapter);

        //初始化标题 - 社区动态
        titleAdapter = mPresenter.initTitle("社区动态");
        mAdapters.add(titleAdapter);
        //初始化list
        BaseDelegateAdapter listAdapter2 = mPresenter.initListCommunity(communityBlocks);
        mAdapters.add(listAdapter2);

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
//        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }
}
