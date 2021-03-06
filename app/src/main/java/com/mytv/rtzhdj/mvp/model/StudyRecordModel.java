package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.StudyRecordService;
import com.mytv.rtzhdj.app.data.entity.StudyRecordEntity;
import com.mytv.rtzhdj.mvp.contract.StudyRecordContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class StudyRecordModel extends BaseModel implements StudyRecordContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public StudyRecordModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<List<StudyRecordEntity>>> getLearningRecords(int userId, boolean update) {
        return mRepositoryManager
                .obtainRetrofitService(StudyRecordService.class)
                .getLearningRecords(userId);
    }
}