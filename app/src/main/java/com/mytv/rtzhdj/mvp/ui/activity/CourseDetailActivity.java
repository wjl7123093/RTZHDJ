package com.mytv.rtzhdj.mvp.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
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
import com.mytv.rtzhdj.app.data.api.Api;
import com.mytv.rtzhdj.app.data.entity.CoursewareDetailEntity;
import com.mytv.rtzhdj.app.utils.TimeTools;
import com.mytv.rtzhdj.di.component.DaggerCourseDetailComponent;
import com.mytv.rtzhdj.di.module.CourseDetailModule;
import com.mytv.rtzhdj.mvp.contract.CourseDetailContract;
import com.mytv.rtzhdj.mvp.presenter.CourseDetailPresenter;
import com.mytv.rtzhdj.mvp.ui.widget.WebProgressBar;

import net.qiujuer.genius.ui.widget.Button;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 必修课／选修课／微党课 课程学习界面
 * 包含 图文 内容
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

    @BindView(R.id.ll_time_counter)
    LinearLayout mLlTimeCounter;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.webProgressBar)
    WebProgressBar mWebProgressBar;

    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_scores)
    TextView mTvScores;
    @BindView(R.id.btn_done)
    Button mBtnDone;

    @Autowired
    String title;
    @Autowired
    int nodeId;
    @Autowired
    int articleId;
    @Autowired
    int courseType; // 1 已学习， 2 未学习

    private CountDownTimer mCountDownTimer;
    private static final long MAX_TIME = 62 * 1000;
    private long curTime = 0;
    private boolean isPause = false;    // 是否暂停
    private boolean isFinish = false;   // 是否结束

    private AlertDialog dialog0 = null;
    CoursewareDetailEntity mCoursewareDetailEntity = null;

    /** 加载进度条 */
    private SweetAlertDialog pDialog;


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
        // [ForResult]
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("title");
        nodeId = bundle.getInt("nodeId");
        articleId = bundle.getInt("articleId");
        courseType = bundle.getInt("courseType");

        mBtnToolbarMenu.setVisibility(View.GONE);

        mPresenter.setActivity(CourseDetailActivity.this);
        mPresenter.initWebview(mWebView, mWebProgressBar);
        mPresenter.getCourseDetail(Api.APP_COURSE_DOMAIN + "nodeId=" + nodeId + "&id=" + articleId);

        // 判断学习状态
        if (courseType == 1) {  // 已学习
            mLlTimeCounter.setVisibility(View.GONE);
            mBtnDone.setVisibility(View.GONE);
        }

        // 获取课件详情数据
        mPresenter.callMethodOfGetCoursewareDetail(articleId, false);

        mBtnDone.setOnClickListener(view -> {
            if (isFinish)
                killMyself();
            else
                showMessage("尚未学习完成");
        });

//        initCountDownTimer(MAX_TIME);
//        mCountDownTimer.start();
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
    protected void onResume() {
        super.onResume();
        mTvToolbarTitle.setText(title);

        if (courseType == 2) { // 未学习 计时
            // 继续倒计时
            if (curTime != 0 && isPause) {
                //将上次当前剩余时间作为新的时长
                initCountDownTimer(curTime);
                mCountDownTimer.start();
                isPause = false;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (courseType == 2) { // 未学习 计时
            // 暂停倒计时
            if (!isPause) {
                isPause = true;
                mCountDownTimer.cancel();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (null != mCountDownTimer) {
            // 取消
            isPause = false;
            mCountDownTimer.cancel();
        }
    }

    @Override
    public void loadWap(String url) {
        mWebView.loadUrl(url);
    }

    @Override
    public void setWebviewProgress(int progress) {
        mWebProgressBar.setProgress(progress);
    }

    @Override
    public void showData(CoursewareDetailEntity coursewareDetailEntity) {
        mCoursewareDetailEntity = coursewareDetailEntity;
        if (courseType == 2) { // 未学习 计时
            initCountDownTimer(coursewareDetailEntity.getStudyTime() * 1000);
            mCountDownTimer.start();
        }
        mTvScores.setText(" 可获取" + coursewareDetailEntity.getIntegral() + "积分哦！");
    }

    @Override
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CourseDetailActivity.this);
        View dialogView = LayoutInflater.from(CourseDetailActivity.this).inflate(R.layout.dialog_study_finish, null);
        android.widget.Button mBtnStudy = dialogView.findViewById(R.id.btn_study);
        android.widget.Button mBtnExit = dialogView.findViewById(R.id.btn_exit);
        TextView mTvScores = dialogView.findViewById(R.id.tv_scores);
        mTvScores.setText("+" + mCoursewareDetailEntity.getIntegral());

        // 保存积分
        DataHelper.setIntergerSF(CourseDetailActivity.this, SharepreferenceKey.KEY_LOGIN_INTEGRAL,
                DataHelper.getIntergerSF(CourseDetailActivity.this, SharepreferenceKey.KEY_LOGIN_INTEGRAL) + mCoursewareDetailEntity.getIntegral());

        mBtnStudy.setOnClickListener(view -> {
            dialog0.dismiss();
        });
        mBtnExit.setOnClickListener(view -> {
            dialog0.dismiss();
            killMyself();
        });
        builder.setView(dialogView);
        builder.create();
        dialog0 = builder.show();
    }

    public void initCountDownTimer(long millisInFuture) {
        mCountDownTimer = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                curTime = millisUntilFinished;
                mTvTime.setText(TimeTools.getCountTimeByLong(millisUntilFinished));
                isPause = false;
            }

            public void onFinish() {
                mTvTime.setText("00:00:00");
                isFinish = true;
                // 上传学习结果
                mPresenter.callMethodOfPostStudyClass(
                        DataHelper.getIntergerSF(CourseDetailActivity.this, SharepreferenceKey.KEY_USER_ID),
                        nodeId, articleId, false);
                showDialog();
            }
        };
    }


}
