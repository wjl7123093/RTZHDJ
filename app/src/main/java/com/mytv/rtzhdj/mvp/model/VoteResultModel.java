package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.VoteResultService;
import com.mytv.rtzhdj.app.data.entity.VoteDetailEntity;
import com.mytv.rtzhdj.mvp.contract.VoteResultContract;

import java.util.List;

import io.reactivex.Observable;


@ActivityScope
public class VoteResultModel extends BaseModel implements VoteResultContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public VoteResultModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<List<VoteDetailEntity>>> postOnlineVoteResult(int id, boolean update) {
        return mRepositoryManager.obtainRetrofitService(VoteResultService.class)
                .postOnlineVoteResult(id);
    }
}