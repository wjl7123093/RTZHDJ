package com.mytv.rtzhdj.mvp.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.SharepreferenceKey;
import com.mytv.rtzhdj.app.utils.PermissionUtils;
import com.mytv.rtzhdj.di.component.DaggerStartComponent;
import com.mytv.rtzhdj.di.module.StartModule;
import com.mytv.rtzhdj.mvp.contract.StartContract;
import com.mytv.rtzhdj.mvp.presenter.StartPresenter;

import java.util.Arrays;

import cn.pedant.SweetAlert.SweetAlertDialog;

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


    /** 加载进度条 */
    private SweetAlertDialog pDialog;

    private Context mContext;
    // 相机权限、多个权限
    private final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    private final String PERMISSION_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String[] PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    // 打开相机请求Code，多个权限请求Code
    private final int REQUEST_CODE_CAMERA = 1,REQUEST_CODE_PERMISSIONS=2;


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
        mContext = this;

        // 申请一个存储权限
        requestPermission1();

        return 0; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {

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

    private void doGetIn() {

        // 通过设置 AndroidManifest.xml 里设置 theme 来解决 启动白屏 问题
        new Handler().postDelayed(() -> {

            // 是否首次进入
            if (-1 == DataHelper.getIntergerSF(StartActivity.this, SharepreferenceKey.KEY_IS_FIRST_IN)) {
                DataHelper.setIntergerSF(StartActivity.this, SharepreferenceKey.KEY_IS_FIRST_IN, 1);
                ARouter.getInstance().build(ARoutePath.PATH_GUIDE).navigation();
                StartActivity.this.finish();
            } else {
                // 1 已登录
                if (1 == DataHelper.getIntergerSF(StartActivity.this, SharepreferenceKey.KEY_IS_LOGIN)) {
                    ARouter.getInstance().build(ARoutePath.PATH_MAIN).navigation();
                    StartActivity.this.finish();
                } else {    // 0 已注销
                    ARouter.getInstance().build(ARoutePath.PATH_LOGIN).navigation();
                    StartActivity.this.finish();
                }
            }
        }, 1000);
    }

    // 普通申请一个权限
    private void requestPermission(){
        PermissionUtils.checkAndRequestPermission(mContext, PERMISSION_CAMERA, REQUEST_CODE_CAMERA,
                new PermissionUtils.PermissionRequestSuccessCallBack() {
                    @Override
                    public void onHasPermission() {
                        // 权限已被授予
                        toCamera();
                    }
                });
    }

    // 自定义申请一个权限
    private void requestPermission1(){
        PermissionUtils.checkPermission(mContext, PERMISSION_STORAGE,
                new PermissionUtils.PermissionCheckCallBack() {
                    @Override
                    public void onHasPermission() {
                        doGetIn();
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDown(String... permission) {
//                        doGetIn();
                        showExplainDialog(permission, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PermissionUtils.requestPermission(mContext, PERMISSION_STORAGE, REQUEST_CODE_CAMERA);
                            }
                        });
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
//                        doGetIn();
                        PermissionUtils.requestPermission(mContext, PERMISSION_STORAGE, REQUEST_CODE_CAMERA);
                    }
                });
    }

    // 普通申请多个权限
    private void requestMorePermissions(){
        PermissionUtils.checkAndRequestMorePermissions(mContext, PERMISSIONS, REQUEST_CODE_PERMISSIONS,
                new PermissionUtils.PermissionRequestSuccessCallBack() {
                    @Override
                    public void onHasPermission() {
                        // 权限已被授予
                        toCamera();
                    }
                });
    }

    // 自定义申请多个权限
    private void requestMorePermissions1(){
        PermissionUtils.checkMorePermissions(mContext, PERMISSIONS, new PermissionUtils.PermissionCheckCallBack() {
            @Override
            public void onHasPermission() {
                toCamera();
            }

            @Override
            public void onUserHasAlreadyTurnedDown(String... permission) {
                showExplainDialog(permission, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtils.requestMorePermissions(mContext, PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    }
                });
            }

            @Override
            public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                PermissionUtils.requestMorePermissions(mContext, PERMISSIONS, REQUEST_CODE_PERMISSIONS);
            }
        });
    }

    private void toCamera() {
        Intent intent = new Intent();
        intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
        startActivity(intent);
    }



    /**
     * 解释权限的dialog
     *
     */
    private void showExplainDialog(String[] permission, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(mContext)
                .setTitle("申请权限")
                .setMessage("我们需要" + Arrays.toString(permission)+"权限")
                .setPositiveButton("确定", onClickListener)
                .show();
    }

    /**
     * 显示前往应用设置Dialog
     *
     */
    private void showToAppSettingDialog() {
        new AlertDialog.Builder(mContext)
                .setTitle("需要权限")
                .setMessage("我们需要相关权限，才能实现功能，点击前往，将转到应用的设置界面，请开启应用的相关权限。")
                .setPositiveButton("前往", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtils.toAppSetting(mContext);
                    }
                })
                .setNegativeButton("取消", null).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                if(PermissionUtils.isPermissionRequestSuccess(grantResults))
                {
                    // 权限申请成功
//                    toCamera();
                    doGetIn();
                } else {
//                    Toast.makeText(mContext,"打开相机失败",Toast.LENGTH_SHORT).show();
                    doGetIn();
                }
                break;
            case REQUEST_CODE_PERMISSIONS:
                PermissionUtils.onRequestMorePermissionsResult(mContext, PERMISSIONS, new PermissionUtils.PermissionCheckCallBack() {
                    @Override
                    public void onHasPermission() {
                        toCamera();
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDown(String... permission) {
                        Toast.makeText(mContext, "我们需要"+Arrays.toString(permission)+"权限", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                        Toast.makeText(mContext, "我们需要"+ Arrays.toString(permission)+"权限", Toast.LENGTH_SHORT).show();
                        showToAppSettingDialog();
                    }
                });


        }
    }

}
