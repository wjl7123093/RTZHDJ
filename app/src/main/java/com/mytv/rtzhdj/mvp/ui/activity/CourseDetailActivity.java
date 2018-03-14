package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.di.component.DaggerCourseDetailComponent;
import com.mytv.rtzhdj.di.module.CourseDetailModule;
import com.mytv.rtzhdj.mvp.contract.CourseDetailContract;
import com.mytv.rtzhdj.mvp.presenter.CourseDetailPresenter;

import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.mvp.ui.widget.WebProgressBar;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 必修课／选修课／微党课 课程学习界面
 * 包含 视频／图文 内容
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-20
 * @update 2018-3-14    填充UI布局
 */
@Route(path = ARoutePath.PATH_COURSE_DETAIL)
public class CourseDetailActivity extends BaseActivity<CourseDetailPresenter> implements CourseDetailContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;

    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.webProgressBar)
    WebProgressBar mWebProgressBar;

    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_scores)
    TextView mTvScores;

    @Autowired
    String title;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerCourseDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .courseDetailModule(new CourseDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        return R.layout.activity_course_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);

        mPresenter.setActivity(CourseDetailActivity.this);
        mPresenter.initWebview(mWebView, mWebProgressBar);
        mPresenter.getCourseDetail("http://www.tencent.com/");
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
    protected void onResume() {
        super.onResume();
        mTvToolbarTitle.setText(title);
    }

    @Override
    public void loadWap(String url) {
        mWebView.loadUrl(url);
    }

    @Override
    public void setWebviewProgress(int progress) {
        mWebProgressBar.setProgress(progress);
    }


}
