package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.di.component.DaggerEffectEvaluationComponent;
import com.mytv.rtzhdj.di.module.EffectEvaluationModule;
import com.mytv.rtzhdj.mvp.contract.EffectEvaluationContract;
import com.mytv.rtzhdj.mvp.presenter.EffectEvaluationPresenter;

import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.mvp.ui.fragment.ContentFragment;
import com.mytv.rtzhdj.mvp.ui.fragment.EffectEvaluationFragment;


import org.raphets.roundimageview.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 效果测评界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-20
 * @update 2018-2-26    填充UI布局
 */
@Route(path = ARoutePath.PATH_EFFECT_EVALUATION)
public class EffectEvaluationActivity extends BaseActivity<EffectEvaluationPresenter> implements EffectEvaluationContract.View {

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


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerEffectEvaluationComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .effectEvaluationModule(new EffectEvaluationModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_effect_evaluation; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        titles = new String[]{"全部", "未测评", "已结束"};

        mTvQueryTask.setOnClickListener(view -> {
            ARouter.getInstance().build(ARoutePath.PATH_MY_TASK).navigation();
        });
        collapsingToolbar.setTitleEnabled(false);
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

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("效果测评");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initTab() {
        final List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            fragments.add(EffectEvaluationFragment.newInstance());
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


}
