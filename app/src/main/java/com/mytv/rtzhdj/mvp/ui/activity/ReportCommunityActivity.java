package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.di.component.DaggerReportCommunityComponent;
import com.mytv.rtzhdj.di.module.ReportCommunityModule;
import com.mytv.rtzhdj.mvp.contract.ReportCommunityContract;
import com.mytv.rtzhdj.mvp.presenter.ReportCommunityPresenter;

import com.mytv.rtzhdj.R;


import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 报到社区界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-21
 * @update
 */
@Route(path = ARoutePath.PATH_REPORT_COMMUNITY)
public class ReportCommunityActivity extends BaseActivity<ReportCommunityPresenter> implements ReportCommunityContract.View {


    /** 加载进度条 */
    private SweetAlertDialog pDialog;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerReportCommunityComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .reportCommunityModule(new ReportCommunityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_report_community; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {

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


}
