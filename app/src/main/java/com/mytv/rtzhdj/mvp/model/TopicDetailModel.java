package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.NewsDetailService;
import com.mytv.rtzhdj.app.data.api.service.TopicDetailService;
import com.mytv.rtzhdj.app.data.entity.SpecialColumnsEntity;
import com.mytv.rtzhdj.mvp.contract.TopicDetailContract;

import io.reactivex.Observable;


@ActivityScope
public class TopicDetailModel extends BaseModel implements TopicDetailContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public TopicDetailModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<SpecialColumnsEntity>> getSpecialClass(int nodeId, boolean update) {
        return mRepositoryManager.obtainRetrofitService(TopicDetailService.class)
                .getSpecialClass(nodeId);
    }
}