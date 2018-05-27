package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.jess.arms.utils.DataHelper;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.SharepreferenceKey;
import com.mytv.rtzhdj.app.data.entity.PartyMemberEntity;
import com.mytv.rtzhdj.app.data.entity.PartyMienEntity;
import com.mytv.rtzhdj.di.component.DaggerPartyMemberComponent;
import com.mytv.rtzhdj.di.module.PartyMemberModule;
import com.mytv.rtzhdj.mvp.contract.PartyMemberContract;
import com.mytv.rtzhdj.mvp.presenter.PartyMemberPresenter;

import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.mvp.ui.adapter.NewsAdapter;
import com.mytv.rtzhdj.mvp.ui.adapter.PartyMemberAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 党员风采 界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-11
 * @update
 */
@Route(path = ARoutePath.PATH_PARTY_MEMBER)
public class PartyMemberActivity extends BaseActivity<PartyMemberPresenter> implements PartyMemberContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;
    @BindView(R.id.toolbar_menu_tv)
    TextView mTvMenu;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private PartyMemberAdapter partyMemberAdapter;
    private static final int PAGE_SIZE = 10;
    private int PAGE_INDEX = 1;
    private int mCurPos = 0;    // 当前列表末节点位置
    private List<PartyMienEntity> mMemberList = new ArrayList<>();
    private boolean mIsLoadMore = false;
    private boolean mIsRefresh = false;

    /** 加载进度条 */
    private SweetAlertDialog pDialog;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerPartyMemberComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .partyMemberModule(new PartyMemberModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_party_member; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);
        mTvMenu.setVisibility(View.VISIBLE);

        mTvMenu.setText("我的私信");
        mTvMenu.setOnClickListener(view -> {
            ARouter.getInstance().build(ARoutePath.PATH_MY_MSG).navigation();
        });
        mPresenter.setActivity(PartyMemberActivity.this);
        mRecyclerView = mPresenter.initRecyclerView(mRecyclerView);
//        initAdapter();
        initRefreshLayout();

        PAGE_INDEX = 1;
        // 获取 党员信息列表数据
//        mPresenter.callMethodOfGetPartyMmeber(1, false);
        mPresenter.callMethodOfGetPartymembermien(
                DataHelper.getIntergerSF(PartyMemberActivity.this, SharepreferenceKey.KEY_PUBLISHMENT_SYSTEM_ID),
                PAGE_INDEX, PAGE_SIZE, false);

    }


    @Override
    public void showLoading() {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("正在加载...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    public void hideLoading() {
        pDialog.dismiss();
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
        finish();
    }

    private void initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败

                mIsRefresh = true;
                PAGE_INDEX = 1;
                // 获取 党员信息列表数据
                mPresenter.callMethodOfGetPartymembermien(
                        DataHelper.getIntergerSF(PartyMemberActivity.this, SharepreferenceKey.KEY_PUBLISHMENT_SYSTEM_ID),
                        PAGE_INDEX, PAGE_SIZE, true);
            }
        });
//        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败

                mIsLoadMore = true;
                // 获取 党员信息列表数据
                mPresenter.callMethodOfGetPartymembermien(
                        DataHelper.getIntergerSF(PartyMemberActivity.this, SharepreferenceKey.KEY_PUBLISHMENT_SYSTEM_ID),
                        ++PAGE_INDEX, PAGE_SIZE, true);

            }
        });
    }

    private void initAdapter(List<PartyMienEntity> memberList, boolean update) {
        if (update) {
            if (mIsRefresh) {  // 下拉刷新
                // 1. 先移除
                partyMemberAdapter.notifyItemRangeRemoved(0, mMemberList.size());
                // 2. 再清空
                mMemberList.clear();

                mRefreshLayout.finishRefresh(true);
                mIsRefresh = false;
            } else {    // 上拉加载
                mRefreshLayout.finishLoadmore(true);
                mIsLoadMore = false;
            }
        }

        mCurPos = mMemberList.size();
        if (null == partyMemberAdapter) {
            mMemberList = memberList;
            partyMemberAdapter = new PartyMemberAdapter(PartyMemberActivity.this, memberList);
            partyMemberAdapter.openLoadAnimation();
            mRecyclerView.setAdapter(partyMemberAdapter);
        } else {
            mMemberList.addAll(memberList);
            partyMemberAdapter.notifyItemRangeInserted(mCurPos, memberList.size());
        }

        partyMemberAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                showMessage("" + Integer.toString(position));

                ARouter.getInstance().build(ARoutePath.PATH_PARTY_MEMBER_DETAIL)
                        .withInt("id", memberList.get(position).getID())
                        .withString("name", memberList.get(position).getTitle()).navigation();
            }
        });

    }


    @Override
    public void loadData(List<PartyMemberEntity> memberList) {
//        initAdapter(memberList);
    }

    @Override
    public void loadDataMine(List<PartyMienEntity> memberList, boolean update) {
        initAdapter(memberList, update);
    }
}
