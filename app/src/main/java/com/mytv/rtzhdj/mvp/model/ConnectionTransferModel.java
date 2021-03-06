package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.cache.ConnectionTransferCache;
import com.mytv.rtzhdj.app.data.api.cache.HomeCache;
import com.mytv.rtzhdj.app.data.api.service.ConnectionTransferService;
import com.mytv.rtzhdj.app.data.api.service.HomeService;
import com.mytv.rtzhdj.app.data.api.service.RegisterService;
import com.mytv.rtzhdj.app.data.entity.HomeEntity;
import com.mytv.rtzhdj.app.data.entity.StationEntity;
import com.mytv.rtzhdj.mvp.contract.ConnectionTransferContract;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class ConnectionTransferModel extends BaseModel implements ConnectionTransferContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public ConnectionTransferModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson> getOrganizationalChange(int publishmentsystemId, String reason, int userId, boolean update) {
        return mRepositoryManager.obtainRetrofitService(ConnectionTransferService.class)
                .getOrganizationalChange(publishmentsystemId, reason, userId);
    }

    @Override
    public Observable<BaseJson<List<StationEntity>>> postAllPublishmentSystem(boolean update) {
        return mRepositoryManager.obtainRetrofitService(ConnectionTransferService.class)
                .postAllPublishmentSystem();
    }
}