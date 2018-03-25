package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.di.component.DaggerDoubleReportingComponent;
import com.mytv.rtzhdj.di.module.DoubleReportingModule;
import com.mytv.rtzhdj.mvp.contract.DoubleReportingContract;
import com.mytv.rtzhdj.mvp.presenter.DoubleReportingPresenter;

import com.mytv.rtzhdj.R;


import net.qiujuer.genius.ui.widget.Button;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 双报到界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-21
 * @update
 */
@Route(path = ARoutePath.PATH_DOUBLE_REPORTING)
public class DoubleReportingActivity extends BaseActivity<DoubleReportingPresenter> implements DoubleReportingContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;

    @BindView(R.id.edt_community)
    EditText mEdtCommunity;
    @BindView(R.id.tv_choose_community)
    TextView mTvChooseCommunity;
    @BindView(R.id.btn_ok)
    Button mBtnOk;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerDoubleReportingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .doubleReportingModule(new DoubleReportingModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_double_reporting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);

        mTvChooseCommunity.setOnClickListener(view -> {});
        mBtnOk.setOnClickListener(view -> {
            mPresenter.callMethodOfDoDoubleReport("member_id", "community_id");
        });

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


}
