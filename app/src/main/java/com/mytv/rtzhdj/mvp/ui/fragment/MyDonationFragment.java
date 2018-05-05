package com.mytv.rtzhdj.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.data.entity.MyDonateEntity;
import com.mytv.rtzhdj.app.data.entity.MyWishEntity;
import com.mytv.rtzhdj.di.component.DaggerMyDonationComponent;
import com.mytv.rtzhdj.di.module.MyDonationModule;
import com.mytv.rtzhdj.mvp.contract.MyDonationContract;
import com.mytv.rtzhdj.mvp.presenter.MyDonationPresenter;

import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.mvp.ui.activity.MyDonationActivity;
import com.mytv.rtzhdj.mvp.ui.activity.MyReceiveWishActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.MyDonationAdapter;
import com.mytv.rtzhdj.mvp.ui.adapter.MyWishAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MyDonationFragment extends BaseFragment<MyDonationPresenter> implements MyDonationContract.View {

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private MyDonationAdapter wishAdapter;
    private static final int PAGE_SIZE = 10;


    public static MyDonationFragment newInstance() {
        MyDonationFragment fragment = new MyDonationFragment();
        return fragment;
    }

    public static MyDonationFragment newInstance(int typeId, String pageType) {
        MyDonationFragment fragment = new MyDonationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("typeId", typeId);
        bundle.putString("pageType", pageType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerMyDonationComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myDonationModule(new MyDonationModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_donation, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mPresenter.setActivity((MyDonationActivity) getActivity());
        mRecyclerView = mPresenter.initRecyclerView(mRecyclerView);
        initRefreshLayout();

        if (getArguments().getString("pageType").equals("all")) {
            // 获取 所有捐赠数据
            mPresenter.callMethodOfGetAllDonateList(8, getArguments().getInt("typeId"), 1, PAGE_SIZE, false);
        } else {
            // 获取 我的捐赠数据
            mPresenter.callMethodOfPostMyClaimDonateList(8, getArguments().getInt("typeId"), false);
        }
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

    }

    @Override
    public void loadData(List<MyDonateEntity> donationList) {
        if (donationList.size() == 0) {
            showMessage("暂无数据");
            return;
        }

        initAdapter(donationList);
    }

    private void initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
//        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    private void initAdapter(List<MyDonateEntity> donationList) {
        wishAdapter = new MyDonationAdapter(getActivity(), donationList);
        wishAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(wishAdapter);

        wishAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showMessage("" + Integer.toString(position));
//                ARouter.getInstance().build(ARoutePath.PATH_WISH_DETAIL)
//                        .withInt("wishId", donationList.get(position).getID()).navigation();
            }
        });

    }

}
