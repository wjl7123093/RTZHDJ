package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.cache.JoinCache;
import com.mytv.rtzhdj.app.data.api.service.JoinService;
import com.mytv.rtzhdj.app.data.entity.MyJoinEntity;
import com.mytv.rtzhdj.mvp.contract.JoinContract;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class JoinModel extends BaseModel implements JoinContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public JoinModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseJson<MyJoinEntity>> getMyPartIn(int userId, int pageIndex, int pageSize, boolean update) {
        return mRepositoryManager.obtainRetrofitService(JoinService.class)
                .getMyPartIn(userId, pageIndex, pageSize);
    }
}