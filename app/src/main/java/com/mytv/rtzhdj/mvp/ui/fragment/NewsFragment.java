package com.mytv.rtzhdj.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.app.data.entity.PartyColumnsEntity;
import com.mytv.rtzhdj.app.data.entity.PartyNewsEntity;
import com.mytv.rtzhdj.di.component.DaggerNewsComponent;
import com.mytv.rtzhdj.di.module.NewsModule;
import com.mytv.rtzhdj.mvp.contract.NewsContract;
import com.mytv.rtzhdj.mvp.presenter.NewsPresenter;

import com.mytv.rtzhdj.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.weyye.library.colortrackview.ColorTrackTabLayout;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * NewsFragment 党建新闻 Fragment
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-19
 * @update 2018-2-6     setTitle() 必须放在 onResume() 里，否则在app退入后台，
 *                                 重新显示的时候，title的值为app名称的BUG。
 */
public class NewsFragment extends BaseFragment<NewsPresenter> implements NewsContract.View {

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

    String[] titles;

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerNewsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .newsModule(new NewsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBtnToolbarBack.setVisibility(View.GONE);
        mIvMenu.setImageResource(R.mipmap.ic_search);
        mBtnToolbarMenu.setOnClickListener(view -> {
            // 跳转到搜索页面
            ARouter.getInstance().build(ARoutePath.PATH_SEARCH).navigation();
        });

//        titles = new String[]{"推荐", "党建快讯", "基层动态", "干部任免", "身边典型", "老党员之声", "他山之石"};
//        initTab();

        // 获取二级栏目标题
        mPresenter.callMethodOfGetPartyColumns(1, false);
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
    public void onResume() {
        super.onResume();
        mTvToolbarTitle.setText("党建新闻");
    }

    @Override
    public void initTab(List<PartyColumnsEntity> partyColumnsList) {
        if (!partyColumnsList.get(0).getTitle().equals("推荐")) {
            PartyColumnsEntity columnsEntity = new PartyColumnsEntity();
            columnsEntity.setTitle("推荐");
            columnsEntity.setNodeID(0);
            partyColumnsList.add(0, columnsEntity);
        }

        final List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < partyColumnsList.size(); i++) {
            fragments.add(ContentFragment.newInstance(partyColumnsList.get(i).getNodeID()));
        }

        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
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
}
