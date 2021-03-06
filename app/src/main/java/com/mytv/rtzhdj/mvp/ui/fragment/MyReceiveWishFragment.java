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
import com.mytv.rtzhdj.di.component.DaggerMyReceiveWishComponent;
import com.mytv.rtzhdj.di.module.MyReceiveWishModule;
import com.mytv.rtzhdj.mvp.contract.MyReceiveWishContract;
import com.mytv.rtzhdj.mvp.presenter.MyReceiveWishPresenter;
import com.mytv.rtzhdj.mvp.ui.activity.MyReceiveWishActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.MyWishAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MyReceiveWishFragment extends BaseFragment<MyReceiveWishPresenter> implements MyReceiveWishContract.View {

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private MyWishAdapter wishAdapter;
    private static final int PAGE_SIZE = 10;

    /** 加载进度条 */
    private SweetAlertDialog pDialog;


    public static MyReceiveWishFragment newInstance() {
        MyReceiveWishFragment fragment = new MyReceiveWishFragment();
        return fragment;
    }

    public static MyReceiveWishFragment newInstance(int type) {
        MyReceiveWishFragment fragment = new MyReceiveWishFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerMyReceiveWishComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myReceiveWishModule(new MyReceiveWishModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_receive_wish, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mPresenter.setActivity((MyReceiveWishActivity) getActivity());
        mRecyclerView = mPresenter.initRecyclerView(mRecyclerView);
        initRefreshLayout();

        // 获取心愿数据
        mPresenter.callMethodOfPostMyClaimWishList(
                DataHelper.getIntergerSF(getActivity(), SharepreferenceKey.KEY_USER_ID),
                getArguments().getInt("type"), false);

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
    public void loadData(List<MyWishEntity> myWishList) {
        // 下面代码会导致 无数据页面上下滑动异常
        /*if (myWishList.size() == 0) {
//            showMessage("暂无数据");
            return;
        }*/

        initAdapter(myWishList);
    }

    @Override
    public void showDialog() {

    }

    private void initRefreshLayout() {
//        mRefreshLayout.setEnableRefresh(false);
//        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000, true);//传入false表示刷新失败
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000, true);//传入false表示加载失败
            }
        });
    }

    private void initAdapter(List<MyWishEntity> wishList) {
        wishAdapter = new MyWishAdapter(getActivity(), wishList);
        wishAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(wishAdapter);

        wishAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                showMessage("" + Integer.toString(position));
                ARouter.getInstance().build(ARoutePath.PATH_WISH_DETAIL)
                        .withInt("wishId", wishList.get(position).getID()).navigation();
            }
        });

    }

}
