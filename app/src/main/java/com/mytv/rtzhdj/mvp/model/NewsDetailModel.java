package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.NewsDetailService;
import com.mytv.rtzhdj.app.data.entity.NewsDetailEntity;
import com.mytv.rtzhdj.mvp.contract.NewsDetailContract;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class NewsDetailModel extends BaseModel implements NewsDetailContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public NewsDetailModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<NewsDetailEntity>> getContent(int currentSystemId, int id, int nodeId, boolean update) {
        return mRepositoryManager.obtainRetrofitService(NewsDetailService.class)
                .getContent(currentSystemId, id, nodeId);
    }

    @Override
    public Observable<BaseJson> postComment(int userId, int nodeId, int contentId, String commentInfo, boolean update) {
        return mRepositoryManager.obtainRetrofitService(NewsDetailService.class)
                .postComment(userId, nodeId, contentId, commentInfo);
    }

    @Override
    public Observable<BaseJson> postDoDig(int currentSystemId, int nodeId, int contentId, int type, boolean update) {
        return mRepositoryManager.obtainRetrofitService(NewsDetailService.class)
                .postDoDig(currentSystemId, nodeId, contentId, type);
    }
}