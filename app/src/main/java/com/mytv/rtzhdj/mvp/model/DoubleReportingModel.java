package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.DoubleReportingService;
import com.mytv.rtzhdj.app.data.api.service.RegisterService;
import com.mytv.rtzhdj.app.data.entity.PartyRecommendEntity;
import com.mytv.rtzhdj.app.data.entity.StationEntity;
import com.mytv.rtzhdj.mvp.contract.DoubleReportingContract;

import java.util.List;

import io.reactivex.Observable;


@ActivityScope
public class DoubleReportingModel extends BaseModel implements DoubleReportingContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public DoubleReportingModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson> postPersonalReach(int userId, int publishmentSystemId, boolean update) {
        return mRepositoryManager.obtainRetrofitService(DoubleReportingService.class)
                .postPersonalReach(userId, publishmentSystemId);
    }

    @Override
    public Observable<BaseJson<List<StationEntity>>> postAllPublishmentSystem(boolean update) {
        return mRepositoryManager.obtainRetrofitService(RegisterService.class)
                .postAllPublishmentSystem();
    }
}