package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.ContentService;
import com.mytv.rtzhdj.app.data.api.service.WishDetailService;
import com.mytv.rtzhdj.app.data.entity.WishDetailEntity;
import com.mytv.rtzhdj.mvp.contract.WishDetailContract;

import io.reactivex.Observable;


@ActivityScope
public class WishDetailModel extends BaseModel implements WishDetailContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public WishDetailModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<WishDetailEntity>> postWishDetail(int wishId, boolean update) {
        return mRepositoryManager.obtainRetrofitService(WishDetailService.class)
                .postWishDetail(wishId);
    }
}