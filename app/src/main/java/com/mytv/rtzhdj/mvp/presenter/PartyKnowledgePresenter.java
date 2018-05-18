package com.mytv.rtzhdj.mvp.presenter;

import android.app.Application;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.mytv.rtzhdj.app.base.RTZHDJApplication;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.entity.PartyColumnsEntity;
import com.mytv.rtzhdj.app.data.entity.PartyNewsEntity;
import com.mytv.rtzhdj.mvp.contract.PartyKnowledgeContract;
import com.mytv.rtzhdj.mvp.ui.activity.PartyKnowledgeActivity;
import com.mytv.rtzhdj.mvp.ui.decoration.DividerItemDecoration;
import com.zchu.rxcache.data.CacheResult;
import com.zchu.rxcache.stategy.CacheStrategy;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class PartyKnowledgePresenter extends BasePresenter<PartyKnowledgeContract.Model, PartyKnowledgeContract.View>
    implements PartyKnowledgeContract.Presenter {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private PartyKnowledgeActivity mActivity;

    @Inject
    public PartyKnowledgePresenter(PartyKnowledgeContract.Model model, PartyKnowledgeContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    @Override
    public void setActivity(PartyKnowledgeActivity activity) {
        mActivity = activity;
    }

    @Override
    public RecyclerView initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setHasFixedSize(true);
        //设置item间距，1dp
        recyclerView.addItemDecoration(new DividerItemDecoration(mActivity,
                LinearLayoutManager.VERTICAL, ArmsUtils.dip2px(mActivity, 1)));
        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);

        return recyclerView;
    }

    @Override
    public List<PartyColumnsEntity> initColums() {
        List<PartyColumnsEntity> partyColumnsList = new ArrayList<>();
        PartyColumnsEntity columnsEntity = new PartyColumnsEntity();
        columnsEntity.setTitle("组织工作");
        columnsEntity.setNodeId(9051);
        partyColumnsList.add(columnsEntity);
        PartyColumnsEntity columnsEntity1 = new PartyColumnsEntity();
        columnsEntity1.setTitle("干部工作");
        columnsEntity1.setNodeId(9052);
        partyColumnsList.add(columnsEntity1);
        PartyColumnsEntity columnsEntity2 = new PartyColumnsEntity();
        columnsEntity2.setTitle("支部工作");
        columnsEntity2.setNodeId(9053);
        partyColumnsList.add(columnsEntity2);

        return partyColumnsList;
    }

    @Override
    public void callMethodOfGetPartyKnowledgeList(int nodeId, int pageIndex, int pageSize, boolean update) {
        mModel.getPartyKnowledgeList(nodeId, pageIndex, pageSize, update)
                .compose(RTZHDJApplication.rxCache.<BaseJson<List<PartyNewsEntity>>>transformObservable("getPartyKnowledgeList" + nodeId,
                        new TypeToken<BaseJson<List<PartyNewsEntity>>>() { }.getType(),
                        CacheStrategy.firstCache()))
                .map(new CacheResult.MapFunc<BaseJson<List<PartyNewsEntity>>>())
                .retryWhen(new RetryWithDelay(3, 2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                })
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<List<PartyNewsEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull BaseJson<List<PartyNewsEntity>> partyKnowledgeLsit) {
                        Log.e("TAG", partyKnowledgeLsit.toString());

                        if (partyKnowledgeLsit.isSuccess() && partyKnowledgeLsit.getData() != null)
                            mRootView.initAdapter(partyKnowledgeLsit.getData());

                    }
                });
    }
}
