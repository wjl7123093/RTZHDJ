package com.mytv.rtzhdj.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.vlayout.DelegateAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.mytv.rtzhdj.app.ARoutePath;
import com.mytv.rtzhdj.di.component.DaggerStudyComponent;
import com.mytv.rtzhdj.di.module.StudyModule;
import com.mytv.rtzhdj.mvp.contract.StudyContract;
import com.mytv.rtzhdj.mvp.presenter.StudyPresenter;

import com.mytv.rtzhdj.R;
import com.mytv.rtzhdj.mvp.ui.activity.MainActivity;
import com.mytv.rtzhdj.mvp.ui.activity.StudyCoursewareActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.BaseDelegateAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * StudyFragment 我要参与 Fragment
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-19
 * @update 2018-2-2     填充布局
 *         2018-2-26    新增跳转接口(PATH_EFFECT_EVALUATION)
 */
public class StudyFragment extends BaseFragment<StudyPresenter> implements StudyContract.View {

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    /** 存放各个模块的适配器*/
    private List<DelegateAdapter.Adapter> mAdapters;


    public static StudyFragment newInstance() {
        StudyFragment fragment = new StudyFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerStudyComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .studyModule(new StudyModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_study, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mAdapters = new LinkedList<>();
        mPresenter.setActivity((MainActivity)getActivity());
        initRecyclerView();
        initRefreshLayout();
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
    public void setOnGridClick(int position, String title) {
        switch (position) {
            case 0: // 学习课件
                ARouter.getInstance().build(ARoutePath.PATH_STUDY_COURSEWARE).navigation();
                break;
            case 1: // 党内法规
            case 3: // 党建研究
                ARouter.getInstance().build(ARoutePath.PATH_NEWS_COMMON)
                        .withString("from", "StudyFragment")
                        .withString("title", title).navigation();
                break;
            case 2: // 党建知识
                ARouter.getInstance().build(ARoutePath.PATH_PARTY_KNOWLEDGE).navigation();
                break;
            case 4: // 绵阳党史
                ARouter.getInstance().build(ARoutePath.PATH_PARTY_HISTORY).navigation();
                break;
            case 5: // 效果测评
                ARouter.getInstance().build(ARoutePath.PATH_EFFECT_EVALUATION).navigation();
                break;
        }
    }

    @Override
    public void setOnListClick(int arrayPos, int position) {
        switch (arrayPos) {
            case 0: // 必修课
//                ARouter.getInstance().build(ARoutePath.PATH_COURSE_DETAIL)
//                        .withString("title", "必修课").navigation();
                ARouter.getInstance().build(ARoutePath.PATH_COURSE_VIDEO_DETAIL)
                        .withString("title", "必修课").navigation();
                break;
            case 1: // 选修课
                ARouter.getInstance().build(ARoutePath.PATH_COURSE_DETAIL)
                        .withString("title", "选修课").navigation();
                break;
            case 2: // 微党课
                ARouter.getInstance().build(ARoutePath.PATH_COURSE_DETAIL)
                        .withString("title", "微党课").navigation();
                break;
        }
    }

    @Override
    public void setOnMoreClick(int arrayPos, String title) {
        switch (arrayPos) {
            case 0: // 必修课
                ARouter.getInstance().build(ARoutePath.PATH_COMPULSORY_COURSE).navigation();
                break;
            case 1: // 选修课
            case 2: // 微党课
                ARouter.getInstance().build(ARoutePath.PATH_NEWS_COMMON)
                        .withString("from", "StudyCoursewareActivity")
                        .withString("title", title).navigation();
                break;
        }
    }

    @Override
    public void setOnStudyRecordClick() {
        ARouter.getInstance().build(ARoutePath.PATH_NEWS_COMMON)
                .withString("from", "StudyRecordActivity")  // 该Activity并不存在，只用来区别 学习记录
                .withString("title", this.getResources().getString(R.string.title_study_record)).navigation();
    }

    private void initRecyclerView() {
        DelegateAdapter delegateAdapter = mPresenter.initRecyclerView(mRecyclerView);

        //初始化头部
        BaseDelegateAdapter headerAdapter = mPresenter.initHeader(
                "http://imgtu.5011.net/uploads/content/20170220/9520371487578487.jpg",
                "はたけ·カカシ", 17, 2, 18);
        mAdapters.add(headerAdapter);

        //初始化九宫格
        BaseDelegateAdapter menuAdapter = mPresenter.initGvMenu();
        mAdapters.add(menuAdapter);

        //初始化标题 - 必修课
        BaseDelegateAdapter titleAdapter = mPresenter.initTitle("必修课", 0);
        mAdapters.add(titleAdapter);
        //初始化list
        BaseDelegateAdapter listAdapter = mPresenter.initList(0);
        mAdapters.add(listAdapter);

        //初始化标题 - 选修课
        titleAdapter = mPresenter.initTitle("选修课", 1);
        mAdapters.add(titleAdapter);
        //初始化list
        listAdapter = mPresenter.initList(1);
        mAdapters.add(listAdapter);

        //初始化标题 - 微党课
        titleAdapter = mPresenter.initTitle("微党课", 2);
        mAdapters.add(titleAdapter);
        //初始化list
        listAdapter = mPresenter.initList(2);
        mAdapters.add(listAdapter);

        //设置适配器
        delegateAdapter.setAdapters(mAdapters);
    }

    private void initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        mRefreshLayout.setEnableLoadmore(false);
//        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
//            }
//        });
    }
}
