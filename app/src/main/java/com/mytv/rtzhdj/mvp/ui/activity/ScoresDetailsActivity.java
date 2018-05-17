package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.data.entity.NewsDetailEntity;
import com.mytv.rtzhdj.app.data.entity.ScoresDetailsEntity;
import com.mytv.rtzhdj.di.component.DaggerScoresDetailsComponent;
import com.mytv.rtzhdj.di.module.ScoresDetailsModule;
import com.mytv.rtzhdj.mvp.contract.ScoresDetailsContract;
import com.mytv.rtzhdj.mvp.presenter.ScoresDetailsPresenter;

import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.mvp.ui.adapter.NewsSimpleAdapter;
import com.mytv.rtzhdj.mvp.ui.adapter.ScoresDetailsAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 积分明细列表界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-10
 * @update
 */
@Route(path = ARoutePath.PATH_SCORES_DETAILS)
public class ScoresDetailsActivity extends BaseActivity<ScoresDetailsPresenter> implements ScoresDetailsContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;

    @BindView(R.id.tv_scores)
    TextView mTvScores;
    @BindView(R.id.tv_scores_total)
    TextView mTvScoresTotal;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private ScoresDetailsAdapter mAdapter;
    private static final int PAGE_SIZE = 10;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerScoresDetailsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .scoresDetailsModule(new ScoresDetailsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_scores_details; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);

        mPresenter.setActivity(ScoresDetailsActivity.this);
        mRecyclerView = mPresenter.initRecyclerView(mRecyclerView);
        initRefreshLayout();

        // 获取积分明细数据
        mPresenter.callMethodOfPostMyScore(8, false);

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
    public void loadData(ScoresDetailsEntity scoresDetailsEntity) {
        mTvScores.setText(scoresDetailsEntity.getMain().getAvailableIntegral() + "");
        mTvScoresTotal.setText(scoresDetailsEntity.getMain().getAccumulativeIntegral() + "");

        initAdapter(scoresDetailsEntity.getDetail());
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

    public void initAdapter(List<ScoresDetailsEntity.DetailScore> newsDetailList) {
        mAdapter = new ScoresDetailsAdapter(ScoresDetailsActivity.this, newsDetailList);
        mAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(mAdapter);
    }
}