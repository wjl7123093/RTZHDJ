package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import com.mytv.rtzhdj.app.data.entity.HeaderIntegralEntity;
import com.mytv.rtzhdj.di.component.DaggerMyTaskComponent;
import com.mytv.rtzhdj.di.module.MyTaskModule;
import com.mytv.rtzhdj.mvp.contract.MyTaskContract;
import com.mytv.rtzhdj.mvp.presenter.MyTaskPresenter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.raphets.roundimageview.RoundImageView;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 我的任务界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-21
 * @update
 */
@Route(path = ARoutePath.PATH_MY_TASK)
public class MyTaskActivity extends BaseActivity<MyTaskPresenter> implements MyTaskContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;

    @BindView(R.id.iv_header)
    RoundImageView mIvHeader;
    @BindView(R.id.tv_scores)
    TextView mTvScores;
    @BindView(R.id.tv_power_num)
    TextView mTvPowerNum;
    @BindView(R.id.tv_differ_from)
    TextView mTvDifferFrom;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;

    @Autowired
    int Integeral;
    @Autowired
    int PlanValue;
    @Autowired
    int NextValue;

    /** 加载进度条 */
    private SweetAlertDialog pDialog;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMyTaskComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myTaskModule(new MyTaskModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        return R.layout.activity_my_task; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);

        initRefreshLayout();

        if (Integeral == -100) {
            // 获取头部信息
            mPresenter.callMethodOfGetMyScore(DataHelper.getIntergerSF(MyTaskActivity.this,
                    SharepreferenceKey.KEY_USER_ID), false);
        } else {

            mTvScores.setText("本月获得正能量值: " + Integeral);
//            mTvPowerNum.setText("您的正能量值: " + PlanValue);
            mTvDifferFrom.setText("距下一任务还差: " + NextValue + " 请继续加油!");
        }
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


    private void initRefreshLayout() {
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadmore(false);
    }


    @Override
    public void loadHeaderData(HeaderIntegralEntity headerIntegralEntity) {

        mTvScores.setText("本月获得正能量值: " + headerIntegralEntity.getIntegral());
//        mTvPowerNum.setText("您的正能量值: " + headerIntegralEntity.getPlanValue());
        mTvDifferFrom.setText("距下一任务还差: " + headerIntegralEntity.getNextValue() + " 请继续加油!");

    }
}
