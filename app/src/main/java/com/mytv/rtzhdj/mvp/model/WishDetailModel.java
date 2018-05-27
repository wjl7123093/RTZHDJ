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
import com.mytv.rtzhdj.app.data.api.service.WishWallService;
import com.mytv.rtzhdj.app.data.entity.WishDetailEntity;
import com.mytv.rtzhdj.mvp.contract.WishDetailContract;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


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

    @Override
    public Observable<BaseJson> postEditMyWish(Map<String, RequestBody> params, List<MultipartBody.Part> parts) {
        return mRepositoryManager.obtainRetrofitService(WishDetailService.class)
                .postEditMyWish(params, parts);
    }

    @Override
    public Observable<BaseJson> postDeleteMyWish(int wishId) {
        return mRepositoryManager.obtainRetrofitService(WishDetailService.class)
                .postDeleteMyWish(wishId);
    }
}