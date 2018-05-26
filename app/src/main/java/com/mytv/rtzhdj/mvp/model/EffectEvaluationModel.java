package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.CompulsoryCourseService;
import com.mytv.rtzhdj.app.data.api.service.EffectEvaluationService;
import com.mytv.rtzhdj.app.data.entity.EffectEvaluationEntity;
import com.mytv.rtzhdj.app.data.entity.HeaderIntegralEntity;
import com.mytv.rtzhdj.mvp.contract.EffectEvaluationContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class EffectEvaluationModel extends BaseModel implements EffectEvaluationContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public EffectEvaluationModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<List<EffectEvaluationEntity>>> getTestList(int userId, int testState,
                                                                          int pageIndex, int pageSize, boolean update) {
        return mRepositoryManager.obtainRetrofitService(EffectEvaluationService.class)
                .getTestList(userId, testState, pageIndex, pageSize);
    }

    @Override
    public Observable<BaseJson<HeaderIntegralEntity>> getMyScore(int userId) {
        return mRepositoryManager.obtainRetrofitService(CompulsoryCourseService.class)
                .getMyScore(userId);
    }
}