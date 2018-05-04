package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.jess.arms.utils.DataHelper;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.SharepreferenceKey;
import com.mytv.rtzhdj.app.utils.StatusBarUtil;
import com.mytv.rtzhdj.di.component.DaggerStartComponent;
import com.mytv.rtzhdj.di.module.StartModule;
import com.mytv.rtzhdj.mvp.contract.StartContract;
import com.mytv.rtzhdj.mvp.presenter.StartPresenter;

import com.mytv.rtzhdj.R;


import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 启动界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-20
 * @update 2018-3-23    填充UI布局
 *         2018-5-4     修复 启动白屏
 */
@Route(path = ARoutePath.PATH_START)
public class StartActivity extends BaseActivity<StartPresenter> implements StartContract.View {

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerStartComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .startModule(new StartModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
//        StatusBarUtil.hideStarusBar(StartActivity.this);

        // 通过设置 AndroidManifest.xml 里设置 theme 来解决 启动白屏 问题
        new Handler().postDelayed(() -> {

            // 1 已登录
            if (1 == DataHelper.getIntergerSF(StartActivity.this, SharepreferenceKey.KEY_IS_LOGIN)) {
                ARouter.getInstance().build(ARoutePath.PATH_MAIN).navigation();
                StartActivity.this.finish();
            } else {    // 0 已注销
                ARouter.getInstance().build(ARoutePath.PATH_LOGIN).navigation();
                StartActivity.this.finish();
            }
        }, 2000);

        return 0; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
