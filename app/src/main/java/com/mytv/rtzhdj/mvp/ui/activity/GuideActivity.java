package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.utils.StatusBarUtil;
import com.mytv.rtzhdj.di.component.DaggerGuideComponent;
import com.mytv.rtzhdj.di.module.GuideModule;
import com.mytv.rtzhdj.mvp.contract.GuideContract;
import com.mytv.rtzhdj.mvp.presenter.GuidePresenter;

import com.mytv.rtzhdj.R;


import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 引导界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-23
 * @update
 */
@Route(path = ARoutePath.PATH_GUIDE)
public class GuideActivity extends BaseActivity<GuidePresenter> implements GuideContract.View {

    @BindView(R.id.banner_guide_background)
    BGABanner mBackgroundBanner;
//    @BindView(R.id.banner_guide_foreground)
//    BGABanner mForegroundBanner;

    /** 加载进度条 */
    private SweetAlertDialog pDialog;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerGuideComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .guideModule(new GuideModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        StatusBarUtil.hideStarusBar(GuideActivity.this);
        return R.layout.activity_guide; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        setListener();
        processLogic();

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
    protected void onResume() {
        super.onResume();

        // 如果开发者的引导页主题是透明的，需要在界面可见时给背景 Banner 设置一个白色背景，避免滑动过程中两个 Banner 都设置透明度后能看到 Launcher
        mBackgroundBanner.setBackgroundResource(android.R.color.white);
    }


    private void setListener() {
        /**
         * 设置进入按钮和跳过按钮控件资源 id 及其点击事件
         * 如果进入按钮和跳过按钮有一个不存在的话就传 0
         * 在 BGABanner 里已经帮开发者处理了防止重复点击事件
         * 在 BGABanner 里已经帮开发者处理了「跳过按钮」和「进入按钮」的显示与隐藏
         */
        mBackgroundBanner.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                ARouter.getInstance().build(ARoutePath.PATH_MAIN).navigation();
                finish();
            }
        });
    }

    private void processLogic() {
        // Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间
        BGALocalImageSize localImageSize = new BGALocalImageSize(720, 1280, 320, 640);
        // 设置数据源
//        mBackgroundBanner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
//                R.mipmap.uoko_guide_background_1,
//                R.mipmap.uoko_guide_background_2,
//                R.mipmap.uoko_guide_background_3);
        mBackgroundBanner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.mipmap.bg_guide_1,
                R.mipmap.bg_guide_2,
                R.mipmap.bg_guide_3);

//        mForegroundBanner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
//                R.mipmap.uoko_guide_foreground_1,
//                R.mipmap.uoko_guide_foreground_2,
//                R.mipmap.uoko_guide_foreground_3);
    }


}
