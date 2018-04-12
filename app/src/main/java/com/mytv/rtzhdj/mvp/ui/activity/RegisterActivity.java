package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.di.component.DaggerRegisterComponent;
import com.mytv.rtzhdj.di.module.RegisterModule;
import com.mytv.rtzhdj.mvp.contract.RegisterContract;
import com.mytv.rtzhdj.mvp.presenter.RegisterPresenter;

import com.mytv.rtzhdj.R;


import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 注册界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-20
 * @update 2018-3-21    填充UI布局
 */
@Route(path = ARoutePath.PATH_REGISTER)
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;

    @BindView(R.id.edt_mobile_phone)
    EditText mEdtMobilePhone;
    @BindView(R.id.edt_community)
    EditText mEdtCommunity;
    @BindView(R.id.edt_password)
    EditText mEdtPassword;
    @BindView(R.id.edt_password2)
    EditText mEdtPassword2;
    @BindView(R.id.edt_vertify_code)
    EditText mEdtVertifyCode;
    @BindView(R.id.btn_get_code)
    Button mBtnGetVertifyCode;
    @BindView(R.id.chk_agree)
    CheckBox mChkAgree;
    @BindView(R.id.tv_link)
    TextView mTvLink;
    @BindView(R.id.btn_register)
    net.qiujuer.genius.ui.widget.Button mBtnRegister;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerRegisterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .registerModule(new RegisterModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_register; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);

        mTvLink.setOnClickListener(view -> goWebviewActivity());
        mBtnGetVertifyCode.setOnClickListener(view -> mPresenter.callMethodOfGetCode(
                mEdtVertifyCode.getText().toString().trim()));
        mBtnRegister.setOnClickListener(view -> mPresenter.callMethodOfDoRegister(
                mEdtMobilePhone.getText().toString().trim(),
                mEdtCommunity.getText().toString().trim(),
                mEdtPassword.getText().toString().trim(),
                mEdtPassword2.getText().toString().trim()));
        mChkAgree.setOnCheckedChangeListener((CompoundButton compoundButton, boolean b) ->
            setBtnRegisterBg(b));

        // 获取 用户类别
        mPresenter.callMethodOfGetUserCategory(false);

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
    public void setBtnRegisterBg(boolean isChecked) {
        if (isChecked)
            mBtnRegister.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        else
            mBtnRegister.setBackgroundColor(getResources().getColor(R.color.grey_500));
    }

    @Override
    public void goWebviewActivity() {
        ARouter.getInstance().build(ARoutePath.PATH_WEBVIEW)
                .withString("title", "用户注册协议").navigation();
    }
}
