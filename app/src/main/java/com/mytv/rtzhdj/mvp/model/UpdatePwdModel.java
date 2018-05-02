package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.UpdatePwdService;
import com.mytv.rtzhdj.mvp.contract.UpdatePwdContract;

import io.reactivex.Observable;


@ActivityScope
public class UpdatePwdModel extends BaseModel implements UpdatePwdContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public UpdatePwdModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson> postUpdatePassword(int userId, String oldPwd, String newPwd) {
        return mRepositoryManager.obtainRetrofitService(UpdatePwdService.class)
                .postUpdatePassword(userId, oldPwd, newPwd);
    }
}