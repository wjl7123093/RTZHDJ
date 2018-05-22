package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.NewsDetailService;
import com.mytv.rtzhdj.app.data.api.service.VolunteerServiceDetailService;
import com.mytv.rtzhdj.app.data.entity.VolunteerDetailEntity;
import com.mytv.rtzhdj.mvp.contract.VolunteerServiceDetailContract;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class VolunteerServiceDetailModel extends BaseModel implements VolunteerServiceDetailContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public VolunteerServiceDetailModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<VolunteerDetailEntity>> getVolunteerServiceDetail(int id, int userId, boolean update) {
        return mRepositoryManager.obtainRetrofitService(VolunteerServiceDetailService.class)
                .getVolunteerServiceDetail(id, userId);
    }

    @Override
    public Observable<BaseJson> postDoDig(int nodeId, int contentId, boolean update) {
        return mRepositoryManager.obtainRetrofitService(NewsDetailService.class)
                .postDoDig(nodeId, contentId);
    }
}