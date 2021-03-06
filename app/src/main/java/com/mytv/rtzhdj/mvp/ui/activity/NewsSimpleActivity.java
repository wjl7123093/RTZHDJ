package com.mytv.rtzhdj.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.SharepreferenceKey;
import com.mytv.rtzhdj.app.data.entity.NewsDetailEntity;
import com.mytv.rtzhdj.app.data.entity.NewsSimpleEntity;
import com.mytv.rtzhdj.app.data.entity.PartyColumnsEntity;
import com.mytv.rtzhdj.di.component.DaggerNewsSimpleComponent;
import com.mytv.rtzhdj.di.module.NewsSimpleModule;
import com.mytv.rtzhdj.mvp.contract.NewsSimpleContract;
import com.mytv.rtzhdj.mvp.presenter.NewsSimplePresenter;
import com.mytv.rtzhdj.mvp.ui.fragment.NewsSimpleFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import me.weyye.library.colortrackview.ColorTrackTabLayout;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 带"推荐"通用二级界面
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-7
 * @update
 */
@Route(path = ARoutePath.PATH_NEWS_SIMPLE)
public class NewsSimpleActivity extends BaseActivity<NewsSimplePresenter> implements NewsSimpleContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mBtnToolbarBack;
    @BindView(R.id.toolbar_menu)
    RelativeLayout mBtnToolbarMenu;
    @BindView(R.id.iv_menu)
    ImageView mIvMenu;

    @BindView(R.id.tab_channel)
    ColorTrackTabLayout mTab;
    @BindView(R.id.vp_content)
    ViewPager mViewPager;

    @Autowired
    int nodeId;
    @Autowired
    String title;

    private final int PAGE_SIZE = 10;
    String[] titles;

    /** 加载进度条 */
    private SweetAlertDialog pDialog;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerNewsSimpleComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .newsSimpleModule(new NewsSimpleModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        return R.layout.activity_news_simple; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        // [ForResult]
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            nodeId = bundle.getInt("nodeId");
            title = bundle.getString("title");
        }

        mBtnToolbarBack.setOnClickListener(view -> this.finish());
        mIvMenu.setImageResource(R.mipmap.ic_search);
        mBtnToolbarMenu.setOnClickListener(view -> {
            // 跳转到搜索页面
            ARouter.getInstance().build(ARoutePath.PATH_SEARCH).navigation();
        });

        // 获取 带"推荐"通用二级页面
        mPresenter.callMethodOfGetTwoLevelList(
                DataHelper.getIntergerSF(NewsSimpleActivity.this, SharepreferenceKey.KEY_PUBLISHMENT_SYSTEM_ID),
                nodeId, 1, PAGE_SIZE, false);

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

    private void initTab(NewsSimpleEntity newsSimpleEntity) {
        List<PartyColumnsEntity> partyColumnsList = newsSimpleEntity.getList_columnList();
        List<NewsDetailEntity> recommendList = newsSimpleEntity.getList_recommendBlock();
        List<NewsDetailEntity> newsDetailList = newsSimpleEntity.getList_listBlock();

        if (null == partyColumnsList || partyColumnsList.size() == 0)
            return;

        if (!partyColumnsList.get(0).getTitle().equals("推荐")) {
            PartyColumnsEntity columnsEntity = new PartyColumnsEntity();
            columnsEntity.setTitle("推荐");
            columnsEntity.setNodeId(0);
            partyColumnsList.add(0, columnsEntity);
        }

        final List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < partyColumnsList.size(); i++) {
            fragments.add(NewsSimpleFragment.newInstance(partyColumnsList.get(i).getNodeId(), newsSimpleEntity));
        }


        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return partyColumnsList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return partyColumnsList.get(position).getTitle();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                // 注释掉下面那行，解决 滑动卡顿 问题
//                super.destroyItem(container, position, object);
            }
        });
        mTab.setupWithViewPager(mViewPager);
    }


    @Override
    public void loadData(NewsSimpleEntity newsSimpleEntity) {
        initTab(newsSimpleEntity);
    }

    @Override
    public void loadListData(List<NewsDetailEntity> newsList, boolean udpate) {

    }
}
