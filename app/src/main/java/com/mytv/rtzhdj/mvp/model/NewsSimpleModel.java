package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.NewsSimpleService;
import com.mytv.rtzhdj.app.data.entity.NewsDetailEntity;
import com.mytv.rtzhdj.app.data.entity.NewsSimpleEntity;
import com.mytv.rtzhdj.mvp.contract.NewsSimpleContract;

import java.util.List;

import io.reactivex.Observable;


@ActivityScope
public class NewsSimpleModel extends BaseModel implements NewsSimpleContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public NewsSimpleModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<NewsSimpleEntity>> getTwoLevelList(int nodeId, int pageIndex, int pageSize, boolean update) {
        return mRepositoryManager.obtainRetrofitService(NewsSimpleService.class)
                .getTwoLevelList(nodeId, pageIndex, pageSize);
    }

    @Override
    public Observable<BaseJson<List<NewsDetailEntity>>> getTwoLevelInfoList(int nodeId, int pageIndex, int pageSize, boolean update) {
        return mRepositoryManager.obtainRetrofitService(NewsSimpleService.class)
                .getTwoLevelInfoList(nodeId, pageIndex, pageSize);
    }
}