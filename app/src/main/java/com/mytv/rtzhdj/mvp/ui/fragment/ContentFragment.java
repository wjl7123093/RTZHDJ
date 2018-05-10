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
import com.mytv.rtzhdj.app.data.entity.PartyNewsEntity;
import com.mytv.rtzhdj.app.data.entity.PartyRecommendEntity;
import com.mytv.rtzhdj.app.data.entity.PartySubNewsEntity;
import com.mytv.rtzhdj.di.component.DaggerContentComponent;
import com.mytv.rtzhdj.di.module.ContentModule;
import com.mytv.rtzhdj.mvp.contract.ContentContract;
import com.mytv.rtzhdj.mvp.presenter.ContentPresenter;

import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.mvp.ui.adapter.NewsAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * ContentFragment 内容页(二级Fragment) Fragment
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-26
 * @update
 */
public class ContentFragment extends BaseFragment<ContentPresenter> implements ContentContract.View {

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private NewsAdapter newsAdapter;
    private static final int PAGE_SIZE = 10;


    public static ContentFragment newInstance(int nodeId) {
        ContentFragment fragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("nodeId", nodeId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerContentComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .contentModule(new ContentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        final List<Object> imgUrls = new ArrayList<>();
        imgUrls.add("http://bpic.wotucdn.com/11/66/23/55bOOOPIC3c_1024.jpg!/fw/780/quality/90/unsharp/true/compress/true/watermark/url/L2xvZ28ud2F0ZXIudjIucG5n/repeat/true");
        imgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505470629546&di=194a9a92bfcb7754c5e4d19ff1515355&imgtype=0&src=http%3A%2F%2Fpics.jiancai.com%2Fimgextra%2Fimg01%2F656928666%2Fi1%2FT2_IffXdxaXXXXXXXX_%2521%2521656928666.jpg");

        mPresenter.setActivity(getActivity());
        mRecyclerView = mPresenter.initRecyclerView(mRecyclerView);
//        initAdapter();

//        View headerView = mPresenter.initHeaderView(imgUrls, (ViewGroup) mRecyclerView.getParent());
//        if (0 == getArguments().getInt("position"))
//            newsAdapter.addHeaderView(headerView);
//        else
//            newsAdapter.removeAllHeaderView();

        initRefreshLayout();

        if (0 == getArguments().getInt("nodeId")) {
            // 获取党建新闻推荐列表数据
            mPresenter.callMethodOfGetPartyRecommend(1, PAGE_SIZE, false);
        } else {
            // 获取党建新闻二级列表(除推荐)数据
            mPresenter.callMethodOfGetPartySubList(getArguments().getInt("nodeId"), 1, PAGE_SIZE, false);
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
    public void onBannerClick(int position) {

    }

    @Override
    public void showRecommendData(PartyRecommendEntity recommendEntity) {
        List<PartyNewsEntity> specialBlock = recommendEntity.getSpecialBlock();
        List<PartyNewsEntity> importandBlock = recommendEntity.getImportandBlock();

        initAdapter(importandBlock);
        View headerView = mPresenter.initHeaderView(specialBlock, (ViewGroup) mRecyclerView.getParent());
        if (0 == getArguments().getInt("nodeId"))
            newsAdapter.addHeaderView(headerView);
        else
            newsAdapter.removeAllHeaderView();
    }

    @Override
    public void showSubListData(PartySubNewsEntity subNewsEntity) {
        List<PartyNewsEntity> channelNewsBlock = subNewsEntity.getChannelNewsBlock();

        initAdapter(channelNewsBlock);
    }

    @Override
    public void initAdapter(List<PartyNewsEntity> importandBlockList) {
        newsAdapter = new NewsAdapter(getContext(), importandBlockList);
        newsAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(newsAdapter);

        newsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getContext(), "" + Integer.toString(position), Toast.LENGTH_LONG).show();

                // 新闻详情页
//                    ARouter.getInstance().build(ARoutePath.PATH_NEWS_DETAIL).navigation();
                ARouter.getInstance().build(ARoutePath.PATH_NEWS_DETAIL)
                        .withInt("articleId", importandBlockList.get(position).getArticleId())
                        .withInt("nodeId", importandBlockList.get(position).getNodeid())
                        .withInt("digs", importandBlockList.get(position).getDigs())
                        .withInt("comments", importandBlockList.get(position).getComments())
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

    /*private void initAdapter() {
        newsAdapter = new NewsAdapter(getContext(), PAGE_SIZE);
        newsAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(newsAdapter);

        newsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getContext(), "" + Integer.toString(position), Toast.LENGTH_LONG).show();
            }
        });

    }*/
}
