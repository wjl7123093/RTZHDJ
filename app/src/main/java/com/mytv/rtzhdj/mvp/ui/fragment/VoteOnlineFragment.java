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
import com.mytv.rtzhdj.app.data.entity.VoteListEntity;
import com.mytv.rtzhdj.di.component.DaggerVoteOnlineComponent;
import com.mytv.rtzhdj.di.module.VoteOnlineModule;
import com.mytv.rtzhdj.mvp.contract.VoteOnlineContract;
import com.mytv.rtzhdj.mvp.presenter.VoteOnlinePresenter;

import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.mvp.ui.activity.VoteOnlineActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.QuestionAdapter;
import com.mytv.rtzhdj.mvp.ui.adapter.VoteOnlineAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class VoteOnlineFragment extends BaseFragment<VoteOnlinePresenter> implements VoteOnlineContract.View {

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private VoteOnlineAdapter voteAdapter;
    private static final int PAGE_SIZE = 10;


    public static VoteOnlineFragment newInstance() {
        VoteOnlineFragment fragment = new VoteOnlineFragment();
        return fragment;
    }

    public static VoteOnlineFragment newInstance(int typeId) {
        VoteOnlineFragment fragment = new VoteOnlineFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("typeId", typeId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerVoteOnlineComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .voteOnlineModule(new VoteOnlineModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vote_online, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        mPresenter.setActivity((VoteOnlineActivity) getActivity());
        mRecyclerView = mPresenter.initRecyclerView(mRecyclerView);
//        initAdapter();
        initRefreshLayout();

        // 获取 投票列表数据
        mPresenter.callMethodOfGetVoteList(getArguments().getInt("typeId"), 1, PAGE_SIZE, false);
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

    private void initAdapter(List<VoteListEntity> voteList) {
        voteAdapter = new VoteOnlineAdapter(getActivity(), voteList);
        voteAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(voteAdapter);

        voteAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                showMessage("" + Integer.toString(position));
                ARouter.getInstance().build(ARoutePath.PATH_VOTE_DETAIL)
                        .withString("title", "投票活动名称").navigation();
            }
        });

    }

    @Override
    public void loadData(List<VoteListEntity> voteList) {
        initAdapter(voteList);
    }
}
