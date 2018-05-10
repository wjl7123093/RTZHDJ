package com.mytv.rtzhdj.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.data.entity.NewsAllEntity;
import com.mytv.rtzhdj.app.data.entity.NewsDetailEntity;
import com.mytv.rtzhdj.app.data.entity.NewsSimpleEntity;
import com.mytv.rtzhdj.di.component.DaggerNewsAllComponent;
import com.mytv.rtzhdj.di.module.NewsAllModule;
import com.mytv.rtzhdj.mvp.contract.NewsAllContract;
import com.mytv.rtzhdj.mvp.presenter.NewsAllPresenter;

import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.mvp.ui.activity.NewsAllActivity;
import com.mytv.rtzhdj.mvp.ui.activity.NewsSimpleActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.NewsSimpleAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class NewsAllFragment extends BaseFragment<NewsAllPresenter> implements NewsAllContract.View {

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private NewsSimpleAdapter newsAdapter;
    private static final int PAGE_SIZE = 10;


    public static NewsAllFragment newInstance() {
        NewsAllFragment fragment = new NewsAllFragment();
        return fragment;
    }

    public static NewsAllFragment newInstance(int nodeId, NewsAllEntity newsAllEntity) {
        NewsAllFragment fragment = new NewsAllFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("nodeId", nodeId);
        bundle.putParcelable("entity", newsAllEntity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerNewsAllComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .newsAllModule(new NewsAllModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_all, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        mPresenter.setActivity((NewsAllActivity) getActivity());
        mRecyclerView = mPresenter.initRecyclerView(mRecyclerView);
        initRefreshLayout();

        if (0 == getArguments().getInt("nodeId")) {
            // 展示 全部页 数据
            loadData(getArguments().getParcelable("entity"));
        } else {
            // 获取二级通用列表数据
            mPresenter.callMethodOfGetTwoLevelInfoList(getArguments().getInt("nodeId"), 1, PAGE_SIZE, false);
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
    public void loadData(NewsAllEntity newsAllEntity) {
        List<NewsDetailEntity> importandBlock = newsAllEntity.getList_allinfoBlock();

        initAdapter(importandBlock);
    }

    @Override
    public void loadListData(List<NewsDetailEntity> newsList) {
        initAdapter(newsList);
    }

    public void initAdapter(List<NewsDetailEntity> newsDetailList) {
        newsAdapter = new NewsSimpleAdapter(getContext(), newsDetailList);
        newsAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(newsAdapter);

        newsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getContext(), "" + Integer.toString(position), Toast.LENGTH_LONG).show();

                // 新闻详情页
//                    ARouter.getInstance().build(ARoutePath.PATH_NEWS_DETAIL).navigation();
                ARouter.getInstance().build(ARoutePath.PATH_NEWS_DETAIL)
                        .withInt("articleId", newsDetailList.get(position).getId())
                        .withInt("nodeId", newsDetailList.get(position).getNodeId())
                        .withInt("digs", newsDetailList.get(position).getDigs())
                        .withInt("comments", newsDetailList.get(position).getComments())
                        .navigation();
            }
        });
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
}
