package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.ScoresDetailsService;
import com.mytv.rtzhdj.app.data.entity.ScoresDetailsEntity;
import com.mytv.rtzhdj.mvp.contract.ScoresDetailsContract;

import io.reactivex.Observable;


@ActivityScope
public class ScoresDetailsModel extends BaseModel implements ScoresDetailsContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public ScoresDetailsModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<ScoresDetailsEntity>> postMyScore(int userId, boolean update) {
        return mRepositoryManager.obtainRetrofitService(ScoresDetailsService.class)
                .postMyScore(userId);
    }
}