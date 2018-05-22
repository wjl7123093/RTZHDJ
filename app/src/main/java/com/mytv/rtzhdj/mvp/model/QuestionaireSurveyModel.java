package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.QuestionnaireSurveyService;
import com.mytv.rtzhdj.app.data.entity.QuestionEntity;
import com.mytv.rtzhdj.mvp.contract.QuestionaireSurveyContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class QuestionaireSurveyModel extends BaseModel implements QuestionaireSurveyContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public QuestionaireSurveyModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<List<QuestionEntity>>> getSurveyDetailList(int examinationId, boolean update) {
        return mRepositoryManager.obtainRetrofitService(QuestionnaireSurveyService.class)
                .getSurveyDetailList(examinationId);
    }

    @Override
    public Observable<BaseJson> postSurveyInfo(int userID, String answerString, boolean update) {
        return mRepositoryManager.obtainRetrofitService(QuestionnaireSurveyService.class)
                .postSurveyInfo(userID, answerString);
    }
}