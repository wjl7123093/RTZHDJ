package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.GradeRankService;
import com.mytv.rtzhdj.app.data.entity.GradeRankEntity;
import com.mytv.rtzhdj.mvp.contract.GradeRankContract;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class GradeRankModel extends BaseModel implements GradeRankContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public GradeRankModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<GradeRankEntity>> getTestResultList(int examinationID, int userID, boolean update) {
        return mRepositoryManager.obtainRetrofitService(GradeRankService.class)
                .getTestResultList(examinationID, userID);
    }
}