package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
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
import com.mytv.rtzhdj.di.component.DaggerUpdateInfoComponent;
import com.mytv.rtzhdj.di.module.UpdateInfoModule;
import com.mytv.rtzhdj.mvp.contract.UpdateInfoContract;
import com.mytv.rtzhdj.mvp.presenter.UpdateInfoPresenter;

import com.mytv.rtzhdj.R;


import net.qiujuer.genius.ui.widget.EditText;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 更新信息界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-28
 * @update
 */
@Route(path = ARoutePath.PATH_UPDATE_INFO)
public class UpdateInfoActivity extends BaseActivity<UpdateInfoPresenter> implements UpdateInfoContract.View {

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
    @BindView(R.id.edt_content)
    EditText mEdtContent;

    @Autowired
    String hint;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerUpdateInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .updateInfoModule(new UpdateInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        return R.layout.activity_update_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);

        mTvTitle.setText(hint);
        mEdtContent.setHint(hint);
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
