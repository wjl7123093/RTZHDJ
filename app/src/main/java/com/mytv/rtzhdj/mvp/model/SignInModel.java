package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.SigninService;
import com.mytv.rtzhdj.app.data.entity.SignEntity;
import com.mytv.rtzhdj.mvp.contract.SignInContract;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class SignInModel extends BaseModel implements SignInContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public SignInModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson> postSignForScore(int userId) {
        return mRepositoryManager.obtainRetrofitService(SigninService.class)
                .postSignForScore(userId);
    }

    @Override
    public Observable<BaseJson<SignEntity>> getSignList(int userId) {
        return mRepositoryManager.obtainRetrofitService(SigninService.class)
                .getSignList(userId);
    }
}