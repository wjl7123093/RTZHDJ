package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.SharepreferenceKey;
import com.mytv.rtzhdj.app.data.entity.CoursewareEntity;
import com.mytv.rtzhdj.app.data.entity.HeaderIntegralEntity;
import com.mytv.rtzhdj.di.component.DaggerCompulsoryCourseComponent;
import com.mytv.rtzhdj.di.module.CompulsoryCourseModule;
import com.mytv.rtzhdj.mvp.contract.CompulsoryCourseContract;
import com.mytv.rtzhdj.mvp.presenter.CompulsoryCoursePresenter;
import com.mytv.rtzhdj.mvp.ui.fragment.CompulsoryCourseFragment;

import org.raphets.roundimageview.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 必修课界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-20
 * @update 2018-2-26    填充UI布局
 */
@Route(path = ARoutePath.PATH_COMPULSORY_COURSE)
public class CompulsoryCourseActivity extends BaseActivity<CompulsoryCoursePresenter> implements CompulsoryCourseContract.View {

    @BindView(R.id.collapse_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.vp_content)
    ViewPager mViewPager;

    @BindView(R.id.iv_header)
    RoundImageView mIvHeader;
    @BindView(R.id.tv_scores)
    TextView mTvScores;
    @BindView(R.id.tv_power_num)
    TextView mTvPowerNum;
    @BindView(R.id.tv_differ_from)
    TextView mTvDifferFrom;
    @BindView(R.id.tv_query_task)
    TextView mTvQueryTask;

    private ArrayList<Fragment> mFragments;
    private String[] titles;
    private String mTitle;      // 标题栏标题

    /** 加载进度条 */
    private SweetAlertDialog pDialog;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerCompulsoryCourseComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .compulsoryCourseModule(new CompulsoryCourseModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_compulsory_course; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        titles = new String[]{"全部", "未完成", "已完成"};

        mTvQueryTask.setOnClickListener(view -> {
            ARouter.getInstance().build(ARoutePath.PATH_MY_TASK).navigation();
        });
        collapsingToolbar.setTitleEnabled(false);

        // 获取头部信息
        mPresenter.callMethodOfGetMyScore(DataHelper.getIntergerSF(CompulsoryCourseActivity.this,
                SharepreferenceKey.KEY_USER_ID), false);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        //点击back键finish当前activity
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("必修课");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initTab() {
        final List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            fragments.add(CompulsoryCourseFragment.newInstance(i));
        }


        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return titles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });
        tabLayout.setupWithViewPager(mViewPager);
//        mTab.setTabTextColors(Color.BLACK, Color.RED);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initToolBar();
        initTab();
    }


    @Override
    public void loadData(List<CoursewareEntity> courseList) {

    }

    @Override
    public void loadHeaderData(HeaderIntegralEntity headerIntegralEntity) {
        mTvScores.setText("本月获得积分: " + headerIntegralEntity.getIntegral());
        mTvPowerNum.setText("您的正能量值: " + headerIntegralEntity.getPlanValue());
        mTvDifferFrom.setText("距下一积分任务还差: " + headerIntegralEntity.getNextValue() + " 请继续加油!");
    }
}
