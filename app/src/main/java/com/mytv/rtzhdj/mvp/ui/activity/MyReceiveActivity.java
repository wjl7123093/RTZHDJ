package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.di.component.DaggerMyReceiveComponent;
import com.mytv.rtzhdj.di.module.MyReceiveModule;
import com.mytv.rtzhdj.mvp.contract.MyReceiveContract;
import com.mytv.rtzhdj.mvp.presenter.MyReceivePresenter;

import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.mvp.ui.fragment.MyReceiveFragment;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.weyye.library.colortrackview.ColorTrackTabLayout;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 我领取的物品 界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-8
 * @update
 */
@Route(path = ARoutePath.PATH_MY_RECEIVE)
public class MyReceiveActivity extends BaseActivity<MyReceivePresenter> implements MyReceiveContract.View {

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

    @BindView(R.id.tab_channel)
    ColorTrackTabLayout mTab;
    @BindView(R.id.vp_content)
    ViewPager mViewPager;

    String[] titles;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMyReceiveComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myReceiveModule(new MyReceiveModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_my_receive; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mIvMenu.setImageResource(R.mipmap.ic_launcher);
        mBtnToolbarMenu.setOnClickListener(view -> {
            // 跳转到搜索页面
            ARouter.getInstance().build(ARoutePath.PATH_SEARCH).navigation();
        });

        titles = new String[]{"全部", "已被领取", "审核中"};
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
            fragments.add(MyReceiveFragment.newInstance());
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
        mTab.setupWithViewPager(mViewPager);
    }


}
