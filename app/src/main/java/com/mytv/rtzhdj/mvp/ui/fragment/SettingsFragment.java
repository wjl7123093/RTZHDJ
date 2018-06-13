package com.mytv.rtzhdj.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.SharepreferenceKey;
import com.mytv.rtzhdj.app.data.entity.UserDetailEntity;
import com.mytv.rtzhdj.di.component.DaggerSettingsComponent;
import com.mytv.rtzhdj.di.module.SettingsModule;
import com.mytv.rtzhdj.mvp.contract.SettingsContract;
import com.mytv.rtzhdj.mvp.presenter.SettingsPresenter;
import com.mytv.rtzhdj.mvp.ui.activity.SettingsActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.BaseDelegateAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class SettingsFragment extends BaseFragment<SettingsPresenter> implements SettingsContract.View {

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    /** 存放各个模块的适配器*/
    private List<DelegateAdapter.Adapter> mAdapters;
    private int mFlag;

    /** 加载进度条 */
    private SweetAlertDialog pDialog;

    public static SettingsFragment newInstance(int flag) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("flag", flag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerSettingsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .settingsModule(new SettingsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mAdapters = new LinkedList<>();
        mPresenter.setActivity((SettingsActivity) getActivity());

        initRefreshLayout();

    }

    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传Message,通过what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事
     * <p>
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onCreate还没执行
     * setData里却调用了presenter的方法时,是会报空的,因为dagger注入是在onCreated方法中执行的,然后才创建的presenter
     * 如果要做一些初始化操作,可以不必让外部调setData,在initData中初始化就可以了
     *
     * @param data
     */

    @Override
    public void setData(Object data) {

    }


    @Override
    public void showLoading() {
        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
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

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            if (getArguments().getInt("flag") == 0) {    // 基本信息
                // 获取用户详情信息
                mPresenter.callMethodOfGetUserDetail(
                        DataHelper.getIntergerSF(getActivity(), SharepreferenceKey.KEY_USER_ID), false);
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        // Fragment 对用户可见时再调用
        if (isVisibleToUser) {
            if (getArguments().getInt("flag") == 1) { // 入党基本信息
                loadData((UserDetailEntity) DataHelper.getDeviceData(getActivity(), SharepreferenceKey.KEY_LOGIN_USER_DETAIL), 1);
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void initRecyclerView1() {
        DelegateAdapter delegateAdapter = mPresenter.initRecyclerView(mRecyclerView);

        //初始化头部1
        BaseDelegateAdapter headerAdapter = mPresenter.initHeader1(
                "http://imgtu.5011.net/uploads/content/20170220/9520371487578487.jpg");
        mAdapters.add(headerAdapter);

        //初始化信息1
        BaseDelegateAdapter infoAdapter = mPresenter.initInfo1("真实姓名:", "xxx");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("性别:", "男");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("民族:", "汉族");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("籍贯:", "四川成都");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("所属支部:", "xxxxxx第二党支部");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("职务:", "无");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("报道社区:", "xxxxxx第二党支部");
        mAdapters.add(infoAdapter);

        //初始化手机
        BaseDelegateAdapter mobileAdapter = mPresenter.initMobilePhone("18945321681", false);
        mAdapters.add(mobileAdapter);

        //初始化信息2
        BaseDelegateAdapter infoAdapter2 = mPresenter.initInfo2("pwd", "修改密码:", "", "");
        mAdapters.add(infoAdapter2);
        infoAdapter2 = mPresenter.initInfo2("info", "QQ:", "", "你的QQ号");
        mAdapters.add(infoAdapter2);
        infoAdapter2 = mPresenter.initInfo2("info", "邮箱:", "", "你的邮箱");
        mAdapters.add(infoAdapter2);
        infoAdapter2 = mPresenter.initInfo2("info", "座机:", "", "你的座机号");
        mAdapters.add(infoAdapter2);
        infoAdapter2 = mPresenter.initInfo2("info", "紧急联系电话:", "", "你的紧急联系电话");
        mAdapters.add(infoAdapter2);
        infoAdapter2 = mPresenter.initInfo2("info", "通讯地址:", "", "你的通讯地址");
        mAdapters.add(infoAdapter2);

        //初始化其他联系方式
        BaseDelegateAdapter infoAdapter3 = mPresenter.initOtherContacts("其他联系方式", "", "其他联系方式");
        mAdapters.add(infoAdapter3);

        //初始化信息2
        infoAdapter2 = mPresenter.initInfo2("webview", "用户协议", "", "");
        mAdapters.add(infoAdapter2);
        infoAdapter2 = mPresenter.initInfo2("webview", "技术支持", "", "");
        mAdapters.add(infoAdapter2);
        infoAdapter2 = mPresenter.initInfo2("webview", "版本更新", "", "");
        mAdapters.add(infoAdapter2);
        infoAdapter2 = mPresenter.initInfo2("feedback", "意见反馈", "", "");
        mAdapters.add(infoAdapter2);
        infoAdapter = mPresenter.initInfo1("退出当前账户", "");
        mAdapters.add(infoAdapter);

        //设置适配器
        delegateAdapter.setAdapters(mAdapters);
    }

    private void initRecyclerView2() {
        DelegateAdapter delegateAdapter = mPresenter.initRecyclerView(mRecyclerView);

        //初始化头部1
        BaseDelegateAdapter headerAdapter = mPresenter.initHeader2(
                "http://imgtu.5011.net/uploads/content/20170220/9520371487578487.jpg");
        mAdapters.add(headerAdapter);

        //初始化信息1
        BaseDelegateAdapter infoAdapter = mPresenter.initInfo1("个人身份:", "公有经济控制企业专业技术人员");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("学历:", "大学");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("学位:", "学士");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("出生日期:", "1990-03-23");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("身份证号:", "510723199009221988");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("一线情况:", "机关第一线");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("技术职务:", "为评定资格或未聘任");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("工作单位:", "绵阳市XXXXX局");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("单位属性:", "机关");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("入党时间:", "2009-01-03");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("转正时间:", "2010-01-03 00:00:00");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("增加时间:", "2016-01-15 00:00:00");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("党员增加:", "自本市(县、区、街道)内的其他乡(镇、街道)转入");
        mAdapters.add(infoAdapter);

        //设置适配器
        delegateAdapter.setAdapters(mAdapters);
    }

    private void initRecyclerView1(UserDetailEntity userDetailEntity) {
        UserDetailEntity.BaseInfo baseInfo = userDetailEntity.getBaseInfo();

        DelegateAdapter delegateAdapter = mPresenter.initRecyclerView(mRecyclerView);

        mAdapters.clear();
        //初始化头部1
        BaseDelegateAdapter headerAdapter = mPresenter.initHeader1(
                "http://imgtu.5011.net/uploads/content/20170220/9520371487578487.jpg");
        mAdapters.add(headerAdapter);

        //初始化信息1
        BaseDelegateAdapter infoAdapter = mPresenter.initInfo1("真实姓名:", baseInfo.getUserName());
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("性别:", baseInfo.getSex());
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("民族:", baseInfo.getNation());
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("籍贯:", baseInfo.getOrigo());
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("所属支部:", baseInfo.getPublishmentsystemName());
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("职务:", "无");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("报道社区:", baseInfo.getPublishmentsystemName());
        mAdapters.add(infoAdapter);

        //初始化手机
        BaseDelegateAdapter mobileAdapter = mPresenter.initMobilePhone(baseInfo.getMobile(), false);
        mAdapters.add(mobileAdapter);

        //初始化信息2
        BaseDelegateAdapter infoAdapter2 = mPresenter.initInfo2("pwd", "修改密码:", "", "");
        mAdapters.add(infoAdapter2);
        infoAdapter2 = mPresenter.initInfo2("info", "QQ:", baseInfo.getQQ(), "你的QQ号");
        mAdapters.add(infoAdapter2);
        infoAdapter2 = mPresenter.initInfo2("info", "邮箱:", baseInfo.getMailbox(), "你的邮箱");
        mAdapters.add(infoAdapter2);
        infoAdapter2 = mPresenter.initInfo2("info", "座机:", baseInfo.getPhone(), "你的座机号");
        mAdapters.add(infoAdapter2);
        infoAdapter2 = mPresenter.initInfo2("info", "紧急联系电话:", baseInfo.getEnmergencyMobile(), "你的紧急联系电话");
        mAdapters.add(infoAdapter2);
        infoAdapter2 = mPresenter.initInfo2("info", "通讯地址:", baseInfo.getAddress(), "你的通讯地址");
        mAdapters.add(infoAdapter2);

        //初始化其他联系方式
        BaseDelegateAdapter infoAdapter3 = mPresenter.initOtherContacts("其他联系方式", baseInfo.getOtherContact(), "其他联系方式");
        mAdapters.add(infoAdapter3);

        //初始化信息2
        infoAdapter2 = mPresenter.initInfo2("webview", "用户协议", "", "");
        mAdapters.add(infoAdapter2);
        infoAdapter2 = mPresenter.initInfo2("webview", "技术支持", "", "");
        mAdapters.add(infoAdapter2);
        infoAdapter2 = mPresenter.initInfo2("webview", "版本更新", "", "");
        mAdapters.add(infoAdapter2);
        infoAdapter2 = mPresenter.initInfo2("feedback", "意见反馈", "", "");
        mAdapters.add(infoAdapter2);
        infoAdapter = mPresenter.initInfo3("退出当前账户", "");
        mAdapters.add(infoAdapter);

        //设置适配器
        delegateAdapter.setAdapters(mAdapters);
    }

    private void initRecyclerView2(UserDetailEntity userDetailEntity) {
        if (null == userDetailEntity)
            return;

        UserDetailEntity.PartyInfo partyInfo = userDetailEntity.getPartyInfo();

        DelegateAdapter delegateAdapter = mPresenter.initRecyclerView(mRecyclerView);
        mAdapters.clear();

        //初始化头部1
        BaseDelegateAdapter headerAdapter = mPresenter.initHeader2(
                "http://imgtu.5011.net/uploads/content/20170220/9520371487578487.jpg");
        mAdapters.add(headerAdapter);

        //初始化信息1
        BaseDelegateAdapter infoAdapter = mPresenter.initInfo1("个人身份:", "公有经济控制企业专业技术人员");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("学历:", partyInfo.getEducation());
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("学位:", "学士");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("出生日期:", partyInfo.getBirthday());
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("身份证号:", partyInfo.getIdCode());
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("一线情况:", "机关第一线");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("技术职务:", "为评定资格或未聘任");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("工作单位:", "绵阳市XXXXX局");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("单位属性:", "机关");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("入党时间:", partyInfo.getWorkTime());
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("转正时间:", "2010-01-03 00:00:00");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("增加时间:", "2016-01-15 00:00:00");
        mAdapters.add(infoAdapter);
        infoAdapter = mPresenter.initInfo1("党员增加:", "自本市(县、区、街道)内的其他乡(镇、街道)转入");
        mAdapters.add(infoAdapter);

        //设置适配器
        delegateAdapter.setAdapters(mAdapters);
    }

    private void initRefreshLayout() {
        mRefreshLayout.setEnableRefresh(false);
        /*mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000*//*,false*//*);//传入false表示刷新失败
            }
        });*/
        mRefreshLayout.setEnableLoadmore(false);
        /*mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000*//*,false*//*);//传入false表示加载失败
            }
        });*/
    }

    @Override
    public void loadData(UserDetailEntity userDetailEntity, int flag) {
//        initRecyclerView1(userDetailEntity);
//        initRecyclerView2(userDetailEntity);
        switch (flag) {
            case 0:
                initRecyclerView1(userDetailEntity);
                break;
            case 1:
                initRecyclerView2(userDetailEntity);
                break;
        }
    }
}
