package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.ReportCommunityService;
import com.mytv.rtzhdj.app.data.entity.ReportCommunityEntity;
import com.mytv.rtzhdj.mvp.contract.ReportCommunityContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class ReportCommunityModel extends BaseModel implements ReportCommunityContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public ReportCommunityModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<List<ReportCommunityEntity>>> getPartOrgList(int userId, boolean update) {
        return mRepositoryManager.obtainRetrofitService(ReportCommunityService.class)
                .getPartOrgList(userId);
    }
}