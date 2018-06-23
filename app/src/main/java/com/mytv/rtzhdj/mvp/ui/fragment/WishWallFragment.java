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
import com.mytv.rtzhdj.app.data.entity.MyWishEntity;
import com.mytv.rtzhdj.di.component.DaggerWishWallComponent;
import com.mytv.rtzhdj.di.module.WishWallModule;
import com.mytv.rtzhdj.mvp.contract.WishWallContract;
import com.mytv.rtzhdj.mvp.presenter.WishWallPresenter;
import com.mytv.rtzhdj.mvp.ui.activity.WishWallActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.MyWishAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class WishWallFragment extends BaseFragment<WishWallPresenter> implements WishWallContract.View {

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private MyWishAdapter wishAdapter;
    private static final int PAGE_SIZE = 10;
    private int PAGE_INDEX = 1;
    private int mCurPos = 0;    // 当前列表末节点位置
    private List<MyWishEntity> mWishList = new ArrayList<>();
    private boolean mIsLoadMore = false;
    private boolean mIsRefresh = false;

    /** 加载进度条 */
    private SweetAlertDialog pDialog;


    public static WishWallFragment newInstance() {
        WishWallFragment fragment = new WishWallFragment();
        return fragment;
    }

    public static WishWallFragment newInstance(int type, String pageType) {
        WishWallFragment fragment = new WishWallFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("pageType", pageType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerWishWallComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .wishWallModule(new WishWallModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wish_wall, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mPresenter.setActivity((WishWallActivity) getActivity());
        mRecyclerView = mPresenter.initRecyclerView(mRecyclerView);
        initRefreshLayout();

        PAGE_INDEX = 1;
        // 获取心愿数据
        if (getArguments().getString("pageType").equals("wall")) {
            // 心愿墙  有分页
            mPresenter.callMethodOfGetWishList(
                    DataHelper.getIntergerSF(getActivity(), SharepreferenceKey.KEY_PUBLISHMENT_SYSTEM_ID),
                    DataHelper.getIntergerSF(getActivity(), SharepreferenceKey.KEY_USER_ID),
                    getArguments().getInt("type"), PAGE_INDEX, PAGE_SIZE, false);
        } else {
            // 我的心愿
            mPresenter.callMethodOfPostMyWishList(
                    DataHelper.getIntergerSF(getActivity(), SharepreferenceKey.KEY_USER_ID),
                    getArguments().getInt("type"), false);
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
    public void loadData(List<MyWishEntity> myWishList, boolean update) {
        initAdapter(myWishList, update);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    private void initRefreshLayout() {
        if (getArguments().getString("pageType").equals("wall")) {  // 心愿墙
            mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败

                    mIsRefresh = true;
                    PAGE_INDEX = 1;
                    // 心愿墙  有分页
                    mPresenter.callMethodOfGetWishList(
                            DataHelper.getIntergerSF(getActivity(), SharepreferenceKey.KEY_PUBLISHMENT_SYSTEM_ID),
                            DataHelper.getIntergerSF(getActivity(), SharepreferenceKey.KEY_USER_ID),
                            getArguments().getInt("type"), PAGE_INDEX, PAGE_SIZE, true);

                }
            });
            mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
                @Override
                public void onLoadmore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败

                    mIsLoadMore = true;
                    // 心愿墙  有分页
                    mPresenter.callMethodOfGetWishList(
                            DataHelper.getIntergerSF(getActivity(), SharepreferenceKey.KEY_PUBLISHMENT_SYSTEM_ID),
                            DataHelper.getIntergerSF(getActivity(), SharepreferenceKey.KEY_USER_ID),
                            getArguments().getInt("type"), ++PAGE_INDEX, PAGE_SIZE, true);
                }
            });
        } else {    // 我的心愿
//            mRefreshLayout.setEnableRefresh(false);
//            mRefreshLayout.setEnableLoadmore(false);
            mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                }
            });
            mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
                @Override
                public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
                }
            });
        }
    }

    private void initAdapter(List<MyWishEntity> wishList, boolean update) {
        if (update) {
            if (mIsRefresh) {  // 下拉刷新
                // 1. 先移除
                wishAdapter.notifyItemRangeRemoved(0, mWishList.size());
                // 2. 再清空
                mWishList.clear();

                mRefreshLayout.finishRefresh(true);
                mIsRefresh = false;
            } else {    // 上拉加载
                mRefreshLayout.finishLoadmore(true);
                mIsLoadMore = false;
            }
        }

        if (getArguments().getString("pageType").equals("wall")) {  // 心愿墙
            mCurPos = mWishList.size();
            if (null == wishAdapter) {
                mWishList = wishList;
                wishAdapter = new MyWishAdapter(getActivity(), wishList);
                wishAdapter.openLoadAnimation();
                mRecyclerView.setAdapter(wishAdapter);
            } else {
                mWishList.addAll(wishList);
                wishAdapter.notifyItemRangeInserted(mCurPos, wishList.size());
            }
        } else {    // 我的心愿
            mWishList = wishList;
            wishAdapter = new MyWishAdapter(getActivity(), wishList);
            wishAdapter.openLoadAnimation();
            mRecyclerView.setAdapter(wishAdapter);
        }

        wishAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                showMessage("" + Integer.toString(position));
                ARouter.getInstance().build(ARoutePath.PATH_WISH_DETAIL)
                        .withInt("wishId", mWishList.get(position).getID())
                        .withBoolean("isAuditStatus", mWishList.get(position).isAuditStatus()).navigation();
            }
        });

    }
}
