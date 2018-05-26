package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.mytv.rtzhdj.app.data.entity.WishDetailEntity;
import com.mytv.rtzhdj.di.component.DaggerWishDetailComponent;
import com.mytv.rtzhdj.di.module.WishDetailModule;
import com.mytv.rtzhdj.mvp.contract.WishDetailContract;
import com.mytv.rtzhdj.mvp.presenter.WishDetailPresenter;

import com.mytv.rtzhdj.R;


import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import lib.kingja.switchbutton.SwitchMultiButton;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 愿望详情 界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-28
 * @update
 */
@Route(path = ARoutePath.PATH_WISH_DETAIL)
public class WishDetailActivity extends BaseActivity<WishDetailPresenter> implements WishDetailContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;
    @BindView(R.id.toolbar_menu_tv)
    TextView mTvToolbarMenu;

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_datetime)
    TextView mTvDatetime;
    @BindView(R.id.tv_mobile_phone)
    TextView mTvMobile;
    @BindView(R.id.switchmultibutton)
    SwitchMultiButton mSwitchButton;
    @BindView(R.id.tv_content)
    TextView mTvContent;

    @Autowired
    int wishId;

    private WishDetailEntity mWishDetailEntity;

    /** 加载进度条 */
    private SweetAlertDialog pDialog;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerWishDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .wishDetailModule(new WishDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        return R.layout.activity_wish_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);
        mTvToolbarMenu.setVisibility(View.VISIBLE);
        mTvToolbarMenu.setText("编辑");

        mPresenter.setActivity(WishDetailActivity.this);
        mPresenter.callMethodOfPostWishDetail(wishId, false);

        // switchbutton 切换
        mSwitchButton.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                switch (position) {
                    case 0:	// TA的心愿
                        mTvContent.setText(mWishDetailEntity.getContent());
                        break;
                    case 1:	// 谁实现了
                        mTvContent.setText("");
                        break;
                }

//				Toast.makeText(PunishListActivity.this, tabText, Toast.LENGTH_SHORT).show();
            }
        });
        // 拨打电话
        mTvMobile.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(mWishDetailEntity.getPhone())) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + mWishDetailEntity.getPhone());
                intent.setData(data);
                startActivity(intent);
            }
        });
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
    public void loadData(WishDetailEntity wishDetailEntity) {
        this.mWishDetailEntity = wishDetailEntity;
        mTvTitle.setText(wishDetailEntity.getWishPublisher() + "的心愿");
        mTvDatetime.setText("许愿时间  " + wishDetailEntity.getWishTime());
        mTvMobile.setText("联系电话  " + wishDetailEntity.getPhone());
        mTvContent.setText(mWishDetailEntity.getContent());

    }
}
