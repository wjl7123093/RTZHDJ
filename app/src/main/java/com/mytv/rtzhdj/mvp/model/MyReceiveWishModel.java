package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.MyReceiveWishService;
import com.mytv.rtzhdj.app.data.entity.MyWishEntity;
import com.mytv.rtzhdj.mvp.contract.MyReceiveWishContract;

import java.util.List;

import io.reactivex.Observable;


@ActivityScope
public class MyReceiveWishModel extends BaseModel implements MyReceiveWishContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public MyReceiveWishModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<List<MyWishEntity>>> postMyClaimWishList(int userId, int type, boolean update) {
        return mRepositoryManager.obtainRetrofitService(MyReceiveWishService.class)
                .postMyClaimWishList(userId, type);
    }
}