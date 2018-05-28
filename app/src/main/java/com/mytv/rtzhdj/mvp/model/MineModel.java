package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.MineService;
import com.mytv.rtzhdj.app.data.entity.MineEntity;
import com.mytv.rtzhdj.mvp.contract.MineContract;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class MineModel extends BaseModel implements MineContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public MineModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<MineEntity>> getUserPartMessage(int userId, boolean update) {
        return mRepositoryManager.obtainRetrofitService(MineService.class)
                .getUserPartMessage(userId);
    }
}