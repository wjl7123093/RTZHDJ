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

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.di.component.DaggerGetVertifyCodeComponent;
import com.mytv.rtzhdj.di.module.GetVertifyCodeModule;
import com.mytv.rtzhdj.mvp.contract.GetVertifyCodeContract;
import com.mytv.rtzhdj.mvp.presenter.GetVertifyCodePresenter;

import com.mytv.rtzhdj.R;


import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.EditText;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 获取验证码界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-3-21
 * @update
 */
@Route(path = ARoutePath.PATH_GET_VERTIFY_CODE)
public class GetVertifyCodeActivity extends BaseActivity<GetVertifyCodePresenter> implements GetVertifyCodeContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.edt_code)
    EditText mEdtCode;
    @BindView(R.id.btn_get_code)
    android.widget.Button mBtnGetCode;
    @BindView(R.id.btn_next)
    Button mBtnNext;

    @Autowired
    String mobile;

    /** 加载进度条 */
    private SweetAlertDialog pDialog;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerGetVertifyCodeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .getVertifyCodeModule(new GetVertifyCodeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        return R.layout.activity_get_vertify_code; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);

        mBtnGetCode.setOnClickListener(view -> mPresenter.callMethodOfGetCode(mobile));
        mBtnNext.setOnClickListener(view -> goSetPwdActivity());
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
    public void goSetPwdActivity() {
        ARouter.getInstance().build(ARoutePath.PATH_SET_PWD).navigation();
    }
}
