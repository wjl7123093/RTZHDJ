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
import com.mytv.rtzhdj.di.component.DaggerVoteDetailComponent;
import com.mytv.rtzhdj.di.module.VoteDetailModule;
import com.mytv.rtzhdj.mvp.contract.VoteDetailContract;
import com.mytv.rtzhdj.mvp.presenter.VoteDetailPresenter;

import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.mvp.ui.adapter.CommentAdapter;
import com.mytv.rtzhdj.mvp.ui.adapter.VoteDetailAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import net.qiujuer.genius.ui.widget.Button;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 投票详情界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-3-26
 * @update
 */
@Route(path = ARoutePath.PATH_VOTE_DETAIL)
public class VoteDetailActivity extends BaseActivity<VoteDetailPresenter> implements VoteDetailContract.View {

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

    @BindView(R.id.btn_vote_brief)
    Button mBtnVoteBrief;
    @BindView(R.id.btn_vote_result)
    Button mBtnVoteResult;

    @Autowired
    String title;

    private VoteDetailAdapter mAdapter;
    private static final int PAGE_SIZE = 10;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerVoteDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .voteDetailModule(new VoteDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        return R.layout.activity_vote_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);

        mPresenter.setActivity(VoteDetailActivity.this);
        mRecyclerView = mPresenter.initRecyclerView(mRecyclerView);
        initAdapter();
        initRefreshLayout();

        mBtnVoteBrief.setOnClickListener(view -> {
            ARouter.getInstance().build(ARoutePath.PATH_VOTE_BRIEF).navigation();
        });
        mBtnVoteResult.setOnClickListener(view -> {
            ARouter.getInstance().build(ARoutePath.PATH_VOTE_RESULT).navigation();
        });

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

    @Override
    protected void onResume() {
        super.onResume();
        mTvToolbarTitle.setText(title);
    }

    private void initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    private void initAdapter() {
        mAdapter = new VoteDetailAdapter(VoteDetailActivity.this, PAGE_SIZE);
        mAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                showMessage("" + Integer.toString(position));
                ARouter.getInstance().build(ARoutePath.PATH_VOTE_ENTRY_DETAIL).navigation();
            }
        });

    }


}
