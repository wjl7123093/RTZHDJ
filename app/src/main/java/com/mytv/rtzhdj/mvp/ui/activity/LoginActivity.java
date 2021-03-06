package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.utils.statusbar.StatusBarCompat;
import com.mytv.rtzhdj.di.component.DaggerLoginComponent;
import com.mytv.rtzhdj.di.module.LoginModule;
import com.mytv.rtzhdj.mvp.contract.LoginContract;
import com.mytv.rtzhdj.mvp.presenter.LoginPresenter;
import com.mytv.rtzhdj.mvp.ui.widget.ClearEditText;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 登录界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-20
 * @update
 */
@Route(path = ARoutePath.PATH_LOGIN)
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.edt_acc)
    ClearEditText mEdtAcc;
    @BindView(R.id.edt_pwd)
    ClearEditText mEdtPwd;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.tv_register)
    TextView mTvRegister;
    @BindView(R.id.tv_forget_pwd)
    TextView mTvForgetPwd;

    /** 加载进度条 */
    private SweetAlertDialog pDialog;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        // 设置状态栏透明
        StatusBarCompat.translucentStatusBar(LoginActivity.this, true);
        mPresenter.setActivity(LoginActivity.this);

        mBtnLogin.setOnClickListener(view -> {
            if (TextUtils.isEmpty(mEdtAcc.getText().toString().trim())
                    || TextUtils.isEmpty(mEdtPwd.getText().toString().trim())) {
                showMessage("账号或密码不能为空");
                return;
            }
            // 登录
            mPresenter.callMethodOfDoLogin(mEdtAcc.getText().toString().trim(),
                    mEdtPwd.getText().toString().trim());
        });
        mTvRegister.setOnClickListener(view -> goRegisterActivity());
        mTvForgetPwd.setOnClickListener(view -> goForgetPwdActivity());

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
    public void setError() {
        showMessage("账号或密码错误");
    }

    @Override
    public void goMainActivity() {
        ARouter.getInstance().build(ARoutePath.PATH_MAIN).navigation();
    }

    @Override
    public void goRegisterActivity() {
        ARouter.getInstance().build(ARoutePath.PATH_REGISTER).navigation();
    }

    @Override
    public void goForgetPwdActivity() {
        ARouter.getInstance().build(ARoutePath.PATH_FIND_PWD).navigation();
    }
}
