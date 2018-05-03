package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
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

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.data.entity.VoteDetailEntity;
import com.mytv.rtzhdj.di.component.DaggerVoteResultComponent;
import com.mytv.rtzhdj.di.module.VoteResultModule;
import com.mytv.rtzhdj.mvp.contract.VoteResultContract;
import com.mytv.rtzhdj.mvp.presenter.VoteResultPresenter;

import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.mvp.ui.adapter.VoteDetailAdapter;
import com.mytv.rtzhdj.mvp.ui.adapter.VoteResultAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 投票结果界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-3-26
 * @update
 */
@Route(path = ARoutePath.PATH_VOTE_RESULT)
public class VoteResultActivity extends BaseActivity<VoteResultPresenter> implements VoteResultContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private VoteResultAdapter mAdapter;
    private static final int PAGE_SIZE = 10;

    @Autowired
    int id;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerVoteResultComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .voteResultModule(new VoteResultModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        return R.layout.activity_vote_result; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);

        mPresenter.setActivity(VoteResultActivity.this);
        mRecyclerView = mPresenter.initRecyclerView(mRecyclerView);
//        initAdapter();
        initRefreshLayout();

        // 获取 投票结果数据
        mPresenter.callMethodOfPostOnlineVoteResult(id, false);

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
        finish();
    }

    private void initRefreshLayout() {
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadmore(false);
        /*mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000*//*,false*//*);//传入false表示刷新失败
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000*//*,false*//*);//传入false表示加载失败
            }
        });*/
    }

    private void initAdapter(List<VoteDetailEntity> voteResultList) {
        mAdapter = new VoteResultAdapter(VoteResultActivity.this, voteResultList);
        mAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showMessage("" + Integer.toString(position));
            }
        });

    }


    @Override
    public void loadData(List<VoteDetailEntity> voteResultList) {
        initAdapter(voteResultList);
    }
}
