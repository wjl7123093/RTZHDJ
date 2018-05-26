package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.data.entity.PartyColumnsEntity;
import com.mytv.rtzhdj.app.data.entity.SpecialColumnsEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;
import com.mytv.rtzhdj.di.component.DaggerTopicDetailComponent;
import com.mytv.rtzhdj.di.module.TopicDetailModule;
import com.mytv.rtzhdj.mvp.contract.TopicDetailContract;
import com.mytv.rtzhdj.mvp.presenter.TopicDetailPresenter;
import com.mytv.rtzhdj.mvp.ui.fragment.SpecialSubDetailFragment;
import com.mytv.rtzhdj.mvp.ui.fragment.WebviewFragment;
import com.mytv.rtzhdj.mvp.ui.widget.AppBarStateChangeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 党建专题详情界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-20
 * @update
 */
@Route(path = ARoutePath.PATH_TOPIC_DETAIL)
public class TopicDetailActivity extends BaseActivity<TopicDetailPresenter> implements TopicDetailContract.View {

    @BindView(R.id.main_appbar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.main_collapsing)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.main_backdrop)
    ImageView mIvBackground;

    @BindView(R.id.tab_channel)
    TabLayout mTab;
    @BindView(R.id.vp_content)
    ViewPager mViewPager;

    String[] titles;

    @Autowired
    int nodeId;

    /** 加载进度条 */
    private SweetAlertDialog pDialog;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerTopicDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .topicDetailModule(new TopicDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        return R.layout.activity_topic_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
//        titles = new String[]{"简介", "工作动态", "文件解读", "文件制度"};
//        initTab();

        mCollapsingToolbar.setTitleEnabled(false);
        // 获取 专题二级栏目
        mPresenter.callMethodOfGetSpecialClass(nodeId, false);

        // 监听 Appbarlayout 的滑动状态
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if ( state == State.EXPANDED ) {
                    //展开状态
                    mToolbar.setTitle("");
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    mToolbar.setTitle("专题详情");
                } else {
                    //中间状态
                    mToolbar.setTitle("");
                }

            }
        });
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


    private void initToolBar(String title) {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

//    private void initTab() {
//        final List<Fragment> fragments = new ArrayList<>();
//        for (int i = 0; i < titles.length; i++) {
//            fragments.add(ContentFragment.newInstance(i));
//        }
//
//
//        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
//            @Override
//            public Fragment getItem(int position) {
//                return fragments.get(position);
//            }
//
//            @Override
//            public int getCount() {
//                return titles.length;
//            }
//
//            @Override
//            public CharSequence getPageTitle(int position) {
//                return titles[position];
//            }
//        });
//        mTab.setupWithViewPager(mViewPager);
////        mTab.setTabTextColors(Color.BLACK, Color.RED);
//    }

    @Override
    public void initBackground(SpecialColumnsEntity specialColumnsEntity) {
//        ImageLoader.getInstance().showImage(TopicDetailActivity.this, mIvBackground,
//                Api.APP_IMAGE_DOMAIN + specialColumnsEntity.getImageUrl().replace("@", ""));
        ImageLoader.getInstance().showImage(TopicDetailActivity.this, mIvBackground,
                specialColumnsEntity.getAllImgUrl());
    }

    @Override
    public void initTab(SpecialColumnsEntity specialColumnsEntity) {
        List<PartyColumnsEntity> partyColumnsList = specialColumnsEntity.getSubObjs();
        PartyColumnsEntity columnsEntity = new PartyColumnsEntity();
        columnsEntity.setTitle("简介");
        columnsEntity.setNodeId(0);
        partyColumnsList.add(0, columnsEntity);

        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(WebviewFragment.newInstance(specialColumnsEntity.getContent()));
        for (int i = 1; i < partyColumnsList.size(); i++) {
            fragments.add(SpecialSubDetailFragment.newInstance(partyColumnsList.get(i).getNodeId()));
        }

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return partyColumnsList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return partyColumnsList.get(position).getTitle();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                // 注释掉下面那行，解决 滑动卡顿 问题
//                super.destroyItem(container, position, object);
            }
        });
        mTab.setupWithViewPager(mViewPager);
//        mTab.setTabTextColors(Color.BLACK, Color.RED);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initToolBar("专题详情");
//        initTab();
    }
}
