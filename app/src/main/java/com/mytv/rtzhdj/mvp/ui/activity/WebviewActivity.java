package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.di.component.DaggerWebviewComponent;
import com.mytv.rtzhdj.di.module.WebviewModule;
import com.mytv.rtzhdj.mvp.contract.WebviewContract;
import com.mytv.rtzhdj.mvp.presenter.WebviewPresenter;

import com.mytv.rtzhdj.R;


import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * WAP加载界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-28
 * @update
 */
@Route(path = ARoutePath.PATH_WEBVIEW)
public class WebviewActivity extends BaseActivity<WebviewPresenter> implements WebviewContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;

    @BindView(R.id.webView)
    WebView mWebView;

    @Autowired
    String title;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerWebviewComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .webviewModule(new WebviewModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        return R.layout.activity_webview; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);

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
    protected void onResume() {
        super.onResume();
        mTvToolbarTitle.setText(title);
    }
}
