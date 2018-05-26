package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.mytv.rtzhdj.mvp.ui.widget.IconView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

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
    /*@BindView(R.id.main_frame)
    FrameLayout mFrameMain;
    // 方案2
//    @BindView(R.id.content)
//    ViewPager viewPager;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationBar;*/
    
    // 方案3

    /** 当前Fragment标识 */
    private String CF_TAG = "";
    /** 记录上一个按下的Tab栏导航键 */
    private String mLastTab = TAB_1;

    public final static String TAB_1 = "tab_1";
    public final static String TAB_2 = "tab_2";
    public final static String TAB_3 = "tab_3";
    public final static String TAB_4 = "tab_4";
    public final static String TAB_5 = "tab_5";

    /** 头条 */
    @BindView(R.id.fl1)
    LinearLayout mFl1;
    @BindView(R.id.icon1)
    IconView mIcon1;
    @BindView(R.id.tv1)
    TextView mTv1;

    /** 直播 */
    @BindView(R.id.fl2)
    LinearLayout mFl2;
    @BindView(R.id.icon2)
    IconView mIcon2;
    @BindView(R.id.tv2)
    TextView mTv2;

    /** 视讯 */
    @BindView(R.id.fl3)
    LinearLayout mFl3;
    @BindView(R.id.icon3)
    IconView mIcon3;
    @BindView(R.id.tv3)
    TextView mTv3;

    /** 政务 */
    @BindView(R.id.fl4)
    LinearLayout mFl4;
    @BindView(R.id.icon4)
    IconView mIcon4;
    @BindView(R.id.tv4)
    TextView mTv4;

    /** 便民 */
    @BindView(R.id.fl5)
    LinearLayout mFl5;
    @BindView(R.id.icon5)
    IconView mIcon5;
    @BindView(R.id.tv5)
    TextView mTv5;

    private HomeFragment homeFragment;
    private NewsFragment newsFragment;
    private StudyFragment studyFragment;
    private JoinFragment joinFragment;
    private MineFragment mineFragment;

    private List<Fragment> mFragments;
    private int mReplace = 0;

    /** 加载进度条 */
    private SweetAlertDialog pDialog;

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
        return R.layout.activity_main_test; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        // 默认页面
        HomeFragment f1 = new HomeFragment();
        changeFragment(f1, TAB_1);
        mLastTab = TAB_1;
        setTabStyle(mIcon1, mTv1, 1);

        /*** the setting for BadgeItem ***/
        /*BadgeItem badgeItem = new BadgeItem();
        badgeItem.setHideOnSelect(false)
                .setText("10")
                .setBackgroundColorResource(R.color.primary)
                .setBorderWidth(0)
                .setHideOnSelect(true);*/

        /*** the setting for BottomNavigationBar ***/

        /** 样式 1 */
        /*mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
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
        setDefaultFragment(savedInstanceState);*/

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



    /** Fragment Tab 标签点击事件 */
    @OnClick({R.id.fl1, R.id.fl2, R.id.fl3, R.id.fl4, R.id.fl5})
    public void fragmentTabClick(View v) {
        switch (v.getId()) {
            case R.id.fl1:
                doClick(0, v);
                break;
            case R.id.fl2:
                doClick(1, v);
                break;
            case R.id.fl3:
                doClick(2, v);
                break;
            case R.id.fl4:
                doClick(3, v);
                break;
            case R.id.fl5:
                doClick(4, v);
                break;
            default:
                break;
        }
    }

    /**
     * fragment页面调用切换页面
     *
     * @param i
     *            0:主页 1:个人中心
     */
    public void doClick(int i, View v) {
        switch (i) {
            case 0: // 头条
                mLastTab = TAB_1;
                setTabStyle(mIcon1, mTv1, 1);
                if (null == homeFragment)
                    homeFragment = new HomeFragment();
                changeFragment(homeFragment, TAB_1);
                break;

            case 1: // 直播
                mLastTab = TAB_2;
                setTabStyle(mIcon2, mTv2, 2);
                if (null == newsFragment)
                    newsFragment = new NewsFragment();
                changeFragment(newsFragment, TAB_2);
                break;

            case 2: // 视讯
                mLastTab = TAB_3;
                setTabStyle(mIcon3, mTv3, 3);
                if (null == studyFragment)
                    studyFragment = new StudyFragment();
                changeFragment(studyFragment, TAB_3);
                break;

            case 3: // 政务
                mLastTab = TAB_4;
                setTabStyle(mIcon4, mTv4, 4);
                if (null == joinFragment)
                    joinFragment = new JoinFragment();
                changeFragment(joinFragment, TAB_4);
                break;

            case 4: // 便民
                mLastTab = TAB_5;
                setTabStyle(mIcon5, mTv5, 5);
                if (null == mineFragment)
                    mineFragment = new MineFragment();
                changeFragment(mineFragment, TAB_5);
                break;
            default:
                break;
        }
    }

    /**
     * 切换界面Fragment
     *
     * @param fragment
     * @param tag
     */
    private void changeFragment(Fragment fragment, String tag) {

        if (CF_TAG.equals(tag)) {
            return;
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (fm.findFragmentByTag(tag) == null) {
            ft.add(R.id.flMain, fragment, tag);
        } else {
            ft.show(fm.findFragmentByTag(tag));
        }

        if (fm.findFragmentByTag(CF_TAG) != null) {
            ft.hide(fm.findFragmentByTag(CF_TAG));
        }
//		ft.commit();
        ft.commitAllowingStateLoss();	// 防止 commit() 是在Activity的onSaveInstanceState()之后调用产生的 BUG
        CF_TAG = tag;

    }

    /**
     * 设置底部tab状态
     *
     * @param iconView
     * @param tv
     */
    public void setTabStyle(IconView iconView, TextView tv, int position) {

        mIcon1.setTextColor(getResources().getColor(R.color.secondary_text));
        mIcon2.setTextColor(getResources().getColor(R.color.secondary_text));
        mIcon3.setTextColor(getResources().getColor(R.color.secondary_text));
        mIcon4.setTextColor(getResources().getColor(R.color.secondary_text));
        mIcon5.setTextColor(getResources().getColor(R.color.secondary_text));

        mTv1.setTextColor(getResources().getColor(R.color.secondary_text));
        mTv2.setTextColor(getResources().getColor(R.color.secondary_text));
        mTv3.setTextColor(getResources().getColor(R.color.secondary_text));
        mTv4.setTextColor(getResources().getColor(R.color.secondary_text));
        mTv5.setTextColor(getResources().getColor(R.color.secondary_text));

        tv.setTextColor(getResources().getColor(R.color.colorPrimary));
        iconView.setTextColor(getResources().getColor(R.color.colorPrimary));

    }

}
