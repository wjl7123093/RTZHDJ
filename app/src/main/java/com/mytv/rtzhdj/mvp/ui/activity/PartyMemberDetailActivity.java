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
import com.alibaba.android.arouter.utils.TextUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.data.entity.PartyMienEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;
import com.mytv.rtzhdj.di.component.DaggerPartyMemberDetailComponent;
import com.mytv.rtzhdj.di.module.PartyMemberDetailModule;
import com.mytv.rtzhdj.mvp.contract.PartyMemberDetailContract;
import com.mytv.rtzhdj.mvp.presenter.PartyMemberDetailPresenter;

import com.mytv.rtzhdj.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import org.raphets.roundimageview.RoundImageView;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 党员详情 界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-3-24
 * @update
 */
@Route(path = ARoutePath.PATH_PARTY_MEMBER_DETAIL)
public class PartyMemberDetailActivity extends BaseActivity<PartyMemberDetailPresenter> implements PartyMemberDetailContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;
    @BindView(R.id.toolbar_menu_tv)
    TextView mTvMenu;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;

    @BindView(R.id.iv_header)
    RoundImageView mIvHeader;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_party_branch)
    TextView mTvPartyBranch;
    @BindView(R.id.tv_contacts)
    TextView mTvContacts;
    @BindView(R.id.tv_other_contacts)
    TextView mTvOtherContacts;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_qq)
    TextView mTvQQ;
    @BindView(R.id.tv_email)
    TextView mTvEmail;

    @Autowired
    int id;
    @Autowired
    String name;

    /** 加载进度条 */
    private SweetAlertDialog pDialog;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerPartyMemberDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .partyMemberDetailModule(new PartyMemberDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        return R.layout.activity_party_member_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);
        mTvMenu.setVisibility(View.VISIBLE);

        mTvMenu.setText("私信TA");
        mTvMenu.setOnClickListener(view -> {
            ARouter.getInstance().build(ARoutePath.PATH_SEND_MSG)
                    .withInt("id", id)
                    .withString("name", name).navigation();
        });

        initRefreshLayout();
        // 获取党员详情
        mPresenter.callMethodOfGetPartyMemberDetails(id, false);
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

    private void initRefreshLayout() {
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadmore(false);
    }


    @Override
    public void loadData(PartyMienEntity memberInfo) {
        mTvName.setText(memberInfo.getTitle());
        mTvPartyBranch.setText(memberInfo.getPublishmentSystemName());
        mTvContacts.setText(memberInfo.getTelePhone());
        mTvOtherContacts.setText(memberInfo.getLinkPhone());
        mTvAddress.setText(memberInfo.getAddress());
        mTvQQ.setText(memberInfo.getQQ());
        mTvEmail.setText(memberInfo.getEmail());

        if (!TextUtils.isEmpty(memberInfo.getAllImgUrl())) {
            ImageLoader.getInstance().showImage(PartyMemberDetailActivity.this, mIvHeader, memberInfo.getAllImgUrl());
        }
    }
}
