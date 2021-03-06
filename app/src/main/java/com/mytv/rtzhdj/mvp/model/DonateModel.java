package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.DonateService;
import com.mytv.rtzhdj.mvp.contract.DonateContract;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


@ActivityScope
public class DonateModel extends BaseModel implements DonateContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public DonateModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson> postDonateSubmit(Map<String, RequestBody> params, List<MultipartBody.Part> parts) {
        return mRepositoryManager.obtainRetrofitService(DonateService.class)
                .postDonateSubmit(params, parts);
    }
}