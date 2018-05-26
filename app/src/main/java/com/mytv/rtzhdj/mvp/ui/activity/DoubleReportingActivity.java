package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
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
import com.mytv.rtzhdj.app.utils.KeyboardUtils;
import com.mytv.rtzhdj.di.component.DaggerDoubleReportingComponent;
import com.mytv.rtzhdj.di.module.DoubleReportingModule;
import com.mytv.rtzhdj.mvp.contract.DoubleReportingContract;
import com.mytv.rtzhdj.mvp.presenter.DoubleReportingPresenter;

import net.qiujuer.genius.ui.widget.Button;

import java.util.List;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 双报到界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-21
 * @update
 */
@Route(path = ARoutePath.PATH_DOUBLE_REPORTING)
public class DoubleReportingActivity extends BaseActivity<DoubleReportingPresenter> implements DoubleReportingContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;

    @BindView(R.id.edt_community)
    EditText mEdtCommunity;
    @BindView(R.id.tv_choose_community)
    TextView mTvChooseCommunity;
    @BindView(R.id.btn_ok)
    Button mBtnOk;

    private int mPublishmentSystemId = 0;   // 站点ID

    /** 加载进度条 */
    private SweetAlertDialog pDialog;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerDoubleReportingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .doubleReportingModule(new DoubleReportingModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_double_reporting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);

        mTvChooseCommunity.setOnClickListener(view -> {});
        mBtnOk.setOnClickListener(view -> {
            if (0 == mPublishmentSystemId) {
                showMessage("还未选择站点");
                return;
            }

            mPresenter.callMethodOfPostPersonalReach(
                    DataHelper.getIntergerSF(DoubleReportingActivity.this, SharepreferenceKey.KEY_USER_ID), mPublishmentSystemId);
        });
        mEdtCommunity.setOnClickListener(view -> {
            KeyboardUtils.hideKeyboard(view);
            mPresenter.callMethodOfPostAllPublishmentSystem(false);
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
    public void showPickerView(List<StationEntity> stationList) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = stationList.get(options1).getPublishmentSystemName();
                mEdtCommunity.setText(tx);
                mPublishmentSystemId = stationList.get(options1).getPublishmentSystemId();


                Toast.makeText(DoubleReportingActivity.this, tx, Toast.LENGTH_SHORT).show();
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
