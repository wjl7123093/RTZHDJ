package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.di.component.DaggerVoteEntryDetailComponent;
import com.mytv.rtzhdj.di.module.VoteEntryDetailModule;
import com.mytv.rtzhdj.mvp.contract.VoteEntryDetailContract;
import com.mytv.rtzhdj.mvp.presenter.VoteEntryDetailPresenter;

import com.mytv.rtzhdj.R;


import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 投票作品详情界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-3-26
 * @update
 */
@Route(path = ARoutePath.PATH_VOTE_ENTRY_DETAIL)
public class VoteEntryDetailActivity extends BaseActivity<VoteEntryDetailPresenter> implements VoteEntryDetailContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerVoteEntryDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .voteEntryDetailModule(new VoteEntryDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_vote_entry_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {

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
