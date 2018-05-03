package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.VoteEntryDetailService;
import com.mytv.rtzhdj.app.data.entity.VoteEntrylEntity;
import com.mytv.rtzhdj.mvp.contract.VoteEntryDetailContract;

import io.reactivex.Observable;


@ActivityScope
public class VoteEntryDetailModel extends BaseModel implements VoteEntryDetailContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public VoteEntryDetailModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<VoteEntrylEntity>> postOnlineVoteDetails(int id, boolean update) {
        return mRepositoryManager.obtainRetrofitService(VoteEntryDetailService.class)
                .postOnlineVoteDetails(id);
    }
}