package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
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
import com.mytv.rtzhdj.app.data.DataServer;
import com.mytv.rtzhdj.app.data.entity.PartyMemberEntity;
import com.mytv.rtzhdj.di.component.DaggerMembershipCredentialsComponent;
import com.mytv.rtzhdj.di.module.MembershipCredentialsModule;
import com.mytv.rtzhdj.mvp.contract.MembershipCredentialsContract;
import com.mytv.rtzhdj.mvp.presenter.MembershipCredentialsPresenter;

import com.mytv.rtzhdj.R;


import net.qiujuer.genius.ui.widget.Button;

import org.raphets.roundimageview.RoundImageView;

import butterknife.BindView;

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

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;

    @BindView(R.id.iv_header)
    RoundImageView mIvHeader;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_duty)
    TextView mTvDuty;
    @BindView(R.id.tv_party_branch)
    TextView mTvPartyBranch;
    @BindView(R.id.btn_transfer)
    Button mBtnTransfer;

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
        mBtnToolbarMenu.setVisibility(View.GONE);

        mPresenter.setActivity(MembershipCredentialsActivity.this);
        mPresenter.initHeader(DataServer.getPartyMemberData(1).get(0), mIvHeader, mTvName,
                mTvDuty, mTvPartyBranch);

        mBtnTransfer.setOnClickListener(view -> {
            ARouter.getInstance().build(ARoutePath.PATH_CONNECTION_TRANSFER).navigation();
        });
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
