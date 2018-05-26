package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.di.component.DaggerFindPwdComponent;
import com.mytv.rtzhdj.di.module.FindPwdModule;
import com.mytv.rtzhdj.mvp.contract.FindPwdContract;
import com.mytv.rtzhdj.mvp.presenter.FindPwdPresenter;

import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.mvp.ui.widget.CodeView;


import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.EditText;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 找回密码界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-3-21
 * @update
 */
@Route(path = ARoutePath.PATH_FIND_PWD)
public class FindPwdActivity extends BaseActivity<FindPwdPresenter> implements FindPwdContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;

    @BindView(R.id.edt_acc)
    EditText mEdtAcc;
    @BindView(R.id.edt_code)
    EditText mEdtCode;
    @BindView(R.id.codeview)
    CodeView mCodeView;
    @BindView(R.id.btn_next)
    Button mBtnNext;

    /** 加载进度条 */
    private SweetAlertDialog pDialog;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerFindPwdComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .findPwdModule(new FindPwdModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_find_pwd; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);

        mCodeView.setOnClickListener(view -> mCodeView.refresh(true, ""));
        mBtnNext.setOnClickListener(view -> goGetVertifyCodeActivity());
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
    public void goGetVertifyCodeActivity() {
        ARouter.getInstance().build(ARoutePath.PATH_GET_VERTIFY_CODE).navigation();
    }
}
