package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.PartyMemberDetailService;
import com.mytv.rtzhdj.app.data.entity.PartyMemberEntity;
import com.mytv.rtzhdj.app.data.entity.PartyMienEntity;
import com.mytv.rtzhdj.mvp.contract.PartyMemberDetailContract;

import io.reactivex.Observable;


@ActivityScope
public class PartyMemberDetailModel extends BaseModel implements PartyMemberDetailContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public PartyMemberDetailModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<PartyMienEntity>> getPartyMemberDetail(int memberId, boolean update) {
        return mRepositoryManager.obtainRetrofitService(PartyMemberDetailService.class)
                .getPartyMemberDetail(memberId);
    }
}