package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.di.component.DaggerGradeRankComponent;
import com.mytv.rtzhdj.di.module.GradeRankModule;
import com.mytv.rtzhdj.mvp.contract.GradeRankContract;
import com.mytv.rtzhdj.mvp.presenter.GradeRankPresenter;

import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.mvp.ui.adapter.GradeRankAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;


import org.raphets.roundimageview.RoundImageView;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 成绩排行界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-28
 * @update
 */
@Route(path = ARoutePath.PATH_GRADE_RANK)
public class GradeRankActivity extends BaseActivity<GradeRankPresenter> implements GradeRankContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;

    @BindView(R.id.iv_header)
    RoundImageView mIvHeader;
    @BindView(R.id.tv_scores)
    TextView mTvScores;
    @BindView(R.id.tv_content1)
    TextView mTvContent1;
    @BindView(R.id.tv_content2)
    TextView mTvContent2;
    @BindView(R.id.tv_content3)
    TextView mTvContent3;
    @BindView(R.id.tv_title3)
    TextView mTvTitle3;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private GradeRankAdapter mAdapter;
    private static final int PAGE_SIZE = 10;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerGradeRankComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .gradeRankModule(new GradeRankModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_grade_rank; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);

        mPresenter.setActivity(GradeRankActivity.this);
        mRecyclerView = mPresenter.initRecyclerView(mRecyclerView);
        initAdapter();
        initRefreshLayout();
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
        /*mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000*//*,false*//*);//传入false表示刷新失败
            }
        });*/
        mRefreshLayout.setEnableLoadmore(false);
        /*mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000*//*,false*//*);//传入false表示加载失败
            }
        });*/
    }

    private void initAdapter() {
        mAdapter = new GradeRankAdapter(GradeRankActivity.this, PAGE_SIZE);
        mAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showMessage("" + Integer.toString(position));
            }
        });

    }


}
