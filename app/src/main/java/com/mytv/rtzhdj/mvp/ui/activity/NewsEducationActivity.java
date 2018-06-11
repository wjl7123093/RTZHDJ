package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.vlayout.DelegateAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.SharepreferenceKey;
import com.mytv.rtzhdj.app.data.entity.NewsDetailEntity;
import com.mytv.rtzhdj.app.data.entity.NewsSimpleEntity;
import com.mytv.rtzhdj.di.component.DaggerNewsEducationComponent;
import com.mytv.rtzhdj.di.module.NewsEducationModule;
import com.mytv.rtzhdj.mvp.contract.NewsEducationContract;
import com.mytv.rtzhdj.mvp.presenter.NewsEducationPresenter;
import com.mytv.rtzhdj.mvp.ui.adapter.BaseDelegateAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 党员教育界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-20
 * @update 2018-2-6     填充布局
 */
@Route(path = ARoutePath.PATH_NEWS_EDUCATION)
public class NewsEducationActivity extends BaseActivity<NewsEducationPresenter> implements NewsEducationContract.View {

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

    private final int PAGE_SIZE = 10;
    private int PAGE_INDEX = 1;
    private int mCurPos = 0;    // 当前列表末节点位置
    private List<NewsDetailEntity> mNewsList = new ArrayList<>();
    private boolean mIsLoadMore = false;
    private boolean mIsRefresh = false;

    /** 存放各个模块的适配器*/
    private List<DelegateAdapter.Adapter> mAdapters;
    private DelegateAdapter delegateAdapter = null;

    /** 加载进度条 */
    private SweetAlertDialog pDialog;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerNewsEducationComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .newsEducationModule(new NewsEducationModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_news_education; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mAdapters = new LinkedList<>();
        mPresenter.setActivity(NewsEducationActivity.this);
        initRecyclerView();
        initRefreshLayout();

        // 获取 带"推荐"通用二级页面
        mPresenter.callMethodOfGetTwoLevelList(
                DataHelper.getIntergerSF(NewsEducationActivity.this, SharepreferenceKey.KEY_PUBLISHMENT_SYSTEM_ID),
                6020, PAGE_INDEX, PAGE_SIZE, false);
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
    public void setOnGridClick(int position, String title) {
        int nodeId = 4008;
        switch (position) {
            case 0: // 重点培训 9056
                nodeId = 9056;
                ARouter.getInstance().build(ARoutePath.PATH_NEWS_EDUCATION_SUB)
                        .withInt("nodeId", 9056)
                        .withString("title", "重点培训").navigation();
                break;
            case 1: // 农民夜校 4008
                nodeId = 4008;
                ARouter.getInstance().build(ARoutePath.PATH_NEWS_SIMPLE)
                        .withInt("nodeId", 4008)
                        .withString("title", "农民夜校").navigation();
                break;
            case 2: // 远程教育 9057
                nodeId = 9057;
                ARouter.getInstance().build(ARoutePath.PATH_NEWS_EDUCATION_SUB)
                        .withInt("nodeId", 9057)
                        .withString("title", "远程教育").navigation();
                break;
            case 3: // 师资库   9062
                nodeId = 9062;
                ARouter.getInstance().build(ARoutePath.PATH_TEACHER_LIST)
                        .withInt("nodeId", 9062)
                        .withString("title", "师资库").navigation();
                break;
            default:
                break;
        }

//        ARouter.getInstance().build(ARoutePath.PATH_NEWS_COMMON)
//                .withString("from", "NewsEducationActivity")
//                .withString("title", title)
//                .withInt("nodeId", nodeId).navigation();
    }

    @Override
    public void setOnListClick(NewsDetailEntity newsDetailEntity, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("articleId", newsDetailEntity.getId());
        bundle.putInt("nodeId", newsDetailEntity.getNodeId());
        bundle.putInt("digs", newsDetailEntity.getDigs());
        bundle.putInt("comments", newsDetailEntity.getComments());
        Intent intent = new Intent(NewsEducationActivity.this, NewsDetailActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, 100);

        // 当前点击位置 所在的列表绝对位置（5 == grid(4) + title(1)）
        mCurPos = position;
    }

    @Override
    public void loadData(NewsSimpleEntity newsSimpleEntity, boolean update) {

        if (update) {
            if (mIsRefresh) {
                mRefreshLayout.finishRefresh(true);
                mIsRefresh = false;
                mAdapters.clear();

                initRecyclerView();
            } else {
                mRefreshLayout.finishLoadmore(true);
                mIsLoadMore = false;
            }
        }

        //初始化list
        BaseDelegateAdapter listAdapter = mPresenter.initList(newsSimpleEntity.getList_listBlock());
        mAdapters.add(listAdapter);
        delegateAdapter.setAdapters(mAdapters);
        delegateAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        if (null == delegateAdapter)
            delegateAdapter = mPresenter.initRecyclerView(mRecyclerView);

        //初始化九宫格
        BaseDelegateAdapter menuAdapter = mPresenter.initGvMenu();
        mAdapters.add(menuAdapter);

        //初始化标题 - 必修课
        BaseDelegateAdapter titleAdapter = mPresenter.initTitle("推荐");
        mAdapters.add(titleAdapter);
//        //初始化list
//        BaseDelegateAdapter listAdapter = mPresenter.initList(DataServer.getNewsData(10));
//        mAdapters.add(listAdapter);

        //设置适配器
        delegateAdapter.setAdapters(mAdapters);
    }

    private void initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败

                mIsRefresh = true;
                PAGE_INDEX = 1;
                // 获取 带"推荐"通用二级页面
                mPresenter.callMethodOfGetTwoLevelList(
                        DataHelper.getIntergerSF(NewsEducationActivity.this, SharepreferenceKey.KEY_PUBLISHMENT_SYSTEM_ID),
                        6020, PAGE_INDEX, PAGE_SIZE, true);
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败

                mIsLoadMore = true;
                // 获取 带"推荐"通用二级页面
                mPresenter.callMethodOfGetTwoLevelList(
                        DataHelper.getIntergerSF(NewsEducationActivity.this, SharepreferenceKey.KEY_PUBLISHMENT_SYSTEM_ID),
                        6020, ++PAGE_INDEX, PAGE_SIZE, true);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 200) {

            // 刷新点赞数
            if (data.getIntExtra("type", 0) == 1)
                delegateAdapter.notifyItemChanged(mCurPos, "xxxxxxx");
        }
    }
}
