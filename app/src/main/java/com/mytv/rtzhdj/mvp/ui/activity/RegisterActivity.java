package com.mytv.rtzhdj.mvp.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.data.entity.StationEntity;
import com.mytv.rtzhdj.app.data.entity.UserCategoryEntity;
import com.mytv.rtzhdj.di.component.DaggerRegisterComponent;
import com.mytv.rtzhdj.di.module.RegisterModule;
import com.mytv.rtzhdj.mvp.contract.RegisterContract;
import com.mytv.rtzhdj.mvp.presenter.RegisterPresenter;

import com.mytv.rtzhdj.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 注册界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-20
 * @update 2018-3-21    填充UI布局
 */
@Route(path = ARoutePath.PATH_REGISTER)
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;

    @BindView(R.id.edt_mobile_phone)
    EditText mEdtMobilePhone;
    @BindView(R.id.tv_community)
    TextView mEdtCommunity;
    @BindView(R.id.edt_password)
    EditText mEdtPassword;
    @BindView(R.id.edt_password2)
    EditText mEdtPassword2;
    @BindView(R.id.edt_vertify_code)
    EditText mEdtVertifyCode;
    @BindView(R.id.btn_get_code)
    Button mBtnGetVertifyCode;
    @BindView(R.id.chk_agree)
    CheckBox mChkAgree;
    @BindView(R.id.tv_link)
    TextView mTvLink;
    @BindView(R.id.btn_register)
    net.qiujuer.genius.ui.widget.Button mBtnRegister;


    private ArrayList<String> options1Items = new ArrayList<>();


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerRegisterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .registerModule(new RegisterModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_register; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarMenu.setVisibility(View.GONE);

        mTvLink.setOnClickListener(view -> goWebviewActivity());
        mEdtCommunity.setOnClickListener(view -> mPresenter.callMethodOfPostAllPublishmentSystem(false));
        mBtnGetVertifyCode.setOnClickListener(view -> mPresenter.callMethodOfGetCode(
                mEdtMobilePhone.getText().toString().trim()));
        mBtnRegister.setOnClickListener(view -> mPresenter.callMethodOfDoRegister(
                mEdtMobilePhone.getText().toString().trim(),
                mEdtCommunity.getText().toString().trim(),
                mEdtPassword.getText().toString().trim(),
                mEdtPassword2.getText().toString().trim()));
        mChkAgree.setOnCheckedChangeListener((CompoundButton compoundButton, boolean b) ->
            setBtnRegisterBg(b));

        // 获取 用户类别
        mPresenter.callMethodOfGetUserCategory(false);

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
    public void setBtnRegisterBg(boolean isChecked) {
        if (isChecked)
            mBtnRegister.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        else
            mBtnRegister.setBackgroundColor(getResources().getColor(R.color.grey_500));
    }

    @Override
    public void goWebviewActivity() {
        ARouter.getInstance().build(ARoutePath.PATH_WEBVIEW)
                .withString("title", "用户注册协议").navigation();
    }

    @Override
    public void showDialog(List<UserCategoryEntity> categoryList) {
        String item1 = "";
        String item2 = "";
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getCategoryName().contains("群众")) {
                item1 += categoryList.get(i).getCategoryName();
            } else {
                item2 += categoryList.get(i).getCategoryName() + "、";
            }
        }
        item2 = item2.length() > 0 ? item2.substring(0, item2.length() - 1) : "";
        String[] items = {item2, item1};

        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        View dialogView = LayoutInflater.from(RegisterActivity.this).inflate(R.layout.dialog_select_usercategory, null);
        TagFlowLayout mFlowLayout = dialogView.findViewById(R.id.flowlayout_usercategory);
        mFlowLayout.setMaxSelectCount(1);   // 设置单选
        mFlowLayout.setAdapter(new TagAdapter<String>(items)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) LayoutInflater.from(RegisterActivity.this)
                        .inflate(R.layout.tv, mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
        mFlowLayout.getAdapter().setSelectedList(1);  // 设置预选项
        builder.setView(dialogView);
        builder.create();
        AlertDialog dialog0 = builder.show();
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener()
        {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent)
            {
                switch (position) {
                    case 0: // 党员、预备党员和积极分子
                        dialog0.dismiss();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(RegisterActivity.this);
                        builder1.setTitle("提示");
                        builder1.setMessage(getResources().getString(R.string.register_usercategory_info));
                        builder1.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder1.create().show();
                        break;
                    case 1: // 群众
                        dialog0.dismiss();
                        break;
                }

                return true;
            }
        });

    }

    @Override
    public void showPickerView(List<StationEntity> stationList) {// 弹出选择器
        final List<String> mOptionsItems = new ArrayList<>();
        mOptionsItems.add("item0");
        mOptionsItems.add("item1");
        mOptionsItems.add("item2");


        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = stationList.get(options1).getPublishmentSystemName();

                Toast.makeText(RegisterActivity.this, tx, Toast.LENGTH_SHORT).show();
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
