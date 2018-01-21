package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.di.component.DaggerMyDonationComponent;
import com.mytv.rtzhdj.di.module.MyDonationModule;
import com.mytv.rtzhdj.mvp.contract.MyDonationContract;
import com.mytv.rtzhdj.mvp.presenter.MyDonationPresenter;

import com.mytv.rtzhdj.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 我的捐赠界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-21
 * @update
 */
@Route(path = ARoutePath.PATH_MY_DONATION)
public class MyDonationActivity extends BaseActivity<MyDonationPresenter> implements MyDonationContract.View {


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMyDonationComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myDonationModule(new MyDonationModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_my_donation; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {

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
