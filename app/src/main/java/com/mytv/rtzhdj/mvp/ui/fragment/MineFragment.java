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

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.di.component.DaggerMineComponent;
import com.mytv.rtzhdj.di.module.MineModule;
import com.mytv.rtzhdj.mvp.contract.MineContract;
import com.mytv.rtzhdj.mvp.presenter.MinePresenter;

import com.mytv.rtzhdj.R;
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
 * MineFragment 我的 Fragment
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-19
 * @update 2018-2-2     填充布局
 */
public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View {

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    /** 存放各个模块的适配器*/
    private List<DelegateAdapter.Adapter> mAdapters;


    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerMineComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mineModule(new MineModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mAdapters = new LinkedList<>();
        mPresenter.setActivity((MainActivity)getActivity());
        initRecyclerView();
        initRefreshLayout();
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
    public void setOnSettingsClick() {
        // 设置
        ARouter.getInstance().build(ARoutePath.PATH_SETTINGS).navigation();
    }

    @Override
    public void setOnSignClick() {

    }

    @Override
    public void setOnGridClick(int arrayPos, int position) {
        switch (arrayPos) {
            case 1: // 我的支部
                switch (position) {
                    case 0: // 党员风采
                        ARouter.getInstance().build(ARoutePath.PATH_PARTY_MEMBER).navigation();
                        break;
                    case 1: // 党支部工作手册
                        break;
                    case 2: // 组织关系
                        ARouter.getInstance().build(ARoutePath.PATH_MEMBERSHIP_CREDENTIALS).navigation();
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
                break;
            case 2: // 我的积分
                break;
            case 3: // 生活助手
                break;
        }
    }

    @Override
    public void setOnSingleClick() {

    }

    @Override
    public void setOnTitleClick(int arrayPos) {

    }

    @Override
    public void setOnColumnClick(int arrayPos, int position) {

    }

    private void initRecyclerView() {
        DelegateAdapter delegateAdapter = mPresenter.initRecyclerView(mRecyclerView);

        //初始化头部
        BaseDelegateAdapter headerAdapter = mPresenter.initHeader(
                "http://imgtu.5011.net/uploads/content/20170220/9520371487578487.jpg",
                "はたけ·カカシ", "xxxxxx党支部");
        mAdapters.add(headerAdapter);

        //初始化 格栏布局 - 积分
        BaseDelegateAdapter columnAdapter = mPresenter.initColumn1(17, 10, 19797);
        mAdapters.add(columnAdapter);

        //初始化标题 - 我的支部
        BaseDelegateAdapter titleAdapter = mPresenter.initTitle(1, "我的支部", "", -1);
        mAdapters.add(titleAdapter);
        //初始化网格
        BaseDelegateAdapter menuAdapter = mPresenter.initGvMenu1();
        mAdapters.add(menuAdapter);

        //初始化标题 - 组织活动
        titleAdapter = mPresenter.initTitle(2, "组织活动", "", -1);
        mAdapters.add(titleAdapter);
        //初始化 格栏布局
        BaseDelegateAdapter columnAdapter2 = mPresenter.initColumn2(0, 0, 0);
        mAdapters.add(columnAdapter2);

        //初始化标题 - 我的党费
        titleAdapter = mPresenter.initTitle(3, "我的党费", "银行卡管理>>", -1);
        mAdapters.add(titleAdapter);
        //初始化 通栏布局
        BaseDelegateAdapter singleAdapter = mPresenter.initSingle(25.75f, 1);
        mAdapters.add(singleAdapter);

        //初始化标题 - 我的积分
        titleAdapter = mPresenter.initTitle(4, "我的积分", "怎样获取积分?", 17);
        mAdapters.add(titleAdapter);
        //初始化网格
        menuAdapter = mPresenter.initGvMenu2();
        mAdapters.add(menuAdapter);

        //初始化标题 - 生活助手
        titleAdapter = mPresenter.initTitle(5, "生活助手", "", -1);
        mAdapters.add(titleAdapter);
        //初始化网格
        menuAdapter = mPresenter.initGvMenu3();
        mAdapters.add(menuAdapter);

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
        /*mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000*//*,false*//*);//传入false表示加载失败
            }
        });*/
    }
}
