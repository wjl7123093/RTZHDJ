package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.SharepreferenceKey;
import com.mytv.rtzhdj.app.data.entity.MembershipEntity;
import com.mytv.rtzhdj.app.utils.ImageLoader;
import com.mytv.rtzhdj.di.component.DaggerMembershipCredentialsComponent;
import com.mytv.rtzhdj.di.module.MembershipCredentialsModule;
import com.mytv.rtzhdj.mvp.contract.MembershipCredentialsContract;
import com.mytv.rtzhdj.mvp.presenter.MembershipCredentialsPresenter;
import com.mytv.rtzhdj.mvp.ui.adapter.MembershipAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import net.qiujuer.genius.ui.widget.Button;

import org.raphets.roundimageview.RoundImageView;

import java.util.List;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

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
    @BindView(R.id.tv_no_data)
    TextView mTvNoData;
    @BindView(R.id.btn_transfer)
    Button mBtnTransfer;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private MembershipAdapter mAdapter;
    private static final int PAGE_SIZE = 10;

    /** 加载进度条 */
    private SweetAlertDialog pDialog;


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

        // 初始化数据
        mTvName.setText("姓名: " +
                DataHelper.getStringSF(MembershipCredentialsActivity.this, SharepreferenceKey.KEY_LOGIN_USER_NAME));
        String duty = "";
        switch (DataHelper.getIntergerSF(MembershipCredentialsActivity.this, SharepreferenceKey.KEY_LOGIN_USER_TYPE)) {
            case 1: // 党员
                duty = "党员";
                break;
            case 2: // 预备党员
                duty = "预备党员";
                break;
            case 3: // 入党积极分子
                duty = "入党积极分子";
                break;
            case 4: // 群众
                duty = "群众";
                break;
        }
        mTvDuty.setText("职务: " + duty);
        mTvPartyBranch.setText("所属支部: " +
                DataHelper.getStringSF(MembershipCredentialsActivity.this, SharepreferenceKey.KEY_PUBLISHMENT_SYSTEM_NAME));
        if (!TextUtils.isEmpty(DataHelper.getStringSF(MembershipCredentialsActivity.this, SharepreferenceKey.KEY_LOGIN_HEADER_URL)))
            ImageLoader.getInstance().showImage(MembershipCredentialsActivity.this, mIvHeader,
                    DataHelper.getStringSF(MembershipCredentialsActivity.this, SharepreferenceKey.KEY_LOGIN_HEADER_URL));

        mPresenter.initRecyclerView(mRecyclerView);
        initRefreshLayout();

        mBtnTransfer.setOnClickListener(view -> {
            ARouter.getInstance().build(ARoutePath.PATH_CONNECTION_TRANSFER).navigation();
        });

        // 获取组织关系转接列表
//      DataHelper.getIntergerSF(MembershipCredentialsActivity.this, SharepreferenceKey.KEY_USER_ID)
        mPresenter.callMethodOfGetUserTransList(DataHelper.getIntergerSF(
                MembershipCredentialsActivity.this, SharepreferenceKey.KEY_USER_ID), false);
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
    public void loadData(List<MembershipEntity> membershipList) {
        if (membershipList.size() > 0) {
            mTvNoData.setVisibility(View.GONE);
        }

        initAdapter(membershipList);
    }


    private void initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
//        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    private void initAdapter(List<MembershipEntity> membershipList) {
        mAdapter = new MembershipAdapter(MembershipCredentialsActivity.this, membershipList);
        mAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showMessage("" + Integer.toString(position));
            }
        });

    }
}
