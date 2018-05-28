package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.ContentService;
import com.mytv.rtzhdj.app.data.entity.PartySubNewsEntity;
import com.mytv.rtzhdj.mvp.contract.NewsEducationSubContract;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class NewsEducationSubModel extends BaseModel implements NewsEducationSubContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public NewsEducationSubModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<PartySubNewsEntity>> getPartySubList(int nodeId, int pageIndex, int pageSize, boolean update) {
        return mRepositoryManager.obtainRetrofitService(ContentService.class)
                .getPartySubList(nodeId, pageIndex, pageSize);
    }
}