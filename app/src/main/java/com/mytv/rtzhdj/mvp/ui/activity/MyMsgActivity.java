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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.di.component.DaggerMyMsgComponent;
import com.mytv.rtzhdj.di.module.MyMsgModule;
import com.mytv.rtzhdj.mvp.contract.MyMsgContract;
import com.mytv.rtzhdj.mvp.presenter.MyMsgPresenter;

import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.mvp.ui.fragment.ContentFragment;
import com.mytv.rtzhdj.mvp.ui.fragment.SettingsFragment;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 我的私信界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-3-24
 * @update
 */
@Route(path = ARoutePath.PATH_MY_MSG)
public class MyMsgActivity extends BaseActivity<MyMsgPresenter> implements MyMsgContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;
    @BindView(R.id.iv_menu)
    ImageView mIvMenu;

    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.vp_content)
    ViewPager mViewPager;

    private ArrayList<Fragment> mFragments;
    private String[] titles;
    private String mTitle;      // 标题栏标题


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMyMsgComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myMsgModule(new MyMsgModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_my_msg; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);

        titles = new String[]{"我的收信箱", "我的发信箱"};
        initTab();
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
            fragments.add(ContentFragment.newInstance(i));
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
