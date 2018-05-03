package com.mytv.rtzhdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mytv.rtzhdj.app.data.BaseJson;
import com.mytv.rtzhdj.app.data.api.service.CommentService;
import com.mytv.rtzhdj.app.data.entity.CommentEntity;
import com.mytv.rtzhdj.mvp.contract.CommentContract;

import java.util.List;

import io.reactivex.Observable;


@ActivityScope
public class CommentModel extends BaseModel implements CommentContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public CommentModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<List<CommentEntity>>> getCommentList(int nodeId, int contentId, boolean update) {
        return mRepositoryManager.obtainRetrofitService(CommentService.class)
                .getCommentList(nodeId, contentId);
    }
}