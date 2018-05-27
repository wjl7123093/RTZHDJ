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

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.jess.arms.utils.DataHelper;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.SharepreferenceKey;
import com.mytv.rtzhdj.di.component.DaggerStudyRecordComponent;
import com.mytv.rtzhdj.di.module.StudyRecordModule;
import com.mytv.rtzhdj.mvp.contract.StudyRecordContract;
import com.mytv.rtzhdj.mvp.presenter.StudyRecordPresenter;

import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.mvp.ui.adapter.NewsAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 学习记录界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-20
 * @update
 */
@Route(path = ARoutePath.PATH_STUDY_RECORD)
public class StudyRecordActivity extends BaseActivity<StudyRecordPresenter> implements StudyRecordContract.View {

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

    private NewsAdapter newsAdapter;
    private static final int PAGE_SIZE = 10;

    /** 加载进度条 */
    private SweetAlertDialog pDialog;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerStudyRecordComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .studyRecordModule(new StudyRecordModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_study_record; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        mPresenter.setActivity(StudyRecordActivity.this);
        mRecyclerView = mPresenter.initRecyclerView(mRecyclerView);
        initAdapter();
        initRefreshLayout();

        // 获取 学习记录数据
        mPresenter.callMethodOfGetLearningRecords(
                DataHelper.getIntergerSF(StudyRecordActivity.this, SharepreferenceKey.KEY_USER_ID), false);

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

    private void initAdapter() {
        /*newsAdapter = new NewsAdapter(StudyRecordActivity.this, PAGE_SIZE);
        newsAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(newsAdapter);

        newsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showMessage("" + Integer.toString(position));
            }
        });*/

    }


}
