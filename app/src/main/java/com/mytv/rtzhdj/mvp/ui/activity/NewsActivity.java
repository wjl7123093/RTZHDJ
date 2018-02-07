package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.di.component.DaggerNewsComponent;
import com.mytv.rtzhdj.di.module.NewsModule;
import com.mytv.rtzhdj.mvp.contract.NewsContract;
import com.mytv.rtzhdj.mvp.presenter.NewsPresenter;

import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.mvp.ui.fragment.ContentFragment;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.weyye.library.colortrackview.ColorTrackTabLayout;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 时政新闻界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-20
 * @update 2018-2-6     填充布局
 */
@Route(path = ARoutePath.PATH_NEWS)
public class NewsActivity extends BaseActivity<NewsPresenter> implements NewsContract.View {

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
        DaggerNewsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .newsModule(new NewsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_news; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mIvMenu.setImageResource(R.mipmap.ic_launcher);
        mBtnToolbarMenu.setOnClickListener(view -> {
            // 跳转到搜索页面
            ARouter.getInstance().build(ARoutePath.PATH_SEARCH).navigation();
        });

        titles = new String[]{"推荐", "国内新闻", "国际新闻", "时评"};
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
        mTab.setupWithViewPager(mViewPager);
    }
}
