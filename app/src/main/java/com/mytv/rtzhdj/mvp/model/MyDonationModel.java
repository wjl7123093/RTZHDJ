package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.MyDonationService;
import com.mytv.rtzhdj.app.data.entity.MyDonateEntity;
import com.mytv.rtzhdj.mvp.contract.MyDonationContract;

import java.util.List;

import io.reactivex.Observable;


@ActivityScope
public class MyDonationModel extends BaseModel implements MyDonationContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public MyDonationModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<List<MyDonateEntity>>> postMyClaimDonateList(int userId, int type, boolean update) {
        return mRepositoryManager.obtainRetrofitService(MyDonationService.class)
                .postMyClaimDonateList(userId, type);
    }

    @Override
    public Observable<BaseJson<List<MyDonateEntity>>> getAllDonateList(int userId, int typeId, int pageIndex, int pageSize, boolean update) {
        return mRepositoryManager.obtainRetrofitService(MyDonationService.class)
                .getAllDonateList(userId, typeId, pageIndex, pageSize);
    }
}