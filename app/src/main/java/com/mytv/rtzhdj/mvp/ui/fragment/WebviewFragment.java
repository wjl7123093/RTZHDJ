package com.mytv.rtzhdj.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.di.component.DaggerWebviewComponent;
import com.mytv.rtzhdj.di.module.WebviewModule;
import com.mytv.rtzhdj.mvp.contract.WebviewContract;
import com.mytv.rtzhdj.mvp.presenter.WebviewPresenter;

import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.mvp.ui.widget.WebProgressBar;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * WAP加载界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-21
 * @update
 */
public class WebviewFragment extends BaseFragment<WebviewPresenter> implements WebviewContract.View {

    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.webProgressBar)
    WebProgressBar mWebProgressBar;

    /** 加载进度条 */
    private SweetAlertDialog pDialog;


    public static WebviewFragment newInstance() {
        WebviewFragment fragment = new WebviewFragment();
        return fragment;
    }

    public static WebviewFragment newInstance(String data) {
        WebviewFragment fragment = new WebviewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerWebviewComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .webviewModule(new WebviewModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_webview, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mPresenter.initWebview(mWebView, mWebProgressBar);
//        mWebView.loadUrl("");
        mWebView.loadData(getArguments().getString("data"), "text/html; charset=UTF-8", null);
    }

    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传Message,通过what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事
     * <p>
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onCreate还没执行
     * setData里却调用了presenter的方法时,是会报空的,因为dagger注入是在onCreated方法中执行的,然后才创建的presenter
     * 如果要做一些初始化操作,可以不必让外部调setData,在initData中初始化就可以了
     *
     * @param data
     */

    @Override
    public void setData(Object data) {

    }


    @Override
    public void showLoading() {
        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
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

    }

    @Override
    public void setWebviewProgress(int progress) {

    }
}
