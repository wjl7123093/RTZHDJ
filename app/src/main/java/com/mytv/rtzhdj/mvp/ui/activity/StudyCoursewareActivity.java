package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.vlayout.DelegateAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.SharepreferenceKey;
import com.mytv.rtzhdj.app.data.entity.StudyCoursewareEntity;
import com.mytv.rtzhdj.di.component.DaggerStudyCoursewareComponent;
import com.mytv.rtzhdj.di.module.StudyCoursewareModule;
import com.mytv.rtzhdj.mvp.contract.StudyCoursewareContract;
import com.mytv.rtzhdj.mvp.presenter.StudyCoursewarePresenter;
import com.mytv.rtzhdj.mvp.ui.adapter.BaseDelegateAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 学习课件界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-20
 * @update 2018-2-6     填充布局
 */
@Route(path = ARoutePath.PATH_STUDY_COURSEWARE)
public class StudyCoursewareActivity extends BaseActivity<StudyCoursewarePresenter> implements StudyCoursewareContract.View {

    private final int PAGE_SIZE = 10;

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

    /** 存放各个模块的适配器*/
    private List<DelegateAdapter.Adapter> mAdapters;
    private DelegateAdapter delegateAdapter = null;

    /** 加载进度条 */
    private SweetAlertDialog pDialog;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerStudyCoursewareComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .studyCoursewareModule(new StudyCoursewareModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_study_courseware; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mAdapters = new LinkedList<>();
        mPresenter.setActivity(StudyCoursewareActivity.this);
        initRecyclerView();
        initRefreshLayout();

        // 获取 学习课件数据
        mPresenter.callMethodOfGetNewCoursewareList(DataHelper.getIntergerSF(StudyCoursewareActivity.this,
                SharepreferenceKey.KEY_USER_ID), 1, PAGE_SIZE, false);
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


    @Override
    public void setOnGridClick(int position, String title) {
        switch (position) {
            case 0:
                ARouter.getInstance().build(ARoutePath.PATH_COMPULSORY_COURSE).navigation();
                break;
            case 1: // 选修课 nodeId = 9044
            case 2: // 微党课 nodeId = 9045
                ARouter.getInstance().build(ARoutePath.PATH_ELECTIVE_COURSE)
                        .withInt("nodeId", position == 1 ? 9044 : 9045)
                        .withString("title", title).navigation();
                break;

        }
    }

    @Override
    public void setOnListClick(int position) {

    }

    @Override
    public void loadData(List<StudyCoursewareEntity> courseList) {
        //初始化list
        BaseDelegateAdapter listAdapter = mPresenter.initList(courseList);
        mAdapters.add(listAdapter);
        delegateAdapter.setAdapters(mAdapters);
    }

    private void initRecyclerView() {
        delegateAdapter = mPresenter.initRecyclerView(mRecyclerView);

        //初始化九宫格
        BaseDelegateAdapter menuAdapter = mPresenter.initGvMenu();
        mAdapters.add(menuAdapter);

        //初始化标题 - 必修课
        BaseDelegateAdapter titleAdapter = mPresenter.initTitle("最新课件");
        mAdapters.add(titleAdapter);
//        //初始化list
//        BaseDelegateAdapter listAdapter = mPresenter.initList(DataServer.getCoursewareData(10));
//        mAdapters.add(listAdapter);

        //设置适配器
        delegateAdapter.setAdapters(mAdapters);
    }

    private void initRefreshLayout() {
//        mRefreshLayout.setEnableRefresh(false);
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
