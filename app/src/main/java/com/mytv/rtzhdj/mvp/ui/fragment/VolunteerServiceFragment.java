package com.mytv.rtzhdj.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
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
import com.jess.arms.utils.DataHelper;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.SharepreferenceKey;
import com.mytv.rtzhdj.app.data.entity.VoluteerServiceEntity;
import com.mytv.rtzhdj.di.component.DaggerVolunteerServiceComponent;
import com.mytv.rtzhdj.di.module.VolunteerServiceModule;
import com.mytv.rtzhdj.mvp.contract.VolunteerServiceContract;
import com.mytv.rtzhdj.mvp.presenter.VolunteerServicePresenter;
import com.mytv.rtzhdj.mvp.ui.activity.VolunteerServiceActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.VoluteerServiceAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class VolunteerServiceFragment extends BaseFragment<VolunteerServicePresenter> implements VolunteerServiceContract.View {

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private VoluteerServiceAdapter voluteerServiceAdapter;
    private static final int PAGE_SIZE = 10;
    private int PAGE_INDEX = 1;
    private int mCurPos = 0;    // 当前列表末节点位置
    private List<VoluteerServiceEntity> mServiceList = new ArrayList<>();
    private boolean mIsLoadMore = false;
    private boolean mIsRefresh = false;

    /** 加载进度条 */
    private SweetAlertDialog pDialog;

    public static VolunteerServiceFragment newInstance() {
        VolunteerServiceFragment fragment = new VolunteerServiceFragment();
        return fragment;
    }

    public static VolunteerServiceFragment newInstance(int typeId) {
        VolunteerServiceFragment fragment = new VolunteerServiceFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("typeId", typeId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerVolunteerServiceComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .volunteerServiceModule(new VolunteerServiceModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_volunteer, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mPresenter.setActivity((VolunteerServiceActivity) getActivity());
        mRecyclerView = mPresenter.initRecyclerView(mRecyclerView);
//        initAdapter();
        initRefreshLayout();

        PAGE_INDEX = 1;
        // 获取志愿服务列表
        mPresenter.callMethodOfGetVoluntaryserviceList(
                DataHelper.getIntergerSF(getActivity(), SharepreferenceKey.KEY_PUBLISHMENT_SYSTEM_ID),
                getArguments().getInt("typeId"), PAGE_INDEX, PAGE_SIZE, false);
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

    private void initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败

                mIsRefresh = true;
                PAGE_INDEX = 1;
                // 获取志愿服务列表
                mPresenter.callMethodOfGetVoluntaryserviceList(
                        DataHelper.getIntergerSF(getActivity(), SharepreferenceKey.KEY_PUBLISHMENT_SYSTEM_ID),
                        getArguments().getInt("typeId"), PAGE_INDEX, PAGE_SIZE, true);
            }
        });
//        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败

                mIsLoadMore = true;
                // 获取志愿服务列表
                mPresenter.callMethodOfGetVoluntaryserviceList(
                        DataHelper.getIntergerSF(getActivity(), SharepreferenceKey.KEY_PUBLISHMENT_SYSTEM_ID),
                        getArguments().getInt("typeId"), ++PAGE_INDEX, PAGE_SIZE, true);
            }
        });
    }

    private void initAdapter(List<VoluteerServiceEntity> serviceList, boolean update) {
        if (update) {
            if (mIsRefresh) {  // 下拉刷新
                // 1. 先移除
                voluteerServiceAdapter.notifyItemRangeRemoved(0, mServiceList.size());
                // 2. 再清空
                mServiceList.clear();

                mRefreshLayout.finishRefresh(true);
                mIsRefresh = false;
            } else {    // 上拉加载
                mRefreshLayout.finishLoadmore(true);
                mIsLoadMore = false;
            }
        }

        mCurPos = mServiceList.size();
        if (null == voluteerServiceAdapter) {
            mServiceList = serviceList;
            voluteerServiceAdapter = new VoluteerServiceAdapter(getActivity(), serviceList);
            voluteerServiceAdapter.openLoadAnimation();
            mRecyclerView.setAdapter(voluteerServiceAdapter);
        } else {
            mServiceList.addAll(serviceList);
            voluteerServiceAdapter.notifyItemRangeInserted(mCurPos, serviceList.size());
        }

        voluteerServiceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                showMessage("" + Integer.toString(position));

                ARouter.getInstance().build(ARoutePath.PATH_VOLUNTEER_SERVICE_DETAIL)
                        .withInt("nodeId", mServiceList.get(position).getNodeId())
                        .withInt("id", mServiceList.get(position).getId())
                        .withString("imageUrl", mServiceList.get(position).getAllImgUrl()).navigation();
            }
        });

    }

    @Override
    public void loadData(List<VoluteerServiceEntity> volunteerServiceList, boolean update) {
        initAdapter(volunteerServiceList, update);
    }
}
