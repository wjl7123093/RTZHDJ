package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.di.component.DaggerVolunteerServiceDetailComponent;
import com.mytv.rtzhdj.di.module.VolunteerServiceDetailModule;
import com.mytv.rtzhdj.mvp.contract.VolunteerServiceDetailContract;
import com.mytv.rtzhdj.mvp.presenter.VolunteerServiceDetailPresenter;

import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.mvp.ui.fragment.SettingsFragment;
import com.mytv.rtzhdj.mvp.ui.fragment.VolunteerServiceDetailFragment;
import com.mytv.rtzhdj.mvp.ui.fragment.VolunteerServiceFragment;


import net.qiujuer.genius.ui.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 志愿服务详情界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-3-28
 * @update
 */
@Route(path = ARoutePath.PATH_VOLUNTEER_SERVICE_DETAIL)
public class VolunteerServiceDetailActivity extends BaseActivity<VolunteerServiceDetailPresenter> implements VolunteerServiceDetailContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_enroll_time)
    TextView mTvEnrollTime;
    @BindView(R.id.tv_event_time)
    TextView mTvEventTime;
    @BindView(R.id.tv_event_site)
    TextView mTvEventSite;
    @BindView(R.id.tv_enrollment)
    TextView mTvEnrollment;
    @BindView(R.id.tv_grade)
    TextView mTvGrade;

    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.vp_content)
    ViewPager mViewPager;

    @BindView(R.id.btn_star)
    Button mBtnStar;
    @BindView(R.id.btn_is_over)
    Button mBtnIsOver;

    private ArrayList<Fragment> mFragments;
    private String[] titles;
    private String mTitle;      // 标题栏标题


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerVolunteerServiceDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .volunteerServiceDetailModule(new VolunteerServiceDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_volunteer_service_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);

        titles = new String[]{"活动详情", "活动回顾"};
        initTab();

        mBtnStar.setOnClickListener(view -> {});
        mBtnIsOver.setOnClickListener(view -> {});
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


    private void initTab() {
        final List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            fragments.add(VolunteerServiceDetailFragment.newInstance());
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
//        tabLayout.setTabTextColors(Color.BLACK, Color.RED);
    }


}
