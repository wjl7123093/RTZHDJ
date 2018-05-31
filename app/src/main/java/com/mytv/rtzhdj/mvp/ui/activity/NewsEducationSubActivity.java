package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.data.entity.PartyNewsEntity;
import com.mytv.rtzhdj.app.data.entity.PartySubNewsEntity;
import com.mytv.rtzhdj.di.component.DaggerNewsEducationSubComponent;
import com.mytv.rtzhdj.di.module.NewsEducationSubModule;
import com.mytv.rtzhdj.mvp.contract.NewsEducationSubContract;
import com.mytv.rtzhdj.mvp.presenter.NewsEducationSubPresenter;
import com.mytv.rtzhdj.mvp.ui.adapter.NewsAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 党员教育[重点培训，远程教育]界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-27
 * @update
 */
@Route(path = ARoutePath.PATH_NEWS_EDUCATION_SUB)
public class NewsEducationSubActivity extends BaseActivity<NewsEducationSubPresenter> implements NewsEducationSubContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Autowired
    String title;
    @Autowired
    int nodeId;

    private NewsAdapter newsAdapter;
    private static final int PAGE_SIZE = 10;
    private int PAGE_INDEX = 1;
    private int mCurPos = 0;    // 当前列表末节点位置
    private List<PartyNewsEntity> mNewsList = new ArrayList<>();
    private boolean mIsLoadMore = false;
    private boolean mIsRefresh = false;

    /** 加载进度条 */
    private SweetAlertDialog pDialog;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerNewsEducationSubComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .newsEducationSubModule(new NewsEducationSubModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        return R.layout.activity_news_education_sub; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mPresenter.setActivity(NewsEducationSubActivity.this);
        mRecyclerView = mPresenter.initRecyclerView(mRecyclerView);
//        initAdapter();

//        View headerView = mPresenter.initHeaderView(imgUrls, (ViewGroup) mRecyclerView.getParent());
//        if (0 == getArguments().getInt("position"))
//            newsAdapter.addHeaderView(headerView);
//        else
//            newsAdapter.removeAllHeaderView();

        initRefreshLayout();

        PAGE_INDEX = 1;
        // 获取党建新闻二级列表(除推荐)数据
        mPresenter.callMethodOfGetPartySubList(nodeId, PAGE_INDEX, PAGE_SIZE, false);
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
    protected void onResume() {
        super.onResume();
        mTvToolbarTitle.setText(title);
    }


    @Override
    public void showSubListData(PartySubNewsEntity subNewsEntity, boolean update) {
        List<PartyNewsEntity> channelNewsBlock = subNewsEntity.getChannelNewsBlock();

        initAdapter(channelNewsBlock, update);
    }

    @Override
    public void initAdapter(List<PartyNewsEntity> importandBlockList, boolean update) {
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
            mNewsList = importandBlockList;
            newsAdapter = new NewsAdapter(NewsEducationSubActivity.this, importandBlockList, false);
            newsAdapter.openLoadAnimation();
            mRecyclerView.setAdapter(newsAdapter);
        } else {
            mNewsList.addAll(importandBlockList);
            if (mIsRefresh) {
//                newsAdapter.notifyDataSetChanged();
                newsAdapter.replaceData(importandBlockList);
                mIsRefresh = false;
            } else if (mIsLoadMore) {
//                newsAdapter.notifyItemRangeInserted(mCurPos, importandBlockList.size());
                newsAdapter.addData(mCurPos, importandBlockList);
                mIsLoadMore = false;
            }
        }

        newsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Toast.makeText(getContext(), "" + Integer.toString(position), Toast.LENGTH_LONG).show();

                // 新闻详情页
                /*ARouter.getInstance().build(ARoutePath.PATH_NEWS_DETAIL)
                        .withInt("articleId", mNewsList.get(position).getArticleId())
                        .withInt("nodeId", mNewsList.get(position).getNodeid())
                        .withInt("digs", mNewsList.get(position).getDigs())
                        .withInt("comments", mNewsList.get(position).getComments())
                        .navigation();*/

                Bundle bundle = new Bundle();
                bundle.putInt("articleId", mNewsList.get(position).getArticleId());
                bundle.putInt("nodeId", mNewsList.get(position).getNodeId());
                bundle.putInt("digs", mNewsList.get(position).getDigs());
                bundle.putInt("comments", mNewsList.get(position).getComments());
                Intent intent = new Intent(NewsEducationSubActivity.this, NewsDetailActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 100);

                mCurPos = position;
            }
        });
    }

    private void initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败

                mIsRefresh = true;
                PAGE_INDEX = 1;
                // 获取党建新闻二级列表(除推荐)数据
                mPresenter.callMethodOfGetPartySubList(nodeId, PAGE_INDEX, PAGE_SIZE, true);
            }
        });
//        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败

                mIsLoadMore = true;
                // 获取党建新闻二级列表(除推荐)数据
                mPresenter.callMethodOfGetPartySubList(nodeId, ++PAGE_INDEX, PAGE_SIZE, true);
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
