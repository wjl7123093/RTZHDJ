package com.mytv.rtzhdj.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.SharepreferenceKey;
import com.mytv.rtzhdj.app.data.entity.NewsDetailEntity;
import com.mytv.rtzhdj.app.data.entity.NewsSimpleEntity;
import com.mytv.rtzhdj.di.component.DaggerNewsSimpleComponent;
import com.mytv.rtzhdj.di.module.NewsSimpleModule;
import com.mytv.rtzhdj.mvp.contract.NewsSimpleContract;
import com.mytv.rtzhdj.mvp.presenter.NewsSimplePresenter;
import com.mytv.rtzhdj.mvp.ui.activity.NewsDetailActivity;
import com.mytv.rtzhdj.mvp.ui.activity.NewsSimpleActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.NewsSimpleAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class NewsSimpleFragment extends BaseFragment<NewsSimplePresenter> implements NewsSimpleContract.View {

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private NewsSimpleAdapter newsAdapter;
    private static final int PAGE_SIZE = 10;
    private int PAGE_INDEX = 1;
    private int mCurPos = 0;    // 当前列表末节点位置
    private List<NewsDetailEntity> mNewsList = new ArrayList<>();
    private boolean mIsLoadMore = false;
    private boolean mIsRefresh = false;
    private boolean mIsHeader = false;      // 是否含有头部banner

    /** 加载进度条 */
    private SweetAlertDialog pDialog;


    public static NewsSimpleFragment newInstance() {
        NewsSimpleFragment fragment = new NewsSimpleFragment();
        return fragment;
    }

    public static NewsSimpleFragment newInstance(int nodeId, NewsSimpleEntity newsSimpleEntity) {
        NewsSimpleFragment fragment = new NewsSimpleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("nodeId", nodeId);
        bundle.putParcelable("entity", newsSimpleEntity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerNewsSimpleComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .newsSimpleModule(new NewsSimpleModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_simple, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        mPresenter.setActivity((NewsSimpleActivity) getActivity());
        mRecyclerView = mPresenter.initRecyclerView(mRecyclerView);
        initRefreshLayout();

        PAGE_INDEX = 1;
        if (0 == getArguments().getInt("nodeId")) {
            // 展示 推荐页 数据
            loadData(getArguments().getParcelable("entity"));
        } else {
            // 获取二级通用列表数据
            mPresenter.callMethodOfGetTwoLevelInfoList(
                    DataHelper.getIntergerSF(getActivity(), SharepreferenceKey.KEY_PUBLISHMENT_SYSTEM_ID),
                    getArguments().getInt("nodeId"), PAGE_INDEX, PAGE_SIZE, false);
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
    public void loadData(NewsSimpleEntity newsSimpleEntity) {
        List<NewsDetailEntity> specialBlock = newsSimpleEntity.getList_recommendBlock();
        List<NewsDetailEntity> importandBlock = newsSimpleEntity.getList_listBlock();

        initAdapter(importandBlock, false);
        View headerView = mPresenter.initHeaderView(specialBlock, (ViewGroup) mRecyclerView.getParent());
        if (0 == getArguments().getInt("nodeId"))
            newsAdapter.addHeaderView(headerView);
        else
            newsAdapter.removeAllHeaderView();
    }

    @Override
    public void loadListData(List<NewsDetailEntity> newsList, boolean update) {
        initAdapter(newsList, update);
    }

    public void initAdapter(List<NewsDetailEntity> newsDetailList, boolean update) {
        if (update) {
            if (mIsRefresh) {  // 下拉刷新
                /*// 1. 先移除
                newsAdapter.notifyItemRangeRemoved(0, mNewsList.size());
                // 2. 再清空
                mNewsList.clear();*/

                mRefreshLayout.finishRefresh(true);
//                mIsRefresh = false;
            } else {    // 上拉加载
                mRefreshLayout.finishLoadmore(true);
//                mIsLoadMore = false;
            }
        }

        mCurPos = mNewsList.size();
        if (null == newsAdapter) {
            mNewsList = newsDetailList;
            if (0 == getArguments().getInt("nodeId")) { // 推荐 -> 有头部
                mIsHeader = true;
            } else {
                mIsHeader = false;
            }
            newsAdapter = new NewsSimpleAdapter(getContext(), mNewsList, mIsHeader);
            newsAdapter.openLoadAnimation();
            mRecyclerView.setAdapter(newsAdapter);
        } else {
//            mNewsList.addAll(newsDetailList);
//            newsAdapter.notifyItemRangeInserted(mCurPos, newsDetailList.size());
            if (mIsRefresh) {
//                newsAdapter.notifyDataSetChanged();
                newsAdapter.replaceData(newsDetailList);
                mIsRefresh = false;
            } else if (mIsLoadMore) {
//                newsAdapter.notifyItemRangeInserted(mCurPos, newsDetailList.size());
                newsAdapter.addData(mCurPos, newsDetailList);
                mIsLoadMore = false;
            }
        }

        newsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Toast.makeText(getContext(), "" + Integer.toString(position), Toast.LENGTH_LONG).show();

                // 新闻详情页
                /*ARouter.getInstance().build(ARoutePath.PATH_NEWS_DETAIL)
                        .withInt("articleId", mNewsList.get(position).getId())
                        .withInt("nodeId", mNewsList.get(position).getNodeId())
                        .withInt("digs", mNewsList.get(position).getDigs())
                        .withInt("comments", mNewsList.get(position).getComments())
                        .navigation();*/

                Bundle bundle = new Bundle();
                bundle.putInt("articleId", mNewsList.get(position).getId());
                bundle.putInt("nodeId", mNewsList.get(position).getNodeId());
                bundle.putInt("digs", mNewsList.get(position).getDigs());
                bundle.putInt("comments", mNewsList.get(position).getComments());
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 100);

                if (mIsHeader)
                    mCurPos = position + 1;
                else
                    mCurPos = position;
            }
        });
    }

    private void initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败

                if (0 == getArguments().getInt("nodeId")) { // 推荐
                    refreshlayout.finishRefresh(2000);  // 模拟效果
                } else {    // 其他页
                    mIsRefresh = true;
                    PAGE_INDEX = 1;
                    // 获取二级通用列表数据
                    mPresenter.callMethodOfGetTwoLevelInfoList(
                            DataHelper.getIntergerSF(getActivity(), SharepreferenceKey.KEY_PUBLISHMENT_SYSTEM_ID),
                            getArguments().getInt("nodeId"), PAGE_INDEX, PAGE_SIZE, true);
                }
            }
        });
//        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败

                if (0 == getArguments().getInt("nodeId")) { // 推荐
                    refreshlayout.finishLoadmore(2000);  // 模拟效果
                } else {    // 其他页
                    mIsLoadMore = true;
                    // 获取二级通用列表数据
                    mPresenter.callMethodOfGetTwoLevelInfoList(
                            DataHelper.getIntergerSF(getActivity(), SharepreferenceKey.KEY_PUBLISHMENT_SYSTEM_ID),
                            getArguments().getInt("nodeId"), ++PAGE_INDEX, PAGE_SIZE, true);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 200) {

            // 刷新点赞数
            if (data.getIntExtra("type", 0) == 1)
                newsAdapter.notifyItemChanged(mCurPos, "xxxxxxx");
        }
    }
}
