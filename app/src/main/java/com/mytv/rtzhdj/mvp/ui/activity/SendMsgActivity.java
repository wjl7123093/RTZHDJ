package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.di.component.DaggerSendMsgComponent;
import com.mytv.rtzhdj.di.module.SendMsgModule;
import com.mytv.rtzhdj.mvp.contract.SendMsgContract;
import com.mytv.rtzhdj.mvp.presenter.SendMsgPresenter;

import com.mytv.rtzhdj.R;


import net.qiujuer.genius.ui.widget.Button;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 发送私信界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-3-24
 * @update
 */
@Route(path = ARoutePath.PATH_SEND_MSG)
public class SendMsgActivity extends BaseActivity<SendMsgPresenter> implements SendMsgContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;

    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.edt_theme)
    EditText mEdtTheme;
    @BindView(R.id.edt_msg)
    EditText mEdtMsg;
    @BindView(R.id.btn_ok)
    Button mBtnOK;

    @Autowired
    int id;
    @Autowired
    String name;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerSendMsgComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .sendMsgModule(new SendMsgModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        return R.layout.activity_send_msg; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);

        mTvName.setText(name);
        mBtnOK.setOnClickListener(view -> mPresenter.callMethodOfPostSendMessage(
                8, id, mEdtTheme.getText().toString().trim(), mEdtMsg.getText().toString().trim()));
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
        mTvToolbarTitle.setText("私信" + name);
    }
}
