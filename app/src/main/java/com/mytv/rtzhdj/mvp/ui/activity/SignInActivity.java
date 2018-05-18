package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.SharepreferenceKey;
import com.mytv.rtzhdj.di.component.DaggerSignInComponent;
import com.mytv.rtzhdj.di.module.SignInModule;
import com.mytv.rtzhdj.mvp.contract.SignInContract;
import com.mytv.rtzhdj.mvp.presenter.SignInPresenter;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 天天签到界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-21
 * @update 2018-2-28    填充UI布局
 */
@Route(path = ARoutePath.PATH_SIGN_IN)
public class SignInActivity extends BaseActivity<SignInPresenter> implements SignInContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;

    @BindView(R.id.tv_sign_big)
    TextView mTvSignBig;
    @BindView(R.id.tv_sign_status)
    TextView mTvSignStatus;
    @BindView(R.id.tv_scores_info)
    TextView mTvScoresInfo;
    @BindView(R.id.tv_sign_1)
    TextView mTvSign1;
    @BindView(R.id.tv_sign_2)
    TextView mTvSign2;
    @BindView(R.id.tv_sign_3)
    TextView mTvSign3;
    @BindView(R.id.tv_sign_4)
    TextView mTvSign4;
    @BindView(R.id.tv_sign_5)
    TextView mTvSign5;
    @BindView(R.id.tv_sign_6)
    TextView mTvSign6;
    @BindView(R.id.tv_sign_7)
    TextView mTvSign7;
    @BindView(R.id.tv_sign_days)
    TextView mTvSignDays;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerSignInComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .signInModule(new SignInModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_sign_in; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);

        mTvSignBig.setOnClickListener(view -> mPresenter.callMethodOfPostSignForScore(
                DataHelper.getIntergerSF(SignInActivity.this, SharepreferenceKey.KEY_USER_ID)
        ));

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
    public void changeStatus() {
        mTvSignStatus.setText("今日已签到");
        mTvScoresInfo.setText("你还差1天签到获" + 2 + "积分");
        mTvSignDays.setText("连续签到" + 1 + "天");
    }
}
