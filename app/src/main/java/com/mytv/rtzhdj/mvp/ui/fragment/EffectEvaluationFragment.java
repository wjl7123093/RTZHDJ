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
import com.mytv.rtzhdj.app.data.entity.EffectEvaluationEntity;
import com.mytv.rtzhdj.app.data.entity.HeaderIntegralEntity;
import com.mytv.rtzhdj.di.component.DaggerEffectEvaluationComponent;
import com.mytv.rtzhdj.di.module.EffectEvaluationModule;
import com.mytv.rtzhdj.mvp.contract.EffectEvaluationContract;
import com.mytv.rtzhdj.mvp.presenter.EffectEvaluationPresenter;
import com.mytv.rtzhdj.mvp.ui.adapter.EffectEvaluationAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * EffectEvaluationFragment 效果测评页(二级Fragment) Fragment
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-27
 * @update
 */
public class EffectEvaluationFragment extends BaseFragment<EffectEvaluationPresenter> implements EffectEvaluationContract.View {

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private EffectEvaluationAdapter mAdapter;
    private static final int PAGE_SIZE = 10;
    private int PAGE_INDEX = 1;
    private int mCurPos = 0;    // 当前列表末节点位置
    private List<EffectEvaluationEntity> mEffectList = new ArrayList<>();
    private boolean mIsLoadMore = false;
    private boolean mIsRefresh = false;

    /** 加载进度条 */
    private SweetAlertDialog pDialog;


    public static EffectEvaluationFragment newInstance() {
        EffectEvaluationFragment fragment = new EffectEvaluationFragment();
        return fragment;
    }

    public static EffectEvaluationFragment newInstance(int testState) {
        EffectEvaluationFragment fragment = new EffectEvaluationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("testState", testState);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerEffectEvaluationComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .effectEvaluationModule(new EffectEvaluationModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_effect_evaluation, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        mPresenter.setActivity(getActivity());
        mRecyclerView = mPresenter.initRecyclerView(mRecyclerView);
//        initAdapter();
        initRefreshLayout();

        PAGE_INDEX = 1;
        // 获取 效果测评列表数据
        mPresenter.callMethodOfGetTestList(
                DataHelper.getIntergerSF(getActivity(), SharepreferenceKey.KEY_USER_ID),
                getArguments().getInt("testState"), PAGE_INDEX, PAGE_SIZE, false);
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

    private void initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败

                mIsRefresh = true;
                PAGE_INDEX = 1;
                // 获取 效果测评列表数据
                mPresenter.callMethodOfGetTestList(
                        DataHelper.getIntergerSF(getActivity(), SharepreferenceKey.KEY_USER_ID),
                        getArguments().getInt("testState"), PAGE_INDEX, PAGE_SIZE, true);
            }
        });
//        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败

                mIsLoadMore = true;
                // 获取 效果测评列表数据
                mPresenter.callMethodOfGetTestList(
                        DataHelper.getIntergerSF(getActivity(), SharepreferenceKey.KEY_USER_ID),
                        getArguments().getInt("testState"), ++PAGE_INDEX, PAGE_SIZE, true);
            }
        });
    }

    private void initAdapter(List<EffectEvaluationEntity> effectList, boolean update) {
        if (update) {
            if (mIsRefresh) {  // 下拉刷新
                // 1. 先移除
                mAdapter.notifyItemRangeRemoved(0, mEffectList.size());
                // 2. 再清空
                mEffectList.clear();

                mRefreshLayout.finishRefresh(true);
                mIsRefresh = false;
            } else {    // 上拉加载
                mRefreshLayout.finishLoadmore(true);
                mIsLoadMore = false;
            }
        }

        mCurPos = mEffectList.size();
        if (null == mAdapter) {
            mEffectList = effectList;
            mAdapter = new EffectEvaluationAdapter(getContext(), effectList);
            mAdapter.openLoadAnimation();
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mEffectList.addAll(effectList);
            mAdapter.notifyItemRangeInserted(mCurPos, effectList.size());
        }

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Toast.makeText(getContext(), "" + Integer.toString(position), Toast.LENGTH_LONG).show();

                if (mEffectList.get(position).getIfOver() == 0
                        && (mEffectList.get(position).getTestAlready() - mEffectList.get(position).getExaminationCount() > 0)) {
                    // 跳转到 问卷调查页面
                    ARouter.getInstance().build(ARoutePath.PATH_QUESTIONAIRE)
                            .withInt("examinationId", mEffectList.get(position).getId())
                            .withInt("examinationTime", mEffectList.get(position).getExaminationTime()).navigation();
                } else {
                    showMessage("该次评测已经结束");
                }
            }
        });

    }

    @Override
    public void loadData(List<EffectEvaluationEntity> effectList, boolean update) {
        initAdapter(effectList, update);
    }

    @Override
    public void loadHeaderData(HeaderIntegralEntity headerIntegralEntity) {

    }
}
