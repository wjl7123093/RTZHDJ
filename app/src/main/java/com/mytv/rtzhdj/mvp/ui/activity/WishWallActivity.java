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

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.data.entity.MyWishEntity;
import com.mytv.rtzhdj.di.component.DaggerWishWallComponent;
import com.mytv.rtzhdj.di.module.WishWallModule;
import com.mytv.rtzhdj.mvp.contract.WishWallContract;
import com.mytv.rtzhdj.mvp.presenter.WishWallPresenter;

import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.mvp.ui.fragment.MyReceiveFragment;
import com.mytv.rtzhdj.mvp.ui.fragment.WishWallFragment;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 心愿墙／我的心愿 界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-21
 * @update 2018-2-8     填充布局
 */
@Route(path = ARoutePath.PATH_WISH_WALL)
public class WishWallActivity extends BaseActivity<WishWallPresenter> implements WishWallContract.View {

    @BindView(R.id.collapse_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.vp_content)
    ViewPager mViewPager;

    @Autowired
    String type;    // 类型（wall 心愿墙，mine 我的心愿）

    private int[] mImageArray, mColorArray;
    private ArrayList<Fragment> mFragments;
    private String[] titles;
    private String mTitle;      // 标题栏标题

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerWishWallComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .wishWallModule(new WishWallModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        return R.layout.activity_wish_wall; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        titles = new String[]{"心愿单", "未被认领", "已被认领"};
//        initTab();

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
        getSupportActionBar().setTitle(mTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initTab() {
        final List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            fragments.add(WishWallFragment.newInstance(i));
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

    @Override
    protected void onResume() {
        super.onResume();
        if (type.equals("wall"))
            mTitle = getResources().getString(R.string.title_wish_wall);
        else
            mTitle = getResources().getString(R.string.title_my_wish);

        initToolBar();
        initTab();
    }

    @Override
    public void loadData(List<MyWishEntity> myWishList) {

    }
}
