package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.QuestionnaireService;
import com.mytv.rtzhdj.app.data.entity.QuestionEntity;
import com.mytv.rtzhdj.mvp.contract.QuestionnaireContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class QuestionnaireModel extends BaseModel implements QuestionnaireContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public QuestionnaireModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<List<QuestionEntity>>> getTestInfo(int examinationId, boolean update) {
        return mRepositoryManager.obtainRetrofitService(QuestionnaireService.class)
                .getTestInfo(examinationId);
    }

    @Override
    public Observable<BaseJson> postTestInfo(int userID, int examinationID, int score, boolean update) {
        return mRepositoryManager.obtainRetrofitService(QuestionnaireService.class)
                .postTestInfo(userID, examinationID, score);
    }
}