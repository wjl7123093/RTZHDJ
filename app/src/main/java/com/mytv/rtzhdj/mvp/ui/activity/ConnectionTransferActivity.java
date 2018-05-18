package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.SharepreferenceKey;
import com.mytv.rtzhdj.app.data.entity.StationEntity;
import com.mytv.rtzhdj.di.component.DaggerConnectionTransferComponent;
import com.mytv.rtzhdj.di.module.ConnectionTransferModule;
import com.mytv.rtzhdj.mvp.contract.ConnectionTransferContract;
import com.mytv.rtzhdj.mvp.presenter.ConnectionTransferPresenter;

import net.qiujuer.genius.ui.widget.Button;

import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 关系转接 界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-11
 * @update
 */
@Route(path = ARoutePath.PATH_CONNECTION_TRANSFER)
public class ConnectionTransferActivity extends BaseActivity<ConnectionTransferPresenter> implements ConnectionTransferContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;

    @BindView(R.id.tv_party_branch)
    TextView mTvPartyBranch;
    @BindView(R.id.edt_reason)
    EditText mEdtReason;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;

    private int mPublishmentSystemId = 0;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerConnectionTransferComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .connectionTransferModule(new ConnectionTransferModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_connection_transfer; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        // 选择站点
        mTvPartyBranch.setOnClickListener(view -> {
            mPresenter.callMethodOfPostAllPublishmentSystem(false);
        });
        // 提交
        mBtnSubmit.setOnClickListener(view -> {

            if (TextUtils.isEmpty(mEdtReason.getText().toString().trim())) {
                showMessage("请输入关系转接原因");
                return;
            }

            // post 组织关系转接
            mPresenter.callMethodOfGetOrganizationalChange(
                    mPublishmentSystemId, mEdtReason.getText().toString().trim(),
                    DataHelper.getIntergerSF(ConnectionTransferActivity.this, SharepreferenceKey.KEY_USER_ID), false);
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


    @Override
    public void showPickerView(List<StationEntity> stationList) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = stationList.get(options1).getPublishmentSystemName();
                mTvPartyBranch.setText(tx);
                mPublishmentSystemId = stationList.get(options1).getPublishmentSystemId();


                Toast.makeText(ConnectionTransferActivity.this, tx, Toast.LENGTH_SHORT).show();
            }
        })

                .setTitleText("站点选择")
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(stationList);//一级选择器
        pvOptions.show();
    }
}
