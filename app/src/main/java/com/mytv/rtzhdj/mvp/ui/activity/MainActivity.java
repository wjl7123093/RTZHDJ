package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.utils.FragmentUtils;
import com.mytv.rtzhdj.app.utils.StatusBarUtil;
import com.mytv.rtzhdj.di.component.DaggerMainComponent;
import com.mytv.rtzhdj.di.module.MainModule;
import com.mytv.rtzhdj.mvp.contract.MainContract;
import com.mytv.rtzhdj.mvp.presenter.MainPresenter;

import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.mvp.ui.fragment.HomeFragment;
import com.mytv.rtzhdj.mvp.ui.fragment.JoinFragment;
import com.mytv.rtzhdj.mvp.ui.fragment.MineFragment;
import com.mytv.rtzhdj.mvp.ui.fragment.NewsFragment;
import com.mytv.rtzhdj.mvp.ui.fragment.StudyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.mytv.rtzhdj.app.EventBusTags.ACTIVITY_FRAGMENT_REPLACE;

/**
 * 主界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-18
 * @update
 */
@Route(path = ARoutePath.PATH_MAIN)
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View,
        BottomNavigationBar.OnTabSelectedListener {

    // 方案1
    @BindView(R.id.main_frame)
    FrameLayout mFrameMain;
    // 方案2
//    @BindView(R.id.content)
//    ViewPager viewPager;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationBar;

    private HomeFragment homeFragment;
    private NewsFragment newsFragment;
    private StudyFragment studyFragment;
    private JoinFragment joinFragment;
    private MineFragment mineFragment;

    private List<Fragment> mFragments;
    private int mReplace = 0;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        /*** the setting for BadgeItem ***/
        BadgeItem badgeItem = new BadgeItem();
        badgeItem.setHideOnSelect(false)
                .setText("10")
                .setBackgroundColorResource(R.color.primary)
                .setBorderWidth(0)
                .setHideOnSelect(true);

        /*** the setting for BottomNavigationBar ***/

        /** 样式 1 */
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar.setBarBackgroundColor(R.color.white);//set background color for navigation bar
        mBottomNavigationBar.setInActiveColor(R.color.divider);//unSelected icon color
        mBottomNavigationBar.setActiveColor(R.color.primary); // selected icon color
        mBottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_tab_home, R.string.tab_home)) // .setBadgeItem(badgeItem)
                .addItem(new BottomNavigationItem(R.mipmap.ic_tab_news, R.string.tab_news))
                .addItem(new BottomNavigationItem(R.mipmap.ic_tab_study, R.string.tab_study))
                .addItem(new BottomNavigationItem(R.mipmap.ic_tab_join, R.string.tab_join))
                .addItem(new BottomNavigationItem(R.mipmap.ic_tab_mine, R.string.tab_mine))
                .setFirstSelectedPosition(0)
                .initialise();

        mBottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment(savedInstanceState);

        //状态栏透明和间距处理
//        StatusBarUtil.immersive(this, 0xff000000, 0.1f);


        // ===================方案2 activity_test.xml -> ViewPager做容器=========================
        /*viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mFragments.size();
            }
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                mBottomNavigationBar.selectTab(position, false);
            }
        });*/

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
    public void onTabSelected(int position) {
        mReplace = position;

        // 方案1
        FragmentUtils.hideAllShowFragment(mFragments.get(mReplace));

        // 方案2
//        viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    /**
     * 说明：
     * 1、onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)
     *    该方法只针对 API 21(Android 5.0) 以上才有 PersistableBundle 参数
     * 2、如果使用上述方法，在 API 21(Android 5.0) 以下机型会报如下错误：
     *    java.lang.ClassNotFoundException: Didn't find class "android.os.PersistableBundle" on path: DexPathList
     *    原因就是因为 低版本(<21) 没有 PersistableBundle 这个 class
     * 3、综合以上两点，从 兼容性 方面考虑，故只调用包含一个参数的方法，即
     *    onSaveInstanceState(Bundle outState)
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //保存当前Activity显示的Fragment索引
        outState.putInt(ACTIVITY_FRAGMENT_REPLACE, mReplace);
    }

    private void setDefaultFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            homeFragment = HomeFragment.newInstance();
            newsFragment = NewsFragment.newInstance();
            studyFragment = StudyFragment.newInstance();
            joinFragment = JoinFragment.newInstance();
            mineFragment = MineFragment.newInstance();
        } else {
            mReplace = savedInstanceState.getInt(ACTIVITY_FRAGMENT_REPLACE);
            FragmentManager fm = getSupportFragmentManager();
            homeFragment = (HomeFragment) FragmentUtils.findFragment(fm, HomeFragment.class);
            newsFragment = (NewsFragment) FragmentUtils.findFragment(fm, NewsFragment.class);
            studyFragment = (StudyFragment) FragmentUtils.findFragment(fm, StudyFragment.class);
            joinFragment = (JoinFragment) FragmentUtils.findFragment(fm, JoinFragment.class);
            mineFragment = (MineFragment) FragmentUtils.findFragment(fm, MineFragment.class);
        }
        if (mFragments == null) {
            mFragments = new ArrayList<>();
            mFragments.add(homeFragment);
            mFragments.add(newsFragment);
            mFragments.add(studyFragment);
            mFragments.add(joinFragment);
            mFragments.add(mineFragment);
        }

        // ===================方案1 activity_main.xml -> FrameLayout做容器=========================
        FragmentUtils.addFragments(getSupportFragmentManager(), mFragments, R.id.main_frame, 0);
    }

}
