package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.NewsAllService;
import com.mytv.rtzhdj.app.data.entity.NewsAllEntity;
import com.mytv.rtzhdj.app.data.entity.NewsDetailEntity;
import com.mytv.rtzhdj.mvp.contract.NewsAllContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class NewsAllModel extends BaseModel implements NewsAllContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public NewsAllModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<NewsAllEntity>> getTwoLevelAllList(int currentSystemId, int nodeId, int pageIndex, int pageSize, boolean update) {
        return mRepositoryManager.obtainRetrofitService(NewsAllService.class)
                .getTwoLevelAllList(currentSystemId, nodeId, pageIndex, pageSize);
    }

    @Override
    public Observable<BaseJson<List<NewsDetailEntity>>> getTwoLevelInfoList(int currentSystemId, int nodeId, int pageIndex, int pageSize, boolean update) {
        return mRepositoryManager.obtainRetrofitService(NewsAllService.class)
                .getTwoLevelInfoList(currentSystemId, nodeId, pageIndex, pageSize);
    }
}