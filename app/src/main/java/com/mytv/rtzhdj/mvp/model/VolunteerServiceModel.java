package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.VolunteerService;
import com.mytv.rtzhdj.app.data.entity.VoluteerServiceEntity;
import com.mytv.rtzhdj.mvp.contract.VolunteerServiceContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class VolunteerServiceModel extends BaseModel implements VolunteerServiceContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public VolunteerServiceModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<List<VoluteerServiceEntity>>> getVoluntaryserviceList(int currentSystemId,
                                                                                     int typeId, int pageIndex, int pageSize, boolean update) {
        return mRepositoryManager.obtainRetrofitService(VolunteerService.class)
                .getVoluntaryserviceList(currentSystemId, typeId, pageIndex, pageSize);
    }
}