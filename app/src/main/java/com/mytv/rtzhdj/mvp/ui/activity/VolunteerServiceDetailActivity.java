package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.SharepreferenceKey;
import com.mytv.rtzhdj.app.data.entity.VolunteerDetailEntity;
import com.mytv.rtzhdj.di.component.DaggerVolunteerServiceDetailComponent;
import com.mytv.rtzhdj.di.module.VolunteerServiceDetailModule;
import com.mytv.rtzhdj.mvp.contract.VolunteerServiceDetailContract;
import com.mytv.rtzhdj.mvp.presenter.VolunteerServiceDetailPresenter;
import com.mytv.rtzhdj.mvp.ui.fragment.VolunteerServiceDetailFragment;

import net.qiujuer.genius.ui.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

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
    @BindView(R.id.ratingbar)
    RatingBar mRatingBar;
    @BindView(R.id.tv_scores)
    TextView mTvScores;

    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.vp_content)
    ViewPager mViewPager;

    @BindView(R.id.btn_star)
    Button mBtnStar;
    @BindView(R.id.btn_is_over)
    Button mBtnIsOver;

    @Autowired
    int nodeId;
    @Autowired
    int id;

    private ArrayList<Fragment> mFragments;
    private String[] titles;
    private String mTitle;      // 标题栏标题
    private int mDigs = 0;      // 点赞数

    /** 加载进度条 */
    private SweetAlertDialog pDialog;


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
        ARouter.getInstance().inject(this);
        return R.layout.activity_volunteer_service_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);

        titles = new String[]{"活动详情", "活动回顾"};
        initTab();

        mBtnStar.setOnClickListener(view -> mPresenter.callMethodOfPostDoDig(nodeId, id, false));
        mBtnIsOver.setOnClickListener(view -> {
            if (mBtnIsOver.getText().equals("正在报名")) {
                ARouter.getInstance().build(ARoutePath.PATH_MY_JOIN)
                        .withInt("contentId", id).navigation();
            } else {
               showMessage("已报名，无需再次报名");
            }});

        // 获取 志愿服务详情
        mPresenter.callMethodOfGetVolunteerServiceDetail(id, DataHelper.getIntergerSF(
                VolunteerServiceDetailActivity.this, SharepreferenceKey.KEY_USER_ID), false);
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


    @Override
    public void loadData(VolunteerDetailEntity volunteerDetailEntity) {
        mTvTitle.setText(volunteerDetailEntity.getTitle());
        mTvEnrollTime.setText(volunteerDetailEntity.getEnrollEndDate().split("T")[0] + "截至");
        mTvEventTime.setText(volunteerDetailEntity.getStartDate().split("T")[0]
                + " 至 " + volunteerDetailEntity.getEndDate().split("T")[0]);
        mTvEventSite.setText(volunteerDetailEntity.getAddress());
        mTvEnrollment.setText(volunteerDetailEntity.getSignedup()
                + "/" + volunteerDetailEntity.getEnrollCount());
        mTvGrade.setText(volunteerDetailEntity.getScore() + "分");
        mTvScores.setText(volunteerDetailEntity.getJoinScore() + "");
        mRatingBar.setRating(volunteerDetailEntity.getScore());

        mDigs = volunteerDetailEntity.getDigs();
        mBtnStar.setText("点赞(" + volunteerDetailEntity.getDigs() + ")");
        mBtnIsOver.setText(volunteerDetailEntity.getIfJoin() == 0 ? "正在报名" : "已报名");
    }

    @Override
    public void changeDigsStatus() {
        mBtnStar.setText("点赞(" + ++mDigs + ")");
    }
}
