package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.JoinService;
import com.mytv.rtzhdj.app.data.api.service.WishWallService;
import com.mytv.rtzhdj.app.data.entity.MyWishEntity;
import com.mytv.rtzhdj.mvp.contract.WishWallContract;
import com.mytv.rtzhdj.mvp.ui.activity.WishWallActivity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


@ActivityScope
public class WishWallModel extends BaseModel implements WishWallContract.Model {
    private Gson mGson;
    private Application mApplication;

    private WishWallActivity mActivity;

    @Inject
    public WishWallModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<List<MyWishEntity>>> postMyWishList(int userId, int type, boolean update) {
        return mRepositoryManager.obtainRetrofitService(WishWallService.class)
                .postMyWishList(userId, type);
    }

    @Override
    public Observable<BaseJson> postMyWish(Map<String, RequestBody> params, List<MultipartBody.Part> parts) {
        return mRepositoryManager.obtainRetrofitService(WishWallService.class)
                .postMyWish(params, parts);
    }
}