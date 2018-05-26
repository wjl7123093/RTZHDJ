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
import com.mytv.rtzhdj.app.data.entity.QuestionOnlineEntity;
import com.mytv.rtzhdj.di.component.DaggerQuestionOnlineComponent;
import com.mytv.rtzhdj.di.module.QuestionOnlineModule;
import com.mytv.rtzhdj.mvp.contract.QuestionOnlineContract;
import com.mytv.rtzhdj.mvp.presenter.QuestionOnlinePresenter;
import com.mytv.rtzhdj.mvp.ui.activity.QuestionOnlineActivity;
import com.mytv.rtzhdj.mvp.ui.adapter.QuestionAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class QuestionOnlineFragment extends BaseFragment<QuestionOnlinePresenter> implements QuestionOnlineContract.View {

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private QuestionAdapter questionAdapter;
    private static final int PAGE_SIZE = 10;

    /** 加载进度条 */
    private SweetAlertDialog pDialog;


    public static QuestionOnlineFragment newInstance() {
        QuestionOnlineFragment fragment = new QuestionOnlineFragment();
        return fragment;
    }

    public static QuestionOnlineFragment newInstance(int typeId) {
        QuestionOnlineFragment fragment = new QuestionOnlineFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("typeId", typeId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerQuestionOnlineComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .questionOnlineModule(new QuestionOnlineModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question_online, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        mPresenter.setActivity((QuestionOnlineActivity)getActivity());
        mRecyclerView = mPresenter.initRecyclerView(mRecyclerView);
//        initAdapter();
        initRefreshLayout();

        // 获取 在线问卷调查
        mPresenter.callMethodOfGetSurveyList(DataHelper.getIntergerSF(getActivity(),
                SharepreferenceKey.KEY_PUBLISHMENT_SYSTEM_ID), getArguments().getInt("typeId"), false);

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
    public void loadData(List<QuestionOnlineEntity> questionList) {
        initAdapter(questionList);
    }

    private void initRefreshLayout() {
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadmore(false);
    }

    private void initAdapter(List<QuestionOnlineEntity> questionList) {
        questionAdapter = new QuestionAdapter(getActivity(), questionList);
        questionAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(questionAdapter);

        questionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showMessage("" + questionList.get(position).getID());

                ARouter.getInstance().build(ARoutePath.PATH_QUESTIONAIRE_SURVEY)
                        .withInt("onlineSurveyId", questionList.get(position).getID()).navigation();
            }
        });

    }
}
