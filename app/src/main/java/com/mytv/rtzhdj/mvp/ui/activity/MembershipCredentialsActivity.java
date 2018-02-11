package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.di.component.DaggerMembershipCredentialsComponent;
import com.mytv.rtzhdj.di.module.MembershipCredentialsModule;
import com.mytv.rtzhdj.mvp.contract.MembershipCredentialsContract;
import com.mytv.rtzhdj.mvp.presenter.MembershipCredentialsPresenter;

import com.mytv.rtzhdj.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 组织关系 界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-11
 * @update
 */
@Route(path = ARoutePath.PATH_MEMBERSHIP_CREDENTIALS)
public class MembershipCredentialsActivity extends BaseActivity<MembershipCredentialsPresenter> implements MembershipCredentialsContract.View {


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMembershipCredentialsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .membershipCredentialsModule(new MembershipCredentialsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_membership_credentials; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
