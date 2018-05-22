package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.OrganizationService;
import com.mytv.rtzhdj.app.data.entity.OrganizationEntity;
import com.mytv.rtzhdj.mvp.contract.OrganizationContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class OrganizationModel extends BaseModel implements OrganizationContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public OrganizationModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<List<OrganizationEntity>>> getOrganizationActivityList(int publishmentSystemId, int meettingTypeId, boolean update) {
        return mRepositoryManager.obtainRetrofitService(OrganizationService.class)
                .getOrganizationActivityList(publishmentSystemId, meettingTypeId);
    }
}