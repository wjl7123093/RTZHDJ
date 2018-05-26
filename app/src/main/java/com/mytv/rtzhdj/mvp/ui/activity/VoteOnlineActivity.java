package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.data.entity.VoteListEntity;
import com.mytv.rtzhdj.di.component.DaggerVoteOnlineComponent;
import com.mytv.rtzhdj.di.module.VoteOnlineModule;
import com.mytv.rtzhdj.mvp.contract.VoteOnlineContract;
import com.mytv.rtzhdj.mvp.presenter.VoteOnlinePresenter;

import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.mvp.ui.adapter.QuestionAdapter;
import com.mytv.rtzhdj.mvp.ui.fragment.VolunteerServiceFragment;
import com.mytv.rtzhdj.mvp.ui.fragment.VoteOnlineFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import me.weyye.library.colortrackview.ColorTrackTabLayout;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 在线投票界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-21
 * @update 2018-2-8     填充布局
 */
@Route(path = ARoutePath.PATH_VOTE_ONLINE)
public class VoteOnlineActivity extends BaseActivity<VoteOnlinePresenter> implements VoteOnlineContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;

    @BindView(R.id.tab_channel)
    ColorTrackTabLayout mTab;
    @BindView(R.id.vp_content)
    ViewPager mViewPager;

    String[] titles;

    /** 加载进度条 */
    private SweetAlertDialog pDialog;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerVoteOnlineComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .voteOnlineModule(new VoteOnlineModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_vote_online; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);

        titles = new String[]{"全部", "进行中", "已结束"};
        initTab();
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

    private void initTab() {
        final List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            fragments.add(VoteOnlineFragment.newInstance(i));
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

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                // 注释掉下面那行，解决 滑动卡顿 问题
//                super.destroyItem(container, position, object);
            }
        });
        mTab.setupWithViewPager(mViewPager);
    }

    @Override
    public void loadData(List<VoteListEntity> voteList, boolean update) {

    }
}
